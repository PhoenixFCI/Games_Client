package FlappyBird;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player {

    protected Vector2 position , velocity;
    protected static final int Gravity = -15;
    public Vector2 getPosition() {
        return position;
    }
    public void update(float dt) {
        if(position.y>0)
            velocity.add(0,Gravity);

        velocity.scl(dt);
        position.add(0,velocity.y);
        velocity.scl(1/dt);

        if(position.y<0)
            position.y=0;

    }
    public  void jump(){
        velocity.y = 550;
    }
    public void move(int x, float y){
        position.add(x,y);
    }



}
