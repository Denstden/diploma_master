package ua.kiev.unicyb.diploma.domain.entity.configuration.question;

import lombok.Setter;import lombok.Getter;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "ESSAY_QUESTION_DESCRIPTION")
@Getter@Setter
@Entity
public class EssayQuestionDescriptionEntity extends AbstractQuestionDescriptionEntity {

}
