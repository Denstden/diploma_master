package ua.kiev.unicyb.diploma.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.generated.Answers;
import ua.kiev.unicyb.diploma.domain.generated.GlobalConfig;
import ua.kiev.unicyb.diploma.domain.generated.Questions;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
@Slf4j
public class ConfigurationParser {

    public static GlobalConfig parse(String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jaxbContext = JAXBContext.newInstance(GlobalConfig.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return  (GlobalConfig) jaxbUnmarshaller.unmarshal(file);
    }

    public static Questions parseQuestion(String fileName) {
        try {
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Questions.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Questions) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.error(e.toString());
            return null;
        }
    }

    public static Answers parseAnswers(String fileName) {
        try {
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Answers.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Answers) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.error(e.toString());
            return null;
        }
    }
}
