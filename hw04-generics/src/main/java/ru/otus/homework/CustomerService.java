package ru.otus.homework;

import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    TreeMap<Customer, String> map = new TreeMap<>((o1, o2) -> {
        if (o1.getScores() > o2.getScores()) {
            return 1;
        } else if (o1.getScores() < o2.getScores()) {
            return -1;
        } else {
            return 0;
        }
    });

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> origin = map.firstEntry();
        return copy(origin);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> origin = map.higherEntry(customer);
        return copy(origin);
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> copy(Map.Entry<Customer, String> origin) {
        if (origin == null) {
            return null;
        }
        return new Map.Entry<>() {
            @Override
            public Customer getKey() {
                return origin.getKey().copy();
            }

            @Override
            public String getValue() {
                return origin.getValue();
            }

            @Override
            public String setValue(String value) {
                return origin.setValue(value);
            }
        };
    }
}
