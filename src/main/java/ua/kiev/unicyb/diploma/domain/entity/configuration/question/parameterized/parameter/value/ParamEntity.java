package ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PARAM")
@Getter
@Setter
public class ParamEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="param_gen")
    @SequenceGenerator(name="param_gen", sequenceName="PARAM_GEN_SEQ", allocationSize=1)
    private Long paramId;

    @Column
    private String paramValue;
}
