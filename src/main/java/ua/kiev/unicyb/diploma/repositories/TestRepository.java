package ua.kiev.unicyb.diploma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;

@Repository
public interface TestRepository extends CrudRepository<TestEntity, Long> {
    Iterable<TestEntity> findByTestType(final TestType testType);
}
