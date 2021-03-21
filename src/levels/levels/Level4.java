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
public class Level4 extends Level {
	// Z position for lives
	private final static Vec3[] bricks = {
			new Vec3(-4, 6.5f, 4),	new Vec3(0, 6.5f, 4),		new Vec3(4, 6.5f, 4),
			new Vec3(-4, 5.5f, 4),	new Vec3(0, 5.5f, 4),		new Vec3(4, 5.5f, 3),
			new Vec3(-4, 4.5f, 4),	new Vec3(4, 4.5f, 4),
			new Vec3(-4, 1.5f, 2),	new Vec3(4, 1.5f, 4),
			new Vec3(-4, 0.5f, 4),	new Vec3(0, 0.5f, 4),		new Vec3(4, 0.5f, 2),
			new Vec3(-4, -0.5f, 3),	new Vec3(0, -0.5f, 3),	new Vec3(4, -0.5f, 2),
	};

	public Level4(Pad pad, final JLabel levelLabel) {
		super(bricks, pad, new Vec2(-4, -6), 4);
	}
}
