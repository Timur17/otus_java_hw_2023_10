package ru.otus.services;

import ru.otus.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAll();

    Optional<Client> findById(long id);

    Optional<Client> findByName(String name);

    Client findRandom();

    Optional<Client> save(Client client);
}