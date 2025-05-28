
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Platform {
    private Image img;
    private AffineTransform tx;

    int x, y;
    int width, height;
    double scaleWidth = 0.3;
    double scaleHeight = 0.3;

    public Platform(int x, int y) {
        this.x = x;
        this.y = y;

        img = getImage("/imgs/" + "platform.png");

        width = 100;
        height = 20;

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
		
		// represents each object as a rectangle
		
		Rectangle rectTemp = new Rectangle(
				
				mc.getX(),
				mc.getY(),
				mc.getWidth(),
				mc.getHeight()
				
				);
		
		// represent object queried for info as a rectangle
		Rectangle rowHitbox = new Rectangle(
				
				this.x,
				this.y,
				this.width,
				this.height

				);
		
		return rectTemp.intersects(rowHitbox);
		
	}
}
			

