package ua.kiev.unicyb.diploma.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionType;

@Data
public class QuestionDto {
    private Long questionId;
    private String preamble;
    private QuestionType questionType;
    private Double mark;
}
