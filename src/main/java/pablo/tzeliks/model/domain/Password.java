package pablo.tzeliks.model.domain;

import pablo.tzeliks.exceptions.InvalidEmailException;
import pablo.tzeliks.exceptions.InvalidPasswordException;

import java.util.Objects;

public class Password implements PasswordInterface {

    private final String password;

    public Password(String password) {
        this.password = password;
    }

    public Password ofRaw(String rawPassword) {
        if (rawPassword == null) throw new InvalidPasswordException("Null password");

        if (!validate(rawPassword)) {
            throw new InvalidPasswordException("Invalid password, this password can not pass in the validation.");
        }

        return new Password(rawPassword);
    }

    public String forDatabase() {
        return password;
    }

    public Password fromStored(String stored) {
        if (stored == null) throw new InvalidPasswordException("Null password");

        return new Password(stored);
    }

    public boolean matchesStored(String stored) {
        // Em implementação real com hash seria algo como:
        // return PasswordHasher.verify(storedHash, this.password);
        return Objects.equals(this.password, stored);
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

    public String toString() {
        return "******";
    }

}
