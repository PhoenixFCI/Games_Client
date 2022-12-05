package Game2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.*;
import com.phoenix.MultipleScreen;


public class GameScreen  implements Screen {
    MultipleScreen game;
    public enum State
    {
        PAUSE,
        RUN,
        //RESUME,
        //STOPPED
    }

    private State state = State.RUN;

    //graphics
    private Texture background;
    private SpriteBatch batch;

    //timing
    private float BackgroundMove;

    //World parameters
    private final float WorldWidth= Gdx.graphics.getWidth();
    private final float WorldHeight=Gdx.graphics.getHeight();
    public GameScreen(MultipleScreen screen){
     game =screen;
    }

    private Viewport viewport;
    private Camera camera;

    @Override
    public void show()
    {
        batch=new SpriteBatch();
        //Screen
        camera = new OrthographicCamera();
        background = new Texture("flappy-bird-assets-master/sprites/background-night.png");
        viewport = new FillViewport(WorldWidth,WorldHeight,camera);
    }

    @Override
    public void render(float delta)
    {
        switch (state)
        {
            case RUN:
                BackgroundMove++;
                BackgroundMove=BackgroundMove%WorldWidth;
                if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                {
                    pause();
                }
                camera.update();
                viewport.apply();
                //batch.setProjectionMatrix(viewport.getCamera().combined);
                batch.begin();
                batch.draw(background,-BackgroundMove,0,WorldWidth,WorldHeight);
                batch.draw(background,-BackgroundMove+WorldWidth,0,WorldWidth,WorldHeight);
                batch.end();
                break;

            case PAUSE:
                if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
                {
                   resume();
                }
                camera.update();
                batch.begin();
                batch.draw(background,-BackgroundMove,0,WorldWidth,WorldHeight);
                batch.draw(background,-BackgroundMove+WorldWidth,0,WorldWidth,WorldHeight);
                batch.end();
                break;
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause()
    {
        this.state = State.PAUSE;
    }

    @Override
    public void resume()
    {
        this.state = State.RUN;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        batch.dispose();
    }

}
