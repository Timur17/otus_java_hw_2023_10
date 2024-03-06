package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.model.Address;
import ru.otus.model.Client;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Phone;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    private final String osData;
    private final String applicationYmlMessage;
//    private final ClientService clientService;

    public ClientController(
            @Value("${app.client-list-page.msg:Тут может находиться ваша реклама}") String applicationYmlMessage,
            @Value("OS: #{T(System).getProperty(\"os.name\")}, "
                    + "JDK: #{T(System).getProperty(\"java.runtime.version\")}")
            String osData) {
        this.applicationYmlMessage = applicationYmlMessage;
        this.osData = osData;
//        this.clientService = clientService;
    }

    @GetMapping("/client/create")
    public String clientCreateView(Model model) {
        Client client = new Client(1L, "test", new Address(1L, "address"),
                List.of(new Phone(1l, "1231231231"), new Phone(2l, "22222222")));
        model.addAttribute("client", client);
        return "clientCreate";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(Client client) {
        return new RedirectView("/", true);
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = new ArrayList<>();
        Client client = new Client(1L, "test", new Address(1L, "address"),
                List.of(new Phone(1l, "1231231231"), new Phone(2l, "22222222")));
        clients.add(client);
        model.addAttribute("clients", clients);
        model.addAttribute("osData", osData);
        model.addAttribute("applicationYmlMessage", applicationYmlMessage);
        return "clientsList";
    }
}
