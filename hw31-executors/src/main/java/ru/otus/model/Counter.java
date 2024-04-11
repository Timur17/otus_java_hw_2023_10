package ru.otus.model;

import lombok.Data;

@Data
public class Counter {
    private boolean reverse = false;
    private int value = 0;
    private boolean order;

    public Counter(boolean order) {
        this.order = order;
    }

    public void executeLogic() {
        if (!reverse) {
            value++;
            if (value == 10) {
                reverse = true;
            }
        } else {
            value--;
            if (value == 0) {
                reverse = false;
            }
        }
    }

    public void changeOrder() {
        order = !order;
    }
}
