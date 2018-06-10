package ua.kiev.unicyb.diploma.resource;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.kiev.unicyb.diploma.domain.entity.answer.UserVariantAnswersEntity;
import ua.kiev.unicyb.diploma.repositories.UserVariantAnswersRepository;
import ua.kiev.unicyb.diploma.service.UserVariantAnswersService;

@RestController
@CrossOrigin
@RequestMapping("/api/variants/answers")
@PreAuthorize("hasAuthority('TUTOR')")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnswerController {
    UserVariantAnswersService userVariantAnswersService;

    @GetMapping
    public Iterable<UserVariantAnswersEntity> getUserAnswersForVariant(@RequestParam(name = "variantId") Long variantId,
                                                                       @RequestParam(name = "userId") Long userId){
        return userVariantAnswersService.getUserAnswersForVariant(variantId, userId);
    }
}
