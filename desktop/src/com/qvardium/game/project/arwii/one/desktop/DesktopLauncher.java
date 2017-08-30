package com.qvardium.game.project.arwii.one.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.qvardium.game.project.arwii.one.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1280;
		config.height=768;
		new LwjglApplication(new MyGame(1), config);
	}
}
