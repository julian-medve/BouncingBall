package objects;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;

/**
 * Create a DynamicBody in the shape of an image. Handles appearance and
 * collisions, but does not handle position and velocity.
 */
public class Bullet extends DynamicBody {
	private static final Shape bullet = new CircleShape(0.25f);
	private static final BodyImage ballImage = new BodyImage("assets/image/ball.png");

	/**
	 * Creates the bullet.
	 *
	 * @param world The world to add the bullet to.
	 * @param cheat If true, bullet will bounce off walls.
	 */
	public Bullet(World world, final boolean cheat) {
		super(world);

		this.addCollisionListener(collisionEvent -> {
			Body otherBody = collisionEvent.getOtherBody();

			if (otherBody instanceof Pad) {
				Vec2 padPosition = otherBody.getPosition();

				//Check bounce was collided the first and third part of the pad
//				setLinearVelocity(new Vec2(getLinearVelocity().x * 1.2f, getLinearVelocity().y * 1.2f));

				if(getPosition().x < padPosition.x - 4 / 4 || getPosition().x > padPosition.x + 4 / 4){

					Vec2 newVelocity = new Vec2(getLinearVelocity().x * (-1), getLinearVelocity().y);
					setLinearVelocity(newVelocity);
				}
			}
		});

		SolidFixture fixture = new SolidFixture(this, bullet, 0);
		fixture.setRestitution(1); // Bouncy bullets!
		fixture.setFriction(0);

		addImage(ballImage);
	}
}
