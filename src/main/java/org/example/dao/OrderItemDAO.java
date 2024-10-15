package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.OrderItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderItemDAO {

    public void addOrderItem(OrderItemDTO orderItem) {
        String sql = "INSERT INTO OrderItem (orderId, menuId, quantity, temperature, totalPrice, orderType) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getMenuId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setString(4, orderItem.getTemperature());
            pstmt.setBigDecimal(5, orderItem.getTotalPrice());
            pstmt.setString(6, String.valueOf(orderItem.getOrderType()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
