package ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DIAPASON_PARAMETER_VALUE")
@Getter
@Setter
public class DiapasonParameterValue extends AbstractParameterValue {
    @Column
    private String minValue;

    @Column
    private String maxValue;
}
