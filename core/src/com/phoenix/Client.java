package com.phoenix;

import Game1.test;
import Game2.GameScreen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

//just a push test 2
public class Client implements Screen
{
	private MultipleScreen multi;
	private Stage stage;
	private Button button1;
	private Button buttonH;
	private Skin myskin;

	public Client(MultipleScreen x)
	{
		multi = x;

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		myskin = new Skin(Gdx.files.internal("Skin/glassyui/glassy-ui.json"));

		int row_height = Gdx.graphics.getWidth() / 12;
		int col_width = Gdx.graphics.getWidth() / 12;

		button1 = new Button(myskin,"small");
		button1.setSize(col_width*4,row_height);
		button1.setPosition(col_width,Gdx.graphics.getHeight()-row_height*3);
		buttonH = new Button(myskin,"small");
		buttonH.setSize(col_width*4,row_height);
		buttonH.setPosition(col_width,Gdx.graphics.getHeight()-row_height);
		button1.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				multi.changeScreen( new test(multi));
			}
		});

		buttonH.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				multi.changeScreen( new GameScreen(multi));
			}
		});


		stage.addActor(button1);
		stage.addActor(buttonH);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
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
		myskin.dispose();
	}
}
