import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class LoginPanel extends JPanel {
    private JTextField userField;
    private Main mainFrame;

    public LoginPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setBackground(new Color(40, 44, 52)); // Dark theme

        JLabel title = new JLabel("FOLLOW THE SHAPES!");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        userField = new JTextField(15);
        JButton btnLogin = new JButton("START GAME");

        btnLogin.addActionListener(e -> {
            String username = userField.getText().trim();
            if (!username.isEmpty()) {
                loginUser(username);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10, 0, 20, 0);
        add(title, gbc);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 10, 0);
        add(userField, gbc);
        gbc.gridy = 2;
        add(btnLogin, gbc);
    }

    private void loginUser(String username) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            // Cek user atau buat baru
            String sql = "INSERT IGNORE INTO users (username) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            
            // Ambil ID (bisa dioptimize, tapi ini cara mudah)
            sql = "SELECT id FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int userId = rs.getInt("id");
                mainFrame.setCurrentUser(userId, username);
                mainFrame.showCard("Menu");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}