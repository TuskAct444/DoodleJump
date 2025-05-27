import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class MovingPlatform {
    private Image img;
    private AffineTransform tx;

    int x, y;
    int vx;
    int width, height;
    double scaleWidth = 0.3;
    double scaleHeight = 0.3;

    public MovingPlatform(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.vx = speed;

        img = getImage("/imgs/platform.png");

        width = 100;
        height = 20;

        tx = AffineTransform.getTranslateInstance(x, y);
        tx.scale(scaleWidth, scaleHeight);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        move();
        init(x, y);
        g2.drawImage(img, tx, null);
    }

    private void move() {
        x += vx;
        if (x <= 0 || x + width >= Frame.width) {
            vx = -vx;
        }
    }

    private void init(double a, double b) {
        tx.setToTranslation(a, b);
        tx.scale(scaleWidth, scaleHeight);
    }

    private Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = MovingPlatform.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }

    public boolean collidesWith(doodleCharacter mc) {
        int mcX = mc.x;
        int mcY = mc.y;
        int mcWidth = 50;
        int mcHeight = 50;

        return (mcX + mcWidth > x && mcX < x + width &&
                mcY + mcHeight > y && mcY + mcHeight < y + height + 10);
    }
}



