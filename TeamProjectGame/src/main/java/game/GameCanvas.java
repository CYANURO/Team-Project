package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import game.input.Keyboard;

public class GameCanvas extends Canvas implements Commons, ActionListener {

	private static final long serialVersionUID = 1L;
	
	private int speedCounter = 0;
	private int counter;
	private int delay = 1;
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

	public GameCanvas() {
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

		livesCount = 3;
		createCanvas();
		
	}

	/**
	 * Starts the game;
	 */
	public void start() {
		timer.start();
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
			terrain.setY(terrain.getY() + 2);
			obstacle.setY(obstacle.getY() + 2);
			
			characterJumping = true; 
		}
		if(key.jump == false && key.jump != previousJump || jumpLength >= 75){
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
				if (characterYPos == terrain.getY()) {
					jumpCount = 0;
					jumpLength = 0;
				} else if(!isJumping){
					character.setY(character.getY() + 2);
					obstacle.setY(obstacle.getY() - 2);
					terrain.setY(terrain.getY() - 2);
				}
				// Destroys Obstacle if attacking while passing through it.
				if (characterXPos >= obstacle.getX()) {
					if (characterYPos > obstacle.getY()){
						if(key.attack){
							// Add points here;
							obstacle.destroy();
						} else if(!obstacle.isDestroyed() && character.getX()<= obstacle.getX()) {
							
							livesCount--;
							checkLiveCount();
						}
					}	
				} 
			} else {
				if (!isJumping) {
					character.setY(character.getY() + 2);
					obstacle.setY(obstacle.getY() - 2);
					terrain.setY(terrain.getY() - 2);
				}
				// ends game if character fall to the bottom of the screen. 
				if (characterYPos > terrain.getY() && characterXPos > terrain.getX()) {
					
					livesCount--;
					checkLiveCount();
				}
				// ends game if character runs into the terrain. 
				else if (characterXPos > terrain.getX() && characterYPos > terrain.getY() && 
						character.getY() < bottomOfTerain) {
					
					livesCount--;
					checkLiveCount();
				}

			}
			
			terrain.update();
			obstacle.update();
			
			if(speedCounter == 300){
				terrain.incrementSpeed();
				obstacle.incrementSpeed();
				speedCounter = 0;
			}
			speedCounter ++;
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
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(), getHeight());

		terrain.paintTerrain(g);
		obstacle.paintObstacle(g);
		character.paintCharacter(g);

		g.dispose();
		bs.show();
	}
	
	public void checkLiveCount() {
		
		if(livesCount > 0) {
			
			//JOptionPane.showMessageDialog(getParent(), "You died! " + livesCount + " lives left!");
			System.out.println("You died! " + livesCount + " lives left!");
			revalidate();
			createCanvas();
		}
		
		else{
			
			gameOver = true;
			JOptionPane.showMessageDialog(getParent(), "Game Over! " + livesCount + " lives left!");
			
		}
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
