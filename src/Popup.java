import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Popup {

    public static void show(Component parent, String title, String message, boolean isError) {
        JDialog dialog = createDialog(parent);
        dialog.setSize(300, 160); 

        // HEADER 
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(Theme.FONT_SUBTITLE); // Font agak besar dikit
        titleLabel.setForeground(isError ? Theme.TEXT_INCORRECT : Theme.TEXT_CORRECT);
        // Padding Atas dikurangi (15), Bawah dirapatkan (0)
        titleLabel.setBorder(new EmptyBorder(15, 10, 0, 10));

        // BODY (PESAN)
        JTextPane messagePane = new JTextPane();
        messagePane.setText(message);
        messagePane.setFont(Theme.FONT_BODY);
        messagePane.setForeground(Theme.TEXT_PRIMARY);
        messagePane.setBackground(new Color(0,0,0,0)); 
        messagePane.setEditable(false);
        messagePane.setFocusable(false);
        
        StyledDocument doc = messagePane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        JPanel msgPanel = new JPanel(new BorderLayout()); 
        msgPanel.setBackground(new Color(Theme.BG_SECONDARY.getRed(), Theme.BG_SECONDARY.getGreen(), Theme.BG_SECONDARY.getBlue(), 220));
        msgPanel.setBorder(new EmptyBorder(5, 15, 0, 15)); 
        msgPanel.add(messagePane, BorderLayout.CENTER);

        // FOOTER (TOMBOL)
        ModernButton btnOk = new ModernButton("OK");
        btnOk.setPreferredSize(new Dimension(80, 30));
        btnOk.addActionListener(e -> dialog.dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(Theme.BG_SECONDARY.getRed(), Theme.BG_SECONDARY.getGreen(), Theme.BG_SECONDARY.getBlue(), 220));
        btnPanel.setBorder(new EmptyBorder(5, 0, 15, 0));
        btnPanel.add(btnOk);

        dialog.add(titleLabel, BorderLayout.NORTH);
        dialog.add(msgPanel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public static void showGameOver(Main mainFrame, int score) {
        JDialog dialog = createDialog(mainFrame);
        // Game Over tetap agak besar biar dramatis, tapi dirapikan
        dialog.setSize(340, 230); 

        JLabel titleLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_INCORRECT);
        titleLabel.setBorder(new EmptyBorder(20, 0, 5, 0));

        JLabel scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 42)); 
        scoreLabel.setForeground(Theme.TEXT_PRIMARY);
        scoreLabel.setBorder(new EmptyBorder(5, 0, 10, 0));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(new Color(Theme.BG_SECONDARY.getRed(), Theme.BG_SECONDARY.getGreen(), Theme.BG_SECONDARY.getBlue(), 220));
        btnPanel.setBorder(new EmptyBorder(0, 0, 25, 0));

        ModernButton btnRetry = new ModernButton("TRY AGAIN");
        btnRetry.setPreferredSize(new Dimension(100, 35));
        btnRetry.addActionListener(e -> {
            dialog.dispose();
            mainFrame.getGamePanel().prepareGame();
        });

        ModernButton btnMenu = new ModernButton("MENU");
        btnMenu.setPreferredSize(new Dimension(80, 35));
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
        dialog.getContentPane().setBackground(new Color(Theme.BG_SECONDARY.getRed(), Theme.BG_SECONDARY.getGreen(), Theme.BG_SECONDARY.getBlue(), 220));
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Theme.BUTTON_COLOR, 2));
        dialog.setLayout(new BorderLayout());
        return dialog;
    }
}