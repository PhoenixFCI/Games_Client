package FlappyBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.phoenix.MultipleScreen;

public class GameOver extends StartScreen
{

    private  Label restartLabel;
    private int currentScore;
    private GameFont currentScoreFont;
    public GameOver(MultipleScreen sc)
    {
        super(sc);
    }

    @Override
    public void show()
    {
        super.show();
        GameScreen.time=4;
        this.currentScore=GameScreen.currentScore;
        GameScreen.currentScore=0;

        highScoreFont.setSize(40);
        highScoreFont.setColor(Color.GOLD);
        currentScoreFont=new GameFont(fontPath,30,Color.LIGHT_GRAY,Color.BLACK,1);
        stage.clear();
        restartLabel = new Label("Restart",labelStyle);
        restartLabel.setSize(restartLabel.getWidth()*1.5f, restartLabel.getHeight()*1.25f);
        restartLabel.setFontScale(1.5f,1.5f);
        restartLabel.setPosition(Gdx.graphics.getWidth()/2f- restartLabel.getWidth()/2,Gdx.graphics.getHeight()/4.5f);

        restartLabel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                sc.changeScreen( new GameScreen(sc));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                restartLabel.setColor(Color.BLUE);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                restartLabel.setColor(Color.WHITE);
            }

        });

        stage.addActor(restartLabel);
        stage.addActor(menuLabel);
        stage.addActor(exitLabel);
    }

    @Override
    public void render(float delta)
    {
        BackgroundMove++;
        BackgroundMove=BackgroundMove%WorldWidth;
        batch.begin();
        drawBackground(batch);
        highScoreFont.draw(batch,"High Score: "+GameScreen.prefs.getInteger("highScore"),(WorldWidth- highScoreFont.getTextwidth())/2f,(WorldHeight- highScoreFont.getTextheight())/2);
        currentScoreFont.draw(batch,"Your Score: "+this.currentScore,(WorldWidth-currentScoreFont.getTextwidth())/2f,(WorldHeight-currentScoreFont.getTextheight())/2.5f);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose()
    {
        currentScoreFont.dispose();
        highScoreFont.dispose();
        stage.dispose();
    }

    @Override
    public void hide() {

    }
}
