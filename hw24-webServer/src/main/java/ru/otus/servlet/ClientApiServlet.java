package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.service.DBServiceClient;

import java.io.IOException;
import java.util.stream.Collectors;

public class ClientApiServlet extends HttpServlet {
    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int g = 7 + 6;
        String value = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        int k = 9;
        extractIdFromRequest(request);
        HttpServletRequest temp = request;
        response.setContentType("text/html");
        ServletOutputStream out = response.getOutputStream();
        out.print("Successfully");
    }

    private long extractIdFromRequest(HttpServletRequest request) throws IOException {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
        return Long.parseLong(id);
    }
}
