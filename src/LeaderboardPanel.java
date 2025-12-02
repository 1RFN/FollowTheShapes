import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class LeaderboardPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Main mainFrame;

    public LeaderboardPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_MAIN);
        setBorder(new EmptyBorder(30, 50, 30, 50));

        JLabel title = new JLabel("TOP PLAYERS", SwingConstants.CENTER);
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.COLOR_YELLOW);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Rank", "Username", "Score", "Date"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setFont(Theme.FONT_BODY);
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setBackground(Theme.BG_SECONDARY);
        table.setForeground(Theme.TEXT_PRIMARY);
        
        // Header Custom
        JTableHeader header = table.getTableHeader();
        header.setFont(Theme.FONT_BUTTON);
        header.setBackground(Theme.BG_MAIN);
        header.setForeground(Theme.COLOR_BLUE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.COLOR_BLUE));
        header.setReorderingAllowed(false); // SOLUSI: Kolom tidak bisa digeser!
        header.setResizingAllowed(false);   // Opsional: Kolom tidak bisa diubah ukurannya
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Theme.BG_MAIN);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Theme.BG_MAIN);
        btnPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        ModernButton btnBack = new ModernButton("BACK TO MENU");
        btnBack.setPreferredSize(new Dimension(200, 40));
        btnBack.addActionListener(e -> mainFrame.showCard("Menu"));
        
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public void refreshData() {
        model.setRowCount(0);
        // Menggunakan SwingWorker agar UI tidak freeze saat loading database
        new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() {
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    String sql = "SELECT u.username, l.score, l.played_at FROM leaderboard l " +
                                 "JOIN users u ON l.id_user = u.id_user ORDER BY l.score DESC LIMIT 10";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    int rank = 1;
                    while (rs.next()) {
                        publish(new Object[]{ rank++, rs.getString("username"), rs.getInt("score"), rs.getString("played_at") });
                    }
                } catch (Exception e) { e.printStackTrace(); }
                return null;
            }

            @Override
            protected void process(java.util.List<Object[]> chunks) {
                for (Object[] row : chunks) model.addRow(row);
            }
        }.execute();
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