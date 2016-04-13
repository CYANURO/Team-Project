package game;

import java.awt.Color;
import java.awt.Graphics;

public class Character implements Position {
	Color characterColor;
	
	private int xPos = 0;
	private int yPos = 60;
	
	private int width = 20;
	private int height = 20;
	
	public Character(int xPos, int yPos){
		characterColor = Color.CYAN;
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
	
	public void jump(){
		yPos-=4;
	}
	public void attack(){
		characterColor = Color.RED;
		//xPos++; 
	}
	public void update(boolean jump, boolean attack){
		if(attack){
			characterColor = Color.RED;
		}else{
			characterColor = Color.CYAN;
		}
		if(jump){
			yPos-=4;
		}
		
	}
	
	public void paintCharacter(Graphics g){
        g.setColor(characterColor);
        g.fillOval(xPos,yPos,width,height);
        g.setColor(Color.BLACK);
        g.drawOval(xPos,yPos,width,height);
    }

}
