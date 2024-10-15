package org.example.service;


import org.example.dao.MemberDAO;
import org.example.dao.MenuDAO;
import org.example.dao.OrderDAO;
import org.example.dao.OrderItemDAO;
import org.example.dto.MemberDTO;
import org.example.dto.MenuDTO;
import org.example.dto.OrderDTO;
import org.example.dto.OrderItemDTO;

import java.util.ArrayList;
import java.util.List;

public class CafeService {
    private MemberDAO memberDAO = new MemberDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private MenuDAO menuDAO = new MenuDAO();
    private List<OrderItemDTO> cart = new ArrayList<>();
    private List<MenuDTO> menus;

    public void registerMember(MemberDTO member) {
        memberDAO.addMember(member);
    }


    public void createOrder(OrderDTO order) {
        orderDAO.addOrder(order);
        for (OrderItemDTO item : order.getItems()) {
            item.setOrderId(order.getId()); // 주문 ID 설정
            orderItemDAO.addOrderItem(item);
        }
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
}
