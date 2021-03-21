package objects;

import city.cs.engine.*;

public class Brick extends StaticBody {

    private static final Shape shape = new BoxShape(1.0f, 0.5f);
    private static final BodyImage brick4 = new BodyImage("assets/image/brick4.png");
    private static final BodyImage brick3 = new BodyImage("assets/image/brick3.png");
    private static final BodyImage brick2 = new BodyImage("assets/image/brick2.png");
    private static final BodyImage brick1 = new BodyImage("assets/image/brick1.png");
    private static final BodyImage brick0 = new BodyImage("assets/image/brick0.png");

    private int lives = 1;

    /**
     * Create a new DynamicBody with the alien image.
     *
     * @param world The world that the Enemy should be added to.
     */
    public Brick(World world, final Pad pad, int lives) {
        super(world, shape);
        setLives(lives);
    }

    /**
     * Get the "lives" of the Enemy. It should take this many bullets to
     * kill it.
     *
     * @return The number of lives the Enemy has: 1, by default.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the lives of the alien.
     *
     * @param lives The new lives of the alien.
     */
    public void setLives(int lives) {
        this.lives = lives;
        updateBodyImage();
    }

    private void updateBodyImage(){
        this.removeAllImages();
        switch (this.lives){
            case 4 : this.addImage(brick4); break;
            case 3 : this.addImage(brick3); break;
            case 2 : this.addImage(brick2); break;
            case 1 : this.addImage(brick1); break;
            case -1 : this.addImage(brick0); break;
        }
    }
}
