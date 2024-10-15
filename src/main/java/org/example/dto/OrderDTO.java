package org.example.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private int id;
    private int memberId; // 사용자 ID
    private LocalDateTime orderDate; // 주문 날짜
    private List<OrderItemDTO> items; // 주문 항목 리스트

    public OrderDTO(int id, int memberId, LocalDateTime orderDate, List<OrderItemDTO> items) {
        this.id = id;
        this.memberId = memberId;
        this.orderDate = orderDate;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}

