package com.qvardium.game.project.arwii.one.android;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.qvardium.game.project.arwii.one.MyGame;

import android.os.Bundle;

public class AndroidLauncher extends AndroidApplication {
	
	Integer for_help;

	
	//private GameHelper gameHelper;
	private final static int requestCode = 1;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		for_help = getResources().getInteger(R.integer.localiz);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer=false;
		config.useCompass=false;
		initialize(new MyGame(for_help), config);
	}
}
