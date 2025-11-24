import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(Main mainFrame) {
        setLayout(new GridBagLayout());
        
        JButton btnStart = new JButton("Play Game");
        JButton btnLeaderboard = new JButton("Leaderboard");
        JButton btnExit = new JButton("Exit");
        
        Dimension btnSize = new Dimension(200, 50);
        btnStart.setPreferredSize(btnSize);
        btnLeaderboard.setPreferredSize(btnSize);
        btnExit.setPreferredSize(btnSize);

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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        add(btnStart, gbc);
        gbc.gridy = 1;
        add(btnLeaderboard, gbc);
        gbc.gridy = 2;
        add(btnExit, gbc);
    }
}