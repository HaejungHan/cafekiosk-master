package org.example.dto;

import org.example.controller.CafeController;

import java.math.BigDecimal;

public class OrderItemDTO {
    private int orderId;
    private int menuId;
    private int quantity;
    private String temperature;
    private BigDecimal totalPrice;
    private String menuName;
    private OrderType orderType;

    private CafeController controller;

    public OrderItemDTO() {
    }

    public OrderItemDTO(
            int orderId, String menuName, int quantity, String temperature,
            BigDecimal totalPrice, OrderType orderType) {
        this.orderId = orderId;
        this.menuName = menuName;
        this.quantity = quantity;
        this.temperature = temperature;
        this.totalPrice = totalPrice;
        this.orderType = orderType;
    }

    public void initializeOrderItem(
            int menuId, int quantity, String temperature, BigDecimal totalPrice,
            String menuName, OrderType orderType) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.temperature = temperature;
        this.totalPrice = totalPrice;
        this.menuName = menuName;
        this.orderType = orderType;
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

    public int getQuantity() {
        return quantity;
    }

    public String getTemperature() {
        return temperature;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getMenuName() {
        return menuName;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public String toString() {
        return "메뉴명: " + menuName + ", 수량: " + quantity + ", 온도: " + temperature + ", 주문타입: " + orderType +",총 가격: " + totalPrice;
    }
}
