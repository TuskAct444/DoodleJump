import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Background {
	private Image forward; 	
	private AffineTransform tx;
	
	int width, height;
	int x, y;						//position of the object
	int vx; 
	double vy;						//movement variables
	double scaleWidth = 4.0;		//change to scale image
	double scaleHeight = 4.0; 		//change to scale image
	
	public Background() {
		forward		= getImage("/imgs/" + "bg.png");
		
		
		
		width = 0;
		height = 0;
		
		
		x = Frame.width/2 - width/2;
		y = Frame.height - height*2;
		
		x = 0;
		y = 0;
		
		vx = 0;
		vy = 0 ;
		
		
		tx = AffineTransform.getTranslateInstance(0, 0);

		init(x, y); 				//initialize the location of the image
									//use your variables
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


	public int getVx() {
		return vx;
	}


	public void setVx(int vx) {
		this.vx = vx;
	}


	public double getVy() {
		return vy;
	}


	public void setVy(double d) {
		this.vy = d;
	}


	public Background(int x, int y) {
		
		this(); // invoke default constructor so it isn't copy pasted code
		
		// get object's attributes
		// this: specifies x attribute instead of the parameter x
		this.x = x;
		this.y = y;
		

	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
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
