package pablo.tzeliks.model.domain;

import pablo.tzeliks.exceptions.InvalidEmailException;
import pablo.tzeliks.exceptions.InvalidPasswordException;

public class Password implements PasswordInterface {

    private final String password;

    public Password(String password) {

        if (!validate(password)) {
            throw new InvalidPasswordException("Invalid password, this password can not pass in the validation.");
        }

        this.password = password;

    }

    private boolean isValid(String password) {

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        return password.length() >= 10 && hasUpper && hasLower && hasDigit;

    }

    @Override
    public boolean matches(String tryPassword) throws InvalidPasswordException {
        return this.password.equals(tryPassword);
    }

    // Validador
    @Override
    public boolean validate(String password) throws InvalidEmailException {
        return isValid(password);
    }

}
