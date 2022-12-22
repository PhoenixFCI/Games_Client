package Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

// @menna ammar
public class Player {
    public Vector2 position ;
    public Vector2 position_bullet;
    public Sprite sprite ;
    private Sprite sprite_bullet;
    public float speed = 300;
    public Player(Texture img , Texture img_bullet){
        sprite = new Sprite(img);
        sprite_bullet.setScale(4);
        sprite.setScale(4);
        position = new Vector2 (Gdx.graphics.getWidth()/2, sprite.getScaleY()*sprite.getHeight()/2);
        position_bullet=new Vector2(0,1000);
    }
     public void Update(float deltaTime){
        if (Gdx.input.isButtonJustPressed(0) && position.y>=Gdx.graphics.getHeight()/2){
            position_bullet.x = position.x+4;
            position_bullet.y = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A))
            position.x-=deltaTime*speed;

        if(Gdx.input.isKeyPressed(Input.Keys.D))
             position.x+=deltaTime*speed;

        if (position.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0)
            position.x = (sprite.getWidth()*sprite.getScaleX()/2);

         if (position.x+(sprite.getWidth()*sprite.getScaleX()/2)>= Gdx.graphics.getWidth())
             position.x = Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);

     }

     public void Draw (SpriteBatch batch) {
        Update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x , position.y);
        sprite.draw(batch);
     }
}
