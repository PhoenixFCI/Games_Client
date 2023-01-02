package FlappyBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Objects {
    //graphics
    private final Texture texture;
    //dimensions&positions
    private final float width,height ;
    protected Vector2 position= new Vector2(20,300);
    final ShapeRenderer shapeRenderer;
    public Objects(Texture texture, float width, float height, float xPos, float yPos) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.position.x = xPos;
        this.position.y = yPos;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }
    //collision Detection
    public boolean intersects(Rectangle otherObject){
        float x,y,w,h;
        x=(position.x+width+width/50f)/2;
        y= position.y+15;
        w= width/2f;
        h= height/1.75f;

        Rectangle thisObject=new Rectangle(x,y,w,h);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.GREEN);
//        shapeRenderer.rect(x, y, w, h);
//        shapeRenderer.end();
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



}
