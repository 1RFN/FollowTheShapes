import java.awt.*;

public class CircleShape extends GameShape {
    public CircleShape(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isActive) {
            // Glow
            g2.setColor(color);
            g2.setStroke(new BasicStroke(15));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2.drawOval(x - 5, y - 5, size + 10, size + 10);
            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2.setColor(Theme.COLOR_GLOW);
            g2.fillOval(x, y, size, size);
            
            g2.setColor(color);
            g2.setStroke(new BasicStroke(4));
            g2.drawOval(x, y, size, size);
        } else {
            g2.setColor(color.darker().darker());
            g2.fillOval(x, y, size, size);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(4));
            g2.drawOval(x, y, size, size);
        }
    }

    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        int centerX = x + size / 2;
        int centerY = y + size / 2;
        double distance = Math.sqrt(Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - centerY, 2));
        return distance <= size / 2;
    }
    
    @Override
    public void highlight(Graphics2D g2) {}
}