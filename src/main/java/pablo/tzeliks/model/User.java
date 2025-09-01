package pablo.tzeliks.model;

import pablo.tzeliks.model.domain.Email;
import pablo.tzeliks.model.domain.Password;

public class User {

    private String name;
    private Email email;
    private Password password;

    public User(String name, Email email, Password password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
