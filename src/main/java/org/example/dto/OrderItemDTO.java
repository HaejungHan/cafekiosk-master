package org.example.dto;

import org.example.controller.CafeController;

import java.math.BigDecimal;

public class OrderItemDTO {
    private int orderId;
    private int menuId; // 메뉴 ID
    private int quantity;
    private String temperature; // 온도 (ICED, HOT)
    private BigDecimal totalPrice;// 수량

    private String menuName;

    private CafeController controller;
    // 생성자
    public OrderItemDTO(int menuId, int quantity, String temperature, BigDecimal totalPrice, String menuName) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.temperature = temperature;
        this.totalPrice = totalPrice;
        this.controller = controller;
        this.menuName = menuName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMenuNameById(int menuId) {// 현재 메뉴 ID 설정
        return controller.getMenuNameById(menuId); // Controller를 통해 메뉴 이름 가져오기
    }

    @Override
    public String toString() {
        return "메뉴명: " + menuName + ", 수량: " + quantity + ", 온도: " + temperature + ", 총 가격: " + totalPrice;
    }
}
