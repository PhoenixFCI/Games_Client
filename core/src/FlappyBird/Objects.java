package FlappyBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Objects {
    //graphics
    protected Texture texture;

    //dimensions&positions
    protected float width,height ;
    protected Vector2 position , velocity;
    protected static final int Gravity = -15;
    public Objects(Texture texture, float width, float height, float xPos, float yPos) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.position=new Vector2(xPos,yPos);
        this.velocity=new Vector2(0,0);
    }

    //collision Detection
    public boolean intersects(Rectangle otherObject){
        Rectangle thisObject=new Rectangle(position.x,position.y,width,height);
        return thisObject.overlaps(otherObject);
    }
    public Rectangle getCoordinates(){
        return new Rectangle(position.x,position.y,width,height);
    }
    public void draw(SpriteBatch batch){
        batch.draw(texture,position.x,position.y,width,height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void move(int x, float y){
        position.add(x,y);
    }
    public void update(float dt)
    {
        if(position.y>0)
            velocity.add(0,Gravity);

        velocity.scl(dt);
        position.add(0,velocity.y);
        velocity.scl(1/dt);

        if(position.y<0)
            position.y=0;

    }
    public  void jump(){
        velocity.y = 500;
    }

    public void setTexture(Texture texture){
        this.texture=texture;
    }



}
