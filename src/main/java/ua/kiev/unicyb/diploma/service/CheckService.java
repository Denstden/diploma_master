package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.dto.request.VariantAnswersDto;
import ua.kiev.unicyb.diploma.dto.response.VariantCheckDto;

public interface CheckService {
    VariantCheckDto checkVariant(VariantAnswersDto variantAnswersDto);
}
