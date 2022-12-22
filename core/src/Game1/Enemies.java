package Game1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Enemies {
    public Vector2 position;
    public Vector2 Position_initial;
    public Sprite sprite;
    public Boolean Alive= true;


    public Enemies(Vector2 _position, Texture img, Color color){
        position= _position;
        sprite=new Sprite(img);
        sprite.setColor(color);
        sprite.setScale(4);
    }
    public void Draw(SpriteBatch batch){
        sprite.setPosition(position.x, position.y);
    }

}
