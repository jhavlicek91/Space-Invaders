
public class Bullet {
	private SpaceWorld world;
	public float x, y;
	public boolean atTop; //has bullet hit top of screen
	public boolean atBottom; //has bullet hit bottom of the screen
	public boolean isHit; //Has bullet hit an alien
	public int bulletNumber; //What number bullet is this one (from ship or alien)

	/** Constructor for Bullet Class */
	public Bullet(float x1, float y1, int num, SpaceWorld bw, boolean Alien){
		world = bw;
		x = (float) (x1 + .3);
		if(Alien)
			y = (float) (y1 + .3);
		else  y = y1;
		bulletNumber = num;
		atTop = false;
		atBottom = false;
		isHit = false;
	}

	public long roundX(){
		return(Math.round(x));
	}
	
	public int roundY(){
		return(Math.round(y));
	}
	
	/** Used to move bullets upwards from ship */
	public void Increment(){
		y -= .2;
	}
	
	/** Determines if a bullet from the ship should remain on the screen */
	public boolean Done(){
		
		if(isHit || atTop){
			return true;
		}
		return false;
	}
	
	/** Based on how many bullets are already on the screen, 
	 * determines if the ship can shoot any more */
	public boolean canShoot(){
		if(bulletNumber > 4){
			for(int j = bulletNumber - 5; j < bulletNumber ; j++){
				if(world.bullets[j].Done()){
					return true;
				}
			}
		}
		return false;
	}


}
