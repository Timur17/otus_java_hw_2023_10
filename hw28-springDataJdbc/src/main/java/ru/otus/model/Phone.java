package ru.otus.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("phone")
public class Phone {

    @Id
    private final Long id;

    @NonNull
    private final String number;

    private final String clientId;

    public Phone(String number, String clientId) {
        this(null, number, clientId);
    }

    @PersistenceCreator
    public Phone(Long id, String number, String clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Phone clone() {
        return new Phone(this.id, this.number, this.clientId);
    }

    @Override
    public String toString() {
        return number;
    }
}
