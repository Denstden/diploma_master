package ua.kiev.unicyb.diploma.service;

import org.springframework.web.multipart.MultipartFile;
import ua.kiev.unicyb.diploma.domain.entity.configuration.ConfigurationEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.exception.UploadFileException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface ConfigurationService {
    void loadFile(final MultipartFile file, final String configFileName, final String userId) throws UploadFileException, IOException;

    TestEntity loadConfiguration(final Long configurationId) throws JAXBException, IOException;

    List<ConfigurationEntity> configurationList(final String username);
}
