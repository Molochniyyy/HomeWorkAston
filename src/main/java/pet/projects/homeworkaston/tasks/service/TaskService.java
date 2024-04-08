package pet.projects.homeworkaston.tasks.service;


import pet.projects.homeworkaston.tasks.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO addTask(TaskDTO taskDto);

    boolean deleteTask(Long taskId);

    TaskDTO getTaskById(Long id);
    public void updateTask(TaskDTO taskDTO);
    List<TaskDTO> getAllTasksOfUser(Long userId);
}

