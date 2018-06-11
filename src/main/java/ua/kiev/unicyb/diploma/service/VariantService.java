package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.answer.VariantCheckResultEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;
import ua.kiev.unicyb.diploma.dto.request.CheckQuestionDto;
import ua.kiev.unicyb.diploma.dto.response.QuestionWithAnswersDto;

import java.util.List;

public interface VariantService {
    VariantEntity getVariant(Long testId);
    List<VariantEntity> generateVariants(Integer count, VariantConfigEntity variantConfigEntity, TestEntity testEntity);

    List<QuestionWithAnswersDto> findByCheckedAndQuestionTypeForCurrentUser(Boolean isChecked, String questionType);
}
