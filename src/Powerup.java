
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Powerup {
    private Image img;
    private AffineTransform tx;
    
    int x, y;
    double vy;
    int width, height;
    String type;
    double scaleWidth = 0.3;
    double scaleHeight = 0.3;

    public Powerup(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
        if(type.equals("none")) {
        	img = null;
        } else if(type.equals("Rocket")){
            img = getImage("/imgs/" + "powerup.png");
        }

        width = 40;
        height = 40;

        tx = AffineTransform.getTranslateInstance(x, y);
        tx.scale(scaleWidth, scaleHeight);
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
            URL imageURL = Powerup.class.getResource(path);
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

    public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
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
    
    public void updateVy(){
    	y += vy;
    }
 
}
			

