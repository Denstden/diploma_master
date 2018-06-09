package ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter;

import lombok.Getter;
import lombok.Setter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value.AbstractParameterValue;
import ua.kiev.unicyb.diploma.domain.generated.ParameterType;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "PARAMETER_CONFIG")
public class ParameterEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameter_config_gen")
    @SequenceGenerator(name = "parameter_config_gen", sequenceName = "PARAMETER_CONFIG_SEQ", allocationSize = 1)
    private Long parameterId;

    @Column
    private String parameterName;

    @Column
    @Enumerated(EnumType.STRING)
    private ParameterType parameterType = ParameterType.DOUBLE;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "PARAMETER_VALUE_ID")
    private AbstractParameterValue parameterValue;
}
