package levels.levels;

import levels.Level;
import objects.Pad;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;

import javax.swing.*;

/**
 * A level. Would be better in Levels.java, but requirements.
 */
public class Level3 extends Level {
	// Z position for lives
	private final static Vec3[] bricks = {
			new Vec3(-4, 6.5f, 2), 	new Vec3(-2, 6.5f, 1),	new Vec3(2, 6.5f, 1),	new Vec3(4, 6.5f, 1),
			new Vec3(-4, 5.5f, 1),	new Vec3(-2, 5.5f, 1), 	new Vec3(2, 5.5f, 3),	new Vec3(4, 5.5f, 1),
			new Vec3(-4, 3.5f, 3),	new Vec3(-2, 3.5f, 1),	new Vec3(2, 3.5f, 2),	new Vec3(4, 3.5f, 1),
			new Vec3(-4, 2.5f, 1),	new Vec3(-2, 2.5f, 1),	new Vec3(2, 2.5f, 1),  new Vec3(4, 2.5f, 1),
			new Vec3(-4, 0.5f, 1),	new Vec3(-2, 0.5f, 2),	new Vec3(2, 0.5f, 1), new Vec3(4, 0.5f, 1),
			new Vec3(-4, -0.5f, 1),	new Vec3(-2, -0.5f, 1),	new Vec3(2, -0.5f, 1),	new Vec3(4, -0.5f, 1),
	};

	public Level3(Pad pad, final JLabel levelLabel) {
		super(bricks, pad, new Vec2(-4, -6), 3);
	}
}
