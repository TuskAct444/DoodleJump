import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class FloatPowerUp {
    private Image img;
    private AffineTransform tx;
    public int x, y;
    public int width = 30, height = 30;
    private boolean collected = false;

    public FloatPowerUp(int x, int y) {

    	this.x = x;
        this.y = y;
        img = getImage("/imgs/" + "Umbrella.png"); // Replace with your actual image

       
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
            URL imageURL = FloatPowerUp.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }
}

