package co.jp.stepCounter.presentation.view.custom;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class EmptyIcon implements Icon {

	  @Override public void paintIcon(Component c, Graphics g, int x, int y) {
		    /* Empty icon */
		  }

		  @Override public int getIconWidth() {
		    return 0;
		  }

		  @Override public int getIconHeight() {
		    return 0;
		  }

}
