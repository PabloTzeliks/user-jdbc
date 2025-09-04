package pablo.tzeliks.service;

import pablo.tzeliks.dao.UserDAO;
import pablo.tzeliks.exceptions.AuthenticationException;
import pablo.tzeliks.exceptions.DuplicateUserException;
import pablo.tzeliks.model.User;
import pablo.tzeliks.model.domain.Email;
import pablo.tzeliks.model.domain.Password;
import pablo.tzeliks.service.contracts.AuthenticationInterface;

public class AutheticationService implements AuthenticationInterface<User> {

    private UserDAO userDAO;

    public AutheticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User register(String name, Email email, Password password) {

        if (name == null || name.isBlank()) {
            throw new AuthenticationException("Name is null or blank");
        }

        if (userDAO.exists(email)) {
            throw new DuplicateUserException("User already exists");
        }

        User user = new User(name, email, password);

        userDAO.save(user);

        return user;
    }

    @Override
    public User login(Email email, Password password) {

        userDAO.login(email, password);

        User userByEmail = userDAO.findByEmail(email);

        return userByEmail;
    }

}
