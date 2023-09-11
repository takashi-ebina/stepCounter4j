package co.jp.stepCounter.presentation.view.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

public class RowSelectionTree extends JTree {
	private static final Color SELECTED_COLOR = new Color(0x64_96_C8);
	private transient TreeWillExpandListener listener;

	@Override
	protected void paintComponent(Graphics g) {
		int[] sr = getSelectionRows();
		if (sr == null) {
			super.paintComponent(g);
			return;
		}

		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setPaint(SELECTED_COLOR);
		Arrays.stream(sr).mapToObj(this::getRowBounds).forEach(r -> g2.fillRect(0, r.y, getWidth(), r.height));
		super.paintComponent(g);
		if (hasFocus()) {
			Optional.ofNullable(getLeadSelectionPath()).ifPresent(path -> {
				Rectangle r = getRowBounds(getRowForPath(path));
				g2.setPaint(SELECTED_COLOR.darker());
				g2.drawRect(0, r.y, getWidth() - 1, r.height - 1);
			});
		}
		g2.dispose();
	}

	@Override
	public void updateUI() {
		setCellRenderer(null);
		removeTreeWillExpandListener(listener);
		super.updateUI();
		setUI(new BasicTreeUI() {
			@Override
			public Rectangle getPathBounds(JTree tree, TreePath path) {
				if (Objects.nonNull(tree) && Objects.nonNull(treeState)) {
					return getTreePathBounds(path, tree.getInsets(), new Rectangle());
				}
				return null;
			}

			private Rectangle getTreePathBounds(TreePath path, Insets insets, Rectangle bounds) {
				Rectangle rect = treeState.getBounds(path, bounds);
				if (Objects.nonNull(rect)) {
					rect.width = tree.getWidth();
					rect.y += insets.top;
				}
				return rect;
			}
		});
		UIManager.put("Tree.repaintWholeRow", Boolean.TRUE);
		TreeCellRenderer r = getCellRenderer();
		setCellRenderer((tree, value, selected, expanded, leaf, row, hasFocus) -> {
			Component c = r.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			c.setBackground(selected ? SELECTED_COLOR : tree.getBackground());
			if (c instanceof JComponent) {
				((JComponent) c).setOpaque(true);
			}
			return c;
		});
		setOpaque(false);
		setRootVisible(false);
		listener = new TreeWillExpandListener() {
			@Override
			public void treeWillExpand(TreeExpansionEvent e) {
				// throw new ExpandVetoException(e, "Tree expansion cancelled");
			}

			@Override
			public void treeWillCollapse(TreeExpansionEvent e) throws ExpandVetoException {
				throw new ExpandVetoException(e, "Tree collapse cancelled");
			}
		};
		addTreeWillExpandListener(listener);
	}
}
