package ua.kiev.unicyb.diploma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
}
