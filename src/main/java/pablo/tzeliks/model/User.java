package pablo.tzeliks.model;

import pablo.tzeliks.model.domain.Email;
import pablo.tzeliks.model.domain.Password;

public class User {

    private String nome;
    private Email email;
    private Password password;

    public User(String nome, Email email, Password password) {
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
