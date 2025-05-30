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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener{
	
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	int level = 0;
	int gravity = 1;
	boolean left = false;
	boolean right = false;
	int score = 0;
	Font myFont = new Font("Courier", Font.BOLD, 25);
	
	// resolution of the frame	
	static int width 		= 600;
	static int height		= 900;
	
	// Object List
	
    Background b = new Background(0, 0);
    MovingPlatform mP = new MovingPlatform(100, 500, 3);
    MC d = new MC(300, 800);
    Platform p = new Platform(200, 600);
    Scroll s = new Scroll(0, 450);
	
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
	    
	    Timer t = new Timer(8, this);
		t.start(); 	
		
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setFont(myFont);
		g.setColor(Color.white);
		
		// BG
		b.paint(g);
		// Platform
		p.paint(g);
		g.drawRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
		// Moving Platform
		mP.paint(g);
		g.drawRect(mP.getX(), mP.getY(), mP.getWidth(), mP.getHeight());
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
		
		// Other
		g.drawString("" + score, 50, 50);
		score = Math.abs((int)d.getVy());
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==65) {
			left = true;
		} else if(e.getKeyCode()==68) {
			right = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==65) {
			left = false;
		} else if(e.getKeyCode()==68) {
			right = false;
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
		if(left && !right) {
			d.move(0);
		} else if(!left && right) {
			d.move(1);
		} else {
			d.decel();
		}
		
		d.updateVx();
	    repaint();
	}
		// TODO Auto-generated method stub
		
	
	
	public static void main(String[] args) {
		
		new Frame();
		
	}
	
	
	
	
	
	
	
	
}
