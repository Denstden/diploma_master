package ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.condition;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CONDITION")
@Getter
@Setter
public class ConditionEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="condition_gen")
    @SequenceGenerator(name="condition_gen", sequenceName="CONDITION_GEN_SEQ", allocationSize=1)
    private Long conditionId;

    @Column
    private String value;
}
