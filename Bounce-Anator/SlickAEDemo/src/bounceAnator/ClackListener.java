package bounceAnator;

import org.newdawn.slick.Sound;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;

public class ClackListener implements ContactListener{

	Sound clack;
	
	public ClackListener(Sound clack){
		this.clack=clack;
	}
	
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

	@Override
	public void endContact(Contact arg0) {
		
	}

}
