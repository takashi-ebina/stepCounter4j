package co.jp.stepCounter.presentation.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import co.jp.stepCounter.infrastructure.log.Log4J2;
import co.jp.stepCounter.presentation.view.custom.EmptyIcon;
import co.jp.stepCounter.presentation.view.custom.RowSelectionTree;

/**
 * <p>
 * ステップ数の集計を実施するGUIクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterGuiMainView extends JFrame {
	/** 全体パネル */
	private JPanel jWholePane = null;
	/** サイドメニューとメインパネルを分割するパネル */
	private JSplitPane jSpritPane = null;
	/** メインパネル */
	private JPanel jMainPane = null;
	/** サイドメニューパネル */
	private JScrollPane jSideMenuPane = null;
	/** ホームパネル */
	private JPanel jHomePane = null;
	/** 情報パネル */
	private JPanel jInfoPane = null;
	
	/** フレームロゴ */
	private final String FRAME_IMG_PATH = "/img/frame_icon_ebi.png";
	/** メインパネルのグラデーション（START） */
	private final Color CONPANE_GRADATION_START_COLOR = new Color(255, 240, 245);
	/** メインパネルのグラデーション（END） */
	private final Color CONPANE_GRADATION_END_COLOR = new Color(255, 192, 203);
	/** 仕切線の色 */
	private final Color SPLIT_LINE_COLOR = new Color(240, 128, 128);
	/** サイドメニューの色 */
	private final Color SIDE_MENU_COLOR = new Color(240, 128, 128);

	/** Log4J2インスタンス */
	private final Log4J2 logger = Log4J2.getInstance();

	/**
	 * <p>
	 * コンストラクタ
	 */
	public StepCounterGuiMainView() {
		logger.logDebug("GUI-mode start ....");
		logger.logDebug("current LookAndFeel:" + UIManager.getLookAndFeel());
		init();
	}
	/**
	 * <p>
	 * 初期化処理
	 */
	private void init() {
		this.setIconImage(ViewUtil.getImageIcon(FRAME_IMG_PATH, this).getImage());
		this.setResizable(false);
		this.setContentPane(getJWholePane());
		this.setTitle("StepCounter");
		this.setSize(850, 430);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * <p>
	 * 全体パネルの生成処理
	 * 
	 * @return 全体パネル
	 */
	private JPanel getJWholePane() {
		if (this.jWholePane == null) {
			this.jWholePane = new JPanel(new BorderLayout(2, 2));
			this.jWholePane.add(getJSplitPane());
			this.jWholePane.setPreferredSize(new Dimension(100, 430));
		}
		return this.jWholePane;
	}
	
	/**
	 * <p>
	 * サイドメニューパネルとメインパネルを分割するパネルの生成処理
	 * 
	 * @return サイドメニューパネルとメインパネルを分割するパネル
	 */
	private JSplitPane getJSplitPane() {
		if (this.jSpritPane == null) {
			// DefaultTreeCellRendererに対して、
			// アイコンを表示させないようにしたり、文字に色を設定するなど
			// サイドメニューのレイアウトに関する設定を行なっている。
			final Icon emptyIcon = new EmptyIcon();
			UIManager.put("Tree.openIcon", emptyIcon);
			UIManager.put("Tree.closedIcon", emptyIcon);
			UIManager.put("Tree.leafIcon", emptyIcon);
			UIManager.put("Tree.expandedIcon", emptyIcon);
			UIManager.put("Tree.collapsedIcon", emptyIcon);
			UIManager.put("Tree.leftChildIndent", 10);
			UIManager.put("Tree.rightChildIndent", 0);
			UIManager.put("Tree.paintLines", false);
			UIManager.put("Tree.textForeground", Color.WHITE);
			
			// FIXME:jMainPaneのインタンスが生成されていないとgetJSideMenePaneがうまく動作しないので、
			// getJMainPane→getJSideMenePaneの順序で呼び出す必要がある。
			final CardLayout cardLayout = new CardLayout();
			getJMainPane(cardLayout);
			getJSideMenuPane(cardLayout);
			// サイドメニューパネルとメインパネルを分割するパネルの生成
			this.jSpritPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.jSideMenuPane, this.jMainPane);
			
			// 仕切線の色設定
			this.jSpritPane.setUI(new BasicSplitPaneUI() {
				public BasicSplitPaneDivider createDefaultDivider() {
					return new BasicSplitPaneDivider(this) {
						public void setBorder(Border b) {
						}
						@Override
						public void paint(Graphics g) {
							g.setColor(SPLIT_LINE_COLOR);
							g.fillRect(0, 0, getSize().width, getSize().height);
							super.paint(g);
						}
					};
				}
			});
			this.jSpritPane.setBorder(null);
		}
		return this.jSpritPane;
	}

	/**
	 * <p>
	 * サイドメニューパネルの生成処理
	 * 
	 * @return サイドメニューパネル
	 */
	private JScrollPane getJSideMenuPane(final CardLayout cardLayout) {
		if (this.jSideMenuPane == null) {
			final TreeModel model = makeModel();
			final JTree tree = new RowSelectionTree();
			tree.setModel(model);
			tree.setRowHeight(32);
			tree.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
			tree.setBackground(SIDE_MENU_COLOR);
			tree.setFont(new Font("Arial", Font.BOLD, 14));
			
			int row = 0;
			while (row < tree.getRowCount()) {
				tree.expandRow(row++);
			}
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			// サイドメニューとメインメニューを紐付けるイベント設定
			tree.addTreeSelectionListener(e -> {
				final Object o = e.getNewLeadSelectionPath().getLastPathComponent();
				if (o instanceof DefaultMutableTreeNode) {
					final DefaultMutableTreeNode node = (DefaultMutableTreeNode) o;
					final String title = Objects.toString(node.getUserObject());
					cardLayout.show(jMainPane, title);
				}
			});
			this.jSideMenuPane = new JScrollPane(tree);
			
			this.jSideMenuPane.setBackground(SIDE_MENU_COLOR);
			this.jSideMenuPane.setBorder(null);
		}
		return this.jSideMenuPane;
	}
	
	/**
	 * <p>
	 * メインパネルの生成処理
	 * 
	 * @return メインパネル
	 */
	private JPanel getJMainPane(CardLayout cardLayout) {
		if (this.jMainPane == null) {
			// CardLayoutかつ背景色がグラデーションのメインパネルを生成
			// メインパネル配下のパネルは、JComponent#setOpaque(false);で背景色を透明にすることで本レイアウトが適用させる
			this.jMainPane = new JPanel(cardLayout) {
				@Override
				protected void paintComponent(Graphics g) {
					Graphics2D g2d = (Graphics2D) g;
					Paint paint = new GradientPaint(0.0f, 0.0f, CONPANE_GRADATION_START_COLOR, 0.0f, getHeight(),
							CONPANE_GRADATION_END_COLOR);
					g2d.setPaint(paint);
					g2d.fillRect(0, 0, getWidth(), getHeight());
				}
			};
			this.jMainPane.add(getJHomePane(), "HOME");
			this.jMainPane.add(getJInfoPane(), "INFO");
		}
		return this.jMainPane;
	}
	
	/**
	 * <p>
	 * サイドメニューのツリーモデルの生成処理
	 * 
	 * @return ツリーモデル
	 */
	private DefaultTreeModel makeModel() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		DefaultMutableTreeNode c1 = new DefaultMutableTreeNode("HOME");
		DefaultMutableTreeNode c2 = new DefaultMutableTreeNode("INFO");
		root.add(c1);
		root.add(c2);
		return new DefaultTreeModel(root);
	}
	/**
	 * <p>
	 * 情報パネルの生成処理
	 * 
	 * @return 情報パネル
	 */
	private JPanel getJInfoPane() {
		if (this.jInfoPane == null ) {
			this.jInfoPane = new InfoPanel();
		}
		return this.jInfoPane;
	}
	/**
	 * <p>
	 * ホームパネル（ヘッダー部 + メイン部）の生成処理
	 * 
	 * @return メインパネル（ヘッダー部 + メイン部）
	 */
	private JPanel getJHomePane() {
		if (this.jHomePane == null) {
			this.jHomePane = new HomePanel();
		}
		return this.jHomePane;
	}

}
