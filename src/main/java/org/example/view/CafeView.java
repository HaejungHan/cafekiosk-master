package org.example.view;


import org.example.controller.CafeController;
import org.example.dto.MenuDTO;
import org.example.dto.OrderItemDTO;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CafeView extends JFrame {
    private CafeController controller;
    private List<OrderItemDTO> cart;
    private DefaultListModel<MenuDTO> menuListModel;
    private DefaultListModel<Object> cartListModel;
    private JList<String> cartList;

    public CafeView() {
        setTitle("카페 키오스크");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cart = new ArrayList<>();
        menuListModel = new DefaultListModel<>();
        cartListModel = new DefaultListModel<>();

        // 초기 화면 구성
        JPanel buttonPanel = new JPanel();
        JButton takeoutButton = new JButton("Takeout");
        JButton eatInButton = new JButton("Eatin");
        JButton adminButton = new JButton("Admin");

        takeoutButton.addActionListener(e -> openMenuView());
        eatInButton.addActionListener(e -> openMenuView());
        adminButton.addActionListener(e -> openLoginView());

        buttonPanel.add(takeoutButton);
        buttonPanel.add(eatInButton);
        buttonPanel.add(adminButton);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void openMenuView() {
        int memberId = generateMemberId(); // memberId 생성
        controller.setMemberId(memberId); // controller에 memberId 설정

        // 기존 화면 제거
        getContentPane().removeAll();
        setTitle("주문 메뉴");

        // 메뉴 패널 생성
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 3, 8, 10)); // 3행 3열 설정, 카드 간의 간격 추가

        // 장바구니 패널 생성
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS)); // 세로로 쌓기
        cartPanel.setBorder(BorderFactory.createTitledBorder("장바구니")); // 테두리 추가
        cartPanel.setBackground(Color.LIGHT_GRAY); // 배경색 설정
        cartPanel.setPreferredSize(new Dimension(350, 400)); // 크기 설정

        JLabel cartLabel = new JLabel("장바구니");
        cartLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // 글꼴 스타일
        cartLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 가운데 정렬
        cartPanel.add(cartLabel);

        JList<Object> cartList = new JList<>(cartListModel); // 장바구니 리스트
        JScrollPane cartScrollPane = new JScrollPane(cartList); // 스크롤 가능하게 설정
        cartScrollPane.setPreferredSize(new Dimension(240, 300)); // 스크롤 패널 크기 조정
        cartPanel.add(cartScrollPane); // 장바구니 리스트 추가

        // 메뉴 데이터 가져오기
        List<MenuDTO> menus = controller.loadMenus(); // 메뉴 데이터 로드

        for (MenuDTO menu : menus) {
            // 카드 형태의 패널 생성
            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(new BorderLayout());
            cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // 카드 테두리
            cardPanel.setPreferredSize(new Dimension(150, 150)); // 카드 크기 조정
            cardPanel.setBackground(Color.WHITE); // 카드 배경색 설정
            cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 카드 내부 여백 추가

            // 메뉴 이미지 로드
            String imagePath = "/images/" + menu.getImagePath(); // 리소스 경로
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 이미지 크기 조정
            ImageIcon imageIcon = new ImageIcon(scaledImage);

            // 이미지 레이블 설정
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);

            // 이름과 가격 레이블을 위한 패널 생성
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // 세로로 쌓기
            textPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT); // 가운데 정렬

            // 메뉴 이름 표시
            JLabel nameLabel = new JLabel(menu.getName(), JLabel.CENTER);
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13)); // 이름 글꼴 스타일 변경
            nameLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
            textPanel.add(nameLabel); // 이름 추가

            // 가격 표시
            JLabel priceLabel = new JLabel(menu.getPrice().toPlainString() + "원", JLabel.CENTER); // 가격 표시
            priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 10)); // 가격 글꼴 스타일 변경
            priceLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
            textPanel.add(priceLabel); // 가격 추가

            // 주문하기 버튼
            JButton orderButton = new JButton("주문하기");
            orderButton.setFont(new Font("SansSerif", Font.PLAIN, 12)); // 버튼 글꼴 스타일 변경
            orderButton.setBackground(Color.LIGHT_GRAY); // 버튼 배경색
            orderButton.addActionListener(e -> {
                addToCart(menu); // 메뉴를 카트에 추가
                displayCart();
            });
            textPanel.add(orderButton); // 버튼을 textPanel에 추가하여 메뉴 이름 아래에 위치

            // 카드 패널에 추가
            cardPanel.add(imageLabel, BorderLayout.NORTH); // 이미지 추가
            cardPanel.add(textPanel, BorderLayout.CENTER); // 이름과 가격 및 버튼 패널 추가

            // 메뉴 패널에 카드 추가
            menuPanel.add(cardPanel);
        }

        // 메뉴 패널과 장바구니 패널을 JFrame에 추가
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(menuPanel), BorderLayout.CENTER);
        mainPanel.add(cartPanel, BorderLayout.EAST); // 장바구니 패널 오른쪽에 추가

        add(mainPanel);
        revalidate(); // 레이아웃 재조정
        repaint(); // 화면 업데이트
    }


    private void openLoginView() {
        // 로그인 창 열기
        String memberId = JOptionPane.showInputDialog("회원 ID:");
        String password = JOptionPane.showInputDialog("비밀번호:");
        controller.login(memberId, password);
    }

    private int generateMemberId() {
        return (int) (Math.random() * 1000);
    }

    public void setController(CafeController controller) {
        this.controller = controller;
    }

    public void displayMenus(List<MenuDTO> menus) {
        menuListModel.clear();
        for (MenuDTO menu : menus) {
            menuListModel.addElement(menu);
        }
    }

    public void updateCart() {
        cartListModel.clear(); // 현재 리스트 비우기
        for (OrderItemDTO item : cart) {
            cartListModel.addElement(item); // toString() 사용
        }
    }

    private void registerMember() {
        String memberId = JOptionPane.showInputDialog("회원 ID:");
        String password = JOptionPane.showInputDialog("비밀번호:");
        String authStr = JOptionPane.showInputDialog("권한(0 또는 1):");
        int auth = Integer.parseInt(authStr);
        controller.registerMember(memberId, password, auth);
    }

    private void addToCart(MenuDTO menu) {
        if (menu != null) {
            String quantityStr = JOptionPane.showInputDialog("수량:");
            int quantity = Integer.parseInt(quantityStr);
            String temperature = JOptionPane.showInputDialog("온도 (ICED, HOT):");

            // 총 가격 계산
            BigDecimal totalPrice = menu.getPrice().multiply(BigDecimal.valueOf(quantity));

            // OrderItemDTO 생성
            OrderItemDTO orderItem = new OrderItemDTO(menu.getId(), quantity, temperature, totalPrice, menu.getName());
            cart.add(orderItem); // 카트에 추가

            // 총 가격 보여주기
            JOptionPane.showMessageDialog(null, "총 가격: " + totalPrice.toString() + "원", "총 가격", JOptionPane.INFORMATION_MESSAGE);

            updateCart(); // 카트 UI 업데이트
        } else {
            showMessage("메뉴를 선택하세요.");
        }
    }

    public void displayCart() {
        for (int i = 0; i < cartListModel.getSize(); i++) {
            OrderItemDTO item = (OrderItemDTO) cartListModel.getElementAt(i);
            System.out.println(item.toString()); // 출력
        }
    }

//    private void order() {
//        String memberIdStr = JOptionPane.showInputDialog("회원 ID:");
//        int memberId = Integer.parseInt(memberIdStr);
//
//        List<OrderItemDTO> orderItems = new ArrayList<>();
//
//        // 장바구니 항목 가져오기
//        for (int i = 0; i < cartListModel.getSize(); i++) {
//            OrderItemDTO orderItem = cartListModel.getElementAt(i);
//            orderItems.add(orderItem);
//        }
//
//        // 주문 생성
//        controller.createOrder(memberId, orderItems);
//    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}

