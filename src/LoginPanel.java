import java.awt.*;
import javax.swing.*;

public class LoginPanel extends JPanel {
    private JTextField userField;
    private JPasswordField passField; // Tambahan field password
    private Main mainFrame;

    public LoginPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setBackground(Theme.BG_DARK); // Menggunakan Theme.java

        // --- UI COMPONENTS ---
        JLabel titleLabel = new JLabel("FOLLOW THE SHAPES!");
        titleLabel.setFont(Theme.FONT_HEADER);
        titleLabel.setForeground(Theme.PRIMARY_YELLOW);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(Theme.FONT_BODY);
        lblUser.setForeground(Theme.TEXT_LIGHT);
        
        userField = new JTextField(15);
        userField.setFont(Theme.FONT_BODY);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(Theme.FONT_BODY);
        lblPass.setForeground(Theme.TEXT_LIGHT);

        passField = new JPasswordField(15);
        passField.setFont(Theme.FONT_BODY);

        JButton btnLogin = new JButton("LOGIN");
        styleButton(btnLogin, Theme.PRIMARY_YELLOW, Theme.TEXT_DARK);

        JButton btnRegister = new JButton("REGISTER");
        styleButton(btnRegister, Theme.BUTTON_DISABLED, Theme.TEXT_LIGHT);

        // --- ACTION LISTENERS ---
        
        // Logic Login
        btnLogin.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username & Password tidak boleh kosong!");
                return;
            }

            // Panggil fungsi login dari DatabaseConnection
            int userId = DatabaseConnection.loginUser(user, pass);
            
            if (userId != -1) {
                // Login Berhasil
                mainFrame.setCurrentUser(userId, user);
                JOptionPane.showMessageDialog(this, "Welcome back, " + user + "!");
                clearFields();
                mainFrame.showCard("Menu"); // Pindah ke Menu Utama
            } else {
                JOptionPane.showMessageDialog(this, "Login Gagal! Cek username/password.");
            }
        });

        // Logic Register
        btnRegister.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Isi username & password untuk mendaftar!");
                return;
            }

            // Panggil fungsi register dari DatabaseConnection
            boolean success = DatabaseConnection.registerUser(user, pass);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Registrasi Berhasil! Silakan Login.");
            } else {
                JOptionPane.showMessageDialog(this, "Registrasi Gagal! Username mungkin sudah ada.");
            }
        });

        // --- LAYOUT SETUP (GridBag) ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1;
        add(lblUser, gbc);
        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(lblPass, gbc);
        gbc.gridx = 1;
        add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnLogin, gbc);
        
        gbc.gridy = 4;
        add(btnRegister, gbc);
    }

    private void styleButton(JButton btn, Color bg, Color text) {
        btn.setFont(Theme.FONT_BUTTON);
        btn.setBackground(bg);
        btn.setForeground(text);
        btn.setFocusPainted(false);
    }

    private void clearFields() {
        userField.setText("");
        passField.setText("");
    }
}