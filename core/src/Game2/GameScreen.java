package Game2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.MultipleScreen;

public class GameScreen  implements Screen {
    MultipleScreen hsson;

    //graphics
    private Texture background;
    private SpriteBatch batch;

    //timing
    private float BackgroundMove;

    //World parameters
    private float WorldWidth= Gdx.graphics.getWidth();
    private float WorldHeight=Gdx.graphics.getHeight();
    public GameScreen(MultipleScreen screen){
     hsson =screen;
    }

    @Override
    public void show() {
        batch=new SpriteBatch();
        //Screen
        Camera camera1 = new OrthographicCamera();
        background=new Texture("flappy-bird-assets-master/sprites/background-night.png");
        Viewport viewport =new StretchViewport(WorldWidth,WorldHeight,camera1);
    }

    @Override
    public void render(float delta) {
    BackgroundMove++;
    BackgroundMove=BackgroundMove%WorldWidth;
    batch.begin();
    batch.draw(background,-BackgroundMove,0,WorldWidth,WorldHeight);
    batch.draw(background,-BackgroundMove+WorldWidth,0,WorldWidth,WorldHeight);
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
    public void dispose() {
        background.dispose();
        batch.dispose();
    }

}
