import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class doodleCharacter{
	private Image forward; 	
	private AffineTransform tx;
	
	
	//attributes of a wolverine object
	int width, height;// <--- used for the hitbox aka for the collision detection code
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = .45;		//change to scale image
	double scaleHeight = .35; 		//change to scale image
	//Constructor (default)
	public doodleCharacter() {
		
		forward 	= getImage("image-removebg-preview(1).png"); //load the image for Tree
		
		//alter these
		width = 50;			//for the hitbox 
		height = 50; 		//for the hitbox
		//top left location of the image
		x = 300;
		y = 730;
		
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	private void init(int x2, int y2) {
		// TODO Auto-generated method stub
		
	}
	private Image getImage(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}