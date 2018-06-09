package ua.kiev.unicyb.diploma.repositories.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.unicyb.diploma.domain.entity.user.Role;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    Iterable<UserEntity> findByRolesIn(Set<Role> roles);
}
