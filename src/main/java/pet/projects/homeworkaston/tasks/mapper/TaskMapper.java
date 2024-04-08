package pet.projects.homeworkaston.tasks.mapper;

import pet.projects.homeworkaston.tasks.dto.TaskDTO;
import pet.projects.homeworkaston.tasks.model.Task;
import pet.projects.homeworkaston.users.model.User;

public interface TaskMapper {
    Task toTask(TaskDTO taskDto, User user);
    TaskDTO toTaskDTO(Task task);
}
