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
import ua.kiev.unicyb.diploma.dto.request.CheckQuestionDto;
import ua.kiev.unicyb.diploma.dto.response.QuestionWithAnswersDto;
import ua.kiev.unicyb.diploma.service.CheckService;
import ua.kiev.unicyb.diploma.service.VariantService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/questions")
@CrossOrigin
@Slf4j
@PreAuthorize("hasAuthority('TUTOR')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class QuestionController {
    CheckService checkService;
    VariantService variantService;

    @RequestMapping(value = "/check", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VariantCheckResultEntity> checkQuestion(@RequestBody CheckQuestionDto checkQuestionDto) {
        return new ResponseEntity<>(checkService.checkQuestion(checkQuestionDto), HttpStatus.OK);
    }


    @GetMapping
    public List<QuestionWithAnswersDto> getNotCheckedYetEssayQuestionsForUser(@RequestParam(name = "isChecked", defaultValue = "false") Boolean isChecked,
                                                                              @RequestParam(name = "questionType", defaultValue = "ESSAY") String questionType) {
        return variantService.findByCheckedAndQuestionTypeForCurrentUser(isChecked, questionType);
    }
}


