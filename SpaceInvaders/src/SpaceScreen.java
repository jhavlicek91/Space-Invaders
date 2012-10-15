import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


@SuppressWarnings("serial")
public class SpaceScreen extends Canvas {
	final private int T = 60;
	
	private SpaceWorld world;
	private int width, height, ssheight, sswidth;

	
	public SpaceScreen (SpaceWorld w){
		world = w;
		height = world.height();
		width = world.width();
		ssheight = T * height;
		sswidth = T * width;

		setSize(sswidth, ssheight);
		setBackground(Color.BLACK);
		
	}
	
	public void paint(final Graphics g){
		
		//Draw Aliens
		for(int w = 0; w < world.alienRow; w++){
			for(int k = 0; k < world.Col + 1; k++){
				g.setColor(world.aliens[w][k].c);
				if(world.aliens[w][k].Display())
					{g.fillRect((int)(world.aliens[w][k].x * T ), ((int)world.aliens[w][k].y * T), 40, 20);}
			}
		}
		
		//Draw Bullets from SpaceShip
		g.setColor(Color.WHITE);
		if(world.shipShots > 0){
			for(int z = 0; z < world.shipShots; z++){
				if(!world.bullets[z].atTop && !world.bullets[z].isHit)
				g.fillRect((int)(world.bullets[z].x * T), (int)(world.bullets[z].y * T), 5, 20);
			}
		}
			
		//Draw Bullets from aliens
		g.setColor(Color.RED);
		if(world.alienShots > 0){
			for(int l = 0; l < world.alienShots; l++){
				g.fillRect((int)(world.fromAliens[l].x * T), (int)(world.fromAliens[l].y * T), 5, 20);
					
				}
		}
		
		
		//Draw SpaceShip
		g.setColor(Color.GREEN);
		g.fillRect((int)(world.ship.x * T), (int)(world.ship.y * T), 40, 30);
		
			
	}
	
	public void update(Graphics g) {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g2 = image.getGraphics();
		paint(g2);
		g.drawImage(image, 0, 0, null);
	}
}

