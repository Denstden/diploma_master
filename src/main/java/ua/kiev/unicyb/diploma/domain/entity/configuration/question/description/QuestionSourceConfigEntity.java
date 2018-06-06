package ua.kiev.unicyb.diploma.domain.entity.configuration.question.description;

import lombok.Setter;import lombok.Getter;

import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name = "QUESTION_SOURCE_CONFIG")
public class QuestionSourceConfigEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="question_source_config_gen")
    @SequenceGenerator(name="question_source_config_gen", sequenceName="QUESTION_SOURCE_CONFIG_SEQ", allocationSize=1)
    private Long questionSourceConfigId;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUESTION_SOURCE_CONFIG_ID")
    private QuestionDescriptionEntity questionDescriptionEntity;

    @Column
    private Integer countQuestions = 1;

}
