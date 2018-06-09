package ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized;

import lombok.Getter;
import lombok.Setter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.condition.ConditionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.ParameterEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PARAMETERIZED_CONFIG")
public class ParameterizedEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameterized_config_gen")
    @SequenceGenerator(name = "parameterized_config_gen", sequenceName = "PARAMETERIZED_CONFIG_SEQ", allocationSize = 1)
    private Long parameterizedId;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "PARAMETERIZED_ID")
    private List<ParameterEntity> parameters = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "PARAMETERIZED_ID")
    private List<ConditionEntity> conditions = new ArrayList<>();
}
