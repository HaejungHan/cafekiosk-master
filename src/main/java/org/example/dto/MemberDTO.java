package org.example.dto;

import java.security.SecureRandom;

public class MemberDTO {
    private int id;
    private String memberId;
    private String password;
    private int auth;

    public MemberDTO() {
    }

    public MemberDTO(int id, String memberId, String password, int auth) {
        this.id = id;
        this.memberId = memberId;
        this.password = password;
        this.auth = auth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getPassword() {
        return password;
    }

    public int getAuth() {
        return auth;
    }

    public String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(8); // 8자리 비밀번호

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }

    public String generateMemberId() {
        int memberId = (int) (Math.random() * 1000);
        return String.valueOf(memberId);
    }
}

