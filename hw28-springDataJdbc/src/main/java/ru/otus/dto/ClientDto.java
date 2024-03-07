package ru.otus.dto;

import lombok.Data;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
public class ClientDto {
    String name;
    String address;
    String phones;

    public Client fromClientDtoToClient() {
        String[] phones = this.getPhones().split(",");
        return new Client(this.getName(),
                new Address( this.getAddress(), null),
                Arrays.stream(phones).map(phone -> new Phone(phone, null)).collect(Collectors.toSet()));
    }
}
