package ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ABSTRACT_PARAMETER_VALUE")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class AbstractParameterValue {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="abstract_parameter_value_gen")
    @SequenceGenerator(name="abstract_parameter_value_gen", sequenceName="ABSTRACT_PARAMETER_VALUE_SEQ", allocationSize=1)
    protected Long parameterValueId;
}
