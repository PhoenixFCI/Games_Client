package Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

// @menna ammar
public class Player
{
    public Vector2 position ;
    public Vector2 position_bullet;
    public Sprite sprite ;
    public Sprite sprite_bullet;
    public float speed = 300, speed_bullet = 1000;


    public Player(Texture img , Texture img_bullet)
    {
        sprite = new Sprite(img);
        sprite_bullet = new Sprite(img_bullet);
        sprite_bullet.setScale(5);
        sprite.setScale(3);
        position = new Vector2 (Gdx.graphics.getWidth()/2, sprite.getScaleY()*sprite.getHeight()/2);
        position_bullet=new Vector2(100000,100000);
    }
     public void Update(float deltaTime){
        //shooting the bullet
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && position_bullet.y>=Gdx.graphics.getHeight()){
            position_bullet.x = position.x + 7;
            position_bullet.y = position.y + 10;
        }

        //Moving Right
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            position.x-=deltaTime*speed;

        //Moving left
        if(Gdx.input.isKeyPressed(Input.Keys.D))
             position.x+=deltaTime*speed;

        //checking if the player crossed the left border or not
        if (position.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0)
        {
            position.x = (sprite.getWidth() * sprite.getScaleX()/2);
        }

         //checking if the player crossed the Right border or not
         if (position.x+(sprite.getWidth()*sprite.getScaleX()/2)>= Gdx.graphics.getWidth())
        {
            position.x = Gdx.graphics.getWidth() - (sprite.getWidth() * sprite.getScaleX() / 2);
        }

         //Bullet speed in y direction
        position_bullet.y+=deltaTime*speed_bullet;
     }

     public void Draw (SpriteBatch batch)
     {
        Update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x , position.y);
        sprite_bullet.setPosition(position_bullet.x, position_bullet.y);
        sprite_bullet.draw(batch);
        sprite.draw(batch);
     }
}
