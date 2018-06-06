package ua.kiev.unicyb.diploma.domain.entity.configuration.question;

import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.AnswerDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.format.FormattingElementsConfigEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ABSTRACT_QUESTION_DESCRIPTION")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter@Setter
public abstract class AbstractQuestionDescriptionEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="abstract_question_description_gen")
    @SequenceGenerator(name="abstract_question_description_gen", sequenceName="QUESTION_DESCRIPTION_SEQ", allocationSize=1)
    protected Long questionId;

    @Column
    protected String preamble;

    @Column
    protected String hashTag;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUESTION_ID")
    protected List<AnswerDescriptionEntity> answers = new ArrayList<>();

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "FORMATTING_ELEMENTS_CONFIG_ID")
    protected FormattingElementsConfigEntity formattingElements;
}
