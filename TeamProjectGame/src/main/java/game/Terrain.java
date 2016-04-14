package game;

import java.awt.Color;
import java.awt.Graphics;

public class Terrain implements Commons, Position {
	
	private int xPos = 10;
	private int yPos = 100;
	
	private int width = 800;
	private int height = 200;
	
	private int speed = 1;
	
	public Terrain(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos; 
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
	
	public void incrementSpeed(){
		speed++;
	}
	
	public void update(){
		if(xPos + width == 0){
		   xPos = GAME_WIDTH;
		}

		xPos -= speed;
	}
	
	public void paintTerrain(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(xPos,yPos,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos,yPos,width,height);  
	}

}
