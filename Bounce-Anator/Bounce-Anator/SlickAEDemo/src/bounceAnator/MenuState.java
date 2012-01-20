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

public class MenuState extends BasicGameState{
	
	/**the image for the play button*/
	Image play;
	/**the location of the play button*/
	Vector2f playLocation;
	
	/**the image for the exit button*/
	Image exit;
	/**the location of the exit button*/
	Vector2f exitLocation;
	
	/**marks which state to go to next.
	 * if nextState==getID(), then do not
	 * change states.*/
	int nextState;
	
	/**a lovely background*/
	private Image brickBackground;
	
	@Override
	public int getID() {
		return BounceAnator.MenuState;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		brickBackground=new Image("resources/brickBack.png");
		
		play=new Image("resources/playButton.png");
		playLocation=new Vector2f(
				(container.getWidth()-play.getWidth())/2,
				(container.getHeight()-play.getHeight())/2-64);
		
		exit=new Image("resources/exitButton.png");
		exitLocation=new Vector2f(
				(container.getWidth()-exit.getWidth())/2,
				(container.getHeight()-exit.getHeight())/2+64);
		
		nextState=getID();
	}

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

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		//see if it's time to change states
		if (nextState!=getID()){
			game.enterState(nextState,new FadeOutTransition(),new FadeInTransition());
			nextState=getID();
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		
		//see if one of the buttons are pressed
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
