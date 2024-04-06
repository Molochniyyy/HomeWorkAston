package pet.projects.homeworkaston.users.service;


import pet.projects.homeworkaston.users.dto.UserDTO;
import pet.projects.homeworkaston.users.model.User;

public interface UserService {
    UserDTO addUser(UserDTO userDto);

    void deleteUser(Long userId);

    User getUserById(Long id);
}
