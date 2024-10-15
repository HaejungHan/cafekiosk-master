package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.Category;
import org.example.dto.MenuDTO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    public List<MenuDTO> getAllMenus() {
        List<MenuDTO> menus = new ArrayList<>();
        String sql = "SELECT * FROM Menu";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                String categoryStr = rs.getString("category");
                Category category;

                try {
                    category = Category.fromString(categoryStr); // Enum으로 변환
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid category: " + categoryStr);
                    continue; // 잘못된 카테고리인 경우 스킵
                }

                String imagePath = rs.getString("imagePath");
                MenuDTO menu = new MenuDTO(id, name, price, category, imagePath); // 생성자 사용
                menus.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }

    public String getMenuNameById(int menuId) {
        String sql = "SELECT name FROM menu WHERE id = ?";
        String menuName = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, menuId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                menuName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuName;
    }

}
