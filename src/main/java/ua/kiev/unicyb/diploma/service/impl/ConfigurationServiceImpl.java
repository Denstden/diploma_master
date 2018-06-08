package ua.kiev.unicyb.diploma.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.kiev.unicyb.diploma.domain.entity.configuration.ConfigurationEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.domain.generated.GlobalConfig;
import ua.kiev.unicyb.diploma.exception.UploadFileException;
import ua.kiev.unicyb.diploma.parser.ConfigurationParser;
import ua.kiev.unicyb.diploma.repositories.ConfigurationRepository;
import ua.kiev.unicyb.diploma.repositories.UserRepository;
import ua.kiev.unicyb.diploma.service.ConfigurationService;
import ua.kiev.unicyb.diploma.service.TestGenerationService;
import ua.kiev.unicyb.diploma.service.ZipService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConfigurationServiceImpl implements ConfigurationService {
    private static final String ZIP_CONTENT_TYPE = "application/zip";
    private static final String ZIP_EXTENSION = "zip";
    private static final String XML_EXTENSION = ".xml";
    private static final String CONFIG_FOLDER_NAME_SEPARATOR = "~^";

    private final ZipService zipService;
    private final TestGenerationService testGenerationService;
    private final ConfigurationRepository configurationRepository;
    private final UserRepository userRepository;
    private final ConfigurationParser configurationParser;

    @Value("${xml.test.configuration.folder}")
    private String configurationFolder;

    @Value("${load.default.configuration.files}")
    private Boolean needDefaultInit;

    @Autowired
    public ConfigurationServiceImpl(final ZipService zipService, final TestGenerationService testGenerationService,
                                    final ConfigurationRepository configurationRepository, final UserRepository userRepository,
                                    final ConfigurationParser configurationParser) {
        this.zipService = zipService;
        this.testGenerationService = testGenerationService;
        this.configurationRepository = configurationRepository;
        this.userRepository = userRepository;
        this.configurationParser = configurationParser;
    }

    /*@PostConstruct
    public void loadConfigurations() throws JAXBException, IOException {
        if (needDefaultInit) {
            loadInitialConfiguration("config1.xml");
            loadInitialConfiguration("config_control1.xml");
        }
    }

    private void loadInitialConfiguration(String configFile) throws JAXBException, IOException {
        final GlobalConfig config = configurationParser.parseGlobal(configurationFolder + "\\" + configFile);
        testGenerationService.loadConfig(config);
    }*/

    @Override
    public void loadFile(final MultipartFile file, String configFileName, final String username) throws UploadFileException, IOException {
//        checkContentType(file.getContentType());
        checkExtension(file.getOriginalFilename());

        final String configurationName = generateConfigurationName(file.getOriginalFilename());
        log.debug("Generated configuration name for zip-archive {}", configurationName);

        final String configurationFolder = this.configurationFolder + "/" + configurationName;
        createConfigurationFolderIfNotExists(configurationFolder);

        log.debug("Starting unzipping file {}", file.getOriginalFilename());
        zipService.unzip(file.getInputStream(), configurationFolder);
        log.debug("Unzipping finished");

        createAndSaveConfigurationEntity(file.getOriginalFilename(), configFileName, username, configurationName, configurationFolder);
    }

    @Override
    public TestEntity loadConfiguration(Long configurationId) {
        final ConfigurationEntity configuration = configurationRepository.findOne(configurationId);
        final String pathToConfigFile = getPathToConfigFile(configuration);

        final GlobalConfig config = configurationParser.parseGlobal(pathToConfigFile);

        final TestEntity testEntity = testGenerationService.loadConfig(config, pathToConfigFile);
        configuration.setIsLoaded(true);
        configurationRepository.save(configuration);

        return testEntity;
    }

    private void createAndSaveConfigurationEntity(String originalFileName, String configFileName, String username, String configurationName, String configurationFolder) {
        final ConfigurationEntity configurationEntity = new ConfigurationEntity();
        configurationEntity.setPathToFolder(configurationFolder);
        configurationEntity.setFolderName(configurationName);

        configurationEntity.setOriginalFileName(getWithoutExtension(originalFileName));
        configFileName = (configFileName == null ? getWithoutExtension(originalFileName) + XML_EXTENSION : configFileName);
        configurationEntity.setConfigFileName(configFileName);
        configurationEntity.setUser(userRepository.findByUsername(username));

        configurationRepository.save(configurationEntity);
    }

    @Override
    public List<ConfigurationEntity> configurationList(final String username) {
        final List<ConfigurationEntity> result = new ArrayList<>();

        final List<ConfigurationEntity> userUploadedConfigurations = configurationRepository.findByUser_UsernameAndIsLoaded(username, false);
        userUploadedConfigurations.forEach(configurationEntity -> {
            final String pathToFolder = configurationEntity.getPathToFolder();
            final String originalFileName = configurationEntity.getOriginalFileName();
            final String configFileName = configurationEntity.getConfigFileName();

            if (configExistsAndCanBeParsed(pathToFolder, originalFileName, configFileName)) {
                result.add(configurationEntity);
            }

        });

        return result;
    }

    private boolean configExistsAndCanBeParsed(final String pathToFolder, final String originalFileName, final String configFileName) {
        if (pathToFolder != null && originalFileName != null && configFileName != null) {
            final String configFilePath = getPathToConfigFile(pathToFolder, originalFileName, configFileName);
            configurationParser.parseGlobal(configFilePath);
            return true;
        }
        return false;
    }

    private String getPathToConfigFile(final ConfigurationEntity configurationEntity) {
        final String pathToFolder = configurationEntity.getPathToFolder();
        final String originalFileName = configurationEntity.getOriginalFileName();
        final String configFileName = configurationEntity.getConfigFileName();
        return getPathToConfigFile(pathToFolder, originalFileName, configFileName);
    }

    private String getPathToConfigFile(String pathToFolder, String originalFileName, String configFileName) {
        final StringBuilder configFilePath = new StringBuilder(pathToFolder);
        configFilePath.append("/").append(originalFileName).append("/").append(configFileName);
        return configFilePath.toString();
    }

    /*private void processFile(List<String> result, File file) {
        final String fileName = file.getName();
        if (file.isDirectory()) {
            final int indexOfNameSeparator = fileName.indexOf(CONFIG_FOLDER_NAME_SEPARATOR);
            if (indexOfNameSeparator > 0) {
                final String configXmlFileName = fileName.substring(0, indexOfNameSeparator) + XML_EXTENSION;

                log.debug("Find config.xml file {} in the folder {}", configXmlFileName, fileName);
                final Optional<File> configXmlFileOptional = findInFolderConfigXmlFileWithName(file, configXmlFileName);

                if (canParseConfig(configXmlFileOptional)) {
                    result.add(fileName);
                }
            }
        }
    }

    private Optional<File> findInFolderConfigXmlFileWithName(File folder, String configXmlFileName) {
        final File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    return findInFolderConfigXmlFileWithName(file, configXmlFileName);
                } else {
                    if (file.getName().toLowerCase().equals(configXmlFileName)) {
                        return Optional.of(file);
                    }
                }
            }
        }
        return Optional.empty();
    }

    private boolean canParseConfig(Optional<File> configXmlFileOptional) {
        if (configXmlFileOptional.isPresent()) {
            final File configXmlFile = configXmlFileOptional.get();
            try {
                ConfigurationParser.parseGlobal(configXmlFile.getAbsolutePath());
                return true;
            } catch (JAXBException e) {
                return false;
            }
        }
        return false;
    }*/

    private void checkContentType(String contentType) throws UploadFileException {
        if (!ZIP_CONTENT_TYPE.equals(contentType)) {
            throw new UploadFileException("Content type should be " + ZIP_CONTENT_TYPE);
        }
    }

    private void checkExtension(String originalFilename) throws UploadFileException {
        if (originalFilename == null) {
            throw new UploadFileException("Name should be not empty");
        }

        final int indexOfLastPoint = originalFilename.lastIndexOf(".");
        final String extension = indexOfLastPoint > 0 ? originalFilename.substring(indexOfLastPoint + 1) : "";

        if (!ZIP_EXTENSION.equals(extension.toLowerCase())) {
            throw new UploadFileException("Extension should be zip");
        }
    }

    private String generateConfigurationName(String originalFilename) {
        final StringBuilder configurationName = new StringBuilder(getWithoutExtension(originalFilename));

        configurationName.append(CONFIG_FOLDER_NAME_SEPARATOR).append(getCurrentDateTime());
        return configurationName.toString();
    }

    private String getWithoutExtension(String originalFilename) {
        final int indexOfLastPoint = originalFilename.lastIndexOf(".");
        return indexOfLastPoint > 0 ?
                originalFilename.substring(0, indexOfLastPoint) : originalFilename;
    }

    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS"));
    }

    private void createConfigurationFolderIfNotExists(String configurationFolder) throws FileNotFoundException {
        File folder = new File(configurationFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }
}
