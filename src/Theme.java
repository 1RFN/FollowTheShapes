import java.awt.Color;
import java.awt.Font;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Theme {
    
    // Palet Warna
    public static final Color BG_DARK = new Color(30, 30, 30);
    public static final Color TEXT_LIGHT = new Color(230, 230, 230);
    public static final Color TEXT_DARK = new Color(30, 30, 30);
    public static final Color PRIMARY_YELLOW = new Color(240, 178, 49);
    public static final Color BUTTON_DISABLED = new Color(80, 80, 80);

    // Warna untuk feedback game
    public static final Color TEXT_CORRECT = new Color(116, 185, 104); // Hijau
    public static final Color TEXT_INCORRECT = new Color(220, 53, 69); // Merah
    public static final Color TEXT_DEFAULT = new Color(150, 150, 150); // Abu-abu
    public static final Color CURRENT_WORD_BG = new Color(60, 60, 60);

    // Font
    public static final Font FONT_HEADER = new Font("Arial", Font.BOLD, 32);
    public static final Font FONT_BODY = new Font("Arial", Font.PLAIN, 18);
    public static final Font FONT_BUTTON = new Font("Arial", Font.BOLD, 16);
    public static final Font FONT_GAME_TEXT = new Font("Monospaced", Font.PLAIN, 22);
    public static final Font FONT_INFO = new Font("Arial", Font.BOLD, 14);

    // Style untuk JTextPane (Disimpan sesuai request, meski game ini pakai Label)
    public static final SimpleAttributeSet STYLE_DEFAULT;
    public static final SimpleAttributeSet STYLE_CORRECT;
    public static final SimpleAttributeSet STYLE_INCORRECT;
    public static final SimpleAttributeSet STYLE_CURRENT_CHAR_CORRECT;
    public static final SimpleAttributeSet STYLE_CURRENT_CHAR_INCORRECT;

    static {
        STYLE_DEFAULT = new SimpleAttributeSet();
        StyleConstants.setFontFamily(STYLE_DEFAULT, FONT_GAME_TEXT.getFamily());
        StyleConstants.setFontSize(STYLE_DEFAULT, FONT_GAME_TEXT.getSize());
        StyleConstants.setForeground(STYLE_DEFAULT, TEXT_DEFAULT);

        STYLE_CORRECT = new SimpleAttributeSet(STYLE_DEFAULT);
        StyleConstants.setForeground(STYLE_CORRECT, TEXT_CORRECT);

        STYLE_INCORRECT = new SimpleAttributeSet(STYLE_DEFAULT);
        StyleConstants.setForeground(STYLE_INCORRECT, TEXT_INCORRECT);

        STYLE_CURRENT_CHAR_CORRECT = new SimpleAttributeSet(STYLE_DEFAULT);
        StyleConstants.setForeground(STYLE_CURRENT_CHAR_CORRECT, TEXT_LIGHT);
        StyleConstants.setBackground(STYLE_CURRENT_CHAR_CORRECT, CURRENT_WORD_BG);

        STYLE_CURRENT_CHAR_INCORRECT = new SimpleAttributeSet(STYLE_DEFAULT);
        StyleConstants.setForeground(STYLE_CURRENT_CHAR_INCORRECT, TEXT_INCORRECT);
        StyleConstants.setBackground(STYLE_CURRENT_CHAR_INCORRECT, CURRENT_WORD_BG);
    }
    private Theme() {}
}