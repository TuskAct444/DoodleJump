
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
    int vx;
    double vy;
    boolean move, broken;
    boolean hasBeenUsed = false;
    boolean s;
    double scaleWidth = 0.3;
    double scaleHeight = 0.3;

    public Platform(int x, int y, boolean move, boolean broken, boolean isSpring) {
        this.x = x;
        this.y = y;
        this.move = move;
        vy = 0;
        if(move == true) {
        	vx = 2;
            img = getImage("/imgs/" + "moving.png");
        } else if(broken == true){
        	img = getImage("/imgs/" + "broken.png");
        } else if(isSpring == true) {
        	img = getImage("/imgs/" + "spring.png");
        } else {
            img = getImage("/imgs/" + "platform.png");
        	vx = 0;
        }
        
        this.broken = broken;
        this.s = isSpring;
        
        width = 140;
        height = 20;
        
        hb = new Rectangle(x,y,width,height);

        tx = AffineTransform.getTranslateInstance(x, y);
        tx.scale(scaleWidth, scaleHeight);
    }

    public boolean isS() {
		return s;
	}

	public void setS(boolean s) {
		this.s = s;
	}

	public boolean isBroken() {
		return broken;
	}

	public void setBroken(boolean broken) {
		this.broken = broken;
	}

	public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        y += vy;
        if(move) {
        	move();
        }
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

	public boolean isMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
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
			

