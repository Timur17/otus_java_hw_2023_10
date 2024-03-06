package ru.otus.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Table("client")
public class Client implements Cloneable {

    @Id
    private final Long id;

    @NonNull
    private final String name;

    @NonNull
    @MappedCollection(idColumn = "client_id")
    private final List<Phone> phones;

    @NonNull
    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @PersistenceCreator
    public <E> Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
//        setClientToAddress(address);
        this.address = address;
//        setClientsToPhone(phones);
        this.phones = phones;
    }

    public Client(String name, Address address, List<Phone> phones) {
        this(null, name, address, phones);
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        List<Phone> copyPhones = new ArrayList<>();
        for (Phone phone : this.getPhones()) {
            copyPhones.add(phone.clone());
        }
        Address copeAddress = null;
        if (this.address != null) {
            copeAddress = new Address(this.address);
        }
        return new Client(this.id, this.name, copeAddress, copyPhones);
    }


    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='"
                + name + '\'' + "phones size "
                + (phones != null ? phones.size() : "0") + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id)
                && Objects.equals(name, client.name)
                && Objects.equals(phones, client.phones)
                && Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phones, address);
    }
}
