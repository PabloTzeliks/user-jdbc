package pablo.tzeliks.model.domain;

import pablo.tzeliks.exceptions.InvalidPasswordException;

public interface PasswordInterface {

    boolean matches(String password) throws InvalidPasswordException;
    boolean validate(String password) throws InvalidPasswordException;

}
