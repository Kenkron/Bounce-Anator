package bounceAnator;

import org.newdawn.slick.ApplicationGDXContainer;
import org.newdawn.slick.SlickException;

public class DesktopLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ApplicationGDXContainer container=new ApplicationGDXContainer(new BounceAnator(), 360, 600, 360, 600);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

}
