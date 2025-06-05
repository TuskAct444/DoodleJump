import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {
    double x, y;
    double dx;
    int width = 10, height = 5;
    double speed = 5;

    public Bullet(int startX, int startY, boolean shootRight) {
        this.x = startX;
        this.y = startY;

        dx = shootRight ? speed : -speed;
    }

    public void update() {
        x += dx;
    }

    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) x, (int) y, width, height);
    }

    public boolean isOffScreen() {
        return x < -50 || x > Frame.width + 50;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}

