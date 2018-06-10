package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.answer.VariantCheckResultEntity;
import ua.kiev.unicyb.diploma.dto.request.CheckQuestionDto;
import ua.kiev.unicyb.diploma.dto.request.VariantAnswersDto;

public interface CheckService {
    VariantCheckResultEntity checkVariant(VariantAnswersDto variantAnswersDto);
    VariantCheckResultEntity checkQuestion(CheckQuestionDto checkQuestionDto);
}
