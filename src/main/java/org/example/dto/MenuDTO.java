package org.example.dto;


import java.math.BigDecimal;

public class MenuDTO {
    private int id;
    private String name;
    private BigDecimal price;
    private Category category;
    private String imagePath;

    public MenuDTO(int id, String name, BigDecimal price, Category category, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public String getImagePath() {
        return imagePath;
    }

}

