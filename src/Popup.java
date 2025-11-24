import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class Popup {

    public static void show(Component parent, String title, String message, boolean isError) {
        JDialog dialog = createDialog(parent);

        // --- HEADER ---
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(Theme.FONT_SUBTITLE);
        titleLabel.setForeground(isError ? Theme.TEXT_INCORRECT : Theme.TEXT_CORRECT);
        titleLabel.setBorder(new EmptyBorder(20, 10, 5, 10)); // Padding atas dikurangi

        // --- BODY (PESAN) ---
        JTextPane messagePane = new JTextPane();
        messagePane.setText(message);
        messagePane.setFont(Theme.FONT_BODY);
        messagePane.setForeground(Theme.TEXT_PRIMARY);
        messagePane.setBackground(Theme.BG_SECONDARY);
        messagePane.setEditable(false);
        messagePane.setFocusable(false);
        
        // Rata Tengah
        StyledDocument doc = messagePane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        JPanel msgPanel = new JPanel(new BorderLayout()); 
        msgPanel.setBackground(Theme.BG_SECONDARY);
        // Padding kiri-kanan dikurangi jadi 20 (sebelumnya 50) agar muat di window kecil
        msgPanel.setBorder(new EmptyBorder(5, 20, 5, 20)); 
        msgPanel.add(messagePane, BorderLayout.CENTER);

        // --- FOOTER (TOMBOL) ---
        ModernButton btnOk = new ModernButton("OK");
        btnOk.setPreferredSize(new Dimension(100, 35)); // Tombol sedikit diperkecil
        btnOk.addActionListener(e -> dialog.dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Theme.BG_SECONDARY);
        btnPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        btnPanel.add(btnOk);

        dialog.add(titleLabel, BorderLayout.NORTH);
        dialog.add(msgPanel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public static void showGameOver(Main mainFrame, int score) {
        JDialog dialog = createDialog(mainFrame);
        // Khusus Game Over ukurannya kita buat sedikit lebih tinggi
        dialog.setSize(350, 260); 

        JLabel titleLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_INCORRECT);
        titleLabel.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        scoreLabel.setForeground(Theme.TEXT_PRIMARY);
        scoreLabel.setBorder(new EmptyBorder(5, 0, 20, 0));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(Theme.BG_SECONDARY);
        btnPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        ModernButton btnRetry = new ModernButton("TRY AGAIN");
        btnRetry.setPreferredSize(new Dimension(110, 40));
        btnRetry.addActionListener(e -> {
            dialog.dispose();
            mainFrame.getGamePanel().startGame();
        });

        ModernButton btnMenu = new ModernButton("MENU");
        btnMenu.setPreferredSize(new Dimension(90, 40));
        btnMenu.addActionListener(e -> {
            dialog.dispose();
            mainFrame.showCard("Menu");
        });

        btnPanel.add(btnRetry);
        btnPanel.add(btnMenu);

        dialog.add(titleLabel, BorderLayout.NORTH);
        dialog.add(scoreLabel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }

    private static JDialog createDialog(Component parent) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog(window, "Message", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.getContentPane().setBackground(Theme.BG_SECONDARY);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Theme.COLOR_YELLOW, 2));
        dialog.setLayout(new BorderLayout());
        
        // --- UKURAN BARU (LEBIH KECIL) ---
        dialog.setSize(350, 220); // Pas untuk pesan pendek
        return dialog;
    }
}