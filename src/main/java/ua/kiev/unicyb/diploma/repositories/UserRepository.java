package ua.kiev.unicyb.diploma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
