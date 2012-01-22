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

	/**a constant value marking the menu state*/
	public static final int MenuState=0;
	/**a constant value marking the play stat*/
	public static final int PlayState=1;
	
	/**the SavedState used for marking high scores
	 *  because this is static, any part of the game can save
	 * a new high score with the saveHighScore method*/
	private static SavedState highScoreSave;
	
	/**the name of the file in which the high score is saved
	 * this is a constant, as the high score should always be
	 * taken from the same place*/
	public static final String highScoreName="highscore";
	
	/**the current high score for the game
	 * Because this is static, any part of the
	 * game can access the high score through the
	 * getHighScore method*/
	private static int highScore;
	
	/**minimalistic initializer: simply calls super("Bounce-Anator);*/
	public BounceAnator() {
		super("Bounce-Anator");
	}
	
	/**set up the states and highScoreSave*/
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
		//if the  updates occurr less than 1/20th of a second,
		//the physics will become too inaccurate
		container.setMaximumLogicUpdateInterval(50);
		
		//get the previously saved high score (if there is one)
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

	/**Saves a given integer as the new high score for
	 * the game.  Because this is static, any part of the
	 * game can save a new HighScore.
	 * 
	 * @param newHigh: the new high score for the game that
	 * should be saved.  This function performs no checks to 
	 * ensure that the new high score is actually higher
	 * than the old high score.*/
	public static void saveHighscore(int newHigh){
		highScoreSave.setNumber(highScoreName, newHigh);
		try {
			highScoreSave.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//ensures that getHighScore returns accurate values
		//after a new high score is saved
		highScore=newHigh;
	}
	
	/**returns the current HighScore*/
	public static int getHighScore(){
		return highScore;
	}

}
