import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Decider implements KeyListener {
	private HighScore score;

	public Decider(HighScore hs){
		score = hs;
	}
	
	@Override
	/** Tells high score screen if escape is clicked */
	public void keyPressed(KeyEvent ke) {
		int code = ke.getKeyCode();
		if(code == KeyEvent.VK_ESCAPE)
			score.quit = true;
	}

	@Override
	/** Does nothing */
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/** Does nothing */
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
