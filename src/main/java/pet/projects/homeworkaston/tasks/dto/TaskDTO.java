package pet.projects.homeworkaston.tasks.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String status;
}

