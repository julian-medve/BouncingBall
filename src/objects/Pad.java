package objects;

import city.cs.engine.*;
import levels.Levels;
import org.jbox2d.common.Vec2;

public class Pad extends DynamicBody {

    private static final Shape shape = new BoxShape(2.0f, 0.5f, new Vec2(0f, 0));
    private static final BodyImage idleImage = new BodyImage("assets/image/Pad.png");
    private static final Shape fingerShape = new BoxShape(0.2f, 0.2f, new Vec2(0.5f, 0.1f));
    private boolean ninja = false;
    private Levels levels;

    /**
     * Create a new DynamicBody. Hulk!
     *
     * @param world The world that the Player should be added to.
     */
    public Pad(World world) {
        super(world);

        SolidFixture fixture = new SolidFixture(this, shape, 10);

        new SolidFixture(this, fingerShape);
        fixture.setFriction(0);
        this.addImage(idleImage);


        // Deduct points when the Player hits a bad guy
//        this.addCollisionListener(collisionEvent -> {
//            if (collisionEvent.getOtherBody() instanceof Brick) {
//                if (ninja) {
//                    return;
//                }
//
//                if (levels != null) {
//                    levels.getCurrentLevel().restorePlayer();
//                } else {
//                    System.out.println("ERROR: Levels data not specified.");
//                }
//
//                Enemy enemy = (Enemy) collisionEvent.getOtherBody();
//                enemy.setLives(enemy.getLives() + 1);
//
//                PointsHandler.removePoints(10);
//            }
//        });

        // Make sure the correct image is being used
//        world.addStepListener(new StepAdapter() {
//            @Override
//            public void preStep(StepEvent stepEvent) {
//                super.preStep(stepEvent);
//
//				Vec2 velocity = Player.this.getLinearVelocity();
//				BodyImage currentImage = Player.this.getImages().get(0);
//
//				if (velocity.x > 0.1 && currentImage != walkingRightImage) {
//					Player.this.addImage(ninja ? kisiRight : walkingRightImage);
//				} else if (velocity.x < -0.1 && currentImage != walkingLeftImage) {
//					Player.this.addImage(ninja ? kisiLeft : walkingLeftImage);
//				} else if (Math.abs(velocity.x) < 0.1 && currentImage != idleImage) {
//					Player.this.addImage(ninja ? kisiRight : idleImage);
//				}
//            }
//        });
    }

    /**
    * Add levels data: as Levels wants access to Player and Player wants
    * access to Levels, this cannot be done in the constructor.
    *
    * @param levels The Levels object.
    */
    public void addLevelsData(levels.Levels levels) {
        this.levels = levels;
    }
}
