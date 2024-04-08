package pet.projects.homeworkaston.tasks.servlet;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import pet.projects.homeworkaston.exception.ErrorResponse;
import pet.projects.homeworkaston.tasks.dto.TaskDTO;
import pet.projects.homeworkaston.tasks.service.TaskService;
import pet.projects.homeworkaston.tasks.service.TaskServiceImpl;
import pet.projects.homeworkaston.utils.Helper;
import pet.projects.homeworkaston.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "TaskServlet", value = "/tasks/*")
@Slf4j
public class TaskServlet extends HttpServlet {

    private TaskService taskService;

    @Override
    public void init() {
        taskService = new TaskServiceImpl();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "";
        }

        switch (pathInfo) {
            case "":
                doGetTaskById(req, resp);
                break;
            case "/all":
                doGetAllTasksOfUser(req, resp);
                break;
        }
    }

    protected void doGetTaskById(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        TaskDTO taskDTO = taskService.getTaskById(Long.parseLong(id));
        try {
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.println(JsonUtils.convertObjectToJson(taskDTO));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doGetAllTasksOfUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("userId"));

        List<TaskDTO> tasks = taskService.getAllTasksOfUser(userId); // Получение списка всех задач из сервиса

        // Преобразование списка задач в JSON
        Gson gson = new Gson();
        String json = gson.toJson(tasks);

        // Отправка JSON-ответа
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Чтение данных о задаче из тела запроса
        BufferedReader reader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        // Преобразование JSON в объект TaskDTO с помощью Gson
        TaskDTO newTask = JsonUtils.convertJsonToObject(requestBody.toString(), TaskDTO.class);

        // Сохранение задачи
        taskService.addTask(newTask);
        // Отправка JSON-ответа
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(JsonUtils.convertObjectToJson(newTask)); // отправляем созданный объект TaskDTO обратно в формате JSON
        out.flush();

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        TaskDTO updatedTask = JsonUtils.convertJsonToObject(requestBody.toString(), TaskDTO.class);

        taskService.updateTask(updatedTask);

        resp.setStatus(HttpServletResponse.SC_OK);
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        Long taskId = 0L;
        if (id != null) {
            try {
                taskId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                Helper.putObjectToResponse(resp, new ErrorResponse("Id is not valid"));
                return;
            }
        }
        boolean isDeleted = taskService.deleteTask(taskId);
        if (!isDeleted) {
            Helper.putObjectToResponse(resp, new ErrorResponse("Task is not deleted"));
            return;
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
