package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	//class variables
	private final int width = 700;
	private final int height = 700;
	
	private int length;
	private int score;

	//The pixel size of each image for the snake sections
	private int sectionSize = 25;

	//number of sections that can fit on the x and y axis
	private int sizex = ((width - 50)/sectionSize) - 1;
	private int sizey = ((height - 100) / sectionSize) - 1;

	//arrays that hold the x and y coordinates for each section
	private int[] snakex = new int[(sizex + 1)*(sizey + 1)];
	private int[] snakey = new int[(sizex + 1)*(sizey + 1)];
	
	private int applex = 10;
	private int appley = 10;
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	private ImageIcon rightMouth;
	private ImageIcon leftMouth;
	private ImageIcon upMouth;
	private ImageIcon downMouth;
	private ImageIcon apple;
	private ImageIcon snakeImage;
	
	private Timer timer;
	private int delay = 100;

	//when true the snake wraps when going to the edges
	private boolean wrap = false;
	
	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);

		score = 0;
		length = 3;
		snakex[0] = 0;
		snakey[0] = 0;
		snakex[1] = 0;
		snakey[1] = 0;
		snakex[2] = 0;
		snakey[2] = 0;
		
		rightMouth = new ImageIcon("rightmouth.png");
		leftMouth = new ImageIcon("leftmouth.png");
		upMouth = new ImageIcon("upmouth.png");
		downMouth = new ImageIcon("downmouth.png");
		snakeImage = new ImageIcon("snakeimage.png");
		apple = new ImageIcon("enemy.png");

		apple();
		timer.start();
		
	}
	
	//draw all elements on the screen
	public void paint(Graphics g) {

		//Draw the background of the screen gray and the play area black
		g.setColor(Color.GRAY);
		g.fillRect(0,0, width, height);
		
		g.setColor(Color.black);
		g.fillRect(25,50,width - 50, height - 100);

		//draw the current score on top of the screen
		g.drawString("Score: " + score, (int)width/2, 25);

		apple.paintIcon(this, g, applex*sectionSize + 25, appley*sectionSize + 50);
		
		if(right) {
			rightMouth.paintIcon(this, g, snakex[0]*sectionSize + 25, snakey[0]*sectionSize + 50);
		}
		else if(left) {
			leftMouth.paintIcon(this, g, snakex[0]*sectionSize + 25, snakey[0]*sectionSize + 50);
		}
		else if(up) {
			upMouth.paintIcon(this, g, snakex[0]*sectionSize + 25, snakey[0]*sectionSize + 50);
		}
		else if(down) {
			downMouth.paintIcon(this, g, snakex[0]*sectionSize + 25, snakey[0]*sectionSize + 50);
		}

		//Loop through the snake and draw each section
		for(int i = 1; i < length; i++) {
			snakeImage.paintIcon(this, g, snakex[i]*sectionSize + 25, snakey[i]*sectionSize + 50);
		}
		
		g.dispose();
	}

	//Method that assigns a new position to the apple
	public void apple() {
		applex = (int) (Math.random()*sizex);
		appley = (int) (Math.random()*sizey);
	}

	//Method that restarts the game
	public void begin() {

		score = 0;
		length = 3;
		apple();

		snakex[0] = 2;
		snakey[0] = 0;
		snakex[1] = 1;
		snakey[1] = 0;
		snakex[2] = 0;
		snakey[2] = 0;
	}

	//Method that checks if the snake head has collided with other objects
	public void checkCollision() {
		//Checks if the snake head is on top of the apple
		if(snakex[0] == applex & snakey[0] == appley) {
			snakex[length] = snakex[length - 1];
			snakey[length] = snakey[length - 1];
			length++;
			score++;
			apple();
		}

		//Checks if the head has collided with a section of its body
		if(length > 3) {
			for(int i = length - 1; i > 3; i--) {
				if((snakex[i] == snakex[0]) && (snakey[i] == snakey[0])) {
					begin();
				}
			}
		}

		//checks if the head goes off the screen and depending on wrap, the game ends of the snake wraps around the board
		if(wrap) {
			if(snakex[0] > sizex) {
				snakex[0] = 0;
			}
			else if(snakex[0] < 0) {
				snakex[0] = sizex;
			}
			else if(snakey[0] > sizey) {
				snakey[0] = 0;
			}
			else if(snakey[0] < 0){
				snakey[0] = sizey;
			}
		}
		else {
			if((snakex[0] > sizex) || (snakex[0] < 0) || (snakey[0] > sizey) || (snakey[0] < 0)) {
				begin();
			}
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		//might want to add a check to make sure the directions are not all false
		
		for(int i = length - 1; i > 0; i--) {
			snakex[i] = snakex[i-1];
			snakey[i] = snakey[i-1];
		}
		
		//depending on the direction change either the x or y value and the other the same
		if(right) {
			snakex[0] += 1;
		}
		else if(left) {
			snakex[0] -= 1;
		}
		else if(down) {
			snakey[0] += 1;
		}
		else if(up) {
			snakey[0] -= 1;
		}
		checkCollision();
		repaint();
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(!left) {
				right = true;
				up = false;
				down = false;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(!right) {
				left = true;
				up = false;
				down = false;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(!up) {
				down = true;
				right = false;
				left = false;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(!down) {
				up = true;
				left = false;
				right = false;
			}
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
		
}
