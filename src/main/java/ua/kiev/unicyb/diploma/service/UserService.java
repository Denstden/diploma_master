package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.user.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
