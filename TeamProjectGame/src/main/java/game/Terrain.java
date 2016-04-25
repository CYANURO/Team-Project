package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Terrain implements Commons, Position {
	
	private int xPos = 10;
	private int yPos = 100;
	
	private int width = 700;
	private int height = 200;
	
	private BufferedImage spriteSheet;
	private BufferedImage[] sprites;
	
	private int speed = 3;
	
	private Random randomYPosition;
	
	public Terrain(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos; 
		
		loadImage("terrainTest");
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
		
		randomYPosition = new Random();
		
		if(xPos + width <= 0){
		   
		   setX(GAME_WIDTH);
		   setY(randomYPosition.nextInt(GAME_HEIGHT - 300));
		}
		
		xPos -= speed;
		
	}
	
	public void loadImage(String fileName){
		
		try {
			spriteSheet = ImageIO.read(getClass().getResource(fileName +".png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}


		//final int rows = 1;
		//final int cols = 1;
		//sprites = new BufferedImage[rows * cols];

		//for (int i = 0; i < rows; i++) {
			//for (int j = 0; j < cols; j++) {
				//sprites[(i * cols) + j] = spriteSheet.getSubimage(j * width, i * height, width, height);
			//}
		//}

		
	}
	
	public void paintTerrain(Graphics g){
		
		//g.setColor(Color.BLACK);
	    //g.fillRect(xPos,yPos,width,height);
	    //g.setColor(Color.BLACK);
	    //g.drawRect(xPos,yPos,width,height);  
	    g.drawImage(spriteSheet,xPos,yPos,width,height, null);
	    //g.drawImage(img, x, y, observer)
	    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
