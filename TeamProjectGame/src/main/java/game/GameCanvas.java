package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

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
	
	private BufferedImage image = new BufferedImage(GAME_WIDTH,GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	//For Testing purposes  
	int x = 40;
	
	public GameCanvas(){
		setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
		
		key = new Keyboard();
		addKeyListener(key);
		
		timer.start();
		
	}
	
	/**
	 * Will take user input as well as any computation needed for the game. 
	 */
	public void update(){
		key.update();
		if(key.jump) x++;
		if(key.attack) x--;
		
		
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
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(),getHeight());
		g.setColor(Color.BLACK);
		g.fillOval(x, 40, 20, 20);
		g.dispose();
		bs.show();
		
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		counter++; 
		
		while (counter > 17){
			update();
			counter = 0;
		}
		
		render();
		
		// TODO Auto-generated method stub
		
	}

}
