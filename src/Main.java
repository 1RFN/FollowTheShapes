import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Panels
    private LoginPanel loginPanel;
    private MainMenuPanel menuPanel;
    private DifficultyPanel difficultyPanel; // BARU
    private GamePanel gamePanel;
    private LeaderboardPanel leaderboardPanel;
    private ExitConfirmationPanel exitConfirmationPanel;

    private int currentUserId;
    private String currentUsername;
    private Difficulty difficulty = Difficulty.EASY; 

    // ENUM CONFIG (Sama seperti sebelumnya)
    public enum Difficulty {
        EASY(1000, -1, false),       
        MEDIUM(600, 1500, false),    
        HARD(400, 1200, true);       
        
        public final int blinkSpeed;
        public final int timePerShape; 
        public final boolean isShuffling;

        Difficulty(int blinkSpeed, int timePerShape, boolean isShuffling) {
            this.blinkSpeed = blinkSpeed;
            this.timePerShape = timePerShape;
            this.isShuffling = isShuffling;
        }
    }

    public Main() {
        setTitle("Follow The Shapes!");
        setSize(900, 700); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        Theme.loadImages();
        SoundManager.loadSounds();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize Panels
        loginPanel = new LoginPanel(this);
        menuPanel = new MainMenuPanel(this);
        difficultyPanel = new DifficultyPanel(this); // BARU
        gamePanel = new GamePanel(this);
        leaderboardPanel = new LeaderboardPanel(this);
        exitConfirmationPanel = new ExitConfirmationPanel(this);

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(difficultyPanel, "Difficulty"); // BARU
        mainPanel.add(gamePanel, "Game");
        mainPanel.add(leaderboardPanel, "Leaderboard");
        mainPanel.add(exitConfirmationPanel, "ExitConfirmation");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    public void showCard(String cardName) {
        if (cardName.equals("Menu") || cardName.equals("Difficulty")) {
            SoundManager.playBackgroundMusic();
        } else if (cardName.equals("Game")) {
            SoundManager.stopBackgroundMusic();
        }
        cardLayout.show(mainPanel, cardName);
    }

    // --- Getter & Setter Standard ---
    public void setDifficulty(Difficulty diff) { this.difficulty = diff; }
    public Difficulty getDifficulty() { return difficulty; }
    public void setCurrentUser(int id, String username) { this.currentUserId = id; this.currentUsername = username; }
    public int getCurrentUserId() { return currentUserId; }
    public GamePanel getGamePanel() { return gamePanel; }
    public LeaderboardPanel getLeaderboardPanel() { return leaderboardPanel; }
    public LoginPanel getLoginPanel() { return loginPanel; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}