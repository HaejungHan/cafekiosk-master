package org.example.view;


import org.example.controller.CafeController;
import org.example.database.DBConnection;
import org.example.dto.MemberDTO;
import org.example.dto.MenuDTO;
import org.example.dto.OrderItemDTO;
import org.example.dto.OrderType;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CafeView extends JFrame {
    private CafeController controller;
    private List<OrderItemDTO> cart;
    private DefaultListModel<MenuDTO> menuListModel;
    private DefaultListModel<OrderItemDTO> cartListModel;
    private OrderType currentOrderType;

    public CafeView() {
        setTitle("카페 키오스크");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cart = new ArrayList<>();
        menuListModel = new DefaultListModel<>();
        cartListModel = new DefaultListModel<>();

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Initialize the button panel for Takeout and Eat In
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10)); // 2 buttons in a grid

        // Create Takeout and Eat In buttons
        JButton takeoutButton = createStyledButton("Takeout");
        JButton eatInButton = createStyledButton("Eat In");

        // Add action listeners
        takeoutButton.addActionListener(e -> {
            currentOrderType = OrderType.TAKEOUT; // Set order type to Takeout
            openMenuView();
        });

        eatInButton.addActionListener(e -> {
            currentOrderType = OrderType.EATIN; // Set order type to Eat In
            openMenuView();
        });

        // Add buttons to the button panel
        buttonPanel.add(takeoutButton);
        buttonPanel.add(eatInButton);

        // Add button panel to the center of the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Create and style the Admin button
        JButton adminButton = new JButton("Admin");
        adminButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font size
        adminButton.setPreferredSize(new Dimension(100, 40)); // Smaller button size
        adminButton.setBackground(Color.LIGHT_GRAY);
        adminButton.setForeground(Color.BLACK);
        adminButton.setFocusPainted(false);
        adminButton.setBorder(BorderFactory.createEtchedBorder());

        // Create a panel for the Admin button and add it to the top right
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        adminPanel.add(adminButton);
        mainPanel.add(adminPanel, BorderLayout.NORTH);

        // Add the main panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size
        button.setPreferredSize(new Dimension(250, 100)); // Set button size
        button.setBackground(Color.LIGHT_GRAY); // Set background color
        button.setForeground(Color.BLACK); // Set text color
        button.setFocusPainted(false); // Remove focus outline
        button.setBorder(BorderFactory.createEtchedBorder()); // Set border style
        return button;
    }

    private void openMenuView() {
        // 기존 화면 제거
        getContentPane().removeAll();
        setTitle("주문 메뉴");

        // 메뉴 패널 생성
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 3, 8, 10)); // 3행 3열 설정, 카드 간의 간격 추가

        // 장바구니 패널 생성
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS)); // 세로로 쌓기
        cartPanel.setBorder(BorderFactory.createTitledBorder("주문내역")); // 테두리 추가
        cartPanel.setBackground(Color.LIGHT_GRAY); // 배경색 설정
        cartPanel.setPreferredSize(new Dimension(550, 400)); // 크기 설정

        JLabel cartLabel = new JLabel("장바구니");
        cartLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // 글꼴 스타일
        cartLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 가운데 정렬
        cartPanel.add(cartLabel);

        JList<OrderItemDTO> cartList = new JList<>(cartListModel); // 장바구니 리스트
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

        // 결제하기 버튼 추가
        JButton paymentButton = new JButton("결제하기");
        paymentButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        paymentButton.setBackground(Color.WHITE);
        paymentButton.addActionListener(e -> processPayment()); // 결제 메서드 호출

        // 장바구니 패널에 결제하기 버튼 추가
        cartPanel.add(paymentButton);

        // 메뉴 패널과 장바구니 패널을 JFrame에 추가
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(menuPanel), BorderLayout.CENTER);
        mainPanel.add(cartPanel, BorderLayout.EAST); // 장바구니 패널 오른쪽에 추가

        add(mainPanel);
        revalidate(); // 레이아웃 재조정
        repaint(); // 화면 업데이트
    }

    // 결제 처리 메서드
    private void processPayment() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "장바구니가 비어 있습니다. 메뉴를 추가하세요.", "결제 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 결제 정보 처리
        BigDecimal totalAmount = cart.stream()
                .map(OrderItemDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 결제 처리 중 메시지 표시
        JOptionPane.showMessageDialog(this, "결제 처리 중... 잠시만 기다려 주세요.", "결제 중", JOptionPane.INFORMATION_MESSAGE);

        // 회원 등록
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // 트랜잭션 시작

            int memberId = controller.registerMember(); // 회원 등록

            // 회원 등록 후 트랜잭션 커밋
            conn.commit();

            // 이제 memberId를 확인
            if (!controller.isMemberExists(memberId)) {
                JOptionPane.showMessageDialog(this, "회원 등록에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                return; // 회원 등록 실패 시 종료
            }

            // 장바구니 항목 가져오기
            List<OrderItemDTO> orderItems = new ArrayList<>();
            for (int i = 0; i < cartListModel.getSize(); i++) {
                OrderItemDTO orderItem = cartListModel.getElementAt(i);
                orderItems.add(orderItem);
            }

            // 주문 생성 및 DB에 저장
            int orderId = controller.createOrder(memberId, orderItems); // conn 전달
            System.out.println("주문ID : " + orderId);
            if (orderId <= 0) {
                JOptionPane.showMessageDialog(this, "주문 생성에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                conn.rollback(); // 롤백
                return;
            }
            conn.commit();


            for (OrderItemDTO orderItem : orderItems) {
                // orderId를 orderItem에 설정해야 합니다.
                orderItem.setOrderId(orderId);
                controller.createOrderItem(orderItem); // 주문 항목 추가
            }

            conn.commit();
            // 결제 완료 메시지
            JOptionPane.showMessageDialog(this, "결제가 완료되었습니다.\n주문 번호: " + orderId + "\n회원 ID: " + memberId, "결제 완료", JOptionPane.INFORMATION_MESSAGE);

            // 장바구니 초기화
            cart.clear();
            cartListModel.clear();
            updateCart();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "결제 처리 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }




    private void openLoginView() {
        // 로그인 창 열기
        String memberId = JOptionPane.showInputDialog("회원 ID:");
        String password = JOptionPane.showInputDialog("비밀번호:");
        controller.login(memberId, password);
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

    private void registerAuth() {
        String memberId = JOptionPane.showInputDialog("회원 ID:");
        String password = JOptionPane.showInputDialog("비밀번호:");
        String authStr = JOptionPane.showInputDialog("권한(0 또는 1):");
        controller.registerAuth(memberId, password);
    }

    private void addToCart(MenuDTO menu) {
        if (menu != null) {
            String quantityStr = JOptionPane.showInputDialog("수량:");
            int quantity = Integer.parseInt(quantityStr);
            String temperature = JOptionPane.showInputDialog("온도 (ICED, HOT):");

            BigDecimal totalPrice = menu.getPrice().multiply(BigDecimal.valueOf(quantity));

            // Create OrderItemDTO with the order type
            OrderItemDTO orderItem = new OrderItemDTO();
            orderItem.initializeOrderItem(menu.getId(), quantity, temperature, totalPrice, menu.getName(), currentOrderType);

            cart.add(orderItem); // Add to cart

            // Show total price
            JOptionPane.showMessageDialog(null, "총 가격: " + totalPrice.toString() + "원", "총 가격", JOptionPane.INFORMATION_MESSAGE);

            updateCart(); // Update cart UI
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

