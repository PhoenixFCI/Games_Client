package FlappyBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.phoenix.MultipleScreen;
import java.util.Random;


public class GameScreen extends StartScreen implements Screen {
    MultipleScreen game;
    public enum State
    {
        //STOPPED
        PAUSE,
        //RESUME
        RUN,

    }

    protected static Preferences prefs = Gdx.app.getPreferences("game score");
    public static State state = State.RUN;

    //graphics
    private static SpriteBatch batch;
    private Player player;
    private Texture playerRunTexture,enemyTexture;

    //World parameters
    public static final float WorldWidth= Gdx.graphics.getWidth();
    public static final float WorldHeight=Gdx.graphics.getHeight();

    //font&score
    protected static int currentScore=0;
    private int highScore=prefs.getInteger("highScore",0);

    private Viewport viewport;
    private Camera camera;

    //enemy attributes
    private final Array<Enemies> enemies = new Array<>();
    private final float distance=225+time;
    protected static float time=4;
    private boolean clicked=false;
    private Random rand;

    //sounds
    private Sound jumpSound;
    private Music backMusic;

    public GameScreen(MultipleScreen screen)
    {
        super(screen);
        this.game =screen;
    }

    @Override
    public void show()
    {
        //camera and rendering things:
        batch=new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WorldWidth,WorldHeight,camera);
        //textures and objects in the game:

        playerRunTexture =new Texture("Robot/Run.png");
        enemyTexture=new Texture("Robot/pxArt.png");
        player=new Player(playerRunTexture, playerRunTexture.getWidth()*0.95f, playerRunTexture.getHeight(),40,3);
        rand=new Random();
        //scoreFont

        scoreFont=new GameFont(fontPath,25,Color.WHITE,Color.BLACK,1);
        //nothing
        jumpSound= Gdx.audio.newSound(Gdx.files.internal("Robot/Jump.ogg"));
        backMusic=Gdx.audio.newMusic(Gdx.files.internal("Robot/Dexters Laboratory.mp3"));
        backMusic.setLooping(true);
        backMusic.setVolume(0.5f);
        StartScreen.scoreFont.setSize(25);
    }


    @Override
    public void render(float delta)
    {

        switch (state)
        {
            case RUN:
                BackgroundMove++;
                BackgroundMove=BackgroundMove%WorldWidth;

                backMusic.play();
                player.update(delta);
                if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                    pause();
                    backMusic.pause();
                    jumpSound.pause();
                }
                System.out.println(enemies.size);
                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)&&player.getPosition().y<=5){
                    player.jump();
                    jumpSound.play();
                }

                if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)&& !clicked){
                    prefs.putInteger("highScore", 0);
                    prefs.flush();
                    clicked=true;
                }


                //Enemies methods
                time +=Gdx.graphics.getDeltaTime()/10;
                updateEnemies(time);
                spawnEnemies();
                addScore();
                batch.begin();
                StartScreen.drawBackground(batch);
                player.draw(batch);
                drawEnemies();
                drawScore();
                batch.end();
                if(collision()) {
                    highScore(currentScore);
                    game.changeScreen(new GameOver(game));
                }
                break;

            case PAUSE:

                if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
                {
                   resume();
                }
                camera.update();
                batch.begin();
                StartScreen.drawBackground(batch);
                player.draw(batch);
                drawEnemies();
                drawScore();
                batch.end();
                break;
        }
    }


    @Override
    public void resize(int width, int height)
    {
        //viewport = new FillViewport(width,height);
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
    public void dispose()
    {
        //backgroundTexture.dispose();
        enemyTexture.dispose();
        playerRunTexture.dispose();
        jumpSound.dispose();
        backMusic.dispose();
        batch.dispose();
    }

    public int highScore(int currentScore){
        if (currentScore > highScore) {
            prefs.putInteger("highScore", currentScore);
            prefs.flush();
        }
        return prefs.getInteger("highScore",0);
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

    public static void drawScore(){
        scoreFont.draw(batch,"Score: "+currentScore,5,WorldHeight-scoreFont.getTextheight());
        scoreFont.draw(batch,"High Score: "+prefs.getInteger("highScore"),WorldWidth-scoreFont.getTextwidth()*2,WorldHeight-scoreFont.getTextheight());
    }
    public void addScore() {
        for (int i = 0; i < enemies.size; i++) {
            if (player.getCoordinates().x > enemies.get(0).getCoordinates().x + enemies.get(i).getCoordinates().width+40) {
                currentScore++;
                enemies.removeIndex(0);
            }
        }
    }

    public void drawEnemies(){
        for (int i = 0; i < enemies.size; i++) {
            enemies.get(i).draw(batch);
        }
    }

    public void updateEnemies(float time){
        for (int i = 0; i < enemies.size; i++) {
            enemies.get(i).enemyUpdate(time);
        }
    }
}
