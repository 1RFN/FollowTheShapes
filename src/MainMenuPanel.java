import java.awt.*;
import javax.swing.*;

public class MainMenuPanel extends JPanel {
    
    public MainMenuPanel(Main mainFrame) {
        setLayout(new GridBagLayout());
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        JLabel title = new JLabel("FOLLOW THE SHAPES", SwingConstants.CENTER);
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_PRIMARY);

        // Tombol-tombol
        ModernButton btnStart = new ModernButton("START GAME");
        ModernButton btnLeaderboard = new ModernButton("LEADERBOARD");
        ModernButton btnExit = new ModernButton("EXIT");

        Dimension size = new Dimension(250, 55);
        btnStart.setPreferredSize(size);
        btnLeaderboard.setPreferredSize(size);
        btnExit.setPreferredSize(size);

        // Action: Pindah ke DifficultyPanel, bukan langsung Game
        btnStart.addActionListener(e -> {
            mainFrame.showCard("Difficulty");
        });
        
        btnLeaderboard.addActionListener(e -> {
            mainFrame.getLeaderboardPanel().refreshData();
            mainFrame.showCard("Leaderboard");
        });

        btnExit.addActionListener(e -> mainFrame.showCard("ExitConfirmation"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0; 
        
        gbc.gridy = 0; contentPanel.add(title, gbc);
        gbc.gridy = 1; contentPanel.add(Box.createVerticalStrut(30), gbc);
        gbc.gridy = 2; contentPanel.add(btnStart, gbc);
        gbc.gridy = 3; contentPanel.add(btnLeaderboard, gbc);
        gbc.gridy = 4; contentPanel.add(btnExit, gbc);

        add(contentPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Theme.imgMenu != null) {
            g.drawImage(Theme.imgMenu, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 100)); 
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            Graphics2D g2 = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, Theme.BG_MAIN, 0, getHeight(), Theme.BG_SECONDARY);
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}