package FlappyBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.*;
import com.phoenix.MultipleScreen;

import java.util.Vector;


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
    private Objects player;

    private final Vector<Objects>enemies= new Vector<>();
    private Texture playerTexture,enemyTexture;
    //private Motions animation;

    //timing
    private float BackgroundMove=0;

    //World parameters
    private final float WorldWidth= Gdx.graphics.getWidth();
    private final float WorldHeight=Gdx.graphics.getHeight();
    public GameScreen(MultipleScreen screen){
     game =screen;
    }

    private Viewport viewport;
    private Camera camera;

    private GameFont gameFont;
    private GameFont scoreFont;
    private int score=0;
    @Override
    public void show()
    {
        //camera and rendering things:
        batch=new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WorldWidth,WorldHeight,camera);

        //font
        //fonts
        String fontPath = "Flappy Bird Game/joystix.monospace-regular.ttf";
        gameFont=new GameFont(fontPath,50,Color.BROWN,Color.BLACK,5);
       scoreFont=new GameFont(fontPath,25,Color.WHITE,Color.BLACK,1);

        //textures and objects in the game:

        background = new Texture("Flappy Bird Game/sprites/background-night.png");
        playerTexture =new Texture("Flappy Bird Game/sprites/bluebird-upflap.png");
        player=new Objects(playerTexture, playerTexture.getWidth()*2, playerTexture.getHeight()*2,6,0);
        enemyTexture=new Texture("Flappy Bird Game/sprites/pipe-red.png");
        int des=0;
        for (int i = 0; i < 5; i++) {

            Objects enemy = new Objects(enemyTexture, enemyTexture.getWidth(), enemyTexture.getHeight(), WorldWidth - 80 - (des), 0);
            des+=200;
            enemies.add(enemy);
        }


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

                //Testing Score function
                for (int i = 0; i < enemies.size(); i++) {
                    if (player.intersects(enemies.get(i).getCoordinates()))
                        System.out.println("true");
                }
                for (int i = 0; i < enemies.size(); i++) {
                    int temp =(int)enemies.get(i).getCoordinates().x;
                    if (temp%2!=0)
                        temp++;
                    if (player.getCoordinates().x==temp)
                        score++;

                }

                camera.update();
                viewport.apply();
                batch.begin();
                //moving background
                batch.draw(background,-BackgroundMove,0,WorldWidth,WorldHeight);
                batch.draw(background,-BackgroundMove+WorldWidth,0,WorldWidth,WorldHeight);
                player.draw(batch);

                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).draw(batch);
                }
                scoreFont.draw(batch,"Score: "+score,5,WorldHeight-scoreFont.textHeight());
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
                gameFont.draw(batch,"Welcome\n"+ "nroibnto\n"+"n",Gdx.graphics.getWidth()/2-gameFont.textWidth()/2,Gdx.graphics.getHeight()/2- gameFont.textHeight()/2);
                batch.end();
                break;
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(camera.combined);
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
