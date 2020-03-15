package ru.vnevzorov.Shop.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum Status {
    CREATED("СОЗДАН"),
    CONFIRMED("ПОДТВЕРЖДЕН"),
    IN_PROGRESS("В ПРОЦЕССЕ"),
    DELIVERED("ДОСТАВЛЕН"),
    CANCELLED("ОТМЕНЕН");

    private String translateName;

    public static final Map<Status, List<Status>> statusMap = Map.of(CREATED, Arrays.asList(CONFIRMED, CANCELLED),
                                                                    CONFIRMED, Arrays.asList(IN_PROGRESS, CANCELLED),
                                                                    IN_PROGRESS, Arrays.asList(DELIVERED, CANCELLED));

    Status() {
    }

    Status(String translateName) {
        this.translateName = translateName;
    }

    public String getTranslateName() {
        return translateName;
    }

    public void setTranslateName(String translateName) {
        this.translateName = translateName;
    }
}
