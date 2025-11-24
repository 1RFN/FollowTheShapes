import java.awt.*;

public class Theme {
    // --- PALET WARNA (Modern Dark Arcade) ---
    public static final Color BG_MAIN = Color.decode("#1E1E2E");       // Background Utama (Dark Navy)
    public static final Color BG_SECONDARY = Color.decode("#26283C");  // Background Kartu/Panel
    
    public static final Color TEXT_PRIMARY = Color.decode("#CDD6F4");  // Putih Tulang (Teks Utama)
    public static final Color TEXT_SECONDARY = Color.decode("#A6ADC8"); // Abu-abu Muda (Sub-judul)
    public static final Color TEXT_INCORRECT = Color.decode("#F38BA8"); // Merah Pastel (Error)
    public static final Color TEXT_CORRECT = Color.decode("#A6E3A1");   // Hijau Pastel (Sukses)
    
    // Warna Tombol
    public static final Color BUTTON_COLOR = Color.decode("#fab387");  // Oranye Peach
    public static final Color BUTTON_HOVER = Color.decode("#f9c096");  // Oranye lebih terang
    public static final Color BUTTON_TEXT = Color.decode("#1E1E2E");   // Teks tombol (Gelap)

    // Warna Shape Game (Pastel Neon)
    public static final Color COLOR_RED = Color.decode("#F38BA8");
    public static final Color COLOR_BLUE = Color.decode("#89B4FA");
    public static final Color COLOR_GREEN = Color.decode("#A6E3A1");
    public static final Color COLOR_YELLOW = Color.decode("#F9E2AF");

    // --- FONT (Lebih Bersih) ---
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 32);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.BOLD, 20);
    public static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 16);

    private Theme() {}
}