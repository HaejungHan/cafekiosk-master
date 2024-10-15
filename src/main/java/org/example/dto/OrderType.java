package org.example.dto;

public enum OrderType {
    TAKEOUT("TAKEOUT"),
    EATIN("EATIN");

    private String value;

    OrderType(String value) {
        this.value = value;
    }

    @Override
    public String toString() { return this.value; }

    public static OrderType fromString(String value) {
        if (value == null || value.length() == 0) {
            for (OrderType orderType : OrderType.values()) {
                if (orderType.value.equals(value)) {
                    return orderType;
                }
            }
        }
        throw new IllegalArgumentException("주문타입을 입력해주세요.");
    }
}
