package ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LIST_PARAMETER_VALUE")
@Getter
@Setter
public class ListParameterValue extends AbstractParameterValue {

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "PARAMETER_VALUE_ID")
    private List<ParamEntity> params = new ArrayList<>();
}
