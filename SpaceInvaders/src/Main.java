import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/** This class is where the program begins */
public class Main {


	public static void main(String[] args) {
		
		SpaceWorld sw = new SpaceWorld();
		SpaceScreen ss = new SpaceScreen(sw);
		HighScore hs = new HighScore(sw);
		
		Frame f = new Frame("Space Invaders");
		Frame s = new Frame("High Scores");
		s.add(hs);
		s.pack();
		f.add(ss);
		f.pack();
		f.addWindowListener (new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		s.addWindowListener (new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		f.setVisible(true);
		
		ss.addKeyListener(new ShipController(sw));
		
		//Time Controller
		while(!sw.gameOver()) {
			sw.advanceTime();
			ss.repaint();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		f.setVisible(false);
  	    
		s.setVisible(true);
		
		hs.addKeyListener(new Decider(hs));
		
		while(!hs.goAgain && !hs.quit){
		   s.repaint();
		}
		if(hs.quit) s.setVisible(false);
		
		System.exit(0);

	}

}
