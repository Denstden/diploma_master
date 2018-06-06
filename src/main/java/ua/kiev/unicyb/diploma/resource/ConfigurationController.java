package ua.kiev.unicyb.diploma.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.kiev.unicyb.diploma.converter.TestConfigConverter;
import ua.kiev.unicyb.diploma.converter.VariantConfigConverter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;
import ua.kiev.unicyb.diploma.domain.generated.GlobalConfig;
import ua.kiev.unicyb.diploma.parser.ConfigurationParser;
import ua.kiev.unicyb.diploma.service.DatabaseService;
import ua.kiev.unicyb.diploma.service.TestGenerationService;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping(value = "/configuration")
public class ConfigurationController {

    private final TestGenerationService testGenerationService;

    @Autowired
    public ConfigurationController(final TestGenerationService testGenerationService) {
        this.testGenerationService = testGenerationService;
    }

    @Value("${xml.test.configuration.folder}")
    private String configurationFolder;

    @PostConstruct
    public void loadConfigurations() throws JAXBException {
        loadConfiguration("config1.xml");
        loadConfiguration("config_control1.xml");
    }

    @RequestMapping(value = "/loadConfiguration", method = RequestMethod.POST)
    public TestEntity loadConfiguration(@RequestParam(name = "configFile", defaultValue = "config1.xml") String configFile) throws JAXBException {
        final GlobalConfig config = ConfigurationParser.parse(configurationFolder + "\\" + configFile);

        return testGenerationService.loadConfig(config);
    }

    @RequestMapping(value = "/loadConfigurationFile", method = RequestMethod.POST)
    public ResponseEntity loadConfigurationFile(@RequestParam("file") MultipartFile file) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        if (file.getSize() > 0) {
            try {
                inputStream = file.getInputStream();
                outputStream = new FileOutputStream(configurationFolder + "\\"
                        + file.getOriginalFilename());
                log.debug("Saved configuration file with name {}", file.getOriginalFilename());
                int readBytes = 0;
                byte[] buffer = new byte[8192];
                while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
                    outputStream.write(buffer, 0, readBytes);
                }
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/configurationList", method = RequestMethod.GET)
    public List<String> configurationList() {
        File file = new File(configurationFolder);
        if (file.list() != null) {
            return Arrays.asList(file.list());
        } else {
            return new ArrayList<>();
        }
    }
}
