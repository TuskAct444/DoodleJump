
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
    private Rectangle hb;

    int x, y;
    int width, height;
    double vy;
    double scaleWidth = 0.3;
    double scaleHeight = 0.3;

    public Platform(int x, int y) {
        this.x = x;
        this.y = y;
        vy = 0;

        img = getImage("/imgs/" + "platform.png");

        width = 140;
        vy = 0;
        height = 20;
        
        hb = new Rectangle(x,y,width,height);

        tx = AffineTransform.getTranslateInstance(x, y);
        tx.scale(scaleWidth, scaleHeight);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        y += vy;
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
    
    public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

    public boolean collides(MC d) {
		
		// represents each object as a rectangle
		
		Rectangle rectTemp = new Rectangle(
				
				d.getX(),
				d.getY(),
				d.getWidth(),
				d.getHeight()
				
				);
		
		// represent object queried for info as a rectangle
		Rectangle rowHitbox = new Rectangle(
				
				this.x,
				this.y,
				this.width,
				this.height

				);
		
		return (rectTemp.intersects(rowHitbox));
		
	}

	public void setVy(double abs) {
		// TODO Auto-generated method stub
		this.vy = abs;
	}
	
	public double getVy() {
		// TODO Auto-generated method stub
		return vy;
	}
	
	public void updateVy() {
		y += vy;
	}
 
}
			

