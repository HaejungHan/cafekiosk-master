package org.example.dto;

public class MemberDTO {
    private int id; // ID를 int로 추가
    private String memberId; // ID를 userId로 변경
    private String password;
    private int auth;

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

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }
}

