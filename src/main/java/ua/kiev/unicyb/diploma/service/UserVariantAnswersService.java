package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.answer.UserVariantAnswersEntity;

public interface UserVariantAnswersService {
    Iterable<UserVariantAnswersEntity> getUserAnswersForVariant(Long variantId, Long userId);
}
