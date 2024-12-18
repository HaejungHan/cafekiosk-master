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

    public int registerMember() {
        String id = member.generateMemberId();
        String pwd = member.generateRandomPassword();
        MemberDTO member = new MemberDTO(0, id, pwd, 0);
        return service.registerMember(member);
    }

//    public void registerAuth(String id, String pwd) {
//        MemberDTO member = new MemberDTO(0, id, pwd, 1);
//        service.registerMember(member);
//    }

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

    public boolean authenticateAdmin(String username, String password) {
        return service.isAdminAuthenticated(username, password); // 파라미터 선언 수정
    }

    public List<OrderItemDTO> getAllOrderItems() {
        return service.getAllOrderItems();
    }

    public void deleteMenu(String menuName) {
        service.deleteMenu(menuName);
    }

    public void addMenu(MenuDTO menu) {
        service.addMenu(menu);
    }

    public List<MenuDTO> loadMenusByCategory(Category category) {
        return service.loadMenusByCategory(category); // 특정 카테고리의 메뉴 목록을 로드
    }
}

