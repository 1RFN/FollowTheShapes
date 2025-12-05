import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Theme {
    // WARNA BARU (SPACE NEON THEME) 
    public static final Color BG_MAIN = Color.decode("#1E1E2E");       
    public static final Color BG_SECONDARY = Color.decode("#26283C");  
    
    public static final Color TEXT_PRIMARY = Color.decode("#E0E7FF"); 
    public static final Color TEXT_SECONDARY = Color.decode("#A6ADC8"); 
    public static final Color TEXT_INCORRECT = Color.decode("#FF3366"); 
    public static final Color TEXT_CORRECT = Color.decode("#00FFCC");   
    
    // WARNA TOMBOL
    // Dari Oranye -> Biru Langit Neon agar selaras dengan background
    public static final Color BUTTON_COLOR = Color.decode("#33CCFF"); 
    public static final Color BUTTON_HOVER = Color.decode("#66FFFF"); 
    public static final Color BUTTON_TEXT = Color.decode("#0D1117");  

    public static final Color COLOR_RED = Color.decode("#FF0055");    
    public static final Color COLOR_BLUE = Color.decode("#00D4FF");   
    public static final Color COLOR_GREEN = Color.decode("#00FF99");  
    public static final Color COLOR_YELLOW = Color.decode("#FFE600"); 
    public static final Color COLOR_GLOW = new Color(255, 255, 255, 180); 

    // FONT 
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 32);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.BOLD, 20);
    public static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_COUNTDOWN = new Font("SansSerif", Font.BOLD, 100);

    public static Image imgMenu;
    public static Image imgGame;
    public static Image imgLogin;

    public static void loadImages() {
        try {
            imgMenu = ImageIO.read(new File("assets/images/bg_menu.png"));
            imgGame = ImageIO.read(new File("assets/images/bg_game.png"));
            imgLogin = ImageIO.read(new File("assets/images/bg_login.png"));
        } catch (Exception e) {}
    }
}