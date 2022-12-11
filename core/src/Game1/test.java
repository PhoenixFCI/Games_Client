package Game1;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.phoenix.MultipleScreen;

public class test implements Screen {
    SpriteBatch batch;
    Texture img;
    private MultipleScreen game;

    public test(MultipleScreen x)
    {
        game = x;
    }

    @Override
    public void show()
    {
        batch = new SpriteBatch();
        img = new Texture("Game1\\image 1.jpg");
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
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
    public void hide() {

    }

    @Override
    public void dispose ()
    {
        batch.dispose();
        img.dispose();
    }
}

