package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import game.input.Keyboard;

public class GameCanvas extends Canvas implements Commons, ActionListener {

	private static final long serialVersionUID = 1L;
	
	private int speedCounter = 0;
	private int counter;
	private int delay = 1;
	private int obstacleRandomXPosition;
	protected Timer timer = new Timer(delay, this);

	private Keyboard key;
	private boolean gameOver;

	private Character character;
	private Terrain terrain;
	private Obstacle obstacle;
	
	private boolean previousJump;
	
	private int jumpLength;
	private int jumpCount;
	private int attackCount;
	private int livesCount;
	
	private int objectMovingSpeed;
	private int score;
	private int maxJumpLength;
	private int obstacleDestructionScore;
	
	private Image spriteBackground;
	

	/**
	 * @wbp.parser.entryPoint
	 */
	public GameCanvas() {
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		loadBackground("volcanic_background1");
		
		score = 0;
		obstacleDestructionScore = 250;
		objectMovingSpeed = 2;
		livesCount = 3;
		maxJumpLength = 100;
		
		createCanvas();
		
	}

	/**
	 * Starts the game;
	 */
	public void start() {
		timer.start();
	}
	
	public void loadBackground(String fileName) {
		
		URL path = this.getClass().getResource(fileName + ".gif");	
		spriteBackground = new ImageIcon(path).getImage();	
	}
	
	public void createCanvas() {
		
		character = new Character(20, 280);
		terrain = new Terrain(0, 300);
		obstacle = new Obstacle(320, 260);
		
		key = new Keyboard();
		addKeyListener(key);

	}
	
	private boolean characterJump() {
		
		boolean characterJumping = false;
		
		if(key.jump && jumpCount < 2){
			
			jumpLength++;
			character.jump();
			
			terrain.setY(terrain.getY() + objectMovingSpeed);
			obstacle.setY(obstacle.getY() + objectMovingSpeed);
			
			score += objectMovingSpeed;
			
			characterJumping = true; 

		}
		
		if(key.jump == false && key.jump != previousJump || jumpLength >= maxJumpLength){
			
			jumpCount++;
			jumpLength = 0;
			
		}
		
		previousJump = key.jump;
		return characterJumping;
		
	}
	
	private void characterAttack() {
		
	}

	/**
	 * Will take user input as well as any computation needed for the game.
	 */
	public void update() {
		
		key.update();

		if (!gameOver) {

			int characterYPos = character.getY() + character.getHeight();
			int characterXPos = character.getX() + character.getWidth();
			int endOfTerrain = terrain.getX() + terrain.getWidth();
			int bottomOfTerain = terrain.getY() + terrain.getHeight();

			boolean isJumping = characterJump();
			character.attack(key.attack);

			if (character.getX() > terrain.getX()) {
				if (characterYPos >= terrain.getY()) {
					jumpCount = 0;
					jumpLength = 0;
					score += objectMovingSpeed;
					
				} else if(!isJumping) {
					
					movingObjects();
					//score += character.getWidth();

				}
				// Destroys Obstacle if attacking while passing through it.
				if (characterXPos >= obstacle.getX()) {
					if (characterYPos > obstacle.getY()){
						if(key.attack){
							
							// Add points here;
							
							if(!obstacle.isDestroyed()) {
								
								score += obstacleDestructionScore;
							}
							else {
								
								score += 0;
							}
							obstacle.destroy();
							
						} else if(!obstacle.isDestroyed() && character.getX()<= obstacle.getX()) {
							
							score += 0;
							livesCount--;
							checkLiveCount();
						
						}
					}	
				} 
			} 
			else {
				
				if (!isJumping) {
					
					movingObjects();
				}
				// ends game if character fall to the bottom of the screen. 
				if (characterYPos > (GAME_HEIGHT - GAME_OBSTACLE_BORDER)) {
					
					score = 0;
					livesCount--;
					checkLiveCount();
				}
				// ends game if character runs into the terrain. 
				else if (characterXPos > terrain.getX() && characterYPos > terrain.getY() && 
						character.getY() < bottomOfTerain) {
					
					score = 0;
					livesCount--;
					checkLiveCount();
				}

			}
			
			/*if(character.getX() >= terrain.getX() && character.getY() >= terrain.getY()) {
				
				createRandomObstacle();
				
				//obstacle.update();
				
			}*/
			
			terrain.update();
			obstacle.update();
			
			
			
			if(speedCounter == 300){
				terrain.incrementSpeed();
				obstacle.incrementSpeed();
				speedCounter = 0;
			}
			speedCounter ++;
			System.out.println(score);
			
			
		}

	}

	/**
	 * Repaints the Canvas
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		// Paint Background.
		//g.setColor(Color.gray);
		//g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(spriteBackground, 0, 0, getWidth(), getHeight(), null);

		terrain.paintTerrain(g);
		obstacle.paintObstacle(g);
		character.paintCharacter(g);

		g.dispose();
		bs.show();
	}
	
	public void movingObjects() {
		
		character.setY(character.getY() + 2);
		obstacle.setY(obstacle.getY() - 2);
		terrain.setY(terrain.getY() - 2);
		
	}
	
	public void createRandomObstacle() {
		
		obstacleRandomXPosition = obstacle.getRandomObstacle(terrain.getWidth());
		
		obstacle = new Obstacle(obstacleRandomXPosition, terrain.getY());
	}
	
	public void checkLiveCount() {
		
		if(livesCount > 0) {
			
			//JOptionPane.showMessageDialog(getParent(), "You died! " + livesCount + " lives left!");
			System.out.println("You died! " + livesCount + " lives left!");
			revalidate();
			
			switch(livesCount) {
			
			case 1: 
				
			
			}
			createCanvas();
		}
		
		else{
			
			gameOver = true;
			JOptionPane.showMessageDialog(getParent(), "Game Over! " + livesCount + " lives left!");
			
		}
	}

	public int getScore() {
		
		return score;
	}
	
	public int getLiveCount() {
		
		return livesCount;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		counter += 2;
		while (counter > 17) {
			update();
			counter = 0;
		}
		render();
	}
}
