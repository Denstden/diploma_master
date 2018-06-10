package ua.kiev.unicyb.diploma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface VariantRepository extends CrudRepository<VariantEntity, Long> {
    List<VariantEntity> findByTest_TestId(final Long testId);
    VariantEntity findByQuestionsIn(Set<QuestionEntity> questionEntitySet);
}
