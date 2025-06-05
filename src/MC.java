import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.net.URL;

public class MC{
	private Image forward; 	
	private AffineTransform tx;
	
	int dir = 0; 
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	int ay;
	double scaleWidth = 0.3;		//change to scale image
	double scaleHeight = 0.3; 	
	int speedX = 0;
    int moveSpeed = 5;
    int currentSpeed = 0;
    int maxSpeed = 10;
    int Acceleration = 1;
    double grav;
    private boolean hasBoost = false;
    private long boostStartTime = 0;
    private int normalBounce = -27;
    private int boostedBounce = -40;
    private int boostDuration = 5000; // milliseconds
    private boolean isFloating = false;
    private long floatStartTime = 0;
    private int floatDuration = 4000; // 4 seconds
    private double floatSpeed = -3;   // gentle upward speed

	//change to scale image
	
	public MC() {
		forward		= getImage("/imgs/"+"doodleMan.png");
		
		
		
		width = 50;
		height = 50;
		
		
		
		x = Frame.width/2 - width/2;
		y = Frame.height - height*2;
		
		x = 100;
		y = 100;
		
		vx = 0;
		vy = 0 ;
		
		ay = 1+vy;
		
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
	public void setSpeed(int direction) {
        if (direction == 2) { // A (left)
        	if(currentSpeed > -maxSpeed) {
        		currentSpeed -= Acceleration;
        	}
        } else if (direction == 3) { // D (right)
         if(currentSpeed < maxSpeed){
        	 currentSpeed += Acceleration;
         }
        	
        }	
        	speedX = currentSpeed;
        
    }
	public void decelerate() {
	    if (currentSpeed > 0) {
	        currentSpeed -= Acceleration;
	        if (currentSpeed < 0) currentSpeed = 0;
	    } else if (currentSpeed < 0) {
	        currentSpeed += Acceleration;
	        if (currentSpeed > 0) currentSpeed = 0;
	    }

	    speedX = currentSpeed;
	}

	public void move(int dir) {
	    setSpeed(dir);
	}

	public void stop() {
	    speedX = 0;
	}

	
	public void update() {
	    x += speedX;
	    vx += 0.5; // gravity force
	    y += vx;

	    x += speedX;
	    if (x > Frame.width) {
	        x = -50; // Appears on the left
	    } else if (x < -50) {
	        x = Frame.width; // Appears on the right
	    }
	    if (isFloating) {
	        // Cancel gravity, float upward
	        vy = (int) floatSpeed;

	        // Stop floating after duration
	        if (System.currentTimeMillis() - floatStartTime > floatDuration) {
	            isFloating = false;
	        }
	    } else {

	    vy += 1; // gravity force
	    }
	    y += vy;

	}

	public void activateBoost() {
	    hasBoost = true;
	    boostStartTime = System.currentTimeMillis();
	}
	public void activateFloat() {
	    isFloating = true;
	    floatStartTime = System.currentTimeMillis();
	}

	public int getBounceStrength() {
	    if (hasBoost) {
	        if (System.currentTimeMillis() - boostStartTime > boostDuration) {
	            hasBoost = false;
	            return normalBounce;
	        }
	        return boostedBounce;
	    }
	    return normalBounce;
	}

	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
	//	x+=vx;
	//  y+=vy;	
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);

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
	public void setCoord() {
		
		
		
	}

	
	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
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


	public int getVy() {
		return vy;
	}


	public void setVy(int vy) {
		this.vy = vy;
	}

	public void update1() {
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
			URL imageURL = MC.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}


	public void updateVy() {
		// TODO Auto-generated method stub
		
	}
	
}
