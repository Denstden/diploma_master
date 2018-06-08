package ua.kiev.unicyb.diploma.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionAnswersDto {
    private Long questionId;
    private List<String> answerIds;
}
