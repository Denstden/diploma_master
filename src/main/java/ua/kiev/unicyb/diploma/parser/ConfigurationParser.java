package ua.kiev.unicyb.diploma.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import ua.kiev.unicyb.diploma.domain.generated.Answers;
import ua.kiev.unicyb.diploma.domain.generated.GlobalConfig;
import ua.kiev.unicyb.diploma.domain.generated.Parameterized;
import ua.kiev.unicyb.diploma.domain.generated.Questions;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
@Slf4j
public class ConfigurationParser {

    public GlobalConfig parseGlobal(String fileName) throws JAXBException, IOException {

        File file = getResource(fileName);
        JAXBContext jaxbContext = JAXBContext.newInstance(GlobalConfig.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return  (GlobalConfig) jaxbUnmarshaller.unmarshal(file);
    }

    public Questions parseQuestion(String fileName) {
        try {
            File file = getResource(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Questions.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Questions) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException | IOException e) {
            log.error(e.toString());
            return null;
        }
    }

    public Answers parseAnswers(String fileName) {
        try {
            File file = getResource(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Answers.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Answers) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException | IOException e) {
            log.error(e.toString());
            return null;
        }
    }

    public Parameterized parseParameterized(String fileName) {
        try {
            File file = getResource(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Parameterized.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Parameterized) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException | IOException e) {
            log.error(e.toString());
            return null;
        }
    }

    private File getResource(String fileName) throws IOException {
        return new File(fileName);
//        return ResourceUtils.getFile("classpath:" + fileName);

    }
}
