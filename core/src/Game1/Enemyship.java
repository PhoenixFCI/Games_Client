package Game1;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

class Enemyship extends Ships {
    Vector2 directionVector;
    float time_since_lastDirectionChange= 0;
    float directionChangeFrequency= 0.75f;

    public Enemyship(float xCenter, float yCenter, float width, float height,
                     float m_speed, int sheild, float laser_width, float laser_height, float laser_speed,
                     TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion,
                     TextureRegion laserTextureRegion) {
        super(xCenter, yCenter, width, height, m_speed, sheild,
                laser_width, laser_height, laser_speed, shipTextureRegion,
                shieldTextureRegion, laserTextureRegion);

        directionVector= new Vector2(0,-1);

    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomizeDirectionVector(){
        double bearing = SpaceShooterGame.random.nextDouble()*6.283185;
        directionVector.x=(float)Math.sin(bearing);
        directionVector.y=(float)Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        time_since_lastDirectionChange +=deltaTime;
        if (time_since_lastDirectionChange >directionChangeFrequency){
            randomizeDirectionVector();
            time_since_lastDirectionChange -=directionChangeFrequency;
        }
    }

    @Override
    public Lasers[] firelaser() {
        Lasers[] laser =new Lasers[2];
        laser[0] =new Lasers( boundingBox.x+boundingBox.width*0.18f, boundingBox.y-laser_height,
                laser_width,laser_height,laser_speed,
                laserTextureRegion);
        laser[1] =new Lasers( boundingBox.x+boundingBox.width*0.82f, boundingBox.y-laser_height,
                laser_width,laser_height,laser_speed,
                laserTextureRegion);

        time_since_lastshot=0;

        return laser;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(shipTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        if (sheild > 0){
            batch.draw(sheildTextureRegion,boundingBox.x,boundingBox.y-boundingBox.height * 0.2f,boundingBox.width,boundingBox.height);
        }
        super.draw(batch);
    }
}
