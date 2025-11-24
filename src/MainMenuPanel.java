import java.awt.*;
import javax.swing.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(Main mainFrame) {
        setLayout(new GridBagLayout());
        setBackground(Theme.BG_MAIN);
        
        JLabel title = new JLabel("FOLLOW THE SHAPES", SwingConstants.CENTER);
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.COLOR_YELLOW); // Warna Kuning Pastel

        ModernButton btnStart = new ModernButton("PLAY GAME");
        ModernButton btnLeaderboard = new ModernButton("LEADERBOARD");
        ModernButton btnExit = new ModernButton("EXIT");

        Dimension size = new Dimension(250, 55);
        btnStart.setPreferredSize(size);
        btnLeaderboard.setPreferredSize(size);
        btnExit.setPreferredSize(size);

        btnStart.addActionListener(e -> {
            mainFrame.getGamePanel().startGame();
            mainFrame.showCard("Game");
        });
        
        btnLeaderboard.addActionListener(e -> {
            mainFrame.getLeaderboardPanel().refreshData();
            mainFrame.showCard("Leaderboard");
        });
        
        btnExit.addActionListener(e -> System.exit(0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0; 
        
        gbc.gridy = 0; add(title, gbc);
        gbc.gridy = 1; add(Box.createVerticalStrut(30), gbc);
        gbc.gridy = 2; add(btnStart, gbc);
        gbc.gridy = 3; add(btnLeaderboard, gbc);
        gbc.gridy = 4; add(btnExit, gbc);
    }
}