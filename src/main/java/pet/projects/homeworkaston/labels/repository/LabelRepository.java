package pet.projects.homeworkaston.labels.repository;


import pet.projects.homeworkaston.labels.model.Label;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabelRepository {

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

    private static final String INSERT_LABEL_SQL = "INSERT INTO labels (name) VALUES (?)";

    private static final String UPDATE_LABEL_SQL = "UPDATE labels SET name = ? WHERE id = ?";
    private static final String SELECT_LABEL_BY_ID_SQL = "SELECT id, name FROM labels WHERE id = ?";
    private static final String SELECT_ALL_LABELS = "SELECT id, name FROM labels";
    private static final String DELETE_LABEL_SQL = "DELETE FROM labels WHERE id = ?";

    public Label addLabel(Label label) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LABEL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                label.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating label failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    public List<Label> getAllLabels() {
        List<Label> labels = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LABELS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Label label = new Label();
                label.setId(resultSet.getLong("id"));
                label.setName(resultSet.getString("name"));
                labels.add(label);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }

    public Label getLabelById(Long id) {
        Label label = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LABEL_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                label = new Label();
                label.setId(resultSet.getLong("id"));
                label.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    public void updateLabel(Label label) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LABEL_SQL)) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.setLong(2, label.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLabel(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LABEL_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

