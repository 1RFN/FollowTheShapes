import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // 1. Konstanta Konfigurasi Database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_follow_the_shapes";
    private static final String DB_USER = "root";      // Default XAMPP biasanya 'root'
    private static final String DB_PASS = "";          // Default XAMPP biasanya kosong

    // 2. Variabel Singleton (Menyimpan satu-satunya instance koneksi)
    private static Connection connection = null;

    // 3. Method Static untuk mendapatkan koneksi
    public static Connection getConnection() {
        // Cek apakah koneksi belum pernah dibuat atau sudah terputus
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    // Load Driver MySQL (Penting agar library terbaca)
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    
                    // Buat koneksi baru
                    connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                    System.out.println("Berhasil terhubung ke Database!");
                    
                } catch (ClassNotFoundException e) {
                    System.err.println("Driver JDBC tidak ditemukan. Pastikan library mysql-connector sudah ditambahkan!");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal terhubung ke Database!");
            e.printStackTrace();
        }
        
        return connection;
    }

    // 4. Method Main untuk TESTING (Hanya dijalankan saat tes koneksi)
    public static void main(String[] args) {
        getConnection();
    }
}