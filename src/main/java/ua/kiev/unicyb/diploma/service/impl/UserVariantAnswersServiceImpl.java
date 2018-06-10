package ua.kiev.unicyb.diploma.service.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.answer.UserVariantAnswersEntity;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;
import ua.kiev.unicyb.diploma.repositories.UserVariantAnswersRepository;
import ua.kiev.unicyb.diploma.repositories.VariantRepository;
import ua.kiev.unicyb.diploma.repositories.user.UserRepository;
import ua.kiev.unicyb.diploma.service.UserVariantAnswersService;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserVariantAnswersServiceImpl implements UserVariantAnswersService {
    UserVariantAnswersRepository repository;
    UserRepository  userRepository;
    VariantRepository variantRepository;

    @Override
    public Iterable<UserVariantAnswersEntity> getUserAnswersForVariant(final Long variantId, final Long userId) {
        final VariantEntity variant = variantRepository.findOne(variantId);
        if (variant == null) {
            throw new RuntimeException("Can not find variant with id " + variantId);
        }

        final UserEntity user = userRepository.findOne(userId);
        if (user == null) {
            throw new RuntimeException("Can not find user with id " + userId);
        }


        return repository.findByVariantIdAndUser_UserId(variantId, userId);
    }
}
