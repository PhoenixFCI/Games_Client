package FlappyBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Objects {
    //graphics
    protected Texture texture;

    //dimensions&positions
    protected Vector2 size;
    protected Vector2 position , velocity,bounds;
    protected static final int Gravity = -30;
    public Objects(Texture texture, float width, float height, float xPos, float yPos) {
        this.texture = texture;
        this.size = new Vector2(width,height);
        this.bounds = new Vector2(width,height);
        this.position=new Vector2(xPos,yPos);
        this.velocity=new Vector2(0,0);
    }

    //collision Detection
    public boolean intersects(Rectangle otherObject){
        Rectangle thisObject=new Rectangle(position.x,position.y,bounds.x*0.85f,bounds.y*0.80f);
        return thisObject.overlaps(otherObject);
    }
    public Rectangle getCoordinates(){
        return new Rectangle(position.x,position.y,bounds.x,bounds.y);
    }
    public void draw(SpriteBatch batch){
        batch.draw(texture,position.x,position.y,size.x,size.y);
    }
    public Vector2 getPosition() {
        return position;
    }

}
