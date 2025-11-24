import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class LeaderboardPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Main mainFrame;

    public LeaderboardPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARK);

        // Header
        JLabel title = new JLabel("TOP 10 LEADERBOARD", SwingConstants.CENTER);
        title.setFont(Theme.FONT_HEADER);
        title.setForeground(Theme.PRIMARY_YELLOW);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Table Setup
        String[] columns = {"Rank", "Username", "Score", "Date"};
        model = new DefaultTableModel(columns, 0) {
            @Override // Agar sel tidak bisa diedit
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(Theme.FONT_BODY);
        table.setRowHeight(30);
        table.getTableHeader().setFont(Theme.FONT_BUTTON);
        
        // Custom Renderer untuk pewarnaan tabel
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Theme.BG_DARK);
        
        JButton btnBack = new JButton("Back to Menu");
        btnBack.setFont(Theme.FONT_BUTTON);
        btnBack.setBackground(Theme.BUTTON_DISABLED);
        btnBack.setForeground(Theme.TEXT_LIGHT);
        btnBack.addActionListener(e -> mainFrame.showCard("Menu"));
        
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public void refreshData() {
        model.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            
            // PERBAIKAN QUERY:
            // 1. Join user_id dengan user_id (bukan id)
            // 2. ORDER BY score DESC (tertinggi ke terendah)
            // 3. LIMIT 10 (hanya 10 teratas)
            String sql = "SELECT u.username, l.score, l.created_at " +
                         "FROM leaderboard l " +
                         "JOIN users u ON l.user_id = u.user_id " + // FIX: u.id -> u.user_id
                         "ORDER BY l.score DESC LIMIT 10"; 
                         
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            int rank = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                    rank++,
                    rs.getString("username"),
                    rs.getInt("score"),
                    rs.getString("created_at")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage());
        }
    }
}