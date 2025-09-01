package pablo.tzeliks.dao;

import pablo.tzeliks.model.User;

import pablo.tzeliks.model.domain.Email;
import pablo.tzeliks.repository.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public User findByEmail(Email email) {

        String query = """
                SELECT name, email, password
                FROM users
                WHERE email = ?""";

        try (Connection conn = ConnectDatabase.connect();
             PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setString(1, email.getEmail());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
