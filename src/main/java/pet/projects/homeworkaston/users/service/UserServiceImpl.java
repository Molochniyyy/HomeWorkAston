package pet.projects.homeworkaston.users.service;

import lombok.RequiredArgsConstructor;
import pet.projects.homeworkaston.users.dto.UserDTO;
import pet.projects.homeworkaston.users.mapper.UserMapper;
import pet.projects.homeworkaston.users.model.User;
import pet.projects.homeworkaston.users.repository.UserRepository;


@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO addUser(UserDTO userDto) {
        return userMapper.toDTO(userRepository.addUser(userMapper.toUser(userDto)));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }
}

