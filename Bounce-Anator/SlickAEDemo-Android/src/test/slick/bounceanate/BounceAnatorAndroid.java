package test.slick.bounceanate;

import org.newdawn.slick.SlickActivity;

import bounceAnator.BounceAnator;

import android.os.Bundle;

/**A SlickActivity to run BounceAnator on an Android*/
public class BounceAnatorAndroid extends SlickActivity {
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //starts the game in a 480x800 window that has been
        //scaled to fullscreen
        start(new BounceAnator(),480,800);
    }
}