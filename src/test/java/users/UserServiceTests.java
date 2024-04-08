package users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pet.projects.homeworkaston.users.dto.UserDTO;
import pet.projects.homeworkaston.users.mapper.UserMapper;
import pet.projects.homeworkaston.users.mapper.UserMapperImpl;
import pet.projects.homeworkaston.users.model.User;
import pet.projects.homeworkaston.users.repository.UserRepository;
import pet.projects.homeworkaston.users.service.UserService;
import pet.projects.homeworkaston.users.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository = new UserRepository();

    @Mock
    private UserMapper userMapper = new UserMapperImpl();

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @BeforeEach
    void setUp() throws SQLException {
        userRepository.clearDatabase();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        // Создаем объекты для теста
        UserDTO userDto = new UserDTO();
        userDto.setName("test");
        userDto.setEmail("test@example.com");
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setEmail("test@example.com");

        // Мокируем поведение userRepository.addUser
        when(userRepository.addUser(any(User.class))).thenReturn(user);

        // Вызываем метод, который тестируем
        UserDTO result = userService.addUser(userDto);

        // Проверяем результат
        assertEquals(user.getUsername(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testDeleteUser_WhenUserExists() throws SQLException {
        // Устанавливаем мокируемое поведение для userRepository.getUserById
        when(userRepository.getUserById(1L)).thenReturn(new User());

        // Вызываем метод, который тестируем
        boolean result = userService.deleteUser(1L);

        // Проверяем результат
        assertTrue(result);
        // Проверяем, что метод deleteUser был вызван у userRepository
        verify(userRepository, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_WhenUserDoesNotExist() throws SQLException {
        // Устанавливаем мокируемое поведение для userRepository.getUserById
        when(userRepository.getUserById(1L)).thenReturn(null);

        // Вызываем метод, который тестируем
        boolean result = userService.deleteUser(1L);

        // Проверяем результат
        assertFalse(result);
        // Проверяем, что метод deleteUser не был вызван у userRepository
        verify(userRepository, never()).deleteUser(1L);
    }

    @Test
    void testGetUserById() {
        // Создаем объект для теста
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setEmail("test@example.com");

        // Устанавливаем мокируемое поведение для userRepository.getUserById
        when(userRepository.getUserById(1L)).thenReturn(user);

        // Вызываем метод, который тестируем
        User result = userService.getUserById(1L);

        // Проверяем результат
        assertEquals(user, result);
    }

    @Test
    void testGetAllUsers() {
        // Создаем список пользователей для теста
        List<UserDTO> users = new ArrayList<>();
        UserDTO user1 = new UserDTO();
        user1.setName("test1");
        user1.setEmail("test1@example.com");
        users.add(user1);
        UserDTO user2 = new UserDTO();
        user2.setName("test2");
        user2.setEmail("test2@example.com");
        users.add(user2);

        userService.addUser(user1);
        userService.addUser(user2);

        // Устанавливаем мокируемое поведение для userRepository.getAllUsers
        when(userRepository.getAllUsers()).thenReturn(users.stream().map(userMapper::toUser)
                .collect(Collectors.toList()));

        // Вызываем метод, который тестируем
        List<UserDTO> result = userService.getAllUsers();

        // Проверяем результат
        assertEquals(users.size(), result.size());
        assertEquals(users.get(0).getId(), result.get(0).getId());
        assertEquals(users.get(0).getName(), result.get(0).getName());
        assertEquals(users.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(users.get(1).getId(), result.get(1).getId());
        assertEquals(users.get(1).getName(), result.get(1).getName());
        assertEquals(users.get(1).getEmail(), result.get(1).getEmail());
    }
}

