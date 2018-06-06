package ua.kiev.unicyb.diploma.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.EstimationStrategy;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;

import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name = "ESTIMATION")
public class EstimationEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_gen")
    @SequenceGenerator(name = "estimation_gen", sequenceName = "ESTIMATION_SEQ", allocationSize = 1)
    private Long estimationId;

    @Column
    @Enumerated(EnumType.STRING)
    private EstimationStrategy estimationStrategy;

    @Column
    private Double mark;

    @OneToOne(mappedBy = "estimation", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private QuestionEntity questionEntity;
}
