package FlappyBird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Objects {
    //graphics
    private Texture texture;

    //dimensions&positions
    private float width,height ;
    private Vector3 position= new Vector3(20,300,0) , velocity= new Vector3(0,0,0);
    private static final int Gravity = -15;
    public Objects(Texture texture, float width, float height, float xPos, float yPos) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.position.x = xPos;
        this.position.y = yPos;
    }
    public  Objects(){}
    //collision Detection
    public boolean intersects(Rectangle otherObject){
        Rectangle thisObject=new Rectangle(position.x,position.y,width,height);
        return thisObject.overlaps(otherObject);
    }
    public Rectangle getCoordinates(){
        return new Rectangle(position.x,position.y,width,height);
    }
    public void draw(SpriteBatch batch){
        batch.draw(texture,position.x,position.y,width,height);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void move(int x, float y){
        position.add(x,y,0);
    }
    public void update(float dt){
        if(position.y<0)
        velocity.add(0,Gravity,0);

    velocity.scl(dt);
    position.add(0,velocity.y,0);
    velocity.scl(1/dt);
    if(position.y<0){
        position.y=0;
    }
        System.out.println("Update is working");
    }
    public  void jump(){
        velocity.y = 300;

    }



    public void move(){}

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture){
        this.texture=texture;
    }



}
