package ua.kiev.unicyb.diploma.domain.entity.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;import lombok.Getter;

import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name = "QUESTION_ANSWER")
public class QuestionAnswerEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="question_answer_gen")
    @SequenceGenerator(name="question_answer_gen", sequenceName="QUESTION_ANSWER_SEQ", allocationSize=1)
    private Long answerId;

    @Column
    @JsonIgnore
    private Boolean isCorrect;

    @Column
    private String answer;
}
