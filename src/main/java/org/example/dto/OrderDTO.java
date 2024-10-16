package org.example.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private int id;
    private int memberId;
    private LocalDateTime orderDate;
    private List<OrderItemDTO> items;

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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

}

