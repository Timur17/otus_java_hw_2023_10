package ru.otus.model;

import java.util.Set;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("client")
public class Client {

    @Id
    private final Long id;

    @NonNull
    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @PersistenceCreator
    public <E> Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Client(String name, Address address, Set<Phone> phones) {
        this(null, name, address, phones);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + ", phones=" + phones + ", address=" + address + '}';
    }
}
