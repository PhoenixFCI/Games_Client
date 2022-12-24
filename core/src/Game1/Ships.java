package Game1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ships {
    /*habiba*/
    // attributes(characteristics)
    float m_speed;
    int sheild;
    //positon
    Rectangle boundingBox;
    //laser info
    float laser_width,laser_height;
    float laser_speed;
    float time_B_shots;
    float time_since_lastshot=0;
    //graphics
    TextureRegion shipTextureRegion, sheildTextureRegion,laserTextureRegion;

    public Ships(float xCenter,float yCenter,
                  float width,float height,float m_speed,int sheild,
                  float laser_width,
                  float laser_height,float laser_speed,
                  TextureRegion shipTextureRegion,
                  TextureRegion shieldTextureRegion,TextureRegion laserTextureRegion) {
        this.m_speed = m_speed;
        this.sheild =sheild;
        this.boundingBox=new Rectangle(xCenter-width/2,yCenter-height/2,width,height);
        this.laser_width = laser_width;
        this.laser_height = laser_height;
        this.laser_speed = laser_speed;
        this.shipTextureRegion = shipTextureRegion;
        this.sheildTextureRegion = sheildTextureRegion;
        this.laserTextureRegion = laserTextureRegion;
    }
    public void update(float deltaTime){
        time_since_lastshot +=deltaTime;
    }

    public boolean canfire(){
        boolean result=(time_since_lastshot-time_B_shots>=0);
        return result;
    }
    public abstract Lasers[] firelaser();

    public boolean intersects(Rectangle otherRectangle){

        return boundingBox.overlaps(otherRectangle);
    }

    public void hit(Lasers lasers){
        if (sheild>0){
            sheild--;
        }
    }

    public void draw(Batch batch){
        batch.draw(shipTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        if (sheild > 0){
            batch.draw(sheildTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        }
    }
}