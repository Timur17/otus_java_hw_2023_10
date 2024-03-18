package ru.otus.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.model.Client;

public interface ClientRepository extends ListCrudRepository<Client, Long> {

    List<Client> findAll();

    <S extends Client> S save(S client);

    Optional<Client> findById(long id);

    Optional<Client> findByName(String name);
}
