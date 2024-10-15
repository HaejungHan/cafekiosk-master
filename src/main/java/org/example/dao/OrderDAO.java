package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.OrderDTO;

import java.sql.*;

public class OrderDAO {
    public int addOrder(OrderDTO order) {
        String sql = "INSERT INTO `Order` (memberId, orderDate) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getMemberId());
            pstmt.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("주문 ID 생성 실패");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // 실패 시 적절한 에러 처리
        }
    }
}
