import java.util.ArrayList;


public class SpaceWorld {

	public final static int STILL = 0, RIGHT = 1, LEFT = -1;
	int shipDirection = STILL;
	int alienDirection = RIGHT;
	public float time;
	int shipShots = 0;// Size of array bullets
	boolean youWon = false;
	boolean lost;
	Bullet[] bullets = new Bullet[100]; //Info about Bullets from the ship
	Bullet[] fromAliens = new Bullet[500]; //Info aboutBullets from the aliens
	int alienShots = 0; // size of array fromAliens
	Alien[][] aliens = new Alien[30][30];
	int alienColumn = 0;
	int alienRow = 0;
	int count = 0; //Number of times you have hit an alien
	int Col = 0; //Holds alien column when column is being updated
	boolean firstColumn; //Is an alien in the first column
	boolean lastColumn; //Is an alien in the last column
	SpaceShip ship = new SpaceShip();
	SpaceWorld world = this;
	int score = 0;
	//boolean ShiporAlien;
	
	
	private String spaceboard = 
			
		"###############\n"+
		"#   -------   #\n"+
		"#   -------   #\n"+
		"#   -------   #\n"+
		"#             #\n"+
		"#             #\n"+
		"#             #\n"+
		"#             #\n"+
		"#             #\n"+
		"#             #\n";
	
	private ArrayList<ArrayList<Character>> board = new ArrayList<ArrayList<Character>>();
	public int nextshipdir = STILL;
	
	public SpaceWorld()
	{
		//Initialize world
		String[] rows = spaceboard.split("\n");
		for(String row : rows) {
			ArrayList<Character> newrow = new ArrayList<Character>();

			for(int i = 0; i < row.length(); i++){
				newrow.add(row.charAt(i));
			}
			board.add(newrow);
			
		}
		
		
		//Create array out aliens in the original model
		for(int d = 0; d < height(); d++){
			for(int c = 0; c < width(); c++){
				Character tile = get(c,d);
				
				if(tile == '-' && get(c-1,d) == ' ')
					firstColumn = true;
				
				if(tile == '-' && get(c+1,d) == ' ')
					lastColumn = true;
				
				//Three cases for aliens in first row, last row, or anywhere else
				if(tile == '-'){
					if(firstColumn){
						aliens[alienRow][alienColumn] = new Alien(c,d);
						aliens[alienRow][alienColumn].first = true;
						aliens[alienRow][alienColumn].setType(alienRow);
						alienColumn++;
						firstColumn = false;
					}
					else if(lastColumn){
						aliens[alienRow][alienColumn] = new Alien(c,d);
						aliens[alienRow][alienColumn].last = true;
						aliens[alienRow][alienColumn].setType(alienRow);
						Col = alienColumn;
						alienColumn = 0;
						alienRow ++;
						lastColumn = false;
					}
					else{
						aliens[alienRow][alienColumn] = new Alien(c,d);
						aliens[alienRow][alienColumn].setType(alienRow);
						alienColumn++;
					}
					
				}
				
			}
		}
		
	}
	
	public int height(){
		return board.size();
	}
	
	public int width(){
		return board.get(0).size();
	}
	
	public Character get(int x, int y){
		return board.get(y).get(x);
	}
	
	public void set(int x, int y, Character C){
		board.get(y).set(x,C);
	}
	
