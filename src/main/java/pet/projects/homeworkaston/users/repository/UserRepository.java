package pet.projects.homeworkaston.users.repository;


import pet.projects.homeworkaston.users.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepository {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver for PostgreSQL", e);
        }
    }

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "sasa";

    private static final String UPDATE_USER_SQL = "UPDATE users SET username = ?, email = ? WHERE id = ?";
    private static final String INSERT_USER_SQL = "INSERT INTO users (username, email) VALUES (?, ?)";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT id, username, email FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT id, username, email FROM users";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?";

    public User addUser(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUserById(Long id) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void deleteUser(Long id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void clearDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            // Удаляем все записи из таблицы пользователей
            statement.executeUpdate("DELETE FROM users");

            // Сбрасываем счетчик ID
            statement.executeUpdate("ALTER SEQUENCE users_id_seq RESTART WITH 1");
        } catch (SQLException e) {
            throw new SQLException("Failed to clear database", e);
        }
    }

}
