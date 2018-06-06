package ua.kiev.unicyb.diploma.domain.entity.configuration.question.description;

import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.EstimationStrategy;
import ua.kiev.unicyb.diploma.domain.entity.configuration.HashTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter
@Entity
@Table(name = "QUESTION_CONFIG")
public class QuestionConfigEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="question_config_gen")
    @SequenceGenerator(name="question_config_gen", sequenceName="QUESTION_CONFIG_SEQ", allocationSize=1)
    private Long questionConfigId;

    @Column
    private Double mark;

    @Column
    @Enumerated(EnumType.STRING)
    private EstimationStrategy strategy;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUESTION_CONFIG_ID")
    private List<HashTag> hashTags = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUESTION_CONFIG_ID")
    private List<QuestionSourceConfigEntity> questionSourceConfigEntities = new ArrayList<>();

}
