package pablo.tzeliks.model.domain;

import pablo.tzeliks.exceptions.InvalidEmailException;

public interface EmailInterface {

    boolean validate(String email) throws InvalidEmailException;

}