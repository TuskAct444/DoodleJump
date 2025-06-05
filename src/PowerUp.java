import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class PowerUp {
    private Image img;
    private AffineTransform tx;
    public int x, y;
    public int width = 30, height = 30;
    private boolean collected = false;

    public PowerUp(int x, int y) {
        img = getImage("/imgs/" + "pogoStick.png"); // Replace with your actual image

    	this.x = x;
        this.y = y;
        
    }

    public void paint(Graphics g) {
    	if (!collected && img != null) {
            g.drawImage(img, x, y, width, height, null); // Draw the powerup
        }
    }


    public boolean collidesWith(MC mc) {
        if (collected) return false;
        Rectangle rectMC = new Rectangle(mc.getX(), mc.getY(), mc.getWidth(), mc.getHeight());
        Rectangle rectPower = new Rectangle(x, y, width, height);
        if (rectMC.intersects(rectPower)) {
            collected = true;
            return true;
        }
        return false;
    }

    public boolean isCollected() {
        return collected;
    }

    private Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = getClass().getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace(); // Debug if file not found
        }
        return tempImage;
    }
}