package pablo.tzeliks.service.contracts;

import pablo.tzeliks.model.domain.Email;
import pablo.tzeliks.model.domain.Password;

public interface AuthenticationInterface<T> {

    T register(String name, Email email, Password password);
    T login(Email email, Password password);
}
