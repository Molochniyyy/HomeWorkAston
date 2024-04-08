package pet.projects.homeworkaston.utils;

import pet.projects.homeworkaston.exception.ErrorResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Helper {
    public static void putObjectToResponse(HttpServletResponse response, Object object) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if (object instanceof Throwable) {
                ErrorResponse errorResponse = new ErrorResponse(((Throwable) object).getMessage());
                String json = JsonUtils.convertObjectToJson(errorResponse);
                out.print(json);
            } else {
                String json = JsonUtils.convertObjectToJson(object);
                out.print(json);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
