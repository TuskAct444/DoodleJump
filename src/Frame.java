import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener{
	
	int waveTimer = 5; // each wave of enemies is 20s
    long ellapseTime = 0;
    Font timeFont = new Font("Courier", Font.BOLD, 70);
    int level = 0;
	// resolution of the frame	

    MovingPlatform mP = new MovingPlatform(100, 300, 3);
    MC d = new MC(230 , 150);
    Platform p = new Platform(200, 600);

    Background b = new Background(0, 0);
	int gravity = 5;

    static int width 		= 600;
	static int height		= 900;
	
	boolean leftPressed = false;
	boolean rightPressed = false;
	
	public Frame() {
		
		JFrame frame = new JFrame("Doodle Jump");
		frame.setSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(false);
		frame.setResizable(false);
		frame.setTitle("Doodle Jump");
		frame.addKeyListener(this);
		frame.add(this);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		Timer t = new Timer(4, this);
		t.start();
     
	    


	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		b.paint(g);

		
		mP.paint(g);
		p.paint(g);
		
		
		
		
		
		d.paint(g);
		if(d.getY()>800) {
			d.setVy(-27);
		}
		
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		  int key = e.getKeyCode();

		    if (key == KeyEvent.VK_A) {
		        leftPressed = true;
		    }
		    if (key == KeyEvent.VK_D) {
		        rightPressed = true;
		    }
		}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();

	    if (key == KeyEvent.VK_A) {
	        leftPressed = false;
	    }
	    if (key == KeyEvent.VK_D) {
	        rightPressed = false;
	    }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		   if (leftPressed && !rightPressed) {
		        d.setSpeed(2);
		    } else if (rightPressed && !leftPressed) {
		        d.setSpeed(3); 
		    } else {
		        d.decelerate(); 
		    }

		    d.update(); 
		    if (p.collided(d) && d.getVy() >= 0) {
		        d.setY(p.y - d.getHeight()); // stick to platform
		        d.setVy(-27); // bounce
		    } else if (mP.collidesWith(d) && d.getVy() >= 0) {
		        d.setY(mP.y - d.getHeight());
		        d.setVy(-27); // bounce
		    }

		    repaint();
		}
		// TODO Auto-generated method stub
		
	
	
	public static void main(String[] args) {
		
		new Frame();
		
	}
	
	
	
	
	
	
	
	
}