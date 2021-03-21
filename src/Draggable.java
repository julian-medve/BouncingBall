import city.cs.engine.UserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


/**
 * Makes a frame draggable.
 */
public class Draggable {
	public static void makeDraggable(final JFrame frame, final UserView view, final Point frameLocation) {
		final Point last = new Point(0, 0);

		view.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);

				if (last.x > 0 && last.y > 0) {
					frameLocation.x += e.getXOnScreen() - last.x;
					frameLocation.y += e.getYOnScreen() - last.y;
					frame.setLocation(frameLocation);
				}

				last.x = e.getXOnScreen();
				last.y = e.getYOnScreen();
			}
		});

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseClicked(e);

				last.x = 0;
				last.y = 0;
			}
		});
	}
}
