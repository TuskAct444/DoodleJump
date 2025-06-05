import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class BrokenPlatform {
    private Image img;
    private AffineTransform tx;

    int x, y;
    int width, height;
    double scaleWidth = 1.8;
    double scaleHeight = 1.5;

    public BrokenPlatform(int x, int y) {
        this.x = x;
        this.y = y;

        img = getImage("/imgs/" + "plankBroken.png");

        width = 300;
        height = 300;

        tx = AffineTransform.getTranslateInstance(x, y);
        tx.scale(scaleWidth, scaleHeight);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        init(x, y);
        g2.drawImage(img, tx, null);
    }

    private void init(double a, double b) {
        tx.setToTranslation(a, b);
        tx.scale(scaleWidth, scaleHeight);
    }

    private Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Platform.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }

    public boolean collided(MC mc) {
        Rectangle mcRect = new Rectangle(mc.getX(), mc.getY(), mc.getWidth(), mc.getHeight());
        Rectangle platformRect = new Rectangle(this.x, this.y, this.width, 10); // only collide with top edge

        // Check if MC is falling (positive Y velocity) and its bottom is above or slightly within the platform
        if (mcRect.intersects(platformRect)) {
            if (mc.getVy() > 0 && (mc.getY() + mc.getHeight()) <= (this.y + 15)) {
                return true;
            }
        }

        return false;
    }
}
