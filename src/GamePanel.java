import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Main mainFrame;
    private ArrayList<GameShape> shapes;
    private ArrayList<Integer> sequence;
    private int currentStep = 0;
    private int score = 0;
    private int roundLevel = 1; // Penanda Level/Jumlah Bentuk
    
    private boolean isPlayerTurn = false;
    private boolean isGameRunning = false;
    
    // UI Elements
    private JLabel statusLabel;
    private JLabel countdownLabel; 
    private JProgressBar timerBar; 
    private Timer turnTimer; 
    private int timeLeft; 

    private Random random;
    private int[] startXPoints;
    private int[] startYPoints;

    public GamePanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(null); 
        this.random = new Random();

        setupUI();
        setupShapes();

        turnTimer = new Timer(50, e -> updateTimer());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPlayerTurn && isGameRunning) {
                    handlePlayerClick(e.getX(), e.getY());
                }
            }
        });
    }

    private void setupUI() {
        statusLabel = new JLabel("Wait...", SwingConstants.CENTER);
        statusLabel.setFont(Theme.FONT_SUBTITLE);
        statusLabel.setForeground(Theme.TEXT_PRIMARY);
        statusLabel.setBounds(0, 20, 900, 40);
        add(statusLabel);

        countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(Theme.FONT_COUNTDOWN);
        countdownLabel.setForeground(Theme.COLOR_YELLOW);
        countdownLabel.setBounds(0, 0, 900, 700);
        add(countdownLabel);

        timerBar = new JProgressBar();
        timerBar.setBounds(200, 70, 500, 10);
        timerBar.setForeground(Theme.COLOR_RED);
        timerBar.setBackground(Theme.BG_SECONDARY);
        timerBar.setBorderPainted(false);
        timerBar.setVisible(false);
        add(timerBar);
    }

    private void setupShapes() {
        shapes = new ArrayList<>();
        sequence = new ArrayList<>();

        int size = 120;
        int gap = 40;
        int centerX = 900 / 2; 
        int centerY = 700 / 2; 
        int startX = centerX - size - (gap / 2); 
        int startY = centerY - size - (gap / 2); 

        startXPoints = new int[]{startX, startX + size + gap, startX, startX + size + gap};
        startYPoints = new int[]{startY, startY, startY + size + gap, startY + size + gap};

        shapes.add(new SquareShape(startXPoints[0], startYPoints[0], size, Theme.COLOR_RED)); 
        shapes.add(new CircleShape(startXPoints[1], startYPoints[1], size, Theme.COLOR_BLUE)); 
        shapes.add(new TriangleShape(startXPoints[2], startYPoints[2], size, Theme.COLOR_GREEN)); 
        shapes.add(new DiamondShape(startXPoints[3], startYPoints[3], size, Theme.COLOR_YELLOW));
    }

    public void prepareGame() {
        sequence.clear();
        score = 0;
        currentStep = 0;
        roundLevel = 1; // Reset Level
        isGameRunning = false;
        isPlayerTurn = false;
        timerBar.setVisible(false);
        resetShapePositions(); 
        
        statusLabel.setText("Get Ready...");
        startCountdown();
    }

    private void resetShapePositions() {
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).x = startXPoints[i];
            shapes.get(i).y = startYPoints[i];
            if(shapes.get(i) instanceof DiamondShape) ((DiamondShape)shapes.get(i)).createPolygon();
        }
        repaint();
    }

    private void shufflePositions() {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < 4; i++) points.add(new Point(startXPoints[i], startYPoints[i]));
        Collections.shuffle(points); 

        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).x = points.get(i).x;
            shapes.get(i).y = points.get(i).y;
            if(shapes.get(i) instanceof DiamondShape) ((DiamondShape)shapes.get(i)).createPolygon();
        }
        repaint();
    }

    private void startCountdown() {
        new Thread(() -> {
            try {
                String[] count = {"3", "2", "1", "GO!"};
                for (String s : count) {
                    SwingUtilities.invokeLater(() -> {
                        countdownLabel.setText(s);
                        repaint();
                    });
                    Thread.sleep(800); 
                }
                
                SwingUtilities.invokeLater(() -> {
                    countdownLabel.setText(""); 
                    isGameRunning = true;
                    statusLabel.setText("Watch Pattern! Score: 0");
                    nextRound();
                });

            } catch (InterruptedException e) { e.printStackTrace(); }
        }).start();
    }

    // --- LOGIKA UTAMA RONDE ---
    private void nextRound() {
        isPlayerTurn = false;
        currentStep = 0;
        timerBar.setVisible(false);
        turnTimer.stop();
        
        // 1. Reset Posisi (Agar player bisa lihat pola dengan jelas dulu)
        resetShapePositions();

        // 2. GENERATE POLA
        Main.Difficulty diff = mainFrame.getDifficulty();
        
        if (diff == Main.Difficulty.EASY) {
            // EASY: Pola bertambah (Akumulatif) seperti Simon Says Klasik
            int nextShape = random.nextInt(shapes.size());
            sequence.add(nextShape);
        } else {
            // MEDIUM & HARD: Pola Selalu Baru & Acak tiap Ronde
            generateNewRandomSequence(roundLevel);
        }
        
        int blinkSpeed = diff.blinkSpeed;

        new Thread(() -> {
            try {
                Thread.sleep(1000); 
                
                // Mainkan Pola
                for (int index : sequence) {
                    GameShape shape = shapes.get(index);
                    shape.setActive(true);
                    SoundManager.playSound(String.valueOf(index + 1));
                    repaint();
                    Thread.sleep(blinkSpeed);
                    shape.setActive(false);
                    repaint();
                    Thread.sleep(200);
                }
                
                // Khusus HARD: Acak Posisi SETELAH pola selesai
                if (diff.isShuffling) {
                    SwingUtilities.invokeLater(() -> statusLabel.setText("Shuffling Positions..."));
                    Thread.sleep(600); 
                    SwingUtilities.invokeLater(this::shufflePositions);
                    Thread.sleep(600); 
                }
                
                isPlayerTurn = true;
                SoundManager.playSound("your-turn");
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("YOUR TURN!");
                    startPlayerTimer(); 
                });
                
            } catch (InterruptedException e) { e.printStackTrace(); }
        }).start();
    }
    
    // Helper untuk membuat pola acak baru sepanjang 'length'
    private void generateNewRandomSequence(int length) {
        sequence.clear();
        for(int i = 0; i < length; i++) {
            sequence.add(random.nextInt(shapes.size()));
        }
    }

    // --- LOGIKA WAKTU BARU ---
    private void startPlayerTimer() {
        Main.Difficulty diff = mainFrame.getDifficulty();
        if (diff == Main.Difficulty.EASY) return;

        // Waktu Dasar per Item (Semakin sulit semakin sedikit waktu berpikir per item)
        // Medium: 1000ms per item, Hard: 800ms per item
        int timePerItem = (diff == Main.Difficulty.MEDIUM) ? 1000 : 800;
        
        // Hitung Total Waktu Dasar
        int totalTime = sequence.size() * timePerItem;
        
        // --- LOGIKA BONUS WAKTU BERTAHAP ---
        int bonusTime = 0;
        if (diff == Main.Difficulty.MEDIUM) {
            // Tiap kelipatan 30 turn, tambah 2 detik buffer
            int milestonesPassed = roundLevel / 30;
            bonusTime = milestonesPassed * 2000; 
        } else if (diff == Main.Difficulty.HARD) {
            // Tiap kelipatan 25 turn, tambah 1.5 detik buffer
            int milestonesPassed = roundLevel / 25;
            bonusTime = milestonesPassed * 1500;
        }

        // Tampilkan notifikasi kecil jika baru saja melewati milestone
        if (bonusTime > 0 && (roundLevel % 25 == 0 || roundLevel % 30 == 0)) {
            statusLabel.setText("TIME EXTENDED! GO!");
        }

        // Total Waktu = (Jumlah Item * Waktu/Item) + Bonus Milestone
        timeLeft = totalTime + bonusTime;
        
        // Set Progress Bar
        timerBar.setMaximum(timeLeft);
        timerBar.setValue(timeLeft);
        timerBar.setVisible(true);
        timerBar.setForeground(Theme.COLOR_BLUE);
        
        turnTimer.start(); 
    }

    private void updateTimer() {
        if (!isPlayerTurn) return;
        timeLeft -= 50;
        timerBar.setValue(timeLeft);
        
        if (timeLeft < timerBar.getMaximum() * 0.3) timerBar.setForeground(Theme.COLOR_RED);

        if (timeLeft <= 0) {
            turnTimer.stop();
            gameOver(); 
        }
    }

    private void handlePlayerClick(int x, int y) {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).isClicked(x, y)) {
                flashShape(shapes.get(i));
                SoundManager.playSound(String.valueOf(i + 1));

                if (i == sequence.get(currentStep)) {
                    currentStep++;
                    if (currentStep >= sequence.size()) {
                        // RONDE SELESAI
                        score++;
                        roundLevel++; // Naik level -> Pola makin panjang
                        
                        isPlayerTurn = false;
                        turnTimer.stop(); 
                        timerBar.setVisible(false);
                        
                        statusLabel.setText("Correct!");
                        score += (mainFrame.getDifficulty() == Main.Difficulty.HARD) ? 2 : 1; 
                        
                        new Thread(() -> {
                            try { Thread.sleep(800); } catch(Exception ex){}
                            SwingUtilities.invokeLater(() -> {
                                statusLabel.setText("Score: " + score);
                                nextRound();
                            });
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
            try { Thread.sleep(150); } catch (Exception e){}
            shape.setActive(false);
            repaint();
        }).start();
    }

    private void gameOver() {
        isGameRunning = false;
        isPlayerTurn = false;
        turnTimer.stop();
        SoundManager.stopBackgroundMusic();
        SoundManager.playSound("game-over");
        saveScore();
        Popup.showGameOver(mainFrame, score);
    }

    private void saveScore() {
        new Thread(() -> {
            try {
                Connection conn = DatabaseConnection.getConnection();
                String sql = "INSERT INTO leaderboard (id_user, score) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, mainFrame.getCurrentUserId());
                pstmt.setInt(2, score);
                pstmt.executeUpdate();
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Theme.imgGame != null) {
            g.drawImage(Theme.imgGame, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0,0,0,150)); 
            g.fillRect(0,0,getWidth(), getHeight());
        } else {
            Graphics2D g2 = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, Theme.BG_MAIN, 0, getHeight(), Theme.BG_SECONDARY);
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        for (GameShape shape : shapes) {
            shape.draw(g);
        }
    }
}