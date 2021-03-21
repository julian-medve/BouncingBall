package levels.levels;

import levels.Level;
import levels.LevelEventListener;
import objects.Pad;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;

import javax.swing.*;
import java.awt.*;

/**
 * A level. Would be better in Levels.java, but requirements.
 */
public class Level5 extends Level {
	// Z position for lives
	private final static Vec3[] bricks = {
		new Vec3(-2, 6.5f, 4), 	new Vec3(0, 6.5f, 4),		new Vec3(2, 6.5f, 4),
		new Vec3(-4, 4.5f, 4), 	new Vec3(-2, 4.5f, 4),	new Vec3(2, 4.5f, 4),	new Vec3(4, 4.5f, 4),
		new Vec3(-4, 3.5f, 4),	new Vec3(4, 3.5f, 4),
		new Vec3(-4, 2.5f, 4),	new Vec3(4, 2.5f, 4),
		new Vec3(-4, 1.5f, 4),	new Vec3(-2, 1.5f, 4),	new Vec3(2, 1.5f, 4),	new Vec3(4, 1.5f, 4),
		new Vec3(-2, -0.5f, -1),	new Vec3(0, -0.5f, 4),	new Vec3(2, -0.5f, 4),
	};

	public Level5(Pad pad, final JLabel levelLabel) {
		super(bricks, pad, new Vec2(-4, -6), 5);
	}
}
