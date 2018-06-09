package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.ParameterizedEntity;

public interface ParameterizedService {
    String processParameterized(ParameterizedEntity entity, String value);
}
