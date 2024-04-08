package pet.projects.homeworkaston.users.service;


import pet.projects.homeworkaston.users.dto.UserDTO;
import pet.projects.homeworkaston.users.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    UserDTO addUser(UserDTO userDto);

    boolean deleteUser(Long userId) throws SQLException;

    User getUserById(Long id);

    List<UserDTO> getAllUsers();
}
