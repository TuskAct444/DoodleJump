import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class MC{
	private Image forward; 	
	private AffineTransform tx;
	
	int width, height;
	int x, y;						//position of the object
	double vx;
	double vy;						//movement variables
	double grav;					//gravity :)
	double scaleWidth = 0.3;		//change to scale image
	double scaleHeight = 0.3; 		//change to scale image
	//Constructor (default)
	public MC() {
		
		forward 	= getImage("/imgs/"+"MC.png"); //load the image for Tree
		
		//alter these
		width = 50;			//for the hitbox 
		height = 50; 		//for the hitbox
		//top left location of the image
		
		x = 0;
		y = 0;
		
		vx = 0.0;
		vy = 0;
		
		grav = 0.35;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	public MC(int x, int y) {
		
		this(); // invoke default constructor so it isn't copy pasted code
		
		// get object's attributes
		// this: specifies x attribute instead of the parameter x
		this.x = x;
		this.y = y;
		

	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x+=vx;
		y+=vy;	
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);

	}

	public void setCoord() {
		
	}	
	
	// Create a hitbox that will determine whether or not
	// the MC hits something
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
	
	// Basic getters and setters
	
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

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}
	
	public void update() {
		vy += grav;
		
		y += vy;
	}

	private void init(int a, int b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}
	
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Background.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
}