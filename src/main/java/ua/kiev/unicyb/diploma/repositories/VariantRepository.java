package ua.kiev.unicyb.diploma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;

import java.util.List;

@Repository
public interface VariantRepository extends CrudRepository<VariantEntity, Long> {
    List<VariantEntity> findByTest_TestId(final Long testId);
}
