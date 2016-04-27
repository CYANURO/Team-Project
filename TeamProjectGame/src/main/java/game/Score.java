package game;

public class Score {
	
	private int score;
	private int finalScore;
	
	public Score(int round) {
		
		score = round;
		
	}
	
	public Score(int round1, int round2, int round3) {
		
		finalScore = round1 + round2 + round3;
		
	}
	
	public void incrementScore(int speedOfGame, int characterPosition) {
		
		score += speedOfGame + characterPosition;
		
	}
	
	

}
