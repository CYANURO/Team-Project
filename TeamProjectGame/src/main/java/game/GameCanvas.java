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

public class GameCanvas extends Canvas implements Commons, ActionListener {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private int delay = 10;
	protected Timer timer = new Timer(delay, this);
	
	private BufferedImage image = new BufferedImage(GAME_WIDTH,GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	//For Testing purposes  
	int x = 40;
	
	public GameCanvas(){
		setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
		
		timer.start();
		
	}
	
	/**
	 * Will take user input as well as any computation needed for the game. 
	 */
	public void update(){
		
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
		g.drawLine(x, 30, 330, 280);
		g.dispose();
		bs.show();
		
		x++;
		
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		update();
		render();
		
		// TODO Auto-generated method stub
		
	}

}
