package pet.projects.homeworkaston.labels.servlet;


import com.google.gson.Gson;
import pet.projects.homeworkaston.exception.ErrorResponse;
import pet.projects.homeworkaston.labels.dto.LabelDTO;
import pet.projects.homeworkaston.labels.service.LabelService;
import pet.projects.homeworkaston.labels.service.LabelServiceImpl;
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

@WebServlet(name = "LabelServlet", value = "/labels/*")
public class LabelServlet extends HttpServlet {
    private LabelService labelService;

    @Override
    public void init() throws ServletException {
        labelService = new LabelServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "";
        }

        switch (pathInfo) {
            case "":
                getAllLabels(request, response);
                break;
            default:
                // Handle other cases if needed
                break;
        }
    }

    private void getAllLabels(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<LabelDTO> labels = labelService.getAllLabels();

        Gson gson = new Gson();
        String json = gson.toJson(labels);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        LabelDTO newLabel = JsonUtils.convertJsonToObject(requestBody.toString(), LabelDTO.class);

        LabelDTO addedLabel = labelService.addLabel(newLabel);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JsonUtils.convertObjectToJson(addedLabel));
        out.flush();

        response.setStatus(HttpServletResponse.SC_CREATED);
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

        LabelDTO updatedLabel = JsonUtils.convertJsonToObject(requestBody.toString(), LabelDTO.class);

        labelService.updateLabel(updatedLabel);

        resp.setStatus(HttpServletResponse.SC_OK);
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Long labelId = 0L;
        if (id != null) {
            try {
                labelId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                Helper.putObjectToResponse(response, new ErrorResponse("Id is not valid"));
                return;
            }
        }

        boolean isDeleted = labelService.deleteLabel(labelId);
        if (!isDeleted) {
            Helper.putObjectToResponse(response, new ErrorResponse("Label is not deleted"));
            return;
        }

        Helper.putObjectToResponse(response, "200 OK");
    }
}

