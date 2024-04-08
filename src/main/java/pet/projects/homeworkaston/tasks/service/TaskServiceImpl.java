package pet.projects.homeworkaston.tasks.service;


import pet.projects.homeworkaston.tasks.dto.TaskDTO;
import pet.projects.homeworkaston.tasks.mapper.TaskMapper;
import pet.projects.homeworkaston.tasks.mapper.TaskMapperImpl;
import pet.projects.homeworkaston.tasks.model.Task;
import pet.projects.homeworkaston.tasks.repository.TaskRepository;
import pet.projects.homeworkaston.users.model.User;
import pet.projects.homeworkaston.users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl() {
        taskRepository = new TaskRepository();
        taskMapper = new TaskMapperImpl();
        userRepository = new UserRepository();
    }

    @Override
    public TaskDTO addTask(TaskDTO taskDto) {
        User user = userRepository.getUserById(taskDto.getUserId());
        Task task = taskMapper.toTask(taskDto, user);
        task = taskRepository.addTask(task);
        return taskMapper.toTaskDTO(task);
    }

    @Override
    public boolean deleteTask(Long taskId) {
        if (taskRepository.getTaskById(taskId) != null) {
            taskRepository.deleteTask(taskId);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.getTaskById(id);
        if (task != null) {
            return taskMapper.toTaskDTO(task);
        } else {
            return null;
        }
    }

    @Override
    public void updateTask(TaskDTO taskDTO) {
        User user = userRepository.getUserById(taskDTO.getUserId());
        Task task = taskMapper.toTask(taskDTO, user);
        taskRepository.updateTask(task);
    }


    @Override
    public List<TaskDTO> getAllTasksOfUser(Long userId) {
        List<Task> tasks = taskRepository.getAllTasksOfUser(userId);
        return tasks.stream()
                .map(taskMapper::toTaskDTO)
                .collect(Collectors.toList());
    }
}

