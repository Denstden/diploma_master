package ua.kiev.unicyb.diploma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.answer.VariantCheckResultEntity;

@Repository
public interface VariantCheckResultRepository extends CrudRepository<VariantCheckResultEntity, Long> {
}
