import java.awt.*;

public class TriangleShape extends GameShape {
    public TriangleShape(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int[] xPoints = {x + size/2, x, x + size};
        int[] yPoints = {y, y + size, y + size};

        if (isActive) {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(15));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2.drawPolygon(xPoints, yPoints, 3);
            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2.setColor(Theme.COLOR_GLOW);
            g2.fillPolygon(xPoints, yPoints, 3);
            
            g2.setColor(color);
            g2.setStroke(new BasicStroke(4));
            g2.drawPolygon(xPoints, yPoints, 3);
        } else {
            g2.setColor(color.darker().darker());
            g2.fillPolygon(xPoints, yPoints, 3);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(4));
            g2.drawPolygon(xPoints, yPoints, 3);
        }
    }

    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
    }
    
    @Override
    public void highlight(Graphics2D g2) {}
}