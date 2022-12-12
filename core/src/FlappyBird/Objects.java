package FlappyBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Objects {
    //graphics
    private Texture robot;

    //dimensions&positions
    private float width,height,xPos,yPos;

    public Objects(Texture robot, float width, float height, float xPos, float yPos) {
        this.robot = robot;
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    //collision Detection
    public boolean intersects(Rectangle otherObject){
        Rectangle thisObject=new Rectangle(xPos,yPos,width,height);
        return thisObject.overlaps(otherObject);
    }
    public Rectangle getCoordinates(){
        return new Rectangle(xPos,yPos,width,height);
    }
    public void draw(SpriteBatch batch){
        batch.draw(robot,xPos,yPos,width,height);
    }


    public void move(int x,int y){
        xPos+=x;
        yPos+=y;
    }
}
