import java.awt.*;
import javax.swing.*;

public class LoginPanel extends JPanel {
    private JTextField userField;
    private JPasswordField passField;
    private Main mainFrame;

    public LoginPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout()); // Posisi Tengah
        setBackground(Theme.BG_MAIN);   

        // --- KARTU LOGIN ---
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.BG_SECONDARY);
        // Border membulat (hack visual) dengan padding
        card.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50)); 

        JLabel title = new JLabel("Follow The Shapes");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Please Login to Play");
        subtitle.setFont(Theme.FONT_BODY);
        subtitle.setForeground(Theme.TEXT_SECONDARY);
        subtitle.setAlignmentX(CENTER_ALIGNMENT);

        // Input Fields Custom
        userField = createStyledTextField();
        passField = createStyledPasswordField();

        // Tombol Modern
        ModernButton btnLogin = new ModernButton("LOGIN");
        btnLogin.setMaximumSize(new Dimension(250, 45));
        btnLogin.setAlignmentX(CENTER_ALIGNMENT);

        ModernButton btnRegister = new ModernButton("REGISTER");
        btnRegister.setMaximumSize(new Dimension(250, 45));
        btnRegister.setAlignmentX(CENTER_ALIGNMENT);
        
        // Space/Jarak
        card.add(title);
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));
        card.add(createLabel("Username"));
        card.add(userField);
        card.add(Box.createVerticalStrut(15));
        card.add(createLabel("Password"));
        card.add(passField);
        card.add(Box.createVerticalStrut(30));
        card.add(btnLogin);
        card.add(Box.createVerticalStrut(10));
        card.add(btnRegister);

        add(card); // Masukkan kartu ke panel utama

        // --- LOGIC TOMBOL ---
        btnLogin.addActionListener(e -> {
            String u = userField.getText().trim();
            String p = new String(passField.getPassword()).trim();
            if(u.isEmpty() || p.isEmpty()) { Popup.show(this, "Oops!", "Data tidak boleh kosong!", true); return; }
            
            int uid = DatabaseConnection.loginUser(u, p);
            if(uid != -1) {
                mainFrame.setCurrentUser(uid, u);
                Popup.show(this, "Success", "Welcome back, " + u + "!", false);
                userField.setText(""); passField.setText("");
                mainFrame.showCard("Menu");
            } else {
                Popup.show(this, "Failed", "Username/Password salah!", true);
            }
        });

        btnRegister.addActionListener(e -> {
             String u = userField.getText().trim();
             String p = new String(passField.getPassword()).trim();
             if(u.isEmpty() || p.isEmpty()) { Popup.show(this, "Info", "Isi username & password!", true); return; }
             
             if(DatabaseConnection.registerUser(u, p)) {
                 Popup.show(this, "Success", "Akun berhasil dibuat! Silakan Login.", false);
             } else {
                 Popup.show(this, "Failed", "Username sudah terpakai.", true);
             }
        });
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.FONT_BODY);
        l.setForeground(Theme.TEXT_SECONDARY);
        l.setAlignmentX(CENTER_ALIGNMENT);
        return l;
    }

    private JTextField createStyledTextField() {
        JTextField f = new JTextField();
        styleField(f);
        return f;
    }
    
    private JPasswordField createStyledPasswordField() {
        JPasswordField f = new JPasswordField();
        styleField(f);
        return f;
    }

    private void styleField(JTextField f) {
        f.setMaximumSize(new Dimension(250, 35));
        f.setFont(Theme.FONT_BODY);
        f.setBackground(Theme.BG_MAIN); // Input gelap
        f.setForeground(Theme.TEXT_PRIMARY); // Teks putih
        f.setCaretColor(Theme.BUTTON_COLOR);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.TEXT_SECONDARY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        f.setAlignmentX(CENTER_ALIGNMENT);
    }
    public void resetFields() {
    userField.setText("");
    passField.setText("");
}

}