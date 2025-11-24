import java.awt.*;
import javax.swing.*;

public class ExitConfirmationPanel extends JPanel {
    public ExitConfirmationPanel(Main mainFrame) {
        setLayout(new GridBagLayout());
        setBackground(Theme.BG_MAIN);

        JLabel title = new JLabel("Apakah Anda yakin?", SwingConstants.CENTER);
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_PRIMARY);

        ModernButton btnNewLogin = new ModernButton("Login Akun Baru");
        ModernButton btnExitApp = new ModernButton("Exit");
        ModernButton btnBack = new ModernButton("Kembali");

        Dimension buttonSize = new Dimension(250, 55);
        btnNewLogin.setPreferredSize(buttonSize);
        btnExitApp.setPreferredSize(buttonSize);
        btnBack.setPreferredSize(buttonSize);

        btnNewLogin.addActionListener(e -> {
            mainFrame.showCard("Login");
            mainFrame.setCurrentUser(-1, "");
            if (mainFrame.getLoginPanel() != null) {
                mainFrame.getLoginPanel().resetFields();
            }
        });

        btnExitApp.addActionListener(e -> {
            System.exit(0);
        });

        btnBack.addActionListener(e -> {
            mainFrame.showCard("Menu");
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        
        gbc.gridy = 0; add(title, gbc);
        gbc.gridy = 1; add(Box.createVerticalStrut(30), gbc);
        gbc.gridy = 2; add(btnNewLogin, gbc);
        gbc.gridy = 3; add(btnExitApp, gbc);
        gbc.gridy = 4; add(btnBack, gbc);
    }
}
