package ua.kiev.unicyb.diploma.domain.entity.configuration.question;

import lombok.Setter;import lombok.Getter;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter@Setter
@Table(name = "CHECKBOX_QUESTION_DESCRIPTION")
public class CheckboxQuestionDescriptionEntity extends AbstractQuestionDescriptionEntity {

    @Column
    private Integer countCorrectAnswers;
    @Column
    private Integer countIncorrectAnswers;

}
