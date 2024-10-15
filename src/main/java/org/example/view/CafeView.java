package org.example.view;


import org.example.controller.CafeController;
import org.example.database.DBConnection;
import org.example.dto.MemberDTO;
import org.example.dto.MenuDTO;
import org.example.dto.OrderItemDTO;
import org.example.dto.OrderType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private DefaultTableModel cartTableModel;

    public CafeView() {
        setTitle("카페 키오스크");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cart = new ArrayList<>();
        menuListModel = new DefaultListModel<>();
        cartListModel = new DefaultListModel<>();

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));

        JButton takeoutButton = createStyledButton("Takeout");
        JButton eatInButton = createStyledButton("Eat In");

        takeoutButton.addActionListener(e -> {
            currentOrderType = OrderType.TAKEOUT;
            openMenuView();
        });

        eatInButton.addActionListener(e -> {
            currentOrderType = OrderType.EATIN;
            openMenuView();
        });

        buttonPanel.add(takeoutButton);
        buttonPanel.add(eatInButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JButton adminButton = new JButton("Admin");
        adminButton.setFont(new Font("Arial", Font.PLAIN, 14));
        adminButton.setPreferredSize(new Dimension(100, 40));
        adminButton.setBackground(Color.LIGHT_GRAY);
        adminButton.setForeground(Color.BLACK);
        adminButton.setFocusPainted(false);
        adminButton.setBorder(BorderFactory.createEtchedBorder());

        adminButton.addActionListener(e -> {
            // AdminView를 열기
            new AdminView(controller); // controller를 AdminView에 전달
        });

        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        adminPanel.add(adminButton);
        mainPanel.add(adminPanel, BorderLayout.NORTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18)); // 글꼴 크기 조정
        button.setBackground(new Color(70, 130, 180)); // 기본 색상
        button.setForeground(Color.WHITE); // 글자 색상
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder()); // 경계선 제거
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 손가락 커서
        button.setPreferredSize(new Dimension(100, 40)); // 크기 조정
        button.setOpaque(true); // 불투명하게 설정

        // 마우스 이벤트 리스너로 색상 변경 효과 추가
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // 마우스 오버 시 색상 변경
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180)); // 원래 색상으로 복원
            }
        });

        return button;
    }

    private void openMenuView() {
        // 기존 화면 제거
        getContentPane().removeAll();
        setTitle("주문 메뉴");

        // 전체 프레임 크기 조정
        setPreferredSize(new Dimension(900, 600)); // 너비를 넓히고 높이 설정

        // 메뉴 패널 생성
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 3, 20, 20)); // 3행 3열 설정, 카드 간의 간격 추가

        // 장바구니 데이터 모델 및 테이블 생성
        cartTableModel = new DefaultTableModel(new Object[]{"주문 내역"}, 0); // 열 제목 수정
        JTable cartTable = new JTable(cartTableModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable); // 스크롤 가능하게 설정
        cartScrollPane.setPreferredSize(new Dimension(450, 400)); // 스크롤 패널 크기 조정

        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout()); // 테이블을 위한 레이아웃
        cartPanel.setBorder(BorderFactory.createTitledBorder("주문내역")); // 테두리 추가
        cartPanel.setBackground(new Color(240, 248, 255)); // 연한 하늘색 배경으로 변경

// 테두리 스타일을 설정하여 둥글게 만들기
        cartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // 내부 여백 추가
        ));

// 테이블 추가
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        // 장바구니 제목
        JLabel cartLabel = new JLabel("장바구니", JLabel.CENTER);
        cartLabel.setFont(new Font("SansSerif", Font.BOLD, 18)); // 글꼴 스타일
        cartPanel.add(cartLabel, BorderLayout.NORTH); // 제목 추가

        // 메뉴 데이터 가져오기
        List<MenuDTO> menus = controller.loadMenus(); // 메뉴 데이터 로드

        for (MenuDTO menu : menus) {
            // 카드 형태의 패널 생성
            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(new BorderLayout());
            cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // 카드 테두리
            cardPanel.setBackground(Color.WHITE); // 카드 배경색 설정
            cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 카드 내부 여백 추가

            // 메뉴 이미지 로드
            String imagePath = "/images/" + menu.getImagePath(); // 리소스 경로
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = originalIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); // 이미지 크기 조정
            ImageIcon imageIcon = new ImageIcon(scaledImage);

            // 이미지 레이블 설정
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // 이미지 상하 여백 추가

            // 이름과 가격 레이블을 위한 패널 생성
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // 세로로 쌓기
            textPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT); // 가운데 정렬
            textPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // 내부 여백 조정

            // 메뉴 이름 표시
            JLabel nameLabel = new JLabel(menu.getName(), JLabel.CENTER);
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14)); // 이름 글꼴 스타일 변경
            textPanel.add(nameLabel); // 이름 추가

            // 가격 표시
            JLabel priceLabel = new JLabel(menu.getPrice().toPlainString() + "원", JLabel.CENTER); // 가격 표시
            priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 12)); // 가격 글꼴 스타일 변경
            textPanel.add(priceLabel); // 가격 추가

            // 주문하기 버튼
            JButton orderButton = new JButton("주문하기");
            orderButton.setFont(new Font("SansSerif", Font.PLAIN, 12)); // 버튼 글꼴 스타일 변경
            orderButton.setBackground(new Color(100, 149, 237)); // 버튼 배경색 (코넬리안 블루)
            orderButton.setForeground(Color.WHITE); // 글자색 흰색
            orderButton.setFocusPainted(false); // 포커스 효과 제거
            orderButton.addActionListener(e -> {
                addToCart(menu); // 메뉴를 카트에 추가
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
        paymentButton.setBackground(new Color(100, 149, 237)); // 버튼 배경색
        paymentButton.setForeground(Color.WHITE); // 글자색 흰색
        paymentButton.addActionListener(e -> processPayment()); // 결제 메서드 호출

        // 결제 및 뒤로가기 버튼을 패널에 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // 버튼을 중앙에 배치
        buttonPanel.add(paymentButton);

        // 장바구니 패널에 버튼 패널 추가
        cartPanel.add(buttonPanel, BorderLayout.SOUTH); // 버튼을 하단에 배치

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
            cartListModel.addElement(item);
        }
    }

    private void addToCart(MenuDTO menu) {
        if (menu != null) {
            String quantityStr = JOptionPane.showInputDialog("수량:");
            int quantity = Integer.parseInt(quantityStr);
            String temperature = JOptionPane.showInputDialog("온도 (ICED, HOT):");

            BigDecimal totalPrice = menu.getPrice().multiply(BigDecimal.valueOf(quantity));

            OrderItemDTO orderItem = new OrderItemDTO();
            orderItem.initializeOrderItem(menu.getId(), quantity, temperature, totalPrice, menu.getName(), currentOrderType);

            cart.add(orderItem);

            // 장바구니에 추가할 문자열 생성
            String orderDetails = "메뉴명: " + menu.getName() +
                    ", 수량: " + quantity +
                    ", 온도: " + temperature +
                    ", 주문타입: " + currentOrderType +
                    ", 총 가격: " + totalPrice.toPlainString() + "원";

            // 장바구니 테이블에 추가
            cartTableModel.addRow(new Object[]{orderDetails});

            JOptionPane.showMessageDialog(null, "주문이 장바구니에 추가되었습니다.", "주문 추가", JOptionPane.INFORMATION_MESSAGE);
            updateCart();
        } else {
            showMessage("메뉴를 선택하세요.");
        }
    }


    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}

