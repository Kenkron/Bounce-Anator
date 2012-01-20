package test.slick.bounceanate;

import org.newdawn.slick.SlickActivity;

import bounceAnator.BounceAnator;

import android.os.Bundle;

public class BounceAnatorAndroid extends SlickActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        start(new BounceAnator(),480,800);
    }
}