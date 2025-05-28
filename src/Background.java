import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.net.URL;

public class Background{
	private Image forward; 	
	private AffineTransform tx;
	
	int dir = 0; 
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 5.0;		//change to scale image
	double scaleHeight = 5.0; 		//change to scale image
	
	public Background() {
		forward		= getImage("/imgs/" + "moonMap.png");


		
		
		
		width = 100;
		height = 100;
		
		
		
		x = Frame.width/2 - width/2;
		y = Frame.height - height*2;
		
		x = 500;
		y = 500;
		
		vx = 0;
		vy = 0;
				
		tx = AffineTransform.getTranslateInstance(0, 0);

		init(x, y); 				//initialize the location of the image
									//use your variables
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
