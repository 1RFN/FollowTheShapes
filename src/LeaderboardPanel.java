import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;

public class LeaderboardPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private Main mainFrame;
    private JComboBox<String> difficultyFilter;

    public LeaderboardPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_MAIN);
        setBorder(new EmptyBorder(30, 50, 30, 50));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        filterPanel.setOpaque(false);
        filterPanel.setPreferredSize(new Dimension(200, 40)); 
        
        JLabel lblFilter = new JLabel("Difficulty: ");
        lblFilter.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblFilter.setForeground(Theme.TEXT_SECONDARY);
        
        String[] levels = {"Easy", "Medium", "Hard"};
        difficultyFilter = new JComboBox<>(levels);
        difficultyFilter.setFont(Theme.FONT_BUTTON);
        difficultyFilter.setFocusable(false);
        difficultyFilter.setSelectedItem("Medium");
        difficultyFilter.setPreferredSize(new Dimension(130, 35));
        
        difficultyFilter.setBackground(Theme.BG_SECONDARY);
        difficultyFilter.setForeground(Theme.COLOR_BLUE);
        difficultyFilter.setBorder(new CompoundBorder(
            new LineBorder(Theme.COLOR_BLUE, 1, true),
            new EmptyBorder(0, 5, 0, 0)
        ));

        difficultyFilter.setRenderer(new DarkListRenderer());
        difficultyFilter.setUI(new DarkComboBoxUI());
        
        difficultyFilter.addActionListener(e -> refreshData());

        filterPanel.add(lblFilter);
        filterPanel.add(difficultyFilter);

        JPanel dummyPanel = new JPanel();
        dummyPanel.setOpaque(false);
        dummyPanel.setPreferredSize(new Dimension(200, 40)); 

        JLabel title = new JLabel("TOP PLAYERS", SwingConstants.CENTER);
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.COLOR_YELLOW);
        
        headerPanel.add(dummyPanel, BorderLayout.WEST);   
        headerPanel.add(title, BorderLayout.CENTER);      
        headerPanel.add(filterPanel, BorderLayout.EAST);  
        
        add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"Rank", "Username", "Score", "Difficulty", "Date"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setFont(Theme.FONT_BODY);
        table.setRowHeight(45);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        
        table.setBackground(Theme.BG_SECONDARY);
        table.setForeground(Theme.TEXT_PRIMARY);
        table.setSelectionBackground(Theme.BUTTON_COLOR);
        table.setSelectionForeground(Theme.BG_MAIN);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(Theme.FONT_BUTTON);
        header.setBackground(Theme.BG_MAIN);
        header.setForeground(Theme.COLOR_BLUE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.COLOR_BLUE));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Theme.BG_MAIN);
        scrollPane.setBorder(null); 
        
        scrollPane.getVerticalScrollBar().setUI(new InvisibleScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new InvisibleScrollBarUI());
        
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Theme.BG_MAIN);
        btnPanel.setBorder(new EmptyBorder(25, 0, 0, 0));
        
        ModernButton btnBack = new ModernButton("BACK TO MENU");
        btnBack.setPreferredSize(new Dimension(200, 45));
        btnBack.addActionListener(e -> mainFrame.showCard("Menu"));
        
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public void refreshData() {
        model.setRowCount(0);
        String selectedDiff = (String) difficultyFilter.getSelectedItem();

        new SwingWorker<Void, Object[]>() {
            @Override
            protected Void doInBackground() {
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    String sql = "SELECT u.username, l.score, l.difficulty, l.played_at " +
                                 "FROM leaderboard l " +
                                 "JOIN users u ON l.id_user = u.id_user " +
                                 "WHERE l.difficulty = ? " +
                                 "ORDER BY l.score DESC LIMIT 10";
                                 
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, selectedDiff);
                    
                    ResultSet rs = pstmt.executeQuery();
                    int rank = 1;
                    while (rs.next()) {
                        publish(new Object[]{ 
                            rank++, 
                            rs.getString("username"), 
                            rs.getInt("score"), 
                            rs.getString("difficulty"),
                            rs.getString("played_at") 
                        });
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

    private static class InvisibleScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(0, 0, 0, 0); 
            this.trackColor = new Color(0, 0, 0, 0); 
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(0, 0));
            btn.setMinimumSize(new Dimension(0, 0));
            btn.setMaximumSize(new Dimension(0, 0));
            return btn;
        }
        
        @Override
        public Dimension getPreferredSize(JComponent c) {
            return new Dimension(0, 0);
        }
    }

    private static class DarkComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton btn = new BasicArrowButton(
                BasicArrowButton.SOUTH,
                Theme.BG_SECONDARY, 
                Theme.BG_SECONDARY, 
                Theme.COLOR_BLUE,   
                Theme.BG_SECONDARY
            );
            btn.setBorder(BorderFactory.createEmptyBorder());
            return btn;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            g.setColor(Theme.BG_SECONDARY); 
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    private static class DarkListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setBorder(new EmptyBorder(5, 10, 5, 10));
            
            if (isSelected) {
                label.setBackground(Theme.BUTTON_COLOR);
                label.setForeground(Theme.BG_MAIN);
            } else {
                label.setBackground(Theme.BG_SECONDARY);
                label.setForeground(Theme.COLOR_BLUE);
            }
            return label;
        }
    }
}