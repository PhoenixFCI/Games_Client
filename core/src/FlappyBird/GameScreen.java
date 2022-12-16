package FlappyBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
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

    private Preferences prefs = Gdx.app.getPreferences("game score");
    private State state = State.RUN;

    //graphics
    private Texture backgroundTexture;
    private SpriteBatch batch;
    private Objects player,enemy;
    private Texture playerTexture,enemyTexture;
    //private Motions animation;

    //timing
    private float BackgroundMove;

    //World parameters
    private final float WorldWidth= Gdx.graphics.getWidth();
    private final float WorldHeight=Gdx.graphics.getHeight();

    //font
    GameFont scoreFont;
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
        viewport = new StretchViewport(WorldWidth,WorldHeight,camera);

        //textures and objects in the game:

        backgroundTexture = new Texture("Robot/background2.jpg");
        playerTexture =new Texture("Robot/run.png");
        enemyTexture=new Texture("Robot/pxArt.png");
        player=new Objects(playerTexture, playerTexture.getWidth()*2, playerTexture.getHeight()*2,20,0);
        enemy=new Objects(enemyTexture,enemyTexture.getWidth()/15,enemyTexture.getHeight()/10,400,0);
        //Score font
        String fontPath = "Flappy Bird Game/joystix.monospace-regular.ttf";
        scoreFont=new GameFont(fontPath,25, Color.WHITE,Color.BLACK,1);
    }

    int currentScore=0;
    @Override
    public void render(float delta)
    {
        switch (state)
        {
            case RUN:
                BackgroundMove++;
                BackgroundMove=BackgroundMove%WorldWidth;
                player.update(delta);
                if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                    pause();

                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)&&player.getPosition().y==0){
                    player.jump();
                    player.setTexture(new Texture("Robot/run.png"));
                    currentScore++;
                    highScore(currentScore);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.D))
                    player.move(2,0);
                if(Gdx.input.isKeyPressed(Input.Keys.A))
                    player.move(-2,0);

                if(player.getPosition().y==0)
                    player.setTexture(new Texture("Robot/run.png"));

                else {
                    player.setTexture(new Texture("Robot/jump.png"));
                }
                if (player.intersects(enemy.getCoordinates())){
                    System.out.println(player.getCoordinates().getX());
                    System.out.println(enemy.getCoordinates().getX());
                }
                System.out.println(prefs.getInteger("highscore"));

                batch.begin();
                batch.draw(backgroundTexture,-BackgroundMove,0,WorldWidth,WorldHeight);
                batch.draw(backgroundTexture,-BackgroundMove+WorldWidth,0,WorldWidth,WorldHeight);
                player.draw(batch);
                enemy.draw(batch);
                scoreFont.draw(batch,"Score: "+currentScore,5,WorldHeight-scoreFont.textHeight());
                batch.end();
                break;

            case PAUSE:
                if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
                {
                   resume();
                }
                camera.update();
                batch.begin();
                batch.draw(backgroundTexture,0,0,WorldWidth,WorldHeight);
                batch.draw(backgroundTexture,-BackgroundMove+WorldWidth,0,WorldWidth,WorldHeight);
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
        backgroundTexture.dispose();
        batch.dispose();
        enemyTexture.dispose();
        playerTexture.dispose();
    }

    public int highScore(int currentScore){
        if (currentScore > prefs.getInteger("highscore")) {
            prefs.putInteger("highscore", currentScore);
            prefs.flush();
        }
        return prefs.getInteger("highscore");
    }

}
