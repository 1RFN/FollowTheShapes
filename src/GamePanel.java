import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Main mainFrame;
    private ArrayList<GameShape> shapes;
    private ArrayList<Integer> sequence;
    private int currentStep = 0;
    private int score = 0;
    private boolean isPlayerTurn = false;
    private JLabel statusLabel;

    public GamePanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new BorderLayout());
        this.setBackground(Theme.BG_MAIN); // Background Gelap

        shapes = new ArrayList<>();
        sequence = new ArrayList<>();

        int size = 100;
        int gap = 30;
        int centerX = 400; 
        int centerY = 300; 
        int startX = centerX - size - (gap / 2); 
        int startY = centerY - size - (gap / 2); 

        // Menggunakan Warna Tema Baru (Pastel)
        shapes.add(new SquareShape(startX, startY, size, Theme.COLOR_RED)); 
        shapes.add(new CircleShape(startX + size + gap, startY, size, Theme.COLOR_BLUE)); 
        shapes.add(new TriangleShape(startX, startY + size + gap, size, Theme.COLOR_GREEN)); 
        shapes.add(new DiamondShape(startX + size + gap, startY + size + gap, size, Theme.COLOR_YELLOW));

        statusLabel = new JLabel("Wait for pattern...", SwingConstants.CENTER);
        statusLabel.setFont(Theme.FONT_SUBTITLE);
        statusLabel.setForeground(Theme.TEXT_PRIMARY);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        add(statusLabel, BorderLayout.NORTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPlayerTurn) {
                    handlePlayerClick(e.getX(), e.getY());
                }
            }
        });
    }

    // --- LOGIC GAME (TIDAK BERUBAH DARI SEBELUMNYA) ---
    public void startGame() {
        sequence.clear();
        score = 0;
        currentStep = 0;
        nextRound();
    }

    private void nextRound() {
        isPlayerTurn = false;
        currentStep = 0;
        statusLabel.setText("Watch carefully! Score: " + score);
        
        Random rand = new Random();
        sequence.add(rand.nextInt(shapes.size()));

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                for (int index : sequence) {
                    GameShape shape = shapes.get(index);
                    shape.setActive(true);
                    repaint();
                    Toolkit.getDefaultToolkit().beep(); 
                    Thread.sleep(600);
                    shape.setActive(false);
                    repaint();
                    Thread.sleep(300);
                }
                isPlayerTurn = true;
                SwingUtilities.invokeLater(() -> statusLabel.setText("Your Turn!"));
            } catch (InterruptedException e) { e.printStackTrace(); }
        }).start();
    }

    private void handlePlayerClick(int x, int y) {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).isClicked(x, y)) {
                if (i == sequence.get(currentStep)) {
                    flashShape(shapes.get(i));
                    currentStep++;
                    if (currentStep >= sequence.size()) {
                        score++;
                        isPlayerTurn = false;
                        statusLabel.setText("Correct!");
                        new Thread(() -> {
                            try { Thread.sleep(1000); } catch(Exception ex){}
                            SwingUtilities.invokeLater(this::nextRound);
                        }).start();
                    }
                } else {
                    gameOver();
                }
                return;
            }
        }
    }
    
    private void flashShape(GameShape shape) {
        new Thread(() -> {
            shape.setActive(true);
            repaint();
            try { Thread.sleep(200); } catch (Exception e){}
            shape.setActive(false);
            repaint();
        }).start();
    }

    private void gameOver() {
        isPlayerTurn = false;
        saveScore();
        Popup.showGameOver(mainFrame, score); // Pakai Popup Keren
    }

    private void saveScore() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO leaderboard (id_user, score) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mainFrame.getCurrentUserId());
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Gambar background dengan shape
        for (GameShape shape : shapes) {
            shape.draw(g);
        }
    }
}