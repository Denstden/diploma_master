package ua.kiev.unicyb.diploma.dto.request;

import lombok.Data;

@Data
public class CheckQuestionDto {
    private Long questionId;
    private Double mark;
}
