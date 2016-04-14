package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.Timer;

import game.input.Keyboard;

public class GameCanvas extends Canvas implements Commons, ActionListener {

	private static final long serialVersionUID = 1L;

	private int counter;
	private int delay = 1;
	protected Timer timer = new Timer(delay, this);

	private Keyboard key;
	private boolean gameOver;

	private Character character;
	private Terrain terrain;
	private Obstacle obstacle;

	public GameCanvas() {
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

		character = new Character(20, 100);
		terrain = new Terrain(0, 300);
		obstacle = new Obstacle(320, 260);

		key = new Keyboard();
		addKeyListener(key);

	}

	/**
	 * Starts the game;
	 */
	public void start() {
		timer.start();
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

			// ends game if character runs in to obstacle and did not destroy it. 
			if (characterXPos == obstacle.getX() + 2 && !obstacle.isDestroyed() && characterYPos > obstacle.getY()) {
				System.out.println("Game Over");
				gameOver = true;
			}
			// ends game if character runs into the terrain. 
			if (characterXPos == terrain.getX() && characterYPos > terrain.getY() && character.getY() < bottomOfTerain) {
				System.out.println("Game Over");
				gameOver = true;
			}
			// ends game if character fall to the bottom of the screen. 
			if (characterYPos > GAME_HEIGHT) {
				System.out.println("Game Over");
				gameOver = true;
			}

			character.update(key.jump, key.attack);
			terrain.update();
			obstacle.update();
			
			// Destroys Obstacle if attacking while passing through it. 
			if (characterXPos == obstacle.getX() && key.attack) {
				// Add points here; 
				obstacle.destroy();
			}

			// makes it so the character falls between the terrain and only falls to the top of the terrain when on top of the terrain. 
			if(character.getX() > endOfTerrain || characterXPos < terrain.getX()){
				character.setY(character.getY() + 2);
			}else if(characterYPos < terrain.getY()){
				character.setY(character.getY() + 2);
				
			}
			
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
