package com.phoenix;

import Game1.MainScreen;
import FlappyBird.GameScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.badlogic.gdx.video.VideoPlayer;
//import com.badlogic.gdx.video.VideoPlayerCreator;
//import java.io.FileNotFoundException;


public class Client implements Screen
{
	private final MultipleScreen multi;
	private Stage stage;
	private TextButton button1;
	private TextButton button2;

	private SpriteBatch batch;
	//private VideoPlayer videoPlayer;
	Animation<TextureRegion> animation;
	float elapsed;
	private final Skin mySkin;

	public Client(MultipleScreen x)
	{
		multi = x;

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		mySkin = new Skin(Gdx.files.internal("Skin/glassyui/glassy-ui.json"));

		batch = new SpriteBatch();
		animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Preview/flappy.gif").read());
		//videoPlayer = VideoPlayerCreator.createVideoPlayer();


		int row_height = Gdx.graphics.getHeight() / 12;
		int col_width = Gdx.graphics.getWidth() / 12;


		button1 = new TextButton("Space Invader",mySkin,"small");
		button1.setSize(col_width*4,row_height);
		button1.setPosition(col_width,Gdx.graphics.getHeight()-600);

		button2 = new TextButton("Flappy Robot",mySkin,"small");
		button2.setSize(col_width*4,row_height);
		button2.setPosition(Gdx.graphics.getWidth()-col_width*5,Gdx.graphics.getHeight()-600);
		button1.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				multi.changeScreen( new MainScreen(multi));
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				button1.getLabel().setColor(Color.RED);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				button1.getLabel().setColor(Color.WHITE);
			}
		});

		button2.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				multi.changeScreen( new GameScreen(multi));
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				button2.getLabel().setColor(Color.RED);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				button2.getLabel().setColor(Color.WHITE);
			}
		});


		stage.addActor(button1);
		stage.addActor(button2);
	}

	@Override
	public void show()
	{
		/*try {
			videoPlayer.play(Gdx.files.internal("Preview/flappy.webm"));
		} catch (FileNotFoundException e) {
			Gdx.app.error("gdx-video", "Oh no!");
		}*/
	}

	@Override
	public void render(float delta)
	{
		/*if (!videoPlayer.isPlaying()) { // As soon as the video is finished, we start the file again using the same player.
			try {
				videoPlayer.play(Gdx.files.internal("Preview/flappy.webm"));
			} catch (FileNotFoundException e) {
				Gdx.app.error("gdx-video", "Oh no!");
			}
		}*/

		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

		batch.begin();
		batch.draw(animation.getKeyFrame(elapsed), Gdx.graphics.getWidth()-(Gdx.graphics.getWidth() / 12)*5 , Gdx.graphics.getHeight()-450,button2.getWidth(), button2.getHeight() * 4);
		//Texture frame = videoPlayer.getTexture();
		/*if (frame != null)
			batch.draw(frame,Gdx.graphics.getWidth()-(Gdx.graphics.getWidth() / 12)*5 , 400, button2.getWidth(), 300);*/
		batch.end();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose()
	{
		stage.dispose();
		mySkin.dispose();
		//videoPlayer.dispose();
		batch.dispose();
	}
}
