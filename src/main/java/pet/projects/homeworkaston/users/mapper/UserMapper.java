package pet.projects.homeworkaston.users.mapper;

import org.mapstruct.Mapper;
import pet.projects.homeworkaston.users.dto.UserDTO;
import pet.projects.homeworkaston.users.model.User;


@Mapper
public interface UserMapper {
    UserDTO toDTO(User user);

    User toUser(UserDTO userDTO);
}
