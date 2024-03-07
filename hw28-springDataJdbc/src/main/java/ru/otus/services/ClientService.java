package ru.otus.services;

import java.util.List;
import java.util.Optional;
import ru.otus.model.Client;

public interface ClientService {
    List<Client> findAll();

    Optional<Client> findById(long id);

    Optional<Client> findByName(String name);

    Client findRandom();

    Optional<Client> save(Client client);
}
