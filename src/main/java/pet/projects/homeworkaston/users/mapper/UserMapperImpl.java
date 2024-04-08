package pet.projects.homeworkaston.users.mapper;

import pet.projects.homeworkaston.users.dto.UserDTO;
import pet.projects.homeworkaston.users.model.User;

public class UserMapperImpl implements UserMapper{
    @Override
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    @Override
    public User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
