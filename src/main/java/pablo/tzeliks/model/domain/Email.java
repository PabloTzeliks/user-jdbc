package pablo.tzeliks.model.domain;

import pablo.tzeliks.exceptions.InvalidEmailException;

public interface Email {

    boolean validate(String email) throws InvalidEmailException;

}
