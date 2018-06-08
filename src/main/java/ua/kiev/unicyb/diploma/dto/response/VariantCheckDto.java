package ua.kiev.unicyb.diploma.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantCheckDto {
    private Double points;
    private Double total;
    private Boolean isComplete = true;
}
