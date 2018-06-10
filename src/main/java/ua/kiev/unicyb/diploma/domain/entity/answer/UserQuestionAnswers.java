package ua.kiev.unicyb.diploma.domain.entity.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USER_QUESTION_ANSWER")
public class UserQuestionAnswers {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_question_answer_gen")
    @SequenceGenerator(name="user_question_answer_gen", sequenceName="USER_QUESTION_ANSWER_SEQ", allocationSize=1)
    private Long userQuestionAnswersId;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUESTION_ID")
    private QuestionEntity question;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "ANSWER_ID")
    private List<QuestionAnswerEntity> questionAnswers = new ArrayList<>();
}
