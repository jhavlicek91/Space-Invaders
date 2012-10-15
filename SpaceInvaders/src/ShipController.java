
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class ShipController implements KeyListener {
	private SpaceWorld world; 
	
	
	public ShipController(SpaceWorld bw) {
		world = bw;
	}

	@Override
	
	/** Tells ship in the model to either move or shoot based on key input*/
	public void keyPressed(KeyEvent ke) {
		int code = ke.getKeyCode();
		if(code == KeyEvent.VK_RIGHT ) { 
			world.nextshipdir = SpaceWorld.RIGHT;
		}
		else if(code == KeyEvent.VK_LEFT){
			world.nextshipdir = SpaceWorld.LEFT;
		}
		else if(code == KeyEvent.VK_SPACE){
			world.createBullets();
		
		}
	}


	/** Does nothing*/
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	/** Does nothing */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
