package Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.phoenix.MultipleScreen;

public class MenuScreen implements Screen
{
    MultipleScreen multi;

    private SpriteBatch batch;
    private Stage stage;
    private TextureAtlas textureAtlas;
    private float[] backgroundOffsets = {0,0,0,0};
    private TextureRegion[] backgrounds;
    private float backgroundmaxSpeed;
    private final int World_width = Gdx.graphics.getWidth();
    private final int World_height = Gdx.graphics.getHeight();

    private TextButton buttonStart;
    private TextButton buttonExit;

    private Skin mySkin;
    private Music music;

    public MenuScreen(MultipleScreen x)
    {
        multi = x;
    }

    @Override
    public void show()
    {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        mySkin = new Skin(Gdx.files.internal("Skin/glassyui/glassy-ui.json"));

        textureAtlas = new TextureAtlas("Space Invader/Atlas/Images2.atlas");
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");
        backgroundmaxSpeed = (float)(World_height)/4;

        batch = new SpriteBatch();

        music = Gdx.audio.newMusic(Gdx.files.internal("Space Invader/Audio/MenuScreen.wav"));
        music.setVolume(1.0f);
        music.setLooping(true);
        music.play();


        int row_height = Gdx.graphics.getHeight() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;

        buttonStart = new TextButton("Start",mySkin,"small");
        buttonStart.setSize(col_width*4,row_height);
        buttonStart.setPosition((Gdx.graphics.getWidth()/2) - buttonStart.getWidth() / 2, Gdx.graphics.getHeight()/2);
        buttonStart.getLabel().setFontScale(2,2);

        buttonExit = new TextButton("Exit",mySkin,"small");
        buttonExit.setSize(col_width*4,row_height);
        buttonExit.setPosition(buttonStart.getX(),buttonStart.getY() - (buttonStart.getY() * 0.3f));
        buttonStart.getLabel().setFontScale(2,2);

        buttonStart.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                multi.changeScreen( new MainScreen(multi));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                buttonStart.getLabel().setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                buttonStart.getLabel().setColor(Color.WHITE);
            }
        });


        buttonExit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                buttonExit.getLabel().setColor(Color.RED);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                buttonExit.getLabel().setColor(Color.WHITE);
            }
        });


        stage.addActor(buttonStart);
        stage.addActor(buttonExit);
    }


    @Override
    public void render(float delta)
    {
        batch.begin();

        renderBackground(delta);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    private void renderBackground(float delta)
    {
        //the furthest background is slower
        backgroundOffsets[0] += delta * backgroundmaxSpeed / 8;
        //faster
        backgroundOffsets[1] += delta * backgroundmaxSpeed / 4;
        //faster
        backgroundOffsets[2] += delta * backgroundmaxSpeed / 2;
        //faster
        backgroundOffsets[3] += delta * backgroundmaxSpeed;

        for (int i = 0; i < backgroundOffsets.length; i++)
        {
            //resetting the offsets if it became bigger than the height of the screen
            if(backgroundOffsets[i] > World_height)
            {
                backgroundOffsets[i] = 0;
            }

            batch.draw(backgrounds[i],0,-backgroundOffsets[i],World_width,World_height);
            batch.draw(backgrounds[i],0,-backgroundOffsets[i]+World_height,World_width,World_height);
        }
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
        batch.dispose();
        music.dispose();
        mySkin.dispose();
        stage.dispose();
        textureAtlas.dispose();
    }
}
