package FlappyBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.phoenix.Client;
import com.phoenix.MultipleScreen;

public class GameOver implements Screen
{
    protected SpriteBatch batch;
    protected MultipleScreen multi;
    public static Label restartLabel;
    protected static Label menuLabel;
    protected static Label exitLabel;
    protected Stage stage;
    public static Label.LabelStyle labelStyle;
    protected GameFont govFont;
    public GameOver(MultipleScreen sc){multi=sc;}

    @Override
    public void show() {
        govFont=new GameFont("Robot/joystix.monospace-regular.ttf",25,Color.WHITE,Color.BLACK,1);
        labelStyle=new Label.LabelStyle();
        labelStyle.font=govFont.getFont();
        batch=new SpriteBatch();
        restartLabel=new Label("Restart",labelStyle);
        restartLabel.setSize(restartLabel.getWidth()*1.5f,restartLabel.getHeight()*1.25f);
        restartLabel.setFontScale(1.5f,1.5f);
        restartLabel.setPosition(Gdx.graphics.getWidth()/2f-restartLabel.getWidth()/2,Gdx.graphics.getHeight()/4.5f);

        menuLabel=new Label("Menu",labelStyle);
        menuLabel.setSize(menuLabel.getWidth()*1.5f,menuLabel.getPrefHeight()*1.25f);
        menuLabel.setFontScale(1.5f,1.5f);
        menuLabel.setPosition(restartLabel.getX() + (restartLabel.getWidth() / 2) - (menuLabel.getWidth() / 2),restartLabel.getY()-menuLabel.getHeight()-(Gdx.graphics.getHeight()*0.03f));

        exitLabel=new Label("Exit",labelStyle);
        exitLabel.setSize(exitLabel.getWidth()*1.5f,exitLabel.getPrefHeight()*1.25f);
        exitLabel.setFontScale(1.5f,1.5f);
        exitLabel.setPosition(restartLabel.getX() + (restartLabel.getWidth() / 2) - (exitLabel.getWidth() / 2),(menuLabel.getY())-exitLabel.getHeight()-(Gdx.graphics.getHeight()*0.03f));
        stage=new Stage(new StretchViewport(GameScreen.WorldWidth,GameScreen.WorldHeight));
        Gdx.input.setInputProcessor(stage);

        restartLabel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                multi.changeScreen( new GameScreen(multi));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                restartLabel.setColor(Color.BLUE);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                restartLabel.setColor(Color.WHITE);
            }

        }

        );

        menuLabel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                multi.changeScreen( new Client(multi));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                menuLabel.setColor(Color.BLUE);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                menuLabel.setColor(Color.WHITE);
            }

        }

        );

        exitLabel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitLabel.setColor(Color.BLUE);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitLabel.setColor(Color.WHITE);
            }

        }

        );

        stage.addActor(restartLabel);
        stage.addActor(exitLabel);
        stage.addActor(menuLabel);


    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        batch.begin();
        StartScreen.BackgroundMove++;
        StartScreen.BackgroundMove=StartScreen.BackgroundMove%StartScreen.WorldWidth;
        StartScreen.drawBackground(batch);
        batch.end();
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
    public void dispose() {
        GameScreen.backgroundTexture.dispose();
        stage.dispose();
    }

    @Override
    public void hide() {

    }
}
