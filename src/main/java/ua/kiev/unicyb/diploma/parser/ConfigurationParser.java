package ua.kiev.unicyb.diploma.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.generated.Answers;
import ua.kiev.unicyb.diploma.domain.generated.GlobalConfig;
import ua.kiev.unicyb.diploma.domain.generated.Parameterized;
import ua.kiev.unicyb.diploma.domain.generated.Questions;
import ua.kiev.unicyb.diploma.exception.ParsingConfigurationException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class ConfigurationParser {

    public GlobalConfig parseGlobal(String fileName) {
        try {
            File file = getResource(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(GlobalConfig.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (GlobalConfig) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.error(e.toString());
            throw new ParsingConfigurationException(e.getMessage());
        }
    }

    public Questions parseQuestion(String fileName) {
        try {
            File file = getResource(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Questions.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Questions) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.error(e.toString());
            throw new ParsingConfigurationException(e.getMessage());
        }
    }

    public Answers parseAnswers(String fileName) {
        try {
            File file = getResource(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Answers.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Answers) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.error(e.toString());
            throw new ParsingConfigurationException(e.getMessage());
        }
    }

    public Parameterized parseParameterized(String fileName) {
        try {
            File file = getResource(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Parameterized.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Parameterized) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.error(e.toString());
            throw new ParsingConfigurationException(e.getMessage());
        }
    }

    private File getResource(String fileName) {
        log.debug("Trying parse file {}", fileName);
        return new File(fileName);
    }
}
