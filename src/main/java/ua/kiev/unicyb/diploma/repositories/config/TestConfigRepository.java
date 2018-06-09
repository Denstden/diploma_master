package ua.kiev.unicyb.diploma.repositories.config;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestConfigEntity;

@Repository
public interface TestConfigRepository extends CrudRepository<TestConfigEntity, Long> {
}
