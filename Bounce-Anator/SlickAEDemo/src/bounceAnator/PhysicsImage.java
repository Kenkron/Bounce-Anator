package bounceAnator;

import org.newdawn.slick.Image;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**an Image that follows a physics object*/
public class PhysicsImage {

	/**box2d is designed to work with numbers from 0.1 to 10.
	 * thus, making a 32x32 shape to fit a 32x32 image is bad.
	 * this number will be used to convert between pixels and
	 * box2d meters*/
	public final float PIXELS_PER_METER;
	
	/**an image that follows the physical body*/
	private Image face;
	
	/**the body subject to physical simulation*/
	private Body body;
	
	/**moves the face a bit to ensure it fit's on the body properly*/
	private float offX=0,offY=0;
	
	public PhysicsImage(Image face, Body b, float pixelsPerMeter){
		this.PIXELS_PER_METER=pixelsPerMeter;
		this.face=face;
		this.body=b;
	}

	/**moves the face a bit to ensure it fit's on the body properly*/
	public void setOffset(float x, float y){
		offX=x;
		offY=y;
	}
	
	/**draws this image based on the location of the physical body
	 * and the offset between the body's origin and the image's origin
	 * (see offX and offY)*/
	public void render(){
		face.setRotation((float) Math.toDegrees(body.getAngle()));
		face.draw(body.getPosition().x*PIXELS_PER_METER+offX,
				body.getPosition().y*PIXELS_PER_METER+offY);
	}
	
	/**sets the image that follows the physical body*/
	public void setFace(Image face) {
		this.face = face;
	}

	/**gets the image that follows the physical body*/
	public Image getFace() {
		return face;
	}

	/**sets the body subject to physical simulation
	 * that this image will follow*/
	public void setBody(Body body) {
		this.body = body;
	}

	/**gets the body subject to physical simulation
	 * that this image will follow*/
	public Body getBody() {
		return body;
	}
	
	/**sets the location of this image in pixel coordinates.
	 * Note that this will change the location of the body
	 * of this image, but will not change the angle, velocity, or
	 * any other physical attributes*/
	public void setLocation(float pixelsX, float pixelsY){
		body.setTransform(new Vector2(pixelsX/PIXELS_PER_METER, pixelsY/PIXELS_PER_METER),
				body.getAngle());
	}
}
