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
	boolean left = false;
	boolean right = false;
	int score = 0;
	Font myFont = new Font("Courier", Font.BOLD, 25);
	int maxP = 20; // max platform
	int maxPow = 1; // max pow
	
	// resolution of the frame	
	static int width 		= 600;
	static int height		= 900;
	
	// Object List
	
    Background b = new Background(0, 0);
    Background b2 = new Background(0, -1450);
    MC d = new MC(300, 800);
    Scroll s = new Scroll(0, 300);
    
    ArrayList<Platform> plat = new ArrayList<>();
    ArrayList<Powerup> pow = new ArrayList<>();

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
		
		
		// GENERATE INITIAL PLATFORMS
		
		// Initial platforms are all static
		
		while(plat.size() < maxP) {
			
			int rX = (int)(Math.random()*450);
			int rY = startY - plat.size() * spacingY;
			
			boolean tooClose = false;
			
			for(Platform p : plat) {
				int y = p.getY();
				int x = p.getX();
				
				// if platforms are too close together
				
				if((Math.abs(rY-y)) < 150 && (Math.abs(rX-x) < 150)){
					tooClose = true;
					break;
				}
			}
			
			// if not too close
			if(!tooClose) {
				plat.add(new Platform(rX, rY, false, false, false));
			}
			
		}
		
		while(pow.size() < maxPow) {
			
			int rX = (int)(Math.random()*450);
			int rY = (int)(Math.random()*800);
			
			boolean tooClose = false;
			
			for(Powerup p: pow) {
				int y = p.getY();
				int x = p.getX();
				
				// if platforms are too close together
				
				if((Math.abs(rY-y)) < 200 && (Math.abs(rX-x) < 200)){
					tooClose = true;
					break;
				}
			}
			
			Double rand = Math.random();
			
			// if not too close
			if(!tooClose && rand < 0.2) {
				pow.add(new Powerup(rX, rY, "None"));
			}
			
		}	
		
	}
	
	public void updatePowerup() {
		ArrayList<Powerup> toRemove = new ArrayList<>();
	    // add out of bound powerups to a remove list
	    for (Powerup p : pow) {
	        if (p.getY() > 1200) {
	            toRemove.add(p);
	        }
	    }
	    pow.removeAll(toRemove);
	    
	    // generate platforms 'infinitely' as long as there is less than maxP
	    while (pow.size() < maxPow) {
	        int rX = (int)(Math.random() * (width - 80));
	        int rY = (int)(Math.random() * (height - 80));

	        boolean tooClose = false;
	        for (Powerup p : pow) {
	            if (Math.abs(rX - p.getX()) < 200 && Math.abs(rY - p.getY()) < 200) {
	                tooClose = true;
	                break;
	            }
	        }
	        double rand = Math.random();

	        if(!tooClose) {
	        	if(rand < 0.05) {
	        		pow.add(new Powerup(rX, rY, "Rocket"));
	        	} else {
	        		pow.add(new Powerup(rX, rY, "none"));
	        	}
	        } 
	    }
	}
	
	public void updatePlatforms() {
	    ArrayList<Platform> toRemove = new ArrayList<>();
	    // add out of bound platforms to a remove list
	    for (Platform p : plat) {
	        if (p.getY() > height) {
	            toRemove.add(p);
	        }
	    }
	    plat.removeAll(toRemove);
	    
	    // generate platforms 'infinitely' as long as there is less than maxP
	    while (plat.size() < maxP) {
	    	int spacingY = 125;
			int startY = 700;
	    	int highestY = findHighestPlatformY();
	        int rX = (int)(Math.random() * (width - 150));
	        int rY = highestY - spacingY;

	        boolean tooClose = false;
	        for (Platform p : plat) {
	            if (Math.abs(rX - p.getX()) < 175 && Math.abs(rY - p.getY()) < 150) {
	                tooClose = true;
	                break;
	            }
	        }

	        if(!tooClose) {
		    	double rand = Math.random();
	        	if(rand < 0.05) {
		            plat.add(new Platform(rX, rY, false, true, false));
		            
		    	    int rescueX = Math.min(Math.max(rX + 100, 0), Frame.width - 100);
		    	    int rescueY = rY - 100;

		    	    plat.add(new Platform(rescueX, rescueY, false, false, false));
	        	} else if(rand < 0.1) {
	        		plat.add(new Platform(rX, rY, true, false, true));
	        	}else if(rand < 0.15) {
	        		plat.add(new Platform(rX, rY, false, false, true));
		        } else if(rand < 0.3){
		        	plat.add(new Platform(rX, rY, true, false, false));
		        } else {
		        	plat.add(new Platform(rX, rY, false, false, false));
		        }
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
		g.setColor(Color.black);
		
		
		
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
		
		System.out.println(pow.size());
		
		for(Powerup p : pow) {
			p.updateVy();
			if(p != null) {
				p.paint(g);
			}
			if(p.collides(d)) {
				if(p.getType().equals("Rocket")) {
					 if (p.getType().equals("Rocket")) {
						 d.setVy(-40);
					 }
				}
			}
			
			if(d.getY() <= s.getY()) {
				p.setVy(Math.abs(d.getVy()));
			} else {
			    p.setVy(0);
			}
		}
		
		for(Platform p : plat) {
			p.updateVy();
			p.paint(g);
			if(p.collides(d) && d.getVy() > 0 && !p.hasBeenUsed) {
				
				 if(p.isS()){
					d.setVy(-16);
					d.incCol(1);
				 } else if(p.isBroken()) {
					d.incCol(1);
					p.setVy(10);
					p.hasBeenUsed = true;
				} else {
					d.setVy(-6);
					d.incCol(1);
				}
			}
			
			if(d.getY() <= s.getY()) {
				if(!p.hasBeenUsed) {
					p.setVy(Math.abs(d.getVy()));
				}
			} else {
				if (!p.hasBeenUsed) {
			        p.setVy(0);
			    }
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
		updatePowerup();
	    repaint();
	}
		// TODO Auto-generated method stub
		
	
	
	public static void main(String[] args) {
		
		new Frame();
		
	}
	
	
	
	
	
	
	
	
}
