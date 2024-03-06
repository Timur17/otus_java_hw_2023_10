package ru.otus.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends ListCrudRepository<Client, Long> {

    List<Client> findAll();

    Client save(Client client);

    Optional<Client> findById(long id);

    Optional<Client> findByName(String name);
}
