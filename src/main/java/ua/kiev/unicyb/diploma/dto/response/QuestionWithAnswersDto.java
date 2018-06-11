package ua.kiev.unicyb.diploma.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
public class QuestionWithAnswersDto {
    private QuestionDto question;
    private List<AnswerDto> answers;
}
