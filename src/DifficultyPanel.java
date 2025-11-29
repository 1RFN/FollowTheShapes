import java.awt.*;
import javax.swing.*;

public class DifficultyPanel extends JPanel {
    private Main mainFrame;

    public DifficultyPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout()); // Gunakan GridBag agar posisi di tengah persis

        // Panel Konten Transparan
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        // Judul
        JLabel title = new JLabel("SELECT DIFFICULTY");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- BUTTONS & DESCRIPTIONS ---
        
        // 1. EASY
        ModernButton btnEasy = new ModernButton("EASY");
        styleButton(btnEasy, Theme.COLOR_GREEN); // Warna Kuning Neon
        btnEasy.addActionListener(e -> startGame(Main.Difficulty.EASY));
        
        JLabel lblEasy = createDesc("Relax Mode • Pattern Accumulates • No Timer");

        // 2. MEDIUM
        ModernButton btnMedium = new ModernButton("MEDIUM");
        styleButton(btnMedium, Theme.COLOR_BLUE); // Warna Biru Neon
        btnMedium.addActionListener(e -> startGame(Main.Difficulty.MEDIUM));
        
        JLabel lblMed = createDesc("New Pattern Every Round • Timer Active • Bonus Time @ Lvl 30");

        // 3. HARD
        ModernButton btnHard = new ModernButton("HARD");
        styleButton(btnHard, Theme.TEXT_INCORRECT); // Warna Merah Neon
        btnHard.addActionListener(e -> startGame(Main.Difficulty.HARD));
        
        JLabel lblHard = createDesc("Random Shuffle • Extreme Timer • Bonus Time @ Lvl 25");

        // 4. BACK
        ModernButton btnBack = new ModernButton("BACK");
        btnBack.setPreferredSize(new Dimension(150, 45));
        btnBack.setForeground(Theme.TEXT_SECONDARY);
        btnBack.addActionListener(e -> mainFrame.showCard("Menu"));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- MENYUSUN LAYOUT (SPACING RAPI) ---
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(40));
        
        addSection(contentPanel, btnEasy, lblEasy);
        contentPanel.add(Box.createVerticalStrut(25));
        
        addSection(contentPanel, btnMedium, lblMed);
        contentPanel.add(Box.createVerticalStrut(25));
        
        addSection(contentPanel, btnHard, lblHard);
        contentPanel.add(Box.createVerticalStrut(50));
        
        contentPanel.add(btnBack);

        add(contentPanel);
    }

    private void styleButton(ModernButton btn, Color color) {
        btn.setPreferredSize(new Dimension(300, 55));
        btn.setForeground(color);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JLabel createDesc(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12)); // Font kecil minimalis
        lbl.setForeground(Theme.TEXT_SECONDARY);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private void addSection(JPanel panel, JComponent btn, JComponent desc) {
        panel.add(btn);
        panel.add(Box.createVerticalStrut(5)); // Jarak dikit antara tombol dan deskripsi
        panel.add(desc);
    }

    private void startGame(Main.Difficulty diff) {
        mainFrame.setDifficulty(diff);
        mainFrame.getGamePanel().prepareGame();
        mainFrame.showCard("Game");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Theme.imgMenu != null) {
            g.drawImage(Theme.imgMenu, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 150)); // Overlay gelap biar tulisan kebaca
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            Graphics2D g2 = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, Theme.BG_MAIN, 0, getHeight(), Theme.BG_SECONDARY);
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}