package ua.kiev.unicyb.diploma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.answer.UserVariantAnswersEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;

@Repository
public interface UserVariantAnswersRepository extends CrudRepository<UserVariantAnswersEntity, Long> {
    Iterable<UserVariantAnswersEntity> findByVariantIdAndUser_UserId(Long variantId, Long userId);
    UserVariantAnswersEntity findByUserQuestionAnswers_Question(QuestionEntity question);
}
