import java.awt.*;

public class DiamondShape extends GameShape {
    private Polygon shapePolygon;

    public DiamondShape(int x, int y, int size, Color color) {
        super(x, y, size, color);
        createPolygon();
    }

    public void createPolygon() {
        int half = size / 2;
        int[] xPoints = {x + half, x + size, x + half, x};
        int[] yPoints = {y, y + half, y + size, y + half};
        shapePolygon = new Polygon(xPoints, yPoints, 4);
    }

    @Override
    public void draw(Graphics g) {
        // Update bentuk (penting jika posisi berubah di mode Hard)
        createPolygon(); 
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (isActive) {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(15));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2.drawPolygon(shapePolygon);
            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2.setColor(Theme.COLOR_GLOW);
            g2.fillPolygon(shapePolygon);
            
            g2.setColor(color);
            g2.setStroke(new BasicStroke(4));
            g2.drawPolygon(shapePolygon);
        } else {
            g2.setColor(color.darker().darker());
            g2.fillPolygon(shapePolygon);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(4));
            g2.drawPolygon(shapePolygon);
        }
    }

    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        return shapePolygon.contains(mouseX, mouseY);
    }
}