import java.awt.*;

public class SquareShape extends GameShape {
    public SquareShape(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isActive) {
            // --- EFEK GLOW (MENYALA) ---
            // 1. Glow Luar (Transparan)
            g2.setColor(color); 
            g2.setStroke(new BasicStroke(15)); // Garis tebal pudar
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2.drawRect(x - 5, y - 5, size + 10, size + 10);
            
            // 2. Kembalikan Opacity
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            
            // 3. Inti Putih Terang
            g2.setColor(Theme.COLOR_GLOW);
            g2.fillRect(x, y, size, size);
            
            // 4. Border Warna Asli
            g2.setColor(color);
            g2.setStroke(new BasicStroke(4));
            g2.drawRect(x, y, size, size);
            
        } else {
            // --- MATI (NORMAL) ---
            g2.setColor(color.darker().darker()); // Gelap
            g2.fillRect(x, y, size, size);
            
            g2.setColor(color);
            g2.setStroke(new BasicStroke(4));
            g2.drawRect(x, y, size, size);
        }
    }

    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
    }
}