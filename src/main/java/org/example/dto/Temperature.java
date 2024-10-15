package org.example.dto;

public enum Temperature {
    ICED("차갑게"),
    HOT("뜨겁게");

    private final String displayTemperature;

    Temperature(String displayTemperature) {
        this.displayTemperature = displayTemperature;
    }

    @Override
    public String toString() {
        return displayTemperature;
    }
}
