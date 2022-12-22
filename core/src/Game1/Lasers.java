package Game1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

class Lasers {
    //positon
    Rectangle boundingBox;
    float m_speed;
    //graphics
    TextureRegion textureRegion;

    public Lasers(float xCenter,float yBottom,float width
                  ,float height,float m_speed,TextureRegion textureRegion) {

        this.boundingBox=new Rectangle(xCenter-width/2,yBottom-height/2,width,height);
        this.m_speed = m_speed;
        this.textureRegion =textureRegion;
    }
    public void translate(float xChange,float yChange){
        boundingBox.setPosition(boundingBox.x+xChange, boundingBox.y+yChange);
    }

    public void draw(Batch batch){
        batch.draw(textureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
    }
}
