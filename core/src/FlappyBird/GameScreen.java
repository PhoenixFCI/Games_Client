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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.phoenix.MultipleScreen;

import java.util.Random;
import java.util.Vector;


public class GameScreen  implements Screen {
    MultipleScreen game;

    int width_window = (int) ((Gdx.graphics.getDisplayMode().width)*(55.0/100.0));  //Ratio of width @Kareem
    int height_window = (int) ((Gdx.graphics.getDisplayMode().height)*(75.0/100.0)); //Ratio of height @Kareem
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
    private Objects player;
    private Texture playerRunTexture,enemyTexture;
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

    //nothing
    private final Array<Enemies> enemies = new Array<>();
    private final int distance=150;
    private Random rand;

    @Override
    public void show()
    {
        //camera and rendering things:
        batch=new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WorldWidth,WorldHeight,camera);
        //textures and objects in the game:

        backgroundTexture = new Texture("Robot/background2.jpg");
        playerRunTexture =new Texture("Robot/Run.png");
        enemyTexture=new Texture("Robot/pxArt.png");
        player=new Objects(playerRunTexture, playerRunTexture.getWidth()*0.95f, playerRunTexture.getHeight(),40,3);
        //Score font
        String fontPath = "Robot/joystix.monospace-regular.ttf";
        scoreFont=new GameFont(fontPath,25, Color.WHITE,Color.BLACK,1);
        rand=new Random();

        //nothing

    }

    int currentScore=0;
    @Override
    public void render(float delta)
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
        {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
        {
            Gdx.graphics.setWindowedMode(width_window,height_window);
        }

        switch (state)
        {
            case RUN:
                BackgroundMove++;
                BackgroundMove=BackgroundMove%WorldWidth;
                player.update(delta);
                if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                    pause();


                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)&&player.getPosition().y==0)
                {
                    player.jump();
                }

                System.out.println(enemies.size);;
                //Enemies methods
                    updateEnemies();
                    spawnEnemies();
                    EnemyDil();
                    addScore();
                    if(collision())
                        pause();


                batch.begin();
                drawBackground();
                player.draw(batch);
                drawEnemies();
                drawScore();
                batch.end();
                break;

            case PAUSE:
                if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
                {
                   resume();
                }
                camera.update();
                batch.begin();
                drawBackground();
                player.draw(batch);
                drawEnemies();
                drawScore();
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
        playerRunTexture.dispose();
        Enemies.dispose();
    }

    public int highScore(int currentScore){
        if (currentScore > prefs.getInteger("highscore")) {
            prefs.putInteger("highscore", currentScore);
            prefs.flush();
        }
        return prefs.getInteger("highscore");
    }

    public void newEnemies(){
        Enemies enemy = new Enemies( WorldWidth +rand.nextInt(500));
        enemies.add(enemy);
    }

    public void spawnEnemies(){
        if (enemies.size==0)
            newEnemies();
        else{
            Enemies lastEnemy= enemies.peek();
            if(lastEnemy.getPosition().x<WorldWidth-distance)
                newEnemies();
        }
    }

    public boolean collision(){
        for (int i = 0; i < enemies.size; i++) {
            if (player.intersects(enemies.get(i).getCoordinates()))
                return true;
        }
        return false;
    }
    public void EnemyDil(){
        Enemies firstEnemy= enemies.first();
        if (firstEnemy.getPosition().x<-WorldWidth)
            enemies.removeValue(firstEnemy,true);

    }

    public void drawScore(){
        scoreFont.draw(batch,"Score: "+currentScore,5,WorldHeight-scoreFont.textHeight());
        scoreFont.draw(batch,"High Score: "+prefs.getInteger("highscore"),WorldWidth-scoreFont.textWidth()*2,WorldHeight-scoreFont.textHeight());
    }
    public void addScore(){
        for (int i = 0; i < enemies.size; i++) {
            int temp =(int)enemies.get(i).getPosition().x;
            while (temp%4!=0)
                    temp++;

            if (40==temp)
                currentScore++;
            highScore(currentScore);
        }
    }
    public void drawBackground(){
        batch.draw(backgroundTexture,-BackgroundMove,0,WorldWidth,WorldHeight);
        batch.draw(backgroundTexture,-BackgroundMove+WorldWidth,0,WorldWidth,WorldHeight);
    }
    public void drawEnemies(){
        for (int i = 0; i < enemies.size; i++) {
            enemies.get(i).draw(batch);
        }
    }

    public void updateEnemies(){
        for (int i = 0; i < enemies.size; i++) {
            enemies.get(i).enemyUpdate(4);
        }
    }


}
