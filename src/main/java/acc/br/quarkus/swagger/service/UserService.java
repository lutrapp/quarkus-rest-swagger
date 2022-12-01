package acc.br.quarkus.swagger.service;

import acc.br.quarkus.swagger.entity.User;
import acc.br.quarkus.swagger.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    User getUserById(long id) throws UserNotFoundException;

    List<User> getAllUsers();

    User updateUser(long id, User user) throws UserNotFoundException;

    User saveUser(User user);

    void deleteUser(long id) throws UserNotFoundException;
}

