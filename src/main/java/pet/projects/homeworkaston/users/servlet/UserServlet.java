package pet.projects.homeworkaston.users.servlet;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import pet.projects.homeworkaston.exception.ErrorResponse;
import pet.projects.homeworkaston.users.dto.UserDTO;
import pet.projects.homeworkaston.users.mapper.UserMapper;
import pet.projects.homeworkaston.users.mapper.UserMapperImpl;
import pet.projects.homeworkaston.users.service.UserService;
import pet.projects.homeworkaston.users.service.UserServiceImpl;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/users/*")
@Slf4j
public class UserServlet extends HttpServlet {

    private UserService userService;
    private UserMapper userMapper;

    @Override
    public void init() {
        userService = new UserServiceImpl();
        userMapper = new UserMapperImpl();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "";
        }

        switch (pathInfo) {
            case "":
                doGetUserById(req, resp);
                break;
            case "/all":
                doGetAllUsers(req, resp);
                break;
        }
    }

    protected void doGetUserById(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        UserDTO userDTO = userMapper.toDTO(userService.getUserById(Long.parseLong(id)));
        try {
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.println(JsonUtils.convertObjectToJson(userDTO));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }


    protected void doGetAllUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDTO> users = userService.getAllUsers(); // Получение списка всех пользователей из сервиса

        // Преобразование списка пользователей в JSON
        Gson gson = new Gson();
        String json = gson.toJson(users);

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
        // Чтение данных о пользователе из тела запроса
        BufferedReader reader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        // Преобразование JSON в объект UserDTO с помощью Gson
        UserDTO newUser = JsonUtils.convertJsonToObject(requestBody.toString(), UserDTO.class);

        // Сохранение пользователя
        userService.addUser(newUser);
        // Отправка JSON-ответа
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(JsonUtils.convertObjectToJson(newUser)); // отправляем созданный объект UserDTO обратно в формате JSON
        out.flush();
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        Long userId = 0L;
        if (id != null) {
            try {
                userId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                Helper.putObjectToResponse(resp, new ErrorResponse("Id is not valid"));
                return;
            }
        }
        boolean isDeleted = false;
        try {
            isDeleted = userService.deleteUser(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!isDeleted) {
            Helper.putObjectToResponse(resp, new ErrorResponse("User is not deleted"));
            return;
        }
        Helper.putObjectToResponse(resp, "200 OK");

    }


}
