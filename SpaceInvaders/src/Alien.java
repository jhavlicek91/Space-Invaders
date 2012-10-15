import java.awt.Color;


public class Alien {
	float x, y;
	int hits; //How many times has the alien been hit
	int type;	
	boolean first; //Is the alien the first in its row
	boolean last; //Is the alien the last in its row
	Color c; 
	int Direction;
	boolean Shoot; //True when alien should shoot
	int shotFreq; //How frequently the alien shoots
	
	/** Alien Class Constructor */
	public Alien(float x1, float y1){
		x = x1;
		y = y1;
		first = false;
		last = false;
		hits = 0;
		Direction = 1;// Go right
	}
	
	public int roundX(){
		return(Math.round(x));
	}
	
	public int roundY(){
		return(Math.round(y));
	}
	
	/** Sets color of alien based on row, also determines how many hits it take to kill it */
	public void setType(int row){
		if(row == 0){
			type = 0;
			c = Color.PINK;
		}
		else if(row == 1){
			type = 1;
			c = Color.CYAN; 
		}
		else if(row == 2){
			type = 2;
			c = Color.YELLOW;
		}
		
	}
	
	/** Based on how many bullets have hit it, determines if the alien is dead */
	public boolean Display(){
		if(type == 0 && hits == 3 )
			return false;
		if(type == 1 && hits == 2)
			return false;
		if(type == 2 && hits == 1)
			return false;
		return true;
	}
	
	/** Updates the aliens color each time a bullet hits it */
	public void updateColor(){
		if(type == 1){
			if(hits == 1)
				c = Color.YELLOW;
		}
		if(type == 0){
			if(hits == 1)
				c = Color.CYAN;
			if(hits == 2)
				c = Color.YELLOW;
		}
	}
	
	
	public boolean checkX(){
		if(Math.abs(roundX() - x) < .1)
			return true;
		return false;
			
	}
	
	/** Hit Detection for Alien */
	public boolean checkHit(Bullet b) {
		if (b.x + .08 < x) return false;
		if (b.x > x + .7) return false;
		if (b.y >= y + .3) return false;
		if (b.y + .3 < y) return false;
	
		return true;
	}
}
