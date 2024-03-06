package ru.otus.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

    private Long id;

    private String street;

    private Client client;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Address(Address address) {
        this.id = address.id;
        this.street = address.street;
        this.client = address.client;
    }
}
