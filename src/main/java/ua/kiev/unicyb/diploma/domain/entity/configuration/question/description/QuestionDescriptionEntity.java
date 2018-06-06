package ua.kiev.unicyb.diploma.domain.entity.configuration.question.description;

import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.AbstractQuestionDescriptionEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter
@Entity
@Table(name = "QUESTION_DESCRIPTION_CONFIG")
public class QuestionDescriptionEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="question_description_config_gen")
    @SequenceGenerator(name="question_description_config_gen", sequenceName="QUESTION_DESCRIPTION_CONFIG_SEQ", allocationSize=1)
    private Long questionDescriptionId;

    @Column
    private String globalPreamble;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUESTION_DESCRIPTION_ID")
    private List<AbstractQuestionDescriptionEntity> questionConfigs = new ArrayList<>();
}
