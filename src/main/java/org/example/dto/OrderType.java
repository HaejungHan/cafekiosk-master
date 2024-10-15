package org.example.dto;

public enum OrderType {
    TAKEOUT("TAKEOUT"),
    EATIN("EATIN");

    private String value;

    OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static OrderType fromString(String value) {
        if (value != null) {
            for (OrderType orderType : OrderType.values()) {
                if (orderType.getValue().equalsIgnoreCase(value)) {
                    return orderType;
                }
            }
        }
        throw new IllegalArgumentException("주문타입을 입력해주세요.");
    }
}
