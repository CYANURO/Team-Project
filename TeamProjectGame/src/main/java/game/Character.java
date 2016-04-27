package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Character implements Position {
	Color characterColor;
	
	private int xPos = 0;
	private int yPos = 60;
	
	private int width = 40;
	private int height = 20;
	
	private boolean destroyed;
	
	private Image characterImage;
	
	private int[] jumpPos = {8, 7, 6, 5, 4, 3, 2, 1, 0}; 
	int jumpPosIndex = 0;
	
	public Character(int xPos, int yPos){

		this.xPos = xPos;
		this.yPos = yPos; 
		
		loadCharacterImage("tasRun");
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
	
	public void destroy() {
		
		destroyed  = true;
	}
	
	public void jump(){
		yPos-=2;
	}
	
	public void loadCharacterImage(String fileName) {
		
		URL path = this.getClass().getResource(fileName + ".gif");
		characterImage = new ImageIcon(path).getImage();
	}
	
	public void attack(boolean attack){
		if(attack){
			loadCharacterImage("tasEat");
		}else{
			loadCharacterImage("tasRun");
		}
		//characterColor = Color.RED;
		//xPos++; 
	}
	public void update(boolean jump, boolean attack){
		
		if(attack){
			loadCharacterImage("tasEat");
			
		}else{
			loadCharacterImage("tasRun");
		}
		//if(jump){
			//int jumpPosIndex;
			//yPos-=4;
		//}
		
	}
	
	public void paintCharacter(Graphics g){
		
		if(!destroyed) {
			
			g.drawImage(characterImage, xPos, yPos, null);
	       
			
		}
    }

}
