package org.example.controller;



import org.example.dto.*;
import org.example.service.CafeService;
import org.example.view.AdminView;
import org.example.view.CafeView;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CafeController {
    private CafeService service;
    private CafeView view;
    private MemberDTO member;

    public CafeController(CafeView view) {
        this.view = view;
        this.service = new CafeService();
        this.member = new MemberDTO();
        init();
    }

    private void init() {
        view.setController(this);
        loadMenus();
    }

    public void login(String memberId, String password) {
        // 로그인 처리 로직
        // 인증 성공 시 AdminView로 이동
        if (authenticate(memberId, password)) {
            AdminView adminView = new AdminView(this);
            adminView.setVisible(true);
            view.dispose(); // 현재 창 닫기
        } else {
            view.showMessage("로그인 실패");
        }
    }

    private boolean authenticate(String memberId, String password) {
        // 인증 로직 (예: DB에서 확인)
        return true; // 임시로 항상 true 반환
    }

    public int registerMember() {
        String id = member.generateMemberId();
        String pwd = member.generateRandomPassword();
        MemberDTO member = new MemberDTO(0, id, pwd, 0);
        return service.registerMember(member);
    }

    public void registerAuth(String id, String pwd) {
        MemberDTO member = new MemberDTO(0, id, pwd, 1);
        service.registerMember(member);
    }

    public int createOrder(int memberId, List<OrderItemDTO> orderItems) {
        OrderDTO order = new OrderDTO(0, memberId, LocalDateTime.now(), orderItems);
        return service.createOrder(order);
    }

    public void createOrderItem(OrderItemDTO orderItem) {
        service.createOrderItem(orderItem);
    }

    public List<MenuDTO> loadMenus() {
        List<MenuDTO> menus = service.getMenus();
       view.displayMenus(menus);
       return menus;
    }

    public String getMenuNameById(int menuId) {
        return service.getMenuNameById(menuId); // DAO를 통해 메뉴 이름 조회
    }

    public boolean isMemberExists(int memberId) {
        return service.isMemberExists(memberId);
    }
}

