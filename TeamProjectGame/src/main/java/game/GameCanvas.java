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
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private int counter;
	private int delay = 1;
	protected Timer timer = new Timer(delay, this);
	
	private Keyboard key;
	private boolean pause;
	
	private Character character;
	private Terrain terrain;
	private Obstacle obstacle;
	
	//private BufferedImage image = new BufferedImage(GAME_WIDTH,GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
	//private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	
	public GameCanvas(){
		setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
		
		character = new Character(20, 100);
		terrain = new Terrain(0, 120);
		obstacle = new Obstacle(80, 80);
		
		key = new Keyboard();
		addKeyListener(key);
		
		
		
		//timer.start();
		
	}
	public void start(){
		timer.start();
	}
	
	/**
	 * Will take user input as well as any computation needed for the game. 
	 */
	public void update(){
		
		key.update();
		
		
		
		if (character.getY() + character.getHeight() < GAME_HEIGHT) {
			
			character.update(key.jump, key.attack);
			terrain.update();
			obstacle.update();
			
			
			if (character.getY() + character.getHeight() <= terrain.getY()
					|| character.getX() > terrain.getX() + terrain.getWidth()) {
				character.setY(character.getY() + 2);
			}

		}
		if(character.getX() + character.getWidth() == obstacle.getX() && key.attack){
			obstacle.destroy();
		}
		
	}
	
	/**
	 * Repaints the Canvas
	 */
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//Paint Background.
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(),getHeight());
		
		terrain.paintTerrain(g);
		obstacle.paintObstacle(g);
		character.paintCharacter(g);
		
		g.dispose();
		bs.show();
		
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
			
		
		counter++; 
		
		while (counter > 17){
			//For Testing
			//System.out.println(getHeight());
			update();
			counter = 0;
			
		}
		render();
		
	}
}
