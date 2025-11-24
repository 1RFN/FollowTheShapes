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
    private ArrayList<GameShape> shapes; // Polymorphism: List menampung berbagai bentuk
    private ArrayList<Integer> sequence; // Urutan langkah game
    private int currentStep = 0; // Langkah pemain saat ini
    private int score = 0;
    private boolean isPlayerTurn = false;
    private JLabel statusLabel;

    // ... import dan variable sama seperti sebelumnya

    public GamePanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);

        shapes = new ArrayList<>();
        sequence = new ArrayList<>();

        // --- INISIALISASI 4 BENTUK (POSISI GRID 2x2) ---
        
        int size = 100; // Ukuran bentuk
        int gap = 20;   // Jarak antar bentuk
        
        // Titik tengah panel (perkiraan, bisa disesuaikan dengan layoutmu)
        int centerX = 400; 
        int centerY = 300; 
        
        // Hitung posisi offset agar rapi di tengah
        int startX = centerX - size - (gap / 2); // Sekitar koordinat 290
        int startY = centerY - size - (gap / 2); // Sekitar koordinat 240

        // 1. Kiri Atas: KOTAK (Square) - Merah
        shapes.add(new SquareShape(startX, startY, size, new Color(220, 53, 69))); 
        
        // 2. Kanan Atas: LINGKARAN (Circle) - Biru
        shapes.add(new CircleShape(startX + size + gap, startY, size, new Color(13, 110, 253))); 
        
        // 3. Kiri Bawah: SEGITIGA (Triangle) - Hijau
        shapes.add(new TriangleShape(startX, startY + size + gap, size, new Color(25, 135, 84))); 
        
        // 4. Kanan Bawah: BELAH KETUPAT (Diamond) - Kuning
        shapes.add(new DiamondShape(startX + size + gap, startY + size + gap, size, new Color(255, 193, 7)));

        // --- AKHIR INISIALISASI ---

        statusLabel = new JLabel("Wait for pattern...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setForeground(Color.WHITE);
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
        
        // Tambah satu langkah acak ke sequence
        Random rand = new Random();
        sequence.add(rand.nextInt(shapes.size()));

        // JALANKAN THREAD UNTUK ANIMASI (MODUL 4: THREAD)
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Jeda sebelum mulai
                for (int index : sequence) {
                    GameShape shape = shapes.get(index);
                    
                    // Nyalakan shape
                    shape.setActive(true);
                    repaint(); // Refresh GUI
                    
                    // Bunyi beep (opsional)
                    Toolkit.getDefaultToolkit().beep(); 
                    
                    Thread.sleep(600); // Durasi nyala
                    
                    // Matikan shape
                    shape.setActive(false);
                    repaint();
                    
                    Thread.sleep(300); // Jeda antar bentuk
                }
                
                // Giliran pemain
                isPlayerTurn = true;
                SwingUtilities.invokeLater(() -> statusLabel.setText("Your Turn!"));
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handlePlayerClick(int x, int y) {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).isClicked(x, y)) {
                // Cek apakah klik sesuai urutan sequence
                if (i == sequence.get(currentStep)) {
                    // Benar
                    flashShape(shapes.get(i)); // Feedback visual instan
                    currentStep++;
                    
                    if (currentStep >= sequence.size()) {
                        // Selesai satu ronde
                        score++;
                        isPlayerTurn = false;
                        statusLabel.setText("Correct!");
                        // Jeda sedikit sebelum ronde berikutnya
                        new Thread(() -> {
                            try { Thread.sleep(1000); } catch(Exception ex){}
                            SwingUtilities.invokeLater(this::nextRound);
                        }).start();
                    }
                } else {
                    // Salah -> Game Over
                    gameOver();
                }
                return; // Keluar loop setelah klik terdeteksi
            }
        }
    }
    
    // Helper untuk flash shape sebentar saat diklik player
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
        JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
        mainFrame.showCard("Menu");
    }

    private void saveScore() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO leaderboard (user_id, score) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mainFrame.getCurrentUserId());
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Menggambar semua bentuk
        for (GameShape shape : shapes) {
            shape.draw(g);
        }
    }
}