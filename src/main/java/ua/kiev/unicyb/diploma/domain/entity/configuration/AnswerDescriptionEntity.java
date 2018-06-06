package ua.kiev.unicyb.diploma.domain.entity.configuration;

import lombok.Setter;import lombok.Getter;
import javax.persistence.*;

@Getter@Setter
@Entity
@Table(name = "ANSWER_DESCRIPTION")
public class AnswerDescriptionEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="answer_description_gen")
    @SequenceGenerator(name="answer_description_gen", sequenceName="ANSWER_DESCRIPTION_SEQ", allocationSize=1)
    private Long answerId;
    @Column
    private Boolean isCorrect;
    @Column
    private String answer;
}
