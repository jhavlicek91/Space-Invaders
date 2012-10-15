
public class SpaceShip {
	float x, y;
	int Direction;
	
	/** Constructor for spaceship */
	public SpaceShip(){
		x = 7;
		y = 8;
		Direction = 0;
	}
	
	public int roundX(){
		return(Math.round(x));
	}
	
	public int roundY(){
		return(Math.round(y));
	}
	
	public boolean checkX(){
		if(Math.abs(roundX() - x) < .1)
			return true;
		return false;
			
	}
	
	/** Hit detection for ship */
	public boolean checkHit(Bullet b){
		if(b.y  + .3 < y) return false;
		if(b.y  > y + .3 ) return false;
		if (b.x + .08 < x) return false;
		if (b.x > x + .7) return false;
		
		return true;
	}

}
