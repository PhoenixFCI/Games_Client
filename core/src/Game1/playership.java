package Game1;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

class playership extends Ships{


    public playership(float xCenter, float yCenter, float width, float height,
                      float m_speed, int sheild, float laser_width,
                      float laser_height, float laser_speed,
                      TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion,
                      TextureRegion laserTextureRegion)
    {
        super(xCenter, yCenter, width, height, m_speed,
                sheild, laser_width, laser_height, laser_speed,
                shipTextureRegion, shieldTextureRegion, laserTextureRegion);


    }

    @Override
    public Lasers[] firelaser() {
        Lasers[] laser =new Lasers[2];
        laser[0] =new Lasers(boundingBox.x+boundingBox.width*0.07f, boundingBox.y+boundingBox.height*0.45f,
                laser_width,laser_height,laser_speed,
                laserTextureRegion);
        laser[1] =new Lasers(boundingBox.x+boundingBox.width*0.07f, boundingBox.y+boundingBox.height*0.45f,
                laser_width,laser_height,laser_speed,
                laserTextureRegion);

        time_since_lastshot=0;

        return laser;
    }
}
