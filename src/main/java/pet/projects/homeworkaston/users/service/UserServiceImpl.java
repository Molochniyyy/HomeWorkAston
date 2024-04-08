package pet.projects.homeworkaston.users.service;

import pet.projects.homeworkaston.users.dto.UserDTO;
import pet.projects.homeworkaston.users.mapper.UserMapper;
import pet.projects.homeworkaston.users.mapper.UserMapperImpl;
import pet.projects.homeworkaston.users.model.User;
import pet.projects.homeworkaston.users.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl() {
        this.userRepository = new UserRepository();
        this.userMapper = new UserMapperImpl();
    }


    @Override
    public UserDTO addUser(UserDTO userDto) {
        return userMapper.toDTO(userRepository.addUser(userMapper.toUser(userDto)));
    }

    @Override
    public boolean deleteUser(Long userId) throws SQLException {
        if (userRepository.getUserById(userId) != null) {
            userRepository.deleteUser(userId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.getAllUsers();

        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

}

