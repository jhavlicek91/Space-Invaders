import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


@SuppressWarnings("serial")
public class HighScore extends Canvas {
	private SpaceWorld world;
	public boolean quit = false;
	public boolean goAgain = false;
	int[] scores = new int[25];
	String outcome = "";
	int score;
	
	public HighScore(SpaceWorld sw){
		world = sw;
		setSize(world.width()*60, world.height()*60);
		setBackground(Color.BLACK);
	}
	
	public void paint(final Graphics g){
		
		g.setColor(Color.GREEN);
		g.drawString("High Scores", 417, 50);
		score = world.setScore();
		
		if(world.count == 42)
			outcome = "You Won";
		else outcome = "You Lost";
			
		g.drawString(outcome, 425, 85);
		g.drawString("Your Score is "+String.valueOf(score), 395, 120);
		
		
		try {
			
  			FileInputStream ifstream = new FileInputStream("scores.txt");
  			DataInputStream in = new DataInputStream(ifstream);
  		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
  		    
  		    String strLine = "";
  		    int i = 0;
  		    
  		    //read in top ten scores line by line and put them in an array
  		    while ((strLine = br.readLine()) != null && strLine != "")   {
  		    	scores[i] = Integer.valueOf(strLine);
  		    	i++;
  		    }
  		    
  		    //add the latest score to the array and then sort it 
  		    scores[i] = score;
  		    Arrays.sort(scores, 0, scores.length);
  		    in.close();
  		    
  			FileWriter ofstream = new FileWriter("scores.txt");
  			BufferedWriter out = new BufferedWriter(ofstream);
  			
  			//Update file with newest scores
  			for(int j = scores.length - 1; j > scores.length - 11; j--){
  				out.write(String.valueOf(scores[j]));
  				out.newLine();
  			}
  			out.close();
  		
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Write scores to high score screen
		int c = 0;
		for(int l = scores.length - 1; l > scores.length - 11; l--){
			
		   g.setColor(Color.GREEN);
		   g.drawString(Integer.toString(c+1)+".  "+Integer.toString(scores[l]), 420, 200+30*(c-1));
		   c++;
		}
		
		g.drawString("Press Esc to exit", 395, 520);
		
	}
	
}
