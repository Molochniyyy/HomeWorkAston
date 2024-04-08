package users;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import pet.projects.homeworkaston.users.model.User;
import pet.projects.homeworkaston.users.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

;import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTests {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
    static UserRepository userRepository = new UserRepository();

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }
    @BeforeEach
    void afterEach() throws SQLException {
        userRepository.clearDatabase();
    }
    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Test
    void testAddUser() throws SQLException {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        User addedUser = userRepository.addUser(user);

        assertEquals(user.getUsername(), addedUser.getUsername());
        assertEquals(user.getEmail(), addedUser.getEmail());
    }

    @Test
    void testGetAllUsers() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@test.test");
        userRepository.addUser(user);
        List<User> users = userRepository.getAllUsers();

        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        User addedUser = userRepository.addUser(user);

        User retrievedUser = userRepository.getUserById(1L);

        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getId());
        assertEquals(addedUser.getUsername(), retrievedUser.getUsername());
        assertEquals(addedUser.getEmail(), retrievedUser.getEmail());
    }

    @Test
    void testDeleteUser() throws SQLException {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        User addedUser = userRepository.addUser(user);

        userRepository.deleteUser(1L);

        User deletedUser = userRepository.getUserById(1L);

        assertNull(deletedUser);
    }
}

