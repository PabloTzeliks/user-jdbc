package pablo.tzeliks.dao;

import pablo.tzeliks.exceptions.AuthenticationException;
import pablo.tzeliks.exceptions.DuplicateUserException;
import pablo.tzeliks.exceptions.UserNotFoundException;
import pablo.tzeliks.model.User;
import pablo.tzeliks.model.domain.Email;
import pablo.tzeliks.model.domain.Password;
import pablo.tzeliks.repository.ConnectDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private Connection getConnection() throws SQLException {
        return ConnectDatabase.connect();
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        if (exists(user.getEmail())) {
            throw new DuplicateUserException("User already exists: " + user.getEmail().getValue());
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail().getValue());
            // pedir ao Password a representação para salvar
            stmt.setString(3, user.getPassword().forDatabase()); // No futuro isso poderá ser trabalhado com Hash (SHA-256)

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Fail to save user. No line affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to save User: ", e);
        }
    }

    @Override
    public void login(Email email, Password password) {
        String sql = "SELECT password FROM users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.getValue());
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new AuthenticationException("Invalid Credentials.");
                }
                String stored = rs.getString("password");

                if (!password.matchesStored(stored)) {
                    throw new AuthenticationException("Invalid Credentials.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to authenticate User: ", e);
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET password = ?, name = ? WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getPassword().forDatabase());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail().getValue());

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new UserNotFoundException("Can not find user for update: " + user.getEmail().getValue());
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to update User: ", e);
        }
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getEmail().getValue());
            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new UserNotFoundException("Can not find user for removal: " + user.getEmail().getValue());
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to remove User: ", e);
        }
    }

    @Override
    public boolean exists(Email email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.getValue());
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to verify User: ", e);
        }
    }

    @Override
    public User findByEmail(Email email) {
        String sql = "SELECT name, email, password FROM users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.getValue());
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;

                String name = rs.getString("name");
                String emailDb = rs.getString("email");
                String senhaDb = rs.getString("password");

                return new User(name, new Email(emailDb), new Password(senhaDb));
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to search User: ", e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT name, email, password FROM users";
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String emailDb = rs.getString("email");
                String senhaDb = rs.getString("password");

                users.add(new User(name, new Email(emailDb), new Password(senhaDb)));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to list User: ", e);
        }
    }
}
