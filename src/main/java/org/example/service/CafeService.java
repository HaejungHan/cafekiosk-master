package org.example.service;


import org.example.dao.MemberDAO;
import org.example.dao.MenuDAO;
import org.example.dao.OrderDAO;
import org.example.dao.OrderItemDAO;
import org.example.dto.*;

import java.util.ArrayList;
import java.util.List;

public class CafeService {
    private MemberDAO memberDAO = new MemberDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private MenuDAO menuDAO = new MenuDAO();
    private List<OrderItemDTO> cart = new ArrayList<>();
    private List<MenuDTO> menus;
    private OrderItemDTO orderItemDTO;

    public int registerMember(MemberDTO member) {
        System.out.println("회원이 추가되었습니다: " + member.getMemberId() + ", 비밀번호: " + member.getPassword());
        return memberDAO.addMember(member);
    }

    public int createOrder(OrderDTO order) {
        return orderDAO.addOrder(order);
    }

    public void createOrderItem(OrderItemDTO orderItem) {
        orderItemDAO.addOrderItem(orderItem);
    }

    public void addToCart(OrderItemDTO orderItem) {
        cart.add(orderItem);
    }

    public List<MenuDTO> getMenus() {
        return menuDAO.getAllMenus();
    }

    public List<OrderItemDTO> getCart() {
        return cart;
    }

    public String getMenuNameById(int id) {
        return menuDAO.getMenuNameById(id);
    }

    public boolean isMemberExists(int memberId) {
        return memberDAO.isMemberExists(memberId);
    }

}
