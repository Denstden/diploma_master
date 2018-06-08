package ua.kiev.unicyb.diploma.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class VariantAnswersDto {
    private Long variantId;
    private List<QuestionAnswersDto> questionAnswers;
}
