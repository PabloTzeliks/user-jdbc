package pablo.tzeliks.model.domain;

import pablo.tzeliks.exceptions.InvalidEmailException;

import java.util.regex.Pattern;

public class EmailValidator implements Email {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private static boolean isValid(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // Validador a partir da Pattern
    public boolean validate(String email) throws InvalidEmailException {
        return isValid(email);
    }

}
