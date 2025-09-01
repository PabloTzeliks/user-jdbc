package pablo.tzeliks.dao;

import pablo.tzeliks.model.User;
import pablo.tzeliks.model.domain.Email;
import pablo.tzeliks.model.domain.Password;

import java.util.List;

public interface UserDAO {

    void save(User user);
    void login(Email email, Password password);
    void update(User user);
    void delete(User user);

    boolean exists(Email email);
    User findByEmail(Email email);
    List<User> findAll();

}
