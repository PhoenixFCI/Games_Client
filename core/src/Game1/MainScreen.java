package Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.MultipleScreen;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class MainScreen implements Screen
{
    MultipleScreen multi;

    Random random = new Random();
    //Screen
    private Camera camera;
    private Viewport viewport;

    //Graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private TextureRegion[] backgrounds;
    private  TextureRegion playerShipTexture,playerShield,enemyShipTexture,enemyShield,playerLaser,enemyLaser;

    //timing
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundmaxSpeed;
    private float timeBetweenEnemySpawn = 3f, enemySpawnTimer = 0;

    //world parameters
    private final int World_width = Gdx.graphics.getWidth();
    private final int World_height = Gdx.graphics.getHeight();


    //Game Objects
    private PlayerShip playership;
    private LinkedList<EnemyShip> enemyshipsList;

    private LinkedList<Lasers> playerLaserList, enemyLaserList;


    public MainScreen(MultipleScreen multi)
    {
        this.multi = multi;

        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        //a camera for 2d perspective
        camera = new OrthographicCamera();
        viewport = new StretchViewport(World_width,World_height,camera);

        //set up the texture atlas
        textureAtlas = new TextureAtlas("Space Invader/Atlas/Images.atlas");


        //set up the BackGrounds
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundmaxSpeed = (float)(World_height)/4;


        //Initialize texture regions
        enemyShipTexture = textureAtlas.findRegion("enemyBlack3");
        enemyShield = textureAtlas.findRegion("shield1");
        enemyLaser = textureAtlas.findRegion("laserRed04");
        enemyShield.flip(false,true); //flipping the shield upside down

        playerShipTexture = textureAtlas.findRegion("playerShip1_blue");
        playerShield = textureAtlas.findRegion("shield2");
        playerLaser = textureAtlas.findRegion("laserBlue05");

        //set up game objects
        playership = new PlayerShip(240,10,60,60,World_width/2,World_height/4,4,23,120,0.6f,playerShipTexture,playerShield,playerLaser);
        enemyshipsList = new LinkedList<>();

        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();

        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
        batch.begin();

        renderBackground(delta);

        inputs(delta);
        playership.update(delta);
        spawnEnemy(delta);

        ListIterator<EnemyShip> enemyiterator = enemyshipsList.listIterator();
        while(enemyiterator.hasNext())
        {
            EnemyShip enemyship = enemyiterator.next();
            moveEnemies(enemyship,delta);
            enemyship.update(delta);

            //Enemey ships
            enemyship.draw(batch);
        }


        //player ships
        playership.draw(batch);

        //lasers
        renderLasers(delta);

        //Collision
        Collisions();

        batch.end();
    }


    private void spawnEnemy(float delta)
    {
        enemySpawnTimer+=delta;
        if(enemySpawnTimer > timeBetweenEnemySpawn)
        {
            enemyshipsList.add(new EnemyShip(190, 5, 60, 60, random.nextFloat() * (World_width - 10), random.nextFloat() * (World_height - 5), 3, 15, 100, 0.8f, enemyShipTexture, enemyShield, enemyLaser));
            enemySpawnTimer -= timeBetweenEnemySpawn;
        }
    }


    //Scrolling background
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


    private void renderLasers(float delta)
    {

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            if(playership.canFireLaser())
            {
                Lasers[] lasers = playership.fireLasers();
                for(Lasers laser : lasers)
                {
                    playerLaserList.add(laser);
                }
            }
        }

        ListIterator<EnemyShip> enemyiterator = enemyshipsList.listIterator();
        while(enemyiterator.hasNext())
        {
            EnemyShip enemyship = enemyiterator.next();
            if(enemyship.canFireLaser())
            {
                Lasers[] lasers = enemyship.fireLasers();
                for(Lasers laser : lasers)
                {
                    enemyLaserList.add(laser);
                }
            }
        }

        //draw lasers
        //remove old lasers
        ListIterator<Lasers> iterator = playerLaserList.listIterator(); //going through the list of playerLaser to add or remove .. etc
        while(iterator.hasNext())
        {
            Lasers laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y += laser.m_speed*delta; //Player Laser speed
            if(laser.boundingBox.y >World_height)
            {
                iterator.remove();
            }
        }


        iterator = enemyLaserList.listIterator(); //going through the list of enemylaser to add or remove .. etc
        while(iterator.hasNext())
        {
            Lasers laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.m_speed*delta; //enemy Laser speed
            if(laser.boundingBox.y + laser.boundingBox.height < 0)
            {
                iterator.remove();
            }
        }

    }


    private void Collisions()
    {
        //for each player laser, check whether it intersects an enemy ship
        ListIterator<Lasers> iterator = playerLaserList.listIterator(); //going through the list of playerLaser to add or remove .. etc
        while(iterator.hasNext())
        {
            Lasers laser = iterator.next();
            ListIterator<EnemyShip> temp = enemyshipsList.listIterator();

            while(temp.hasNext())
            {
                EnemyShip enemyship = temp.next();
                if(enemyship.intersects(laser.boundingBox))
                {
                    //removing when hitting the enemy
                    enemyship.hit(laser);
                    iterator.remove();
                    break;
                }
            }
        }


        //for each enemy laser, check whether it intersects the player ship
        iterator = enemyLaserList.listIterator(); //going through the list of playerLaser to add or remove .. etc
        while(iterator.hasNext())
        {
            Lasers laser = iterator.next();
            if(playership.intersects(laser.boundingBox))
            {
                //removing when hitting the player
                playership.hit(laser);
                iterator.remove();
            }
        }
    }


    private void moveEnemies(EnemyShip enemyship,float delta)
    {
        float leftlimit, rightlimit, uplimit, downlimit;
        leftlimit = -enemyship.boundingBox.x;
        downlimit = (float)(World_height/2) -enemyship.boundingBox.y - enemyship.boundingBox.height;

        rightlimit= World_width - enemyship.boundingBox.x - enemyship.boundingBox.width;
        uplimit =  World_height - enemyship.boundingBox.y - enemyship.boundingBox.height;

        float xMove = enemyship.getDirection().x * enemyship.m_speed * delta;
        float yMove = enemyship.getDirection().y * enemyship.m_speed * delta;

        if(xMove > 0 )
        {
            xMove = Math.min(xMove,rightlimit);
        }
        else
        {
            xMove = Math.max(xMove,leftlimit);
        }


        if(yMove > 0 )
        {
            yMove = Math.min(yMove,uplimit);
        }
        else
        {
            yMove = Math.max(yMove,downlimit);
        }

        enemyship.translate(xMove,yMove);
    }


    //movmenets and world limit
    private void inputs(float delta)
    {
        //keyboard input
        float leftlimit, rightlimit, uplimit, downlimit;
        leftlimit = -playership.boundingBox.x;
        downlimit = -playership.boundingBox.y;

        rightlimit= World_width - playership.boundingBox.x - playership.boundingBox.width;
        uplimit= (World_height/2) - playership.boundingBox.y - playership.boundingBox.height;

        //moving forward
        if(Gdx.input.isKeyPressed(Input.Keys.D) && rightlimit > 0)
        {
            /*float xChange = playership.m_speed*delta;
            xChange = Math.min(xChange,rightlimit);
            playership.translate(xChange,0.0f);*/
            playership.translate(Math.min(playership.m_speed*delta,rightlimit),0f);
        }

        //moving upward
        if(Gdx.input.isKeyPressed(Input.Keys.W) && uplimit > 0)
        {
            playership.translate(0f,Math.min(playership.m_speed*delta,uplimit));
        }

        //moving left
        if(Gdx.input.isKeyPressed(Input.Keys.A) && leftlimit < 0)
        {
            playership.translate(Math.max(-playership.m_speed*delta,leftlimit),0f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) && downlimit < 0)
        {
            playership.translate(0f,Math.max(-playership.m_speed*delta,downlimit));
        }
    }




    @Override
    public void resize(int width, int height)
    {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(camera.combined);
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
    }
}
