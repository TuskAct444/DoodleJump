import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Iterator;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener{
	
	int waveTimer = 5; // each wave of enemies is 20s
    long ellapseTime = 0;
    Font timeFont = new Font("Courier", Font.BOLD, 70);
    int level = 0;
	// resolution of the frame	

    MovingPlatform mP = new MovingPlatform(100, 300, 3);
    MC d = new MC(130 , 750);
    Platform p = new Platform(200, 600);
    BrokenPlatform bP = new BrokenPlatform(360, 430);
    Background b = new Background(0, 0);
    ArrayList<HornetEnemy> enemies = new ArrayList<>();
    Random rand = new Random();
    int enemySpawnTimer = 0;
    PowerUp power = new PowerUp(250, 400); 
    FloatPowerUp floatPower = new FloatPowerUp(300, 500);

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

		power.paint(g);
		floatPower.paint(g);

		mP.paint(g);
		p.paint(g);
		bP.paint(g);
		
		super.paintComponent(g);
		g.setFont(myFont);
		g.setColor(Color.white);
		
		// BG
		b.paint(g);
		// Platform
		p.paint(g);
		g.drawRect(p.getX(), p.getX(), p.getWidth(), p.getHeight());
		// Moving Platform
		mP.paint(g);
		g.drawRect(mP.getX(), mP.getX(), mP.getWidth(), mP.getHeight());
		// Character
		d.paint(g);
		d.updateVy();
		g.drawRect(d.getX(), d.getY(), d.getWidth(), d.getHeight());
		
		// Scoring system
		s.paint(g);
		g.drawRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
		
		// Character collision
		if(p.collides(d) || mP.collided(d)) {
			d.setVy(-7.5);
			d.incCol(1);
		}
		
		
		
		d.paint(g);
		if(d.getY()>800) {
			d.setVy(-27);
		}
		for (HornetEnemy enemy : enemies) {
		    enemy.paint(g);
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
		    enemySpawnTimer++;
		    if (enemySpawnTimer > 1000) {
		        int side = rand.nextInt(2); // 0 = left, 1 = right
		        int x = (side == 0) ? 0 : Frame.width - 50;
		        int y = rand.nextInt(Frame.height - 200);
		        enemies.add(new HornetEnemy(x, y));
		        enemySpawnTimer = 0;
		    }


		    // Update & remove off-screen enemies
		    Iterator<HornetEnemy> iterator = enemies.iterator();
		    while (iterator.hasNext()) {
		        HornetEnemy enemy = iterator.next();
		        enemy.update();

		        if (enemy.getBounds().intersects(new Rectangle(d.getX(), d.getY(), d.getWidth(), d.getHeight()))) {
		            System.out.println("Hit by enemy!");
		        }

		        // Remove enemies that go off screen
		        if (enemy.y < -100 || enemy.y > Frame.height + 100) {
		            iterator.remove();
		        }

		    }
		    for (HornetEnemy enemy : enemies) {
		        for (Bullet bullet : enemy.getBullets()) {
		            Rectangle bulletRect = bullet.getBounds();
		            Rectangle playerRect = new Rectangle(d.getX(), d.getY(), d.getWidth(), d.getHeight());

		            if (bulletRect.intersects(playerRect)) {
		                System.out.println("Player hit by bullet!");
		            }
		        }
		    }

		    if (power.collidesWith(d)) {
		        d.activateBoost();
		    }
		    if (p.collided(d) && d.getVy() >= 0) {
		        d.setY(p.y - d.getHeight());
		        d.setVy(d.getBounceStrength());
		    } else if (mP.collidesWith(d) && d.getVy() >= 0) {
		        d.setY(mP.y - d.getHeight());
		        d.setVy(d.getBounceStrength());
		    }
		    if (floatPower.collidesWith(d)) {
		        d.activateFloat();
		    }

		 

		    repaint();
		}

		// TODO Auto-generated method stub
		
	
	
	public static void main(String[] args) {
		
		new Frame();
		
	}
	
	
	
	
	
	
	
	
}
