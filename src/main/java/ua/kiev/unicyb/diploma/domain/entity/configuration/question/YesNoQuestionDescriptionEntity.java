package ua.kiev.unicyb.diploma.domain.entity.configuration.question;

import lombok.Setter;import lombok.Getter;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "YES_NO_QUESTION_DESCRIPTION")
@Getter@Setter
@Entity
public class YesNoQuestionDescriptionEntity extends AbstractQuestionDescriptionEntity {

    @Column
    private Boolean answer;
}
