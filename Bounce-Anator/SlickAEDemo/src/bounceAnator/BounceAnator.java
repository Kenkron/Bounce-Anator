package bounceAnator;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**a demo game that works on both slick and android
 * 
 * bounce the balls as long as you can without
 * letting them hit the ground.
 * the longer you go, the more will be added.
 * High scores will be recorded.*/
public class BounceAnator extends StateBasedGame{

	public static final int MenuState=0;
	public static final int PlayState=1;
	
	private static SavedState highScoreSave;
	
	public static final String highScoreName="highscore";
	
	private static int highScore;
	
	public BounceAnator() {
		super("Bounce-Anator");
	}

	public static void saveHighscore(int newHigh){
		highScoreSave.setNumber(highScoreName, newHigh);
		try {
			highScoreSave.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		highScore=newHigh;
	}
	
	public static int getHighScore(){
		return highScore;
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
		container.setMaximumLogicUpdateInterval(50);
		
		highScoreSave=new SavedState(highScoreName);
		try {
			highScoreSave.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		highScore=(int) highScoreSave.getNumber(highScoreName, 0);
		
		//add the menu
		addState(new MenuState());
		
		//add the game
		addState(new PlayState());
		
		//start with the menu
		enterState(MenuState);
	}

}
