
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.Random;
import java.util.ArrayList;

public class HornetEnemy {
    private Image image;
    private AffineTransform tx;

    int x, y;
    int width = 50;
    int height = 50;
    int speed;
    boolean fromLeft;
    ArrayList<Bullet> bullets = new ArrayList<>();
    int shootCooldown = 0;
    int speedY = 1;
    boolean movingDown = true;
    private int lifetime = 0;  // counts update ticks
    private static final int MAX_LIFETIME = 2500; // 10 seconds / 4 ms per update

    public HornetEnemy(int x, int y) {
        image = getImage("/imgs/" + "HornetEnemy.png"); // enemy sprite

        // Use passed x, y directly (don't overwrite them here)
        this.x = x;
        this.y = y;

        tx = AffineTransform.getTranslateInstance(x, y);
        tx.scale(0.5, 0.5);
    }


    public void update() {
        y += speedY;
        lifetime++;  // increment every update tick

        
        if (y <= 0 || y + height >= Frame.height) {
            speedY *= -1; // reverse vertical direction
        }

        // Update transform position
        tx.setToTranslation(x, y);
        tx.scale(0.5, 0.5);

        // Shooting bullets horizontally every so often
        shootCooldown++;
        if (shootCooldown > 60 && Math.random() < 0.01) {
            // Shoot bullets both left and right from enemy center
            bullets.add(new Bullet(x, y + height / 2, false)); // shoot left
            bullets.add(new Bullet(x + width, y + height / 2, true)); // shoot right
            shootCooldown = 0;
        }

        for (Bullet bullet : bullets) {
            bullet.update();
        }

        bullets.removeIf(Bullet::isOffScreen);
    

        // Update all bullets
        for (Bullet bullet : bullets) {
            bullet.update();
        }

        // Remove bullets off screen
        bullets.removeIf(Bullet::isOffScreen);
    }





    

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, tx, null);
        for (Bullet bullet : bullets) {
            bullet.paint(g);
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }


    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    private Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = HornetEnemy.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }
}
