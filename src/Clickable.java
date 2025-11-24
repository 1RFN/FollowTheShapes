import java.awt.Graphics2D;
interface Clickable {
    boolean isClicked(int mouseX, int mouseY);
    void highlight(Graphics2D g2);
}