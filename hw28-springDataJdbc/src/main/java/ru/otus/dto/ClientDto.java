package ru.otus.dto;

import lombok.Data;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.util.Arrays;

@Data
public class ClientDto {
    String name;
    String address;
    String phones;

    public Client fromClientDtoToClient() {
        String[] phones = this.getPhones().split(",");
        return new Client(null,
                this.getName(),
                new Address(null, this.getAddress()),
                Arrays.stream(phones).map(phone -> new Phone(null, phone)).toList());
    }
}
