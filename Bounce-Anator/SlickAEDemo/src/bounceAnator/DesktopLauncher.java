package bounceAnator;

import org.newdawn.slick.ApplicationGDXContainer;
import org.newdawn.slick.SlickException;

/**a launcher that can run the BounceAnator game on a normal computer*/
public class DesktopLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//uses an ApplicationGDXContainer, allowing for libgdx physics
			//creates a 360x600 window to play the game in
			//the application is run in 480x800 resolution, but is scaled to fit a 360x600 window
			ApplicationGDXContainer container=new ApplicationGDXContainer(new BounceAnator(), 480, 800, 360, 600);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

}
