package pet.projects.homeworkaston.tasks.mapper;


import pet.projects.homeworkaston.tasks.dto.TaskDTO;
import pet.projects.homeworkaston.tasks.model.Task;
import pet.projects.homeworkaston.users.model.User;

public class TaskMapperImpl implements TaskMapper{

    @Override
    public Task toTask(TaskDTO taskDto, User user) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setUser(user);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        return task;
    }

    @Override
    public TaskDTO toTaskDTO(Task task) {
        TaskDTO taskDto = new TaskDTO();
        taskDto.setId(task.getId());
        taskDto.setUserId(task.getUser().getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        return taskDto;
    }
}

