package pet.projects.homeworkaston.users.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    Long id;
    String name;
    String email;
}
