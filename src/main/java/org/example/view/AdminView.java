package org.example.view;

import org.example.controller.CafeController;

import javax.swing.*;

public class AdminView extends JFrame {
    private CafeController controller;

    public AdminView(CafeController controller) {
        this.controller = controller;
        setTitle("관리 화면");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 메뉴 관리 UI 구성 (예: 추가, 삭제 버튼)

        JButton addMenuButton = new JButton("메뉴 추가");
        JButton removeMenuButton = new JButton("메뉴 삭제");

        addMenuButton.addActionListener(e -> addMenu());
        removeMenuButton.addActionListener(e -> removeMenu());

        JPanel panel = new JPanel();
        panel.add(addMenuButton);
        panel.add(removeMenuButton);
        add(panel);

        setVisible(true);
    }

    private void addMenu() {
        // 메뉴 추가 로직
    }

    private void removeMenu() {
        // 메뉴 삭제 로직
    }
}

