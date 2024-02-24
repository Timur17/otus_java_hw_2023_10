package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.LoginService;
import ru.otus.config.DbServiceClientConfiguration;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerWithBasicSecurity;
import ru.otus.services.InMemoryLoginServiceImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3

    // Страница клиентов
    http://localhost:8080/clients
*/
public class WebServerWithBasicSecurity {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        var dbServiceClient = new DbServiceClientConfiguration().configure();

        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        LoginService loginService = new InMemoryLoginServiceImpl(userDao); // NOSONAR

        UsersWebServer usersWebServer =
                new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT, loginService, userDao, gson,
                        templateProcessor, dbServiceClient);

        usersWebServer.start();
        usersWebServer.join();
    }
}
