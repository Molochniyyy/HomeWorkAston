package pet.projects.homeworkaston.tasks.repository;


import pet.projects.homeworkaston.tasks.model.Task;
import pet.projects.homeworkaston.users.model.User;
import pet.projects.homeworkaston.users.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final UserRepository userRepository;

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

    private static final String INSERT_TASK_SQL = "INSERT INTO tasks (user_id, title, description, status) VALUES (?, ?, ?, ?)";
    private static final String SELECT_TASK_BY_ID_SQL = "SELECT id, user_id, title, description, status FROM tasks WHERE id = ?";
    private static final String SELECT_ALL_TASKS_OF_USER_SQL = "SELECT id, user_id, title, description, status FROM tasks WHERE user_id = ?";
    private static final String UPDATE_TASK_SQL = "UPDATE tasks SET title = ?, description = ?, status = ? WHERE id = ?";
    private static final String DELETE_TASK_SQL = "DELETE FROM tasks WHERE id = ?";

    public TaskRepository() {
        userRepository = new UserRepository();
    }

    public Task addTask(Task task) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, task.getUser().getId());
            preparedStatement.setString(2, task.getTitle());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getStatus());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                task.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public List<Task> getAllTasksOfUser(Long userId) {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASKS_OF_USER_SQL)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setUser(userRepository.getUserById(userId));
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setStatus(resultSet.getString("status"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public Task getTaskById(Long id) {
        Task task = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task = new Task();
                task.setId(resultSet.getLong("id"));
                User user = userRepository.getUserById(resultSet.getLong("user_id"));
                task.setUser(user);
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setStatus(resultSet.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public void updateTask(Task task) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK_SQL)) {
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getStatus());
            preparedStatement.setLong(4, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteTask(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

