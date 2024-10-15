package org.example.controller;



import org.example.dto.MemberDTO;
import org.example.dto.MenuDTO;
import org.example.dto.OrderDTO;
import org.example.dto.OrderItemDTO;
import org.example.service.CafeService;
import org.example.view.AdminView;
import org.example.view.CafeView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class CafeController {
    private CafeService service;
    private CafeView view;
    private int memberId;

    public CafeController(CafeView view) {
        this.view = view;
        this.service = new CafeService();
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

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    private boolean authenticate(String memberId, String password) {
        // 인증 로직 (예: DB에서 확인)
        return true; // 임시로 항상 true 반환
    }

    public void registerMember(String memberId, String password, int auth) {
        MemberDTO member = new MemberDTO(0, memberId, password, auth);
        service.registerMember(member);
    }

    public void addToCart(MenuDTO menu, int quantity, String temperature) {
        OrderItemDTO orderItem = new OrderItemDTO(menu.getId(), quantity, temperature, menu.getPrice(), menu.getName()); // 생성자 사용
        BigDecimal totalPrice = menu.getPrice().multiply(BigDecimal.valueOf(quantity)); // BigDecimal로 계산
        orderItem.setTotalPrice(totalPrice); // 총 가격 설정
        service.addToCart(orderItem); // 서비스에 추가
        view.updateCart(); // 뷰 업데이트
    }

    public void createOrder(int memberId, List<OrderItemDTO> orderItems) {
        OrderDTO order = new OrderDTO(0, memberId, LocalDateTime.now(), orderItems);
        service.createOrder(order);
        view.showMessage("주문이 완료되었습니다!");
    }

    public List<MenuDTO> loadMenus() {
        List<MenuDTO> menus = service.getMenus();
       view.displayMenus(menus);
       return menus;
    }

    public String getMenuNameById(int menuId) {
        return service.getMenuNameById(menuId); // DAO를 통해 메뉴 이름 조회
    }
}

