package ua.kiev.unicyb.diploma.domain.entity.question;

import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.domain.entity.EstimationEntity;
import ua.kiev.unicyb.diploma.domain.entity.FormattingElementsEntity;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "QUESTION")
@Getter
@Setter
public class QuestionEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_gen")
    @SequenceGenerator(name = "question_gen", sequenceName = "QUESTION_SEQ", allocationSize = 1)
    private Long questionId;

    @Column
    private String preamble;

    @Column
    private String hashTag;

    @Column
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUESTION_ID")
    private List<QuestionAnswerEntity> questionAnswers = new ArrayList<>();

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "FORMATTING_ELEMENTS_ID")
    private FormattingElementsEntity formattingElements;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "ESTIMATION_ID")
    private EstimationEntity estimation;
}
