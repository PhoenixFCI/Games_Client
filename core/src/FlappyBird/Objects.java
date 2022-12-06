package FlappyBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    public void draw(SpriteBatch batch){
        batch.draw(robot,xPos,yPos,width,height);
    }
}
