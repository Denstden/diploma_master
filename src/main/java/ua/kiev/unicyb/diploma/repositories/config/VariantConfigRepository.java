package ua.kiev.unicyb.diploma.repositories.config;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;

@Repository
public interface VariantConfigRepository extends CrudRepository<VariantConfigEntity, Long> {
}
