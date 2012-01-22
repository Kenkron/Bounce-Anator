package bounceAnator;

import org.newdawn.slick.Sound;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;

/**A simple LIBGdx Box2D listener that plays a clacking
 * sound whenever two bodies collide.*/
public class ClackListener implements ContactListener{

	/**the sound of two bodies colliding*/
	Sound clack;
	
	/**Initializer
	 * 
	 * @param clack: the sound of two bodies colliding*/
	public ClackListener(Sound clack){
		this.clack=clack;
	}
	
	/**called by LIBGdx Box2d whenever two bodies collide
	 * this method will play the sound 'clack', at a volume
	 * determined by the relative velocity squared of the bodies.  
	 * Thus, a harder impact will usually create a louder sound.*/
	@Override
	public void beginContact(Contact arg0) {
		
		//make it louder for harder collisions:
		float xHitSpeed=arg0.getFixtureA().getBody().getLinearVelocity().x-
			arg0.getFixtureB().getBody().getLinearVelocity().x;
		float yHitSpeed=arg0.getFixtureA().getBody().getLinearVelocity().y-
			arg0.getFixtureB().getBody().getLinearVelocity().y;
		
		//play the sound based on the difference in velocity squared
		clack.play(1, (float) (xHitSpeed*xHitSpeed+yHitSpeed*yHitSpeed)/1000f);
	}

	/**called by LIBGdx Box2d whenever two bodies are finished colliding
	 * this method is not used*/
	@Override
	public void endContact(Contact arg0) {
		
	}

}
