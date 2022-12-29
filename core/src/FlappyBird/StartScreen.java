package FlappyBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.phoenix.MultipleScreen;

public class StartScreen extends GameOver {
    public static Texture backgroundTexture;
    public static float BackgroundMove=0;
    public static final float WorldWidth= Gdx.graphics.getWidth();
    public static final float WorldHeight=Gdx.graphics.getHeight();
    public StartScreen(MultipleScreen sc) {
        super(sc);
    }

    @Override
    public void show() {
        backgroundTexture = new Texture("Robot/background2.jpg");
        super.show();
        GameOver.restartLabel.setText("Start");
        GameOver.restartLabel.setSize(restartLabel.getWidth()*1.5f,restartLabel.getHeight()*1.25f);
        GameOver.restartLabel.setFontScale(1.5f,1.5f);
        GameOver.restartLabel.setPosition(Gdx.graphics.getWidth()/2f-restartLabel.getWidth()/2,Gdx.graphics.getHeight()/4.5f);
        GameOver.menuLabel.setPosition(restartLabel.getX() + (restartLabel.getWidth() / 2),restartLabel.getY()-menuLabel.getHeight()-(Gdx.graphics.getHeight()*0.03f));
        GameOver.exitLabel.setPosition(restartLabel.getX() + (restartLabel.getWidth() / 2) - (exitLabel.getWidth() / 2),(menuLabel.getY())-exitLabel.getHeight()-(Gdx.graphics.getHeight()*0.03f));

    }

    @Override
    public void render(float delta) {
        BackgroundMove++;
        BackgroundMove=BackgroundMove%WorldWidth;
        batch.begin();
        drawBackground(batch);
        batch.end();
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
    public static void drawBackground(SpriteBatch batch){
        batch.draw(backgroundTexture,-BackgroundMove,0,WorldWidth,WorldHeight);
        batch.draw(backgroundTexture,-BackgroundMove+WorldWidth,0,WorldWidth,WorldHeight);
    }
}
