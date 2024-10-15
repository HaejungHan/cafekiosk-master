package org.example.view;

import org.example.controller.CafeController;
import org.example.dto.Category;
import org.example.dto.MenuDTO;
import org.example.dto.OrderItemDTO;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class AdminView extends JFrame {
    private CafeController controller;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel menuPanel;

    public AdminView(CafeController controller) {
        this.controller = controller;
        setTitle("관리자 로그인");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 배치

        // 로그인 패널 설정
        JPanel loginPanel = createLoginPanel();
        add(loginPanel);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 패딩 설정

        // 사용자 ID 라벨
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("사용자 ID:"), gbc);

        // 사용자 ID 입력 필드
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // 비밀번호 라벨
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("비밀번호:"), gbc);

        // 비밀번호 입력 필드
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // 로그인 버튼
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // 버튼을 두 칸 차지하도록 설정
        JButton loginButton = new JButton("로그인");
        loginButton.setBackground(Color.GRAY); // 버튼 배경색
        loginButton.setForeground(Color.WHITE); // 글자색
        loginButton.addActionListener(e -> login());
        panel.add(loginButton, gbc);

        gbc.gridy = 3; // 다음 줄로 이동
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(e -> openPreviousScreen()); // 이전 화면으로 돌아가는 메서드 호출
        panel.add(backButton, gbc); // 패널에 추가


        return panel;
    }

    private void openPreviousScreen() {
        // 현재 화면 닫기
        this.setVisible(false);
        this.dispose();

        new CafeView(controller);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // 로그인 처리 로직
        if (controller.authenticateAdmin(username, password)) {
            JOptionPane.showMessageDialog(this, "로그인 성공!", "성공", JOptionPane.INFORMATION_MESSAGE);
            openMainMenu(); // 메인 메뉴 화면으로 전환
        } else {
            JOptionPane.showMessageDialog(this, "로그인 실패! 사용자 ID 또는 비밀번호를 확인하세요.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openMainMenu() {
        // 로그인 후 메인 메뉴 화면 생성
        remove(getContentPane().getComponent(0)); // 로그인 패널 제거
        JPanel mainMenuPanel = new JPanel();
        JButton menuManagementButton = new JButton("메뉴 관리");
        JButton orderHistoryButton = new JButton("이전 주문 내역");
        JButton backButton = new JButton("뒤로가기");

        menuManagementButton.addActionListener(e -> openMenuManagement());
        orderHistoryButton.addActionListener(e -> showOrderHistory());
        backButton.addActionListener(e -> openLoginScreen());

        mainMenuPanel.add(menuManagementButton);
        mainMenuPanel.add(orderHistoryButton);
        mainMenuPanel.add(backButton);
        add(mainMenuPanel);
        revalidate(); // UI 업데이트
        repaint(); // UI 재렌더링
    }

    private void openLoginScreen() {
        remove(getContentPane().getComponent(0)); // 메인 메뉴 패널 제거
        JPanel loginPanel = createLoginPanel(); // 로그인 패널 생성
        add(loginPanel);
        revalidate(); // UI 업데이트
        repaint(); // UI 재렌더링
    }

    private void openMenuManagement() {
        // 메뉴 관리 UI 구현
        remove(getContentPane().getComponent(0)); // 메인 메뉴 패널 제거
        JPanel menuPanel = createMenuManagementPanel();
        add(menuPanel);
        revalidate(); // UI 업데이트
        repaint(); // UI 재렌더링
    }


    private JPanel createMenuManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 600)); // 전체 프레임 크기 조정

        // 현재 메뉴 목록 패널
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> menuList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(menuList);
        scrollPane.setPreferredSize(new Dimension(300, 400)); // 메뉴 목록 크기 조정
        scrollPane.setBorder(BorderFactory.createTitledBorder("현재 메뉴 내역")); // 제목 추가

        // 메뉴 내역 제목 패널
        JPanel menuListPanel = new JPanel(new BorderLayout());
        menuListPanel.add(scrollPane, BorderLayout.CENTER);

        // 메뉴 추가 패널
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // 그리드 레이아웃으로 배치
        JTextField menuNameField = new JTextField();
        JTextField priceField = new JTextField();

        // 카테고리 드롭다운 추가
        JComboBox<Category> categoryComboBox = new JComboBox<>(Category.values()); // enum의 값을 드롭다운에 추가
        JTextField imagePathField = new JTextField();

        JButton addMenuButton = new JButton("메뉴 추가");
        JButton removeMenuButton = new JButton("메뉴 삭제");

        // 메뉴 추가 버튼 클릭 이벤트
        addMenuButton.addActionListener(e -> {
            String menuName = menuNameField.getText();
            String priceStr = priceField.getText();
            Category category = (Category) categoryComboBox.getSelectedItem(); // 선택된 카테고리
            String imagePath = imagePathField.getText();

            if (!menuName.isEmpty() && !priceStr.isEmpty() && category != null && !imagePath.isEmpty()) {
                try {
                    BigDecimal price = new BigDecimal(priceStr);
                    MenuDTO newMenu = new MenuDTO(0, menuName, price, category, imagePath);

                    // DB에 메뉴 추가하는 로직
                    controller.addMenu(newMenu); // DB에 추가하는 메서드 호출
                    listModel.addElement(menuName + " - " + price + "원");

                    // 입력 필드 초기화
                    menuNameField.setText("");
                    priceField.setText("");
                    imagePathField.setText("");
                    categoryComboBox.setSelectedIndex(0); // 첫 번째 항목으로 초기화

                    // 추가 성공 메시지
                    JOptionPane.showMessageDialog(this, menuName + " 메뉴가 추가되었습니다.", "메뉴 추가", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "가격을 올바른 형식으로 입력하세요.", "오류", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요.", "오류", JOptionPane.WARNING_MESSAGE);
            }
        });

        // 메뉴 삭제 버튼 클릭 이벤트
        removeMenuButton.addActionListener(e -> {
            int selectedIndex = menuList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedMenu = listModel.getElementAt(selectedIndex);
                String menuName = selectedMenu.split(" - ")[0]; // 메뉴 이름 추출

                // DB에서 메뉴 삭제하는 로직
                controller.deleteMenu(menuName); // 삭제 메서드 호출
                listModel.remove(selectedIndex); // JList에서 항목 삭제

                // 삭제 성공 메시지
                JOptionPane.showMessageDialog(this, menuName + " 메뉴가 삭제되었습니다.", "메뉴 삭제", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "삭제할 메뉴를 선택하세요.", "오류", JOptionPane.WARNING_MESSAGE);
            }
        });

        // 입력 패널에 필드 및 버튼 추가
        inputPanel.add(new JLabel("메뉴 이름:"));
        inputPanel.add(menuNameField);
        inputPanel.add(new JLabel("가격:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("카테고리:"));
        inputPanel.add(categoryComboBox); // 드롭다운 추가
        inputPanel.add(new JLabel("이미지 경로:"));
        inputPanel.add(imagePathField);
        inputPanel.add(addMenuButton);
        inputPanel.add(removeMenuButton);

        // 메뉴 목록을 DB에서 불러오기
        loadMenus(listModel); // 추가된 코드

        // 패널들을 메인 패널에 추가
        panel.add(menuListPanel, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        // 뒤로가기 버튼 추가
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(e -> openMainMenu()); // 메인 메뉴로 돌아가는 메서드 호출
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.NORTH); // 패널 상단에 추가

        return panel;
    }

    // 메뉴를 DB에서 불러와서 리스트에 추가하는 메서드
    private void loadMenus(DefaultListModel<String> listModel) {
        List<MenuDTO> menus = controller.loadMenus(); // 메뉴 로드
        listModel.clear(); // 기존 목록 초기화
        for (MenuDTO menu : menus) {
            listModel.addElement(menu.getName() + " - " + menu.getPrice().toPlainString() + "원 " + " 메뉴 종류(" +menu.getCategory() + ")"); // 리스트에 추가
        }
    }

    private void showOrderHistory() {
        // 주문 내역 화면 구현
        remove(getContentPane().getComponent(0)); // 메인 메뉴 패널 제거

        JPanel orderHistoryPanel = new JPanel();
        orderHistoryPanel.setLayout(new BorderLayout());
        orderHistoryPanel.setBackground(new Color(240, 240, 240)); // 부드러운 배경색

        // 테이블 모델 생성
        String[] columnNames = {"주문 ID", "메뉴 이름", "수량", "온도", "총 가격", "주문 타입"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // 주문 내역 불러오기
        List<OrderItemDTO> orderItems = controller.getAllOrderItems();
        for (OrderItemDTO orderItem : orderItems) {
            Object[] rowData = {
                    orderItem.getOrderId(),
                    orderItem.getMenuName(),
                    orderItem.getQuantity(),
                    orderItem.getTemperature(),
                    orderItem.getTotalPrice(),
                    orderItem.getOrderType()
            };
            tableModel.addRow(rowData);
        }

        // 테이블 생성
        JTable orderTable = new JTable(tableModel);
        orderTable.setFont(new Font("SansSerif", Font.PLAIN, 14)); // 산세리프체로 설정
        orderTable.setRowHeight(30); // 행 높이 설정
        orderTable.setDefaultEditor(Object.class, null); // 편집 불가

        // 스크롤 페인 추가
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(800, 400)); // 화면 크기 조정

        orderHistoryPanel.add(scrollPane, BorderLayout.CENTER);

        // 뒤로가기 버튼 추가
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(e -> {
            // 메인 메뉴로 돌아가는 로직
            openMenuManagement(); // 기존의 메뉴 관리 화면 열기 메서드
        });

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(backButton);

        // 메인 패널에 추가
        orderHistoryPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(orderHistoryPanel);

        setSize(850, 500); // 프레임 크기 설정
        setLocationRelativeTo(null); // 화면 중앙에 배치
        revalidate(); // UI 업데이트
        repaint(); // UI 재렌더링
    }


}






