package bounceAnator;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**a state that acts as the main menu for the game
 * In this case. it shows the high scores, and
 * has "buttons" for starting the game and
 * exiting the game.  Note that these "buttons"
 * are not a single object, but consist of simply
 * an Image and a Vector2f that are used in tandem
 * to serve the same purpose.  Because of the simplicity
 * of this project, a separate Button class has not been
 * created.*/
public class MenuState extends BasicGameState{
	
	/**an image that, when pressed starts the game*/
	Image play;
	/**the location of the image that, when pressed, starts the game*/
	Vector2f playLocation;
	
	/**an image that, when pressed exits the game*/
	Image exit;
	/**the location of the image that, when pressed, exits the game*/
	Vector2f exitLocation;
	
	/**marks which state to go to next.
	 * if nextState==getID(), then do not
	 * change states.*/
	int nextState;
	
	/**a lovely background*/
	private Image brickBackground;
	
	/**provides a unique integer with which to identify this state
	 * This identifier is taken from the BounceAnator class
	 * In this case, BounceAnator.MenuState*/
	@Override
	public int getID() {
		return BounceAnator.MenuState;
	}

	/**Initializes the images of this state,
	 * as well as their locations*/
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		brickBackground=new Image("resources/brickBack.png");
		
		//setup the play "button" image and location
		play=new Image("resources/playButton.png");
		playLocation=new Vector2f(
				(container.getWidth()-play.getWidth())/2,
				(container.getHeight()-play.getHeight())/2-64);
		
		//setup the exit "button" image and location
		exit=new Image("resources/exitButton.png");
		exitLocation=new Vector2f(
				(container.getWidth()-exit.getWidth())/2,
				(container.getHeight()-exit.getHeight())/2+64);
		
		//Reset nextState to not change states next update
		nextState=getID();
	}

	/**renders everything on the menu
	 * In this case, the background, the high score,
	 * the play button, and the exit button.*/
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		//tile the background image to get a full background
		for (int x=0;x<container.getWidth();x+=brickBackground.getWidth()){
			for (int y=0;y<container.getHeight();y+=brickBackground.getHeight()){
				brickBackground.draw(x, y);
			}
		}
		
		//show the high score
		g.setColor(new Color(0,0,0,0.5f));
		g.fillRect((container.getWidth()-g.getFont().getWidth("High Score: "+BounceAnator.getHighScore()/1000f))/2, 
				128, g.getFont().getWidth("High Score: "+BounceAnator.getHighScore()/1000f), 
				g.getFont().getHeight("High Score: "+BounceAnator.getHighScore()/1000f));
		g.setColor(Color.white);
		g.drawString("High Score: "+BounceAnator.getHighScore()/1000f, 
				(container.getWidth()-g.getFont().getWidth("High Score: "+BounceAnator.getHighScore()/1000f))/2,
				128);
		
		//show buttons
		play.draw(playLocation.getX(),playLocation.getY());
		exit.draw(exitLocation.getX(),exitLocation.getY());
	}

	/**updates the game logic.  In this case, simply switches to another state
	 * if nextState has been changed (see nextState)*/
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		//see if it's time to change states
		if (nextState!=getID()){
			game.enterState(nextState,new FadeOutTransition(),new FadeInTransition());
			//resets nextState to not change states next update
			nextState=getID();
		}
	}

	/**called whenever a mouse button is pressed, or in the case
	 * of an android application, whenever the android is touched
	 * Checks whether the play or exit "buttons" have been pressed*/
	@Override
	public void mousePressed(int button, int x, int y) {
		
		//see if one of the "buttons" are pressed
		if (x>playLocation.x&&x<playLocation.x+play.getWidth()&&
				y>playLocation.y&&y<playLocation.y+play.getHeight()){
			//start the game
			nextState=BounceAnator.PlayState;
		}
		if (x>exitLocation.x&&x<exitLocation.x+exit.getWidth()&&
				y>exitLocation.y&&y<exitLocation.y+exit.getHeight()){
			//exit
			System.exit(0);
		}
	}
}
