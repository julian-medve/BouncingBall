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
public class Level1 extends Level {
	// Z position for lives
	private final static Vec3[] bricks = {
			new Vec3(-2, 6.5f, 1), 	new Vec3(2, 6.5f, 1),
			new Vec3(-4f, 5.5f, 1),	new Vec3(4f, 5.5f, 1),
			new Vec3(-4f, 3.5f, 1),	new Vec3(4f, 3.5f, 1),
			new Vec3(-4f, 2.5f, 1),	new Vec3(4f, 2.5f, 1),
			new Vec3(-4f, 0.5f, 1),	new Vec3(4f, 0.5f, 1),
			new Vec3(-2, -0.5f, 1), 	new Vec3(2, -0.5f, 1),
	};

	public Level1(Pad pad, final JLabel levelLabel) {
		super(bricks, pad, new Vec2(-4, -6), 1);

		// Display instructions
//		this.addEventListener(new LevelEventListener() {
//			Font oldFont;
//			String oldText;
//
////			@Override
////			public void levelStart() {
////				oldFont = levelLabel.getFont();
////				oldText = levelLabel.getText();
////
////				Font defaultFont = UIManager.getDefaults().getFont("TabbedPane.font");
////				levelLabel.setFont(new Font(defaultFont.getFontName(), defaultFont.getStyle(), 24));
////				levelLabel.setText("<html><center>" +
////						"Use the arrow keys to move and space to start.<br>");
////				levelLabel.setVisible(true);
////			}
////
////			@Override
////			public void levelComplete() {
//////				levelLabel.setFont(oldFont);
//////				levelLabel.setText(oldText);
////			}
//
//			@Override
//			public void levelLose() {
//
//			}
//		});
	}
}
