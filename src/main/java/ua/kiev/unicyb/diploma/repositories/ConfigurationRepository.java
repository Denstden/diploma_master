package ua.kiev.unicyb.diploma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.configuration.ConfigurationEntity;

import java.util.List;

@Repository
public interface ConfigurationRepository extends CrudRepository<ConfigurationEntity, Long> {
    List<ConfigurationEntity> findByUser_UsernameAndIsLoaded(final String username, Boolean isLoaded);
}
