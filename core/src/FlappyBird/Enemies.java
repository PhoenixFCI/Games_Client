package FlappyBird;

import com.badlogic.gdx.graphics.Texture;

public class Enemies extends Objects {
    private static final Texture enemy=new Texture("Robot/pxArt.png");

    public Enemies(float x) {
        super(enemy,enemy.getWidth()/12f, enemy.getHeight()/11f, x, 0);
    }

    public void enemyUpdate(float dt){
        position.add(-dt,0);
    }

    public static void dispose(){
        enemy.dispose();
    }

}
