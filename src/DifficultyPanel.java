import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class DifficultyPanel extends JPanel {
    private Main mainFrame;

    public DifficultyPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setOpaque(false);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(new Color(20, 25, 40, 240));
        
        cardPanel.setBorder(new CompoundBorder(
            new LineBorder(Theme.COLOR_BLUE, 2, true),
            new EmptyBorder(40, 60, 40, 60)
        ));

        JLabel title = new JLabel("SELECT DIFFICULTY");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        ModernButton btnEasy = createColoredButton("EASY", Theme.COLOR_GREEN);
        btnEasy.addActionListener(e -> startGame(Main.Difficulty.EASY));
        JLabel lblEasy = createDesc("Relax Mode • Pattern Accumulates");

        ModernButton btnMedium = createColoredButton("MEDIUM", Theme.COLOR_BLUE);
        btnMedium.addActionListener(e -> startGame(Main.Difficulty.MEDIUM));
        JLabel lblMed = createDesc("Timer Active • New Pattern Every Round");

        ModernButton btnHard = createColoredButton("HARD", Theme.TEXT_INCORRECT);
        btnHard.addActionListener(e -> startGame(Main.Difficulty.HARD));
        JLabel lblHard = createDesc("Shuffle Mode • Extreme Speed");

        ModernButton btnBack = new ModernButton("BACK");
        btnBack.setPreferredSize(new Dimension(120, 40)); 
        btnBack.setForeground(Color.BLACK); 
        btnBack.addActionListener(e -> mainFrame.showCard("Menu"));
        
        JPanel backPanel = new JPanel();
        backPanel.setOpaque(false);
        backPanel.add(btnBack);
        backPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardPanel.add(title);
        cardPanel.add(Box.createVerticalStrut(40));
        
        addOptionGroup(cardPanel, btnEasy, lblEasy);
        cardPanel.add(Box.createVerticalStrut(25));
        
        addOptionGroup(cardPanel, btnMedium, lblMed);
        cardPanel.add(Box.createVerticalStrut(25));
        
        addOptionGroup(cardPanel, btnHard, lblHard);
        cardPanel.add(Box.createVerticalStrut(45));
        
        cardPanel.add(backPanel);

        add(cardPanel);
    }

    private ModernButton createColoredButton(String text, Color baseColor) {
        ModernButton btn = new ModernButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2.setColor(baseColor.brighter());
                } else {
                    g2.setColor(baseColor);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 4;

                g2.setColor(Color.BLACK); 
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        return btn;
    }

    private void addOptionGroup(JPanel parent, ModernButton btn, JLabel desc) {
        btn.setPreferredSize(new Dimension(300, 50));
        btn.setMaximumSize(new Dimension(300, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        parent.add(btn);
        parent.add(Box.createVerticalStrut(8));
        parent.add(desc);
    }

    private JLabel createDesc(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13)); 
        lbl.setForeground(new Color(230, 230, 230));
        return lbl;
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