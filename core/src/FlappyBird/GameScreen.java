package FlappyBird;
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
        //STOPPED
        PAUSE,
        //RESUME
        RUN,

    }


    private State state = State.RUN;

    //graphics
    private Texture background;
    private SpriteBatch batch;
    private Objects player,enemy;
    private Texture playerTexture,enemyTexture;
    //private Motions animation;

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
        //camera and rendering things:
        batch=new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FillViewport(WorldWidth,WorldHeight,camera);

        //textures and objects in the game:

        background = new Texture("Flappy Bird Game/sprites/background-night.png");
        playerTexture =new Texture("Flappy Bird Game/sprites/bluebird-upflap.png");
        player=new Objects(playerTexture, playerTexture.getWidth()*2, playerTexture.getHeight()*2,5,0);
        enemyTexture=new Texture("Flappy Bird Game/sprites/pipe-red.png");
        enemy=new Objects(enemyTexture,enemyTexture.getWidth(),enemyTexture.getHeight(),WorldWidth-80,0);

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
                    pause();

                if(Gdx.input.isKeyPressed(Input.Keys.D)){
                    player.move(2,0);
                    //I was testing how can I make Animations: by changing the photo everytime the user press button
                    player.setTexture(new Texture("Flappy Bird Game/sprites/redbird-downflap.png"));
                }

                if(Gdx.input.isKeyPressed(Input.Keys.A))
                    player.move(-2,0);

                if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
                   player.setTexture(new Texture("Flappy Bird Game/sprites/bluebird-upflap.png"));

                if (player.intersects(enemy.getCoordinates()))
                    System.out.println("true");

                camera.update();
                viewport.apply();
                batch.begin();
                batch.draw(background,-BackgroundMove,0,WorldWidth,WorldHeight);
                batch.draw(background,-BackgroundMove+WorldWidth,0,WorldWidth,WorldHeight);
                player.draw(batch);
                enemy.draw(batch);
                batch.end();
                break;

            case PAUSE:
                if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
                {
                   resume();
                }
                camera.update();
                batch.begin();
                batch.draw(background,0,0,WorldWidth,WorldHeight);
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
        enemyTexture.dispose();
        playerTexture.dispose();
    }

}
