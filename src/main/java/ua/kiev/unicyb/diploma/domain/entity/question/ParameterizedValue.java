package ua.kiev.unicyb.diploma.domain.entity.question;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PARAMETERIZED_VALUE")
@Getter
@Setter
public class ParameterizedValue {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameterized_value_gen")
    @SequenceGenerator(name = "parameterized_value_gen", sequenceName = "PARAMETERIZED_VALUE_SEQ", allocationSize = 1)
    private Long parametrizedValueId;
    @Column
    private String name;
    @Column
    private String value;
}
