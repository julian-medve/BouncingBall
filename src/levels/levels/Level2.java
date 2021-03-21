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
public class Level2 extends Level {
	// Z position for lives
	private final static Vec3[] bricks = {
			new Vec3(0, 5.5f, 2),
			new Vec3(-2f, 4.5f, 2),	new Vec3(0f, 4.5f, 2), new Vec3(2f, 4.5f, 2),
			new Vec3(-4f, 3.5f, 2),	new Vec3(-2f, 3.5f, 2),new Vec3(0f, 3.5f, 4), new Vec3(2f, 3.5f, 2), new Vec3(4f, 3.5f, 2),
			new Vec3(-2f, 2.5f, 2),	new Vec3(0f, 2.5f, 2), new Vec3(2f, 2.5f, 2),
			new Vec3(0, 1.5f, 2),
	};

	public Level2(Pad pad, final JLabel levelLabel) {
		super(bricks, pad, new Vec2(-4, -6), 2);
	}
}
