package ua.kiev.unicyb.diploma.domain.entity.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USER_VARIANT_ANSWER")
public class UserVariantAnswersEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_variant_answer_gen")
    @SequenceGenerator(name="user_variant_answer_gen", sequenceName="USER_VARIANT_ANSWER_SEQ", allocationSize=1)
    private Long userVariantAnswersId;

    @Column
    private Long variantId;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private UserEntity user;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_QUESTION_ANSWERS_ID")
    private List<UserQuestionAnswers> userQuestionAnswers = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "VARIANT_CHECK_RESULT_ID")
    private VariantCheckResultEntity variantCheckResult;
}
