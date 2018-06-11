package ua.kiev.unicyb.diploma.repositories.user.answer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.answer.UserQuestionAnswers;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;

@Repository
public interface UserQuestionAnswersRepository extends CrudRepository<UserQuestionAnswers, Long> {
    Iterable<UserQuestionAnswers> findByQuestion(QuestionEntity question);
}
