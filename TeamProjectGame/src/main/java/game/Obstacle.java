package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Obstacle implements Position {
	private int xPos = 10;
	private int yPos = 100;
	
	private int width = 40;
	private int height = 40;
	
	private int speed = 3;
	private int minimumXPosition = 250;
	
	private boolean destroyed;
	
	public Obstacle(int xPos, int yPos){
				
		this.xPos = xPos;
		this.yPos = yPos; 
		
		validateXPosition();
	}

	@Override
	public void setX(int xPos) {
		this.xPos = xPos;
	}

	@Override
	public int getX() {
		return xPos;
	}

	@Override
	public void setY(int yPos) {
		this.yPos = yPos;
		
	}

	@Override
	public int getY() {
		return yPos;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	public void validateXPosition() {

		if(xPos < minimumXPosition) {
			
			xPos = minimumXPosition;
			xPos += new Random().nextInt(minimumXPosition);
		}
	}
	
	public void incrementSpeed(){
		speed++;
	}
	
	public void update() {
		xPos -= speed;
	}
	
	public void destroy(){
		destroyed = true;
	}

	public boolean isDestroyed(){
		return destroyed;
	}
	
	public int getRandomObstacle(int xPosition) {
		
		Random randomXPosition = new Random();
		int randomPos = randomXPosition.nextInt(xPosition);
		
		return randomPos;
		
	}
	
	public void paintObstacle(Graphics g){
		if(!destroyed){
        g.setColor(Color.ORANGE);
        g.fillRect(xPos,yPos,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos,yPos,width,height); 
		}
	}
}
