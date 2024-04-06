package pet.projects.homeworkaston.users.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import pet.projects.homeworkaston.users.dto.UserDTO;
import pet.projects.homeworkaston.users.mapper.UserMapper;
import pet.projects.homeworkaston.users.service.UserService;
import pet.projects.homeworkaston.utils.JsonUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/users")
@RequiredArgsConstructor
public class UserServlet extends HttpServlet {

    private final UserService userService;
    private final UserMapper userMapper;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*String id = req.getParameter("id");
        UserDTO userDTO = userMapper.toDTO(userService.getUserById(Long.parseLong(id)));
        try {
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.println(JsonUtils.convertObjectToJson(userDTO));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        resp.setContentType("text/html");
        String message = "Hello";
        // Hello
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
