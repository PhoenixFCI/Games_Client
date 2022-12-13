package com.phoenix;

import Game1.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Phoenix");
		config.setWindowedMode(Lwjgl3ApplicationConfiguration.getDisplayMode().width/2,Lwjgl3ApplicationConfiguration.getDisplayMode().height/2);
		Lwjgl3Application app = new Lwjgl3Application(new MultipleScreen(), config);
	}
}
