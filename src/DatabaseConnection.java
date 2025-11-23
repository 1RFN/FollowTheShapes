import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_follow_the_shapes";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    private static Connection connection = null;

    // --- Singleton Connection ---
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                System.out.println("Berhasil terhubung ke Database!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC tidak ditemukan!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Gagal terhubung ke Database!");
            e.printStackTrace();
        }
        return connection;
    }

    // --- Inisialisasi tabel umum ---
    public static void initialize() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) NOT NULL UNIQUE," +
                "password VARCHAR(100) NOT NULL" +
                ")";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            System.out.println("Tabel users siap digunakan.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Register User ---
    public static boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                System.err.println("Username sudah digunakan!");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    // --- Login User ---
    public static int loginUser(String username, String password) {
        String sql = "SELECT user_id FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id"); // return ID user
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // gagal login
    }

    // --- Testing ---
    public static void main(String[] args) {
        initialize();
        registerUser("nb_test", "12345");
        int userId = loginUser("nb_test", "12345");
        System.out.println("Login berhasil, user_id = " + userId);
    }
}
