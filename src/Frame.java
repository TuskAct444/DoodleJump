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
	int maxP = 10;
	
	// resolution of the frame	
	static int width 		= 600;
	static int height		= 900;
	
	// Object List
	
    Background b = new Background(0, 0);
    Background b2 = new Background(0, -1450);
    MC d = new MC(300, 800);
    Scroll s = new Scroll(0, 250);
    
    ArrayList<Platform> plat = new ArrayList<>();
    ArrayList<MovingPlatform> mP = new ArrayList<>();
	
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
		
		int spacingY = 150;
		int startY = 700;
		
		while(plat.size() < maxP) {
			
			int rX = (int)(Math.random()*450);
			int rY = startY - plat.size() * spacingY;
			
			boolean tooClose = false;
			
			for(Platform p : plat) {
				int y = p.getY();
				int x = p.getX();
				
				if((Math.abs(rY-y)) < 100 && (Math.abs(rX-x) < 150)){
					tooClose = true;
					break;
				}
			}
			
			if(!tooClose && Math.random() < 0.6) {
				plat.add(new Platform(rX, rY, false, false, false));
			} else if(!tooClose && Math.random() < 0.3){
				plat.add(new Platform(rX, rY, true, false, false));
			} else {
        		plat.add(new Platform(rX, rY, false, true, false));
			}
			
		}
		
	}
	
	// FINISH THIS
	
	public void updatePlatforms() {
	    ArrayList<Platform> toRemove = new ArrayList<>();
	    ArrayList<MovingPlatform> toRemoveM = new ArrayList<>();
	    for (Platform p : plat) {
	        if (p.getY() > height) {
	            toRemove.add(p);
	        }
	    }
	    plat.removeAll(toRemove);
	    
	    for (MovingPlatform p : mP) {
	        if (p.getY() > height) {	
	            toRemoveM.add(p);
	        }
	    }
	    mP.removeAll(toRemoveM);

	    double rand = Math.random();
	    
	    while (plat.size() < maxP) {
	    	int spacingY = 150;
			int startY = 700;
	    	int highestY = findHighestPlatformY();
	        int rX = (int)(Math.random() * (width - 150));
	        int rY = startY - plat.size() * spacingY;

	        boolean tooClose = false;
	        for (Platform p : plat) {
	            if (Math.abs(rX - p.getX()) > 150 && Math.abs(rY - p.getY()) > 100) {
	                tooClose = true;
	                break;
	            }
	        }

	        if (!tooClose && rY > -1000 && Math.random() < 0.75) {
	            plat.add(new Platform(rX, rY, false, false, false));
	        } else if(!tooClose && Math.random() < 0.3){
	        	plat.add(new Platform(rX, rY, true, false, false));
	        } else {
	        	plat.add(new Platform(rX, rY, false, true, false));
	        }
	    }
	}

	private int findHighestPlatformY() {
	    int minY = height;
	    for (Platform p : plat) {
	        if (p.getY() < minY) {
	            minY = p.getY();
	        }
	    }
	    return minY;
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setFont(myFont);
		g.setColor(Color.white);
		
		// BG
		b.paint(g);
		b2.paint(g);
		// Character
		d.paint(g);
		d.updateVy();
		g.drawRect(d.getX(), d.getY(), d.getWidth(), d.getHeight());
		
		// Scoring system
		s.paint(g);
		g.drawRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
		
		// Character collision
		
		for(Platform p : plat) {
			p.paint(g);
			if(p.collides(d)) {
				if(p.isBroken()) {
					d.setVy(-8);
					d.incCol(1);
					p.setVy(3);
					p.hasBeenUsed = true;
				} else {
					d.setVy(-8);
					d.incCol(1);
				}
			}
			
			if(d.getY() <= s.getY()) {
				p.setVy(Math.abs(d.getVy()*2));
			} else {
				p.setVy(0);
			}
			g.drawRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
			
		}
		
		if(d.getY() <= s.getY()) {
			b.setVy(Math.abs(d.getVy()));
			b2.setVy(Math.abs(d.getVy()));
			d.setY(s.getY());
			
			score += Math.abs(d.getVy());
		} else {
			b.setVy(0);
			b2.setVy(0);
		}
		
		if(d.getY() > Frame.height) {
			g.drawString("Lose!", 450, 650);
		}
		
		if(b.getY() > Frame.height) {
			b.setY(-1450);
		}
		
		if(b2.getY() > Frame.height) {
			b2.setY(-1450);
		}
		
		// Other
		g.drawString("" + score, 50, 50);
		
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
		updatePlatforms();
	    repaint();
	}
		// TODO Auto-generated method stub
		
	
	
	public static void main(String[] args) {
		
		new Frame();
		
	}
	
	
	
	
	
	
	
	
}
