package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.dto.ClientDto;
import ru.otus.model.Client;
import ru.otus.services.ClientService;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    private final String osData;
    private final String applicationYmlMessage;
    private final ClientService clientService;

    public ClientController(
            @Value("${app.client-list-page.msg:Тут может находиться ваша реклама}") String applicationYmlMessage,
            @Value("OS: #{T(System).getProperty(\"os.name\")}, "
                    + "JDK: #{T(System).getProperty(\"java.runtime.version\")}")
            String osData, ClientService clientService) {
        this.applicationYmlMessage = applicationYmlMessage;
        this.osData = osData;
        this.clientService = clientService;
    }

    @GetMapping("/client/create")
    public String clientCreateView(Model model) {
        model.addAttribute("client", new ClientDto());
        return "clientCreate";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(ClientDto clientDto) {
        Client client = clientDto.fromClientDtoToClient();
        Optional<Client> clientDb = clientService.save(client);
        System.out.println("::: " + clientDb.get());
        return new RedirectView("/", true);
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("osData", osData);
        model.addAttribute("applicationYmlMessage", applicationYmlMessage);
        return "clientsList";
    }
}
