package ua.kiev.unicyb.diploma.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.ConfigurationConverter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.AnswerDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.EstimationStrategy;
import ua.kiev.unicyb.diploma.domain.entity.configuration.HashTag;
import ua.kiev.unicyb.diploma.domain.entity.configuration.format.FormattingElementsConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.format.FormattingStrategy;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.*;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.description.QuestionConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.description.QuestionDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.description.QuestionSourceConfigEntity;
import ua.kiev.unicyb.diploma.domain.generated.Answers;
import ua.kiev.unicyb.diploma.domain.generated.FormattingElements;
import ua.kiev.unicyb.diploma.domain.generated.QuestionConfig;
import ua.kiev.unicyb.diploma.domain.generated.Questions;
import ua.kiev.unicyb.diploma.parser.ConfigurationParser;
import ua.kiev.unicyb.diploma.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class QuestionConfigConverter implements ConfigurationConverter<QuestionConfigEntity, QuestionConfig> {
    private static final String EOLN = "\r\n";

    @Autowired
    private ConfigurationParser configurationParser;
    @Value("${xml.test.configuration.folder}")
    private String configurationFolder;

    @Override
    public QuestionConfigEntity toEntity(QuestionConfig dto) {
        return null;
    }

    private void convertHashTags(QuestionConfig.Hashtags hashTags, QuestionConfigEntity questionConfigEntity) {
        if (hashTags != null) {
            final List<String> hashTagList = hashTags.getHashtag();
            final List<HashTag> hashTagEntities = new ArrayList<>();
            hashTagList.forEach(ht -> {
                HashTag hashTag = new HashTag();
                hashTag.setValue(ht);
                hashTagEntities.add(hashTag);
            });
            questionConfigEntity.setHashTags(hashTagEntities);
        }
    }

    private void convertQuestionSources(QuestionConfig.Sources sources, QuestionConfigEntity questionConfigEntity, String filePath) {
        if (sources != null) {
            final List<QuestionConfig.Sources.Source> sourceList = sources.getSource();
            final List<QuestionSourceConfigEntity> questionSourceConfigEntities = new ArrayList<>();

            sourceList.forEach(source -> {
                QuestionSourceConfigEntity entity = convertSource(source, filePath);
                questionSourceConfigEntities.add(entity);
            });

            questionConfigEntity.setQuestionSourceConfigEntities(questionSourceConfigEntities);
        }
    }

    private QuestionSourceConfigEntity convertSource(QuestionConfig.Sources.Source source, String filePath) {
        QuestionSourceConfigEntity entity = new QuestionSourceConfigEntity();
        if (source.getCountQuestions() == null) {
            entity.setCountQuestions(1);
        } else {
            entity.setCountQuestions(source.getCountQuestions().intValue());
        }

        final Questions questions = configurationParser.parseQuestion(parentFilePath(filePath) + "/" + source.getQuestionSource());
        if (questions != null) {
            convertQuestionDescriptions(entity, questions, filePath);
        } else {
            log.error("Parsing error!");
        }
        return entity;
    }

    private String parentFilePath(final String childFilePath) {
        if (childFilePath == null) {
            return null;
        }

        final int lastOfSlash = childFilePath.lastIndexOf("/");
        final int lastOfBackSlash = childFilePath.lastIndexOf("\\");
        return childFilePath.substring(0, Math.max(lastOfSlash, lastOfBackSlash));
    }

    private void convertQuestionDescriptions(QuestionSourceConfigEntity entity, Questions questions, String filePath) {
        QuestionDescriptionEntity questionDescriptionEntity = new QuestionDescriptionEntity();
        questionDescriptionEntity.setGlobalPreamble(StringUtil.trim(questions.getGlobalPreamble()));


        final List<AbstractQuestionDescriptionEntity> questionConfig = new ArrayList<>();
        final List<Object> allQuestionDescriptionsFromOneSource = questions.getQuestionCheckboxOrQuestionYesNoOrQuestionEssay();

        allQuestionDescriptionsFromOneSource.forEach(questionDescriptionFromOneSource -> {
            AbstractQuestionDescriptionEntity abstractQuestionDescriptionEntity = convertOneQuestionDescription(questionDescriptionFromOneSource, filePath);
            questionConfig.add(abstractQuestionDescriptionEntity);
        });


        questionDescriptionEntity.setQuestionConfigs(questionConfig);

        entity.setQuestionDescriptionEntity(questionDescriptionEntity);
    }

    private AbstractQuestionDescriptionEntity convertOneQuestionDescription(final Object questionDescriptionFromOneSource, String filePath) {
        if (questionDescriptionFromOneSource instanceof Questions.QuestionCheckbox) {
            return convertCheckbox((Questions.QuestionCheckbox) questionDescriptionFromOneSource, filePath);
        } else if (questionDescriptionFromOneSource instanceof Questions.QuestionEssay) {
            return convertEssay((Questions.QuestionEssay) questionDescriptionFromOneSource, filePath);
        } else if (questionDescriptionFromOneSource instanceof Questions.QuestionRadiobutton) {
            return convertRadiobutton((Questions.QuestionRadiobutton) questionDescriptionFromOneSource, filePath);
        } else {
            return convertYesNo((Questions.QuestionYesNo) questionDescriptionFromOneSource, filePath);
        }
    }

    private CheckboxQuestionDescriptionEntity convertCheckbox(Questions.QuestionCheckbox questionDescriptionFromOneSource, String filePath) {
        final CheckboxQuestionDescriptionEntity entity = new CheckboxQuestionDescriptionEntity();

        entity.setCountCorrectAnswers(questionDescriptionFromOneSource.getCountCorrectAnswers().intValue());
        entity.setCountIncorrectAnswers(questionDescriptionFromOneSource.getCountIncorrectAnswers().intValue());
        entity.setPreamble(StringUtil.trim(questionDescriptionFromOneSource.getPreamble()));
        entity.setHashTag(questionDescriptionFromOneSource.getHashTag());

        final List<AnswerDescriptionEntity> answerDescriptionEntities = convertAnswers(questionDescriptionFromOneSource.getSourceCorrectAnswers(), true, filePath);
        answerDescriptionEntities.addAll(convertAnswers(questionDescriptionFromOneSource.getSourceIncorrectAnswers(), false, filePath));

        entity.setAnswers(answerDescriptionEntities);

        entity.setFormattingElements(convertFormattingElements(questionDescriptionFromOneSource.getFormattingElements()));

        return entity;
    }

    private EssayQuestionDescriptionEntity convertEssay(final Questions.QuestionEssay questionDescriptionFromOneSource, String filePath) {
        final EssayQuestionDescriptionEntity entity = new EssayQuestionDescriptionEntity();

        entity.setHashTag(questionDescriptionFromOneSource.getHashTag());
        entity.setPreamble(StringUtil.trim(questionDescriptionFromOneSource.getPreamble()));
        entity.setFormattingElements(convertFormattingElements(questionDescriptionFromOneSource.getFormattingElements()));

        return entity;
    }

    private RadioButtonQuestionDescriptionEntity convertRadiobutton(final Questions.QuestionRadiobutton questionDescriptionFromOneSource, String filePath) {
        final RadioButtonQuestionDescriptionEntity entity = new RadioButtonQuestionDescriptionEntity();

        entity.setCountAnswers(questionDescriptionFromOneSource.getCountAnswers().intValue());
        entity.setPreamble(StringUtil.trim(questionDescriptionFromOneSource.getPreamble()));
        entity.setHashTag(questionDescriptionFromOneSource.getHashTag());

        final List<AnswerDescriptionEntity> answerDescriptionEntities = convertAnswers(questionDescriptionFromOneSource.getSourceCorrectAnswers(), true, filePath);
        answerDescriptionEntities.addAll(convertAnswers(questionDescriptionFromOneSource.getSourceIncorrectAnswers(), false, filePath));

        entity.setAnswers(answerDescriptionEntities);

        entity.setFormattingElements(convertFormattingElements(questionDescriptionFromOneSource.getFormattingElements()));

        return entity;
    }

    private YesNoQuestionDescriptionEntity convertYesNo(final Questions.QuestionYesNo questionDescriptionFromOneSource, String filePath) {
        final YesNoQuestionDescriptionEntity entity = new YesNoQuestionDescriptionEntity();

        entity.setPreamble(StringUtil.trim(questionDescriptionFromOneSource.getPreamble()));
        entity.setHashTag(questionDescriptionFromOneSource.getHashTag());
        entity.setAnswer(Boolean.valueOf(questionDescriptionFromOneSource.getAnswer()));

        entity.setFormattingElements(convertFormattingElements(questionDescriptionFromOneSource.getFormattingElements()));

        return entity;
    }

    private FormattingElementsConfigEntity convertFormattingElements(FormattingElements formattingElements) {
        if (formattingElements != null) {
            final FormattingElementsConfigEntity formattingElementsConfigEntity = new FormattingElementsConfigEntity();

            if (formattingElements.getCount() == null) {
                formattingElementsConfigEntity.setCount(1);
            } else {
                formattingElementsConfigEntity.setCount(formattingElements.getCount().intValue());
            }
            formattingElementsConfigEntity.setFormattingStrategy(FormattingStrategy.valueOf(formattingElements.getStrategy().toUpperCase()));

            return formattingElementsConfigEntity;
        } else {
            return null;
        }
    }

    private List<AnswerDescriptionEntity> convertAnswers(String source, Boolean isCorrect, String filePath) {
        final List<AnswerDescriptionEntity> answerDescriptionEntities = new ArrayList<>();

        final Answers answers = configurationParser.parseAnswers(parentFilePath(filePath) + "/" + source);
        if (answers != null) {
            final List<Answers.Answer> answerList = answers.getAnswer();
            answerList.forEach(answer -> {
                final AnswerDescriptionEntity entity = convertOneAnswer(isCorrect, answer);
                answerDescriptionEntities.add(entity);
            });
        }

        return answerDescriptionEntities;
    }

    private AnswerDescriptionEntity convertOneAnswer(Boolean isCorrect, Answers.Answer answer) {
        final AnswerDescriptionEntity entity = new AnswerDescriptionEntity();
        entity.setIsCorrect(isCorrect);

        final StringBuilder stringBuilder = new StringBuilder();

        final List<String> lines = answer.getLine();
        lines.forEach(line -> {
            stringBuilder.append(line).append(EOLN);
        });

        entity.setAnswer(StringUtil.trim(stringBuilder.toString()));
        return entity;
    }


    @Override
    public QuestionConfig toDto(QuestionConfigEntity entity) {
        return null;
    }

    @Override
    public QuestionConfigEntity toEntityWithFile(QuestionConfig dto, String filePath) {
        QuestionConfigEntity questionConfigEntity = new QuestionConfigEntity();

        convertHashTags(dto.getHashtags(), questionConfigEntity);

        convertQuestionSources(dto.getSources(), questionConfigEntity, filePath);

        questionConfigEntity.setMark(dto.getEstimation().getMark().doubleValue());
        questionConfigEntity.setStrategy(EstimationStrategy.valueOf(dto.getEstimation().getStrategy().toUpperCase()));

        return questionConfigEntity;
    }
}
