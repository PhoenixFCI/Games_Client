package FlappyBird;

import Game1.test;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.phoenix.Client;
import com.phoenix.MultipleScreen;

import javax.swing.plaf.nimbus.State;


public class GameOver implements Screen
{

    private Stage stage;
    private Button button1;
    private Button button2;
    private Button buttonH;
    MultipleScreen multi;
    private Skin myskin;

    private SpriteBatch batch ;
    private BitmapFont font;


    GameOver(MultipleScreen x)
    {
        multi = x;



    }


    @Override
    public void show()
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch =new SpriteBatch();
        font =new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(20);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        myskin = new Skin(Gdx.files.internal("Skin/glassyui/glassy-ui.json"));

        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;

        button1 = new TextButton("Exit",myskin);
        button1.setSize(col_width*4,row_height);
        button1.setPosition(600,200);


        button2 = new TextButton("Menu",myskin);
        button2.setSize(col_width*4,row_height);
        button2.setPosition(600,10);


        buttonH = new TextButton("Restart",myskin);


        buttonH.setSize(col_width*4,row_height);
        buttonH.setPosition(600,400);
        button1.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {


                Gdx.app.exit();


             }
        });

        button2.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {

           multi.changeScreen(new Client(multi));
            }
        });
        buttonH.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                GameScreen.State state = GameScreen.State.RUN ;
                multi.changeScreen(new GameScreen(multi));
            }
        });


        stage.addActor(button1);
        stage.addActor(buttonH);
        stage.addActor(button2);


    }

    @Override
    public void render(float delta)
    {

        Gdx.gl.glClearColor(1, 1, 10, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_WRITEMASK );
        batch.begin();
        font.draw(batch , "Error 404", 400,900);
        batch.end();
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
    public void dispose() {
  font.dispose();
  batch.dispose();
        myskin.dispose();
        stage.dispose();
        buttonH.setDisabled(true);
        button1.setDisabled(true);
        button2.setDisabled(true);


    }
}
