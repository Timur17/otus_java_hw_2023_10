package ru.otus.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("address")
public class Address {

    @Id
    private final Long id;

    @NonNull
    private final String street;

    @NonNull
    private final String clientId;

    @PersistenceCreator
    public Address(Long id, String street, String clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    public Address(String street, String clientId) {
        this(null, street, clientId);
    }
}
