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
			ApplicationGDXContainer container=new ApplicationGDXContainer(new BounceAnator(), 480, 800, 480, 800);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

}
