package ua.kiev.unicyb.diploma.resource;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.kiev.unicyb.diploma.domain.entity.answer.VariantCheckResultEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;
import ua.kiev.unicyb.diploma.dto.request.CheckQuestionDto;
import ua.kiev.unicyb.diploma.dto.request.VariantAnswersDto;
import ua.kiev.unicyb.diploma.dto.response.QuestionWithAnswersDto;
import ua.kiev.unicyb.diploma.service.CheckService;
import ua.kiev.unicyb.diploma.service.VariantService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/variants")
@CrossOrigin
@Slf4j
@PreAuthorize("hasAuthority('TUTOR') or hasAuthority('STUDENT')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class VariantController {
    VariantService variantService;
    CheckService checkService;

    @GetMapping
    public VariantEntity getVariant(@RequestParam(name = "testId") Long testId) {
        final VariantEntity variant = variantService.getVariant(testId);
        return variant;
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VariantCheckResultEntity> checkVariant(@RequestBody VariantAnswersDto variantAnswersDto) {
        final VariantCheckResultEntity variantCheckResultEntity = checkService.checkVariant(variantAnswersDto);
        return new ResponseEntity<>(variantCheckResultEntity, HttpStatus.OK);
    }
}
