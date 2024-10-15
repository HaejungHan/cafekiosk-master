package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.OrderItemDTO;
import org.example.dto.OrderType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<OrderItemDTO> getAllOrderItems() {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        String sql = "SELECT oi.orderId, m.name AS menuName, oi.quantity, oi.temperature, oi.totalPrice, oi.orderType " +
                "FROM OrderItem oi " +
                "JOIN Menu m ON oi.menuId = m.id"; // Menu 테이블과 조인

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                String menuName = rs.getString("menuName"); // 메뉴 이름 가져오기
                int quantity = rs.getInt("quantity");
                String temperature = rs.getString("temperature");
                BigDecimal totalPrice = rs.getBigDecimal("totalPrice");
                String orderTypeString = rs.getString("orderType"); // 데이터베이스에서 가져온 주문 타입 문자열

                OrderType orderType = OrderType.fromString(orderTypeString);

                // OrderItemDTO 객체 생성
                OrderItemDTO orderItem = new OrderItemDTO(orderId, menuName, quantity, temperature, totalPrice, orderType);
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems;
    }
}
