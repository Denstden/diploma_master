package ua.kiev.unicyb.diploma.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.ConfigurationConverter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.description.QuestionConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantFooterConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantHeaderConfigEntity;
import ua.kiev.unicyb.diploma.domain.generated.QuestionConfig;
import ua.kiev.unicyb.diploma.domain.generated.VariantConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class VariantConfigConverter implements ConfigurationConverter<VariantConfigEntity, VariantConfig> {
    private final QuestionConfigConverter questionConfigConverter;

    @Autowired
    public VariantConfigConverter(final QuestionConfigConverter questionConfigConverter) {
        this.questionConfigConverter = questionConfigConverter;
    }

    @Override
    public VariantConfigEntity toEntity(VariantConfig dto) {
        return null;
    }

    private void convertHeader(VariantConfig dto, VariantConfigEntity entity) {
        VariantConfig.VariantHeader variantHeader = dto.getVariantHeader();
        if (variantHeader != null) {
            final List<VariantHeaderConfigEntity> variantHeaders = new ArrayList<>();
            variantHeader.getLine().forEach(line -> {
                final VariantHeaderConfigEntity variantHeaderConfigEntity = new VariantHeaderConfigEntity();
                variantHeaderConfigEntity.setValue(line);
                variantHeaders.add(variantHeaderConfigEntity);
            });
            entity.setVariantHeader(variantHeaders);
        }
    }

    private void convertFooter(VariantConfig dto, VariantConfigEntity entity) {
        VariantConfig.VariantFooter variantFooter = dto.getVariantFooter();
        if (variantFooter != null) {
            final List<VariantFooterConfigEntity> variantFooters = new ArrayList<>();
            variantFooter.getLine().forEach(line -> {
                final VariantFooterConfigEntity variantFooterConfigEntity = new VariantFooterConfigEntity();
                variantFooterConfigEntity.setValue(line);
                variantFooters.add(variantFooterConfigEntity);
            });
            entity.setVariantFooter(variantFooters);
        }
    }

    @Override
    public VariantConfig toDto(VariantConfigEntity entity) {
        return null;
    }

    @Override
    public VariantConfigEntity toEntityWithFile(VariantConfig dto, String filePath) {
        if (dto == null) {
            return null;
        }

        VariantConfigEntity entity = new VariantConfigEntity();

        convertFooter(dto, entity);

        convertHeader(dto, entity);

        BigDecimal points = dto.getPoints();
        if (points != null) {
            entity.setPoints(points.doubleValue());
        }

        final List<QuestionConfigEntity> questionConfigEntities = new ArrayList<>();

        final VariantConfig.Questions questions = dto.getQuestions();
        if (questions != null) {
            final List<QuestionConfig> questionConfigs = questions.getQuestion();
            questionConfigs.forEach(questionConfig -> {
                final QuestionConfigEntity configEntity = questionConfigConverter.toEntityWithFile(questionConfig, filePath);
                questionConfigEntities.add(configEntity);
            });
        }
        entity.setQuestionConfigs(questionConfigEntities);

        return entity;
    }
}
