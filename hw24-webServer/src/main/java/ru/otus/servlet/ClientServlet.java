package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_RANDOM_USER = "clients";

    private final TemplateProcessor templateProcessor;
    private final DBServiceClient dbServiceClient;

    public ClientServlet(DBServiceClient dbServiceClient, TemplateProcessor templateProcessor) {
        this.dbServiceClient = dbServiceClient;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
//        List<Client> clients = new ArrayList<>();
        Map<String, Object> paramsMap = new HashMap<>();
//        userDao.findRandomUser().ifPresent(randomUser -> paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, randomUser));
//        var client = new Client(
//                1L,
//                "Vasya",  new Address(null, "AnyStreet"),
//                List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333")));
//        var client2 = new Client(
//                2L,
//                "Asya",  new Address(null, "Street"),
//                List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333")));
//        clients.add(client);
//        clients.add(client2);
        List<Client> clients = dbServiceClient.findAll();
        paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, clients);
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));

//        Map<String, Object> paramsMap = new HashMap<>();
//        response.setContentType("application/json;charset=UTF-8");
//        ServletOutputStream out = response.getOutputStream();
//        var client = new Client(
//                1L,
//                "Vasya");
////                new Address(null, "AnyStreet"),
////                List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333")));
//        out.print(gson.toJson(client));
    }
}
