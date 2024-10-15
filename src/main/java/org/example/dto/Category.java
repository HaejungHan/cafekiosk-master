package org.example.dto;

public enum Category {
    COFFEE("커피"),
    DESSERT("디저트"),
    BEVERAGE("음료");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Category fromString(String category) {
        if (category != null) {
            for (Category c : values()) {
                if (c.name().equalsIgnoreCase(category.trim())) {
                    return c;
                }
            }
        }
        throw new IllegalArgumentException("Unknown category: " + category);
    }
}