	public void advanceTime() {
		time += 1;	
	
		//Only run if ship has fired a bullet
		if( shipShots > 0 ){
	
			for(int p = 0; p < shipShots; p++){
					
				// Move bullets from ship
				if(bullets[p].y >= .2 && !bullets[p].isHit){
					bullets[p].Increment();
				}
				else bullets[p].atTop = true;
				
			}
		}
		
		//Moving bullets from aliens
		for(int w = 0; w < alienShots; w++){
			
			if(fromAliens[w].y >= .1)
				fromAliens[w].y += .1;
			else fromAliens[w].atBottom = true;			
		}
		
		
		//Control alien behavior
		
		for(int h = 0; h < alienRow; h++){
			for(int q = 0; q < Col + 1; q++){
				
			   //Should alien shoot and how often
			// When aliens in bottom row should shoot
			   if(h == 2 && aliens[h][q].hits == 0){
					if(q == 0 || q == 4 ){
						aliens[h][q].Shoot = true;
						aliens[h][q].shotFreq = 50;
					}
					else if(q == 2 || q == 6){
						aliens[h][q].Shoot = true;
						aliens[h][q].shotFreq = 150;
					}
				}
			  //When aliens in middle row should shoot
				else if(h == 1 && aliens[h+1][q].hits == 1 && aliens[h][q].hits < 2){
					if(q == 3 || q == 5)
					aliens[h][q].Shoot = true;
					aliens[h][q].shotFreq = 75;
				}
			  //When aliens in top row should shoot
				else if(h == 0 && aliens[h+2][q].hits == 1 && aliens[h+1][q].hits == 2 && aliens[h][q].hits < 3){
					if(q == 0 || q == 4 ){
						aliens[h][q].Shoot = true;
						aliens[h][q].shotFreq = 50;
					}
					else if(q == 2 || q == 6){
						aliens[h][q].Shoot = true;
						aliens[h][q].shotFreq = 120;
					}
				}
			   
				//Switching direction to right when aliens hit left boundary
				if(aliens[h][q].checkX()){
					if(alienDirection == LEFT && h == 0 && q == 0 && get(aliens[h][q].roundX()-1,aliens[h][q].roundY()) == '#'){
						alienDirection = RIGHT;
					}
				}
				
				//Keep aliens moving left or right
				if(alienDirection == RIGHT)
					aliens[h][q].x += .05;
				else aliens[h][q].x -= .05;
					
				//Switching direction to left when aliens hit right boundary
				if(aliens[h][q].checkX()){
					if(alienDirection == RIGHT && h == alienRow-1 && q == Col && get(aliens[h][q].roundX() + 1,aliens[h][q].roundY()) == '#'){
						alienDirection = LEFT;
					}
				}
					
				//Shots from Aliens
				if(aliens[h][q].Shoot == true && time % 200 == aliens[h][q].shotFreq){
					fromAliens[alienShots] = new Bullet(aliens[h][q].x, aliens[h][q].y, alienShots + 1, world, true);
					alienShots++;
					aliens[h][q].Shoot = false;				
				}
					
				//If ship is hit then game is over
				if(aliens[h][q].Display() && aliens[h][q].roundY() >= ship.roundY())
					lost = true;
					
				
			}	
			
		}
			
		//Moving Ship		
		if (ship.checkX()){
		shipDirection = nextshipdir;
		
		if (shipDirection == RIGHT && get(ship.roundX() + 1,ship.roundY()) == '#')
			shipDirection = STILL;
		if (shipDirection == LEFT && get(ship.roundX() - 1,ship.roundY()) == '#')
			shipDirection = STILL;
		}
		
		if(shipDirection == LEFT)
			ship.x -= .15;
		if(shipDirection == RIGHT)
			ship.x += .15;
		
		
		//Only run if a shot has been fired from ship
		if( shipShots > 0 ){
			
			for(int p = 0; p < shipShots; p++){
				
				for(int m = 0; m < alienRow; m++){
					for(int r = 0; r < Col + 1; r++){
		
					//Detect if alien is hit
						if(aliens[m][r].checkHit(bullets[p]) && aliens[m][r].Display() && !bullets[p].Done()) {
							aliens[m][r].hits ++;
							aliens[m][r].updateColor();
							bullets[p].isHit = true;
							count ++;
						}
					}
						
				}
			}
		}
		
		for(int w = 0; w < alienShots; w++){
			
			//Detect if ship is hit
			if(ship.checkHit(fromAliens[w]))
				lost = true;		
		}
		
	}

	/** Determines if the game should continue*/
	public boolean gameOver() {
		if(count == 42){
			youWon = true;
			return true;
		}	
		if(lost)
			return true;
		return false;
		
	}
	
	/** Used to determine the final score after game is over */
	public int setScore(){
		score = (int) (1000 * count);
		return score;
	}
	
	/** When called, creates a new bullet from the spaceship*/
	public void createBullets(){
		if(shipShots < 4){
			shipShots += 1;
			bullets[shipShots - 1] = new Bullet(ship.x, ship.y, shipShots, world, false);
		}
		else if(world.shipShots >= 4){
			shipShots += 1;
			bullets[shipShots - 1] = new Bullet(ship.x, ship.y, shipShots, world, false);
			if(!bullets[shipShots - 1].canShoot()){
				shipShots -= 1;
			}		
		}
	}
	

}
