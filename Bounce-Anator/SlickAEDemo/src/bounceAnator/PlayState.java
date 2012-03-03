package bounceAnator;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**the state in which the game BounceAnator is played
 * 
 * In this game, balls drop from the ceiling regularly, and you must keep
 * them bouncing in the air, off of the ground, for as long as possible.
 * as you play more balls appear every fifteen seconds, making it more difficult.*/
public class PlayState extends BasicGameState{

	/**stores all of the physicsImages in the level*/
	ArrayList<PhysicsImage> physicsImages=new ArrayList<PhysicsImage>();
	
	/**the conversion scale between pixels and LIBGdx Box2D meters*/
	public static final int PIXELS_PER_METER=8;
	
	/**handles the physics*/
	World physicsWorld;
	
	/**the density of the balls in this simulation*/
	public static final float BALL_DENSITY=1f;
	
	/**there will be a one second pause before the
	 * first ball is dropped*/
	public static final int STARTUP_DELAY=1000;
	
	/**a new ball will be added every 15 seconds*/
	public static final int BALL_DELAY=15000;
	
	/**a timer for delaying more balls*/
	int timeTillNextBall;
	
	/**keeps track of how long the player has managed to 
	 * keep the balls off the ground*/
	int timePassed;
	
	/**what you use to keep the balls up*/
	PhysicsImage block;
	
	/**have you lost yet?*/
	boolean lost;
	
	/**indicates what state to go to next
	 * if nextState==getID(), do not change states*/
	int nextState;
	
	/**a decorative background for the game*/
	Image brickBackground;
	
	/**the sound of two bodies hitting each other
	 * (in this case ball>ball, ball>wall, and ball>paddle)*/
	Sound clack;
	
	/**provides a unique integer with which to identify this state
	 * This identifier is taken from the BounceAnator class
	 * In this case, BounceAnator.PlayState*/
	@Override
	public int getID() {
		return BounceAnator.PlayState;
	}

