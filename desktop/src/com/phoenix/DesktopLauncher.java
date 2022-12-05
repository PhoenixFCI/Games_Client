package com.phoenix;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		int Width=Lwjgl3ApplicationConfiguration.getDisplayMode().width;
		int Height=Lwjgl3ApplicationConfiguration.getDisplayMode().height*90/100;
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Phoenix");
		config.setWindowedMode(Width,Height);
		//config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		Lwjgl3Application app = new Lwjgl3Application(new MultipleScreen(), config);

	}
}
