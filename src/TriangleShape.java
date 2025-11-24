import java.awt.*;
public class TriangleShape extends GameShape {
    public TriangleShape(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(isActive ? color.brighter().brighter() : color);
        
        int[] xPoints = {x + size/2, x, x + size};
        int[] yPoints = {y, y + size, y + size};
        g2.fillPolygon(xPoints, yPoints, 3);
        
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawPolygon(xPoints, yPoints, 3);
    }

    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        // Bounding box sederhana untuk memudahkan
        return mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
    }
    
    @Override
    public void highlight(Graphics2D g2) {}
}