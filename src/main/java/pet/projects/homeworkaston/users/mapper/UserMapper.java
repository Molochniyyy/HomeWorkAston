package pet.projects.homeworkaston.users.mapper;

import pet.projects.homeworkaston.users.dto.UserDTO;
import pet.projects.homeworkaston.users.model.User;


public interface UserMapper {
    UserDTO toDTO(User user);

    User toUser(UserDTO userDTO);
}