	/**Initializes the images and sounds used in this state*/
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		nextState=getID();
		brickBackground=new Image("resources/brickBack.png");
		clack=new Sound("resources/clackAnator.wav");
	}

	/**sets up the game again each time this state is entered
	 * Because this game has no paused state, or other need to
	 * change states without resetting the game, the game will be
	 * reset immediately upon calling the enter method.*/
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		//make sure you havn't lost already
		lost=false;
		
		//clear the world and physics images
		physicsWorld=new World(new Vector2(0,9.8f), false);
		physicsImages.clear();
		
		//add a listener to play a sound when things collide
		physicsWorld.setContactListener(new ClackListener(clack));
		
		//create walls to keep the balls in bounds:
		PolygonShape verticalWall=new PolygonShape();
		verticalWall.setAsBox(1,container.getHeight()/PIXELS_PER_METER);
		
		BodyDef wallDef=new BodyDef();
		wallDef.type=BodyType.StaticBody;
		
		//left wall:
		Body leftWall=physicsWorld.createBody(wallDef);
		leftWall.createFixture(verticalWall,1);
		leftWall.setTransform(new Vector2(-1,0), 0);
		
		//right wall:
		Body rightWall=physicsWorld.createBody(wallDef);
		rightWall.createFixture(verticalWall, 1);
		rightWall.setTransform(new Vector2(container.getWidth()/PIXELS_PER_METER,0), 0);
		
		//ceiling:
		PolygonShape roofShape=new PolygonShape();
		roofShape.setAsBox(container.getWidth()/PIXELS_PER_METER, 1);
		
		Body roof=physicsWorld.createBody(wallDef);
		roof.createFixture(roofShape, 1);
		roof.setTransform(new Vector2(0,-1), 0);
		
		//create the block you use to bounce the images
		PolygonShape blockShape=new PolygonShape();
		blockShape.set(new Vector2[]{
				new Vector2(0,16/PlayState.PIXELS_PER_METER),
				new Vector2(32/PlayState.PIXELS_PER_METER,0),
				new Vector2(64/PlayState.PIXELS_PER_METER,0),
				new Vector2(96/PlayState.PIXELS_PER_METER,16/PlayState.PIXELS_PER_METER)});
		
		Body blockBody=physicsWorld.createBody(wallDef);
		blockBody.createFixture(blockShape, 1);
		
		block=new PhysicsImage(new Image("resources/block.png"), blockBody, PIXELS_PER_METER);
		block.setLocation(100, 500);
		physicsImages.add(block);
		
		timeTillNextBall=STARTUP_DELAY;
		timePassed=0;
	}
	
	/**draws all of the images in the gameplay
	 * in this case: the background, the paddle and balls, and the current time*/
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		//tile the background image to get a full background
		for (int x=0;x<container.getWidth();x+=brickBackground.getWidth()){
			for (int y=0;y<container.getHeight();y+=brickBackground.getHeight()){
				brickBackground.draw(x, y);
			}
		}
		
		//render the physics objects
		for (PhysicsImage o:physicsImages){
			o.render();
		}
		
		//show the current time
		g.setColor(new Color(0,0,0,0.5f));
		g.fill(new Rectangle(0, 0, container.getWidth(), 32));
		g.setColor(Color.white);
		g.drawString("Time: "+timePassed/1000f, 
				(container.getWidth())/2-48,
				10);
		
		//if you lost, tell the score
		if (lost){
			g.setColor(new Color(0,0,0,0.5f));
			g.fillRect(0, 0, container.getWidth(), container.getHeight());
			g.setColor(Color.white);
			g.drawString("YOUR SCORE: "+timePassed/1000f, 
					(container.getWidth()-g.getFont().getWidth("YOUR SCORE: "+timePassed/1000f))/2,
					container.getHeight()/2);
		}
	}

	/**drops a new ball into play*/
	public void createBall(float x, float y) throws SlickException{
		Image ballImage=new Image("resources/stoneBall48.png");
		
		//create a shape for the ball
		CircleShape circle=new CircleShape();
		circle.setPosition(new Vector2(0,0));
		circle.setRadius(ballImage.getWidth()*0.5f/PIXELS_PER_METER);
		
		//make a bodyDef, and make it dynamic so it actually moves
		BodyDef ballDef=new BodyDef();
		ballDef.type=BodyType.DynamicBody;
		
		//call physicsWorld.createBody to actually make the body
		Body ballBody=physicsWorld.createBody(ballDef);
		ballBody.createFixture(circle, BALL_DENSITY);
		
		//put the body and image together
		PhysicsImage ball=new PhysicsImage(ballImage, ballBody, PIXELS_PER_METER);
		ball.setLocation(x, y);
		ball.getBody().setLinearVelocity(new Vector2(1,0));//don't let them perfectly stack
		ball.getBody().getFixtureList().get(0).setRestitution(1);//keep all energy
		ball.getBody().getFixtureList().get(0).setFriction(0);//keep all energy
		
		//a circle shape is moved from it's center, so for the image to follow the
		//shape right, it must be moved to the upper left corner
		ball.setOffset(-ballImage.getWidth()/2, -ballImage.getWidth()/2);
		physicsImages.add(ball);
	}
	
	/**updates the game
	 * This involves updating physics, keeping track of the time passed,
	 * adding balls regularly, checking for death, and switching to a different
	 * state based on nextState*/
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		//run the world simulation
		physicsWorld.step(delta/1000f, 2, 4);
		
		//keep track of time
		if (!lost){
			timePassed+=delta;
			timeTillNextBall-=delta;
		}
		
		//handle ball creation
		if (timeTillNextBall<=0){
			timeTillNextBall+=BALL_DELAY;
			createBall((container.getWidth()-24)/2, 64);
		}
		
		//check for lost balls
		for (PhysicsImage po:physicsImages){
			if (po.getBody().getPosition().y>container.getHeight()/PIXELS_PER_METER){
				lost=true;
			}
		}
		
		//switch states when needed
		if (nextState!=getID()){
			game.enterState(nextState, new FadeOutTransition(), new FadeInTransition());
			nextState=getID();
			if (timePassed>BounceAnator.getHighScore()){
				BounceAnator.saveHighscore(timePassed);
			}
		}
	}
	
	
	/**called each time the mouse is pressed, or in the case of android,
	 * the screen is touched.  This method will move the block to the location
	 * of the touch, or end the game if the game is lost*/
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		if (!lost)
			//move the block to where you click/touch
			block.setLocation(x-block.getFace().getWidth()/2, y-block.getFace().getHeight()/2);
		else{
			//return to menu when you click after losing
			nextState=BounceAnator.MenuState;
		}
	}
}
