package Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.phoenix.MultipleScreen;

public class MainScreen implements Screen
{
    MultipleScreen multi;

    ShapeRenderer shape;
    Texture img_player, img_bullet, img_alien;
    Player player;
    Enemies[] aliens;
    SpriteBatch batch;
    int spacing = 95, aliens_height = 4, aliens_width = 8;
    public MainScreen(MultipleScreen multi)
    {
        this.multi = multi;

        batch = new SpriteBatch();

        img_player = new Texture(Gdx.files.internal("Game1/Player.png"));
        img_bullet = new Texture(Gdx.files.internal("Game1/Bullet.png"));
        img_alien = new Texture(Gdx.files.internal("Game1/Alien.png"));
        player = new Player(img_player,img_bullet);

        aliens = new Enemies[aliens_width*aliens_height];

        int z = 0;

        //initializing enemies positions
        for(int i = 0; i < aliens_height; i++)
        {
            for (int j = 0; j < aliens_width; j++)
            {
                Vector2 position = new Vector2(j*spacing,i*spacing);
                position.x += (int)((Gdx.graphics.getWidth()/2)) + 40;
                position.y += Gdx.graphics.getHeight();

                position.x -= (int)((aliens_width/2))* spacing;
                position.y -= aliens_height * spacing;
                aliens[z] = new Enemies(position,img_alien);
                z++;
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0,0,0,1);
        batch.begin();

        //Drawing enemy
        for (int i = 0; i < aliens.length;i++)
        {
            //checks if the enemy is alive or dead
            if(aliens[i].Alive)
            {
                //checks the collision of the bullet and the enemy sprite
                if(player.sprite_bullet.getBoundingRectangle().overlaps(aliens[i].sprite.getBoundingRectangle()))
                {
                    //sends the bullet to a far place xD
                    player.position_bullet.y = 10000;
                    //kills the enemy
                    aliens[i].Alive = false;
                    break;
                }

                aliens[i].Draw(batch);
            }
        }
        //Drawing player
        player.Draw(batch);
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
    public void dispose()
    {
        batch.dispose();
        img_alien.dispose();
        img_player.dispose();
        img_bullet.dispose();
    }
}
