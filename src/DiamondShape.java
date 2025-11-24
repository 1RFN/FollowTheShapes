import java.awt.*;
class DiamondShape extends GameShape {
    private Polygon shapePolygon;

    public DiamondShape(int x, int y, int size, Color color) {
        super(x, y, size, color);
        createPolygon();
    }

    // Membuat bentuk poligon berdasarkan koordinat x, y dan size
    private void createPolygon() {
        int half = size / 2;
        int[] xPoints = {x + half, x + size, x + half, x};
        int[] yPoints = {y, y + half, y + size, y + half};
        shapePolygon = new Polygon(xPoints, yPoints, 4);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Logika warna saat menyala
        g2.setColor(isActive ? color.brighter().brighter() : color);
        g2.fillPolygon(shapePolygon);
        
        // Garis pinggir
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawPolygon(shapePolygon);
    }

    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        // Menggunakan method bawaan Polygon untuk deteksi klik yang akurat
        return shapePolygon.contains(mouseX, mouseY);
    }

    @Override
    public void highlight(Graphics2D g2) {
        // Implementasi highlight jika diperlukan
    }
}