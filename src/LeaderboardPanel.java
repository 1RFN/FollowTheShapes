import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LeaderboardPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Main mainFrame;

    public LeaderboardPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        
        String[] columns = {"Rank", "Username", "Score", "Date"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnBack = new JButton("Back to Menu");
        btnBack.addActionListener(e -> mainFrame.showCard("Menu"));
        add(btnBack, BorderLayout.SOUTH);
    }

    public void refreshData() {
        model.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT u.username, l.score, l.created_at " +
                         "FROM leaderboard l JOIN users u ON l.user_id = u.id " +
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
        }
    }
}