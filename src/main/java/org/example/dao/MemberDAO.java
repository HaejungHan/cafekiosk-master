package org.example.dao;

import org.example.database.DBConnection;
import org.example.dto.MemberDTO;

import java.sql.*;

public class MemberDAO {

    public int addMember(MemberDTO member) {
        String sql = "INSERT INTO Member (memberId, password, auth) VALUES (?, ?, ?)";
        int generatedId = -1; // 기본값 설정

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // 키 반환 요청
            pstmt.setString(1, member.getMemberId());
            pstmt.setString(2, member.getPassword());
            pstmt.setInt(3, member.getAuth());

            pstmt.executeUpdate();

            // 생성된 키 가져오기
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1); // 첫 번째 키를 가져옴
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId; // 생성된 ID 반환
    }

    public boolean isMemberExists(int id) {
        String sql = "SELECT COUNT(*) FROM Member WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
