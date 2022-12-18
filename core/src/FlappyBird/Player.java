package FlappyBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends Objects {
    private Vector2  velocity;
    private static final int Gravity = -30;

    public Player(Texture texture, float width, float height, float xPos, float yPos) {
        super(texture, width, height, xPos, yPos);
        this.velocity=new Vector2(0,0);
    }

    public void update(float dt)
    {
        if(position.y>0)
            velocity.add(0,Gravity);

        velocity.scl(dt);
        position.add(0,velocity.y);
        velocity.scl(1/dt);
        if(position.y<0){
            position.y=0;
        }

    }
    public  void jump(){
        velocity.y = 650;
    }

    public void setTexture(Texture texture){
        this.texture=texture;
    }
}
