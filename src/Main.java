import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Panels
    private LoginPanel loginPanel;
    private MainMenuPanel menuPanel;
    private GamePanel gamePanel;
    private LeaderboardPanel leaderboardPanel;
    
    // User Session
    private int currentUserId;
    private String currentUsername;

    public Main() {
        setTitle("Follow The Shapes! - Tugas Besar PBO");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize Panels
        loginPanel = new LoginPanel(this);
        menuPanel = new MainMenuPanel(this);
        gamePanel = new GamePanel(this);
        leaderboardPanel = new LeaderboardPanel(this);

        // Add to CardLayout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamePanel, "Game");
        mainPanel.add(leaderboardPanel, "Leaderboard");

        add(mainPanel);
        
        // Show Login first
        cardLayout.show(mainPanel, "Login");
    }

    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }

    public void setCurrentUser(int id, String username) {
        this.currentUserId = id;
        this.currentUsername = username;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }
    
    public GamePanel getGamePanel() { return gamePanel; }
    public LeaderboardPanel getLeaderboardPanel() { return leaderboardPanel; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}