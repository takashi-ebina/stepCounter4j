package co.jp.stepCounter.presentation.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageProducer;
import java.io.File;
import java.net.URL;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import co.jp.stepCounter.infrastructure.log.Log4J2;
import co.jp.stepCounter.presentation.controller.gui.StepCounterGuiMainController;
import co.jp.stepCounter.presentation.controller.gui.StepCounterGuiRequestDto;
import co.jp.stepCounter.presentation.view.custom.EmptyIcon;
import co.jp.stepCounter.presentation.view.custom.JPlaceholderTextField;
import co.jp.stepCounter.presentation.view.custom.RowSelectionTree;

/**
 * <p>
 * ステップ数の集計を実施するGUIクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterGuiMainView extends JFrame implements ActionListener {
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
	/** ホームヘッダー */
	private JPanel jHeaderContentPane = null;
	/** ホームパネル１（入力フォルダ） */
	private JPanel jp1 = null;
	/** ホームパネル２(結果出力選択) */
	private JPanel jp2 = null;
	/** ホームパネル３（ラジオボタン） */
	private JPanel jp3 = null;
	/** ホームパネル４（ボタン） */
	private JPanel jp4 = null;
	/** 入力フォルダテキストボックス */
	private JTextField input = null;
	/** 入力フォルダ選択ボタン */
	private JButton jSelButton = null;
	/** 出力ファイルテキストボックス */
	private JTextField input2 = null;
	/** 出力ファイル選択ボタン */
	private JButton jSelButton2 = null;
	/** ソート区分ラジオボタン */
	private JPanel jSortTypeRadioButton = null;
	/** ソート対象ラジオボタン */
	private JPanel jSortTargetRadioButton = null;
	/** ソート区分ラジオグループ */
	private ButtonGroup sortTypeRadioGroup = null;
	/** ソート対象ラジオグループ */
	private ButtonGroup sortTargetRadioGroup = null;
	/** 開始ボタン */
	private JButton jStartButton = null;
	
	/** 情報パネル */
	private JPanel jInfoPane = null;
	
	/** GridBagLayout */
	private final GridBagLayout gbl = new GridBagLayout();
	/** GridBagConstraints */
	private final GridBagConstraints gbc = new GridBagConstraints();
	/** タイトルロゴ */
	private final String TITLE_IMG_PATH = "/img/icon_ebi.png";
	/** フレームロゴ */
	private final String FRAME_IMG_PATH = "/img/frame_icon_ebi.png";
	/** コントローラー */
	private final StepCounterGuiMainController controller = new StepCounterGuiMainController();
	/** メインパネルのグラデーション（START） */
	private final Color CONPANE_GRADATION_START_COLOR = new Color(255, 240, 245);
	/** メインパネルのグラデーション（END） */
	private final Color CONPANE_GRADATION_END_COLOR = new Color(255, 192, 203);
	/** ボタンの背景色 */
	private final Color BACKGROUND_BUTTON_COLOR = new Color(240, 128, 128);
	/** ボタンの前景色 */
	private final Color FOREGROUND_BUTTON_COLOR = new Color(248, 248, 255);

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
		this.setIconImage(getImageIcon(FRAME_IMG_PATH).getImage());
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
							g.setColor(BACKGROUND_BUTTON_COLOR);
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
			tree.setBackground(BACKGROUND_BUTTON_COLOR);
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
			
			this.jSideMenuPane.setBackground(BACKGROUND_BUTTON_COLOR);
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
			this.jInfoPane = new JPanel();
			String strHtml = 
					"<html><h1><font>stepcounterについて</font></h1>"
					+ "<p>プログラムのステップ数を集計するツールです。</p>"
					+ "<br>"
					+ "<p>作成者：takashi.ebina<br>"
					+ "GitHubリポジトリ：https://github.com/takashi-ebina/stepCounterforJava/</p>"
					+ "<h2>対応プログラムファイル</h2>"
					+ "<ul><li>Java</li><li>Cs</li><li>sql</li></ul></p>"
					+ "<h2>変更履歴</h2>"
					+ "<h3>1.0.0</h3>"
					+ "新規リリース"
					;
			JLabel l = new JLabel();
			l.setText(strHtml);

			this.jInfoPane.add(l);
			this.jInfoPane.setOpaque(false);
			this.jInfoPane.setPreferredSize(new Dimension(750, 440));
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
			this.jHomePane = new JPanel();
			this.jHomePane.add(getJContentHeaderPane());
			this.jHomePane.add(getJp1());
			this.jHomePane.add(getJp2());
			this.jHomePane.add(getJp3());
			this.jHomePane.add(getJp4());
			this.jHomePane.setOpaque(false);
			this.jHomePane.setPreferredSize(new Dimension(750, 440));
		}
		return this.jHomePane;
	}

	/**
	 * <p>
	 * ホームパネル（ヘッダー部）の生成処理
	 * 
	 * @return ホームパネル（ヘッダー部）
	 */
	private JPanel getJContentHeaderPane() {
		if (this.jHeaderContentPane == null) {
			this.jHeaderContentPane = new JPanel();
			this.jHeaderContentPane.add(new JLabel(getImageIcon(TITLE_IMG_PATH)), null);
			this.jHeaderContentPane.setOpaque(false);
			setGridBagLayout(this.jHeaderContentPane, GridBagConstraints.BOTH, 0, 0, 1, 1);
		}
		return this.jHeaderContentPane;
	}

	/**
	 * <p>
	 * ホームパネル１（入力フォルダ）
	 * 
	 * @return ホームパネル１（入力フォルダ）
	 */
	public JPanel getJp1() {
		if (this.jp1 == null) {
			this.jp1 = new JPanel();
			this.jp1.add(getInput(), BorderLayout.CENTER);
			this.jp1.add(getSelButton(), BorderLayout.EAST);
			this.jp1.setOpaque(false);
			this.jp1.setPreferredSize(new Dimension(750, 70));
		}
		return this.jp1;
	}

	/**
	 * <p>
	 * テキストフィールド（フォルダ）
	 * 
	 * @return テキストフィールド（フォルダ）
	 */
	public JTextField getInput() {
		if (this.input == null) {
			this.input = new JPlaceholderTextField("入力フォルダ");
			this.input.setOpaque(false);
			File f = new File(".").getAbsoluteFile().getParentFile();
			this.input.setText(f.getPath());
			this.input.setPreferredSize(new Dimension(650, 50));
			setGridBagLayout(this.input, GridBagConstraints.BOTH, 0, 1, 1, 1);
		}
		return this.input;
	}

	/**
	 * <p>
	 * フォルダ選択ボタン
	 * 
	 * @return フォルダ選択ボタン
	 */
	public JButton getSelButton() {
		if (this.jSelButton == null) {
			this.jSelButton = new JButton();
			this.jSelButton.setText("...");
			this.jSelButton.addActionListener(this);
			this.jSelButton.setActionCommand("sel1");
			this.jSelButton.setPreferredSize(new Dimension(50, 50));
			setGridBagLayout(this.jSelButton, GridBagConstraints.BOTH, 1, 1, 1, 1);
		}
		return this.jSelButton;
	}

	/**
	 * <p>
	 * ホームパネル２（出力ファイル）
	 * 
	 * @return ホームパネル２（出力ファイル）
	 */
	public JPanel getJp2() {
		if (this.jp2 == null) {
			this.jp2 = new JPanel();
			this.jp2.add(getInput2(), BorderLayout.CENTER);
			this.jp2.add(getSelButton2(), BorderLayout.EAST);
			this.jp2.setOpaque(false);
			this.jp2.setPreferredSize(new Dimension(750, 70));
		}
		return this.jp2;
	}

	/**
	 * <p>
	 * テキストフィールド（ファイル）
	 * 
	 * @return テキストフィールド（ファイル）
	 */
	public JTextField getInput2() {
		if (this.input2 == null) {
			this.input2 = new JPlaceholderTextField("出力ファイル");
			File f = new File(".").getAbsoluteFile().getParentFile();
			this.input2.setText(f.getPath());
			this.input2.setPreferredSize(new Dimension(650, 50));
			this.input2.setToolTipText("拡張子はCSVを指定してください");
			setGridBagLayout(this.input2, GridBagConstraints.BOTH, 0, 2, 1, 1);
		}
		return this.input2;
	}

	/**
	 * <p>
	 * ファイル選択ボタン
	 * 
	 * @return ファイル選択ボタン
	 */
	public JButton getSelButton2() {
		if (this.jSelButton2 == null) {
			this.jSelButton2 = new JButton();
			this.jSelButton2.setText("...");
			this.jSelButton2.addActionListener(this);
			this.jSelButton2.setActionCommand("sel2");
			this.jSelButton2.setPreferredSize(new Dimension(50, 50));
			setGridBagLayout(this.jSelButton2, GridBagConstraints.BOTH, 1, 2, 1, 1);
		}
		return this.jSelButton2;
	}

	/**
	 * <p>
	 * ホームパネル３（ラジオボタン）
	 * 
	 * @return ホームパネル３（ラジオボタン）
	 */
	public JPanel getJp3() {
		if (this.jp3 == null) {
			this.jp3 = new JPanel();
			this.jp3.add(getSortTypeRadioButton(), BorderLayout.WEST);
			this.jp3.add(getSortTargetRadioButton(), BorderLayout.CENTER);
			this.jp3.setOpaque(false);
			this.jp3.setPreferredSize(new Dimension(750, 70));
			setGridBagLayout(this.jp3, GridBagConstraints.BOTH, 0, 3, 1, 1);
		}
		return this.jp3;
	}

	/**
	 * <p>
	 * ソート区分ラジオボタン
	 * 
	 * @return ソート区分ラジオボタン
	 */
	public JPanel getSortTypeRadioButton() {
		if (this.jSortTypeRadioButton == null && this.sortTypeRadioGroup == null) {
			this.jSortTypeRadioButton = new JPanel();
			// ラジオボタンのタイトル
			setRadioButtonTitle(this.jSortTypeRadioButton, "ソート区分");
			// 各ラジオボタンの生成
			JRadioButton sortTypeRadioFromNoSort = new JRadioButton(SortType.NO_SORT.getSortTypeName(), true);
			JRadioButton sortTypeRadioFromAscOrder = new JRadioButton(SortType.ASCENDING_ORDER.getSortTypeName());
			JRadioButton sortTypeRadioFromDesOrder = new JRadioButton(SortType.DESCENDING_ORDER.getSortTypeName());
			// 各ラジオボタンのアクション
			sortTypeRadioFromNoSort.setActionCommand(SortType.NO_SORT.getSortTypeName());
			sortTypeRadioFromAscOrder.setActionCommand(SortType.ASCENDING_ORDER.getSortTypeName());
			sortTypeRadioFromDesOrder.setActionCommand(SortType.DESCENDING_ORDER.getSortTypeName());
			sortTypeRadioFromNoSort.setOpaque(false);
			sortTypeRadioFromAscOrder.setOpaque(false);
			sortTypeRadioFromDesOrder.setOpaque(false);
			// ラジオボタンのグルーピング
			this.sortTypeRadioGroup = new ButtonGroup();
			this.sortTypeRadioGroup.add(sortTypeRadioFromNoSort);
			this.sortTypeRadioGroup.add(sortTypeRadioFromAscOrder);
			this.sortTypeRadioGroup.add(sortTypeRadioFromDesOrder);
			// ラジオボタンの追加
			this.jSortTypeRadioButton.add(sortTypeRadioFromNoSort);
			this.jSortTypeRadioButton.add(sortTypeRadioFromAscOrder);
			this.jSortTypeRadioButton.add(sortTypeRadioFromDesOrder);
			this.jSortTypeRadioButton.setOpaque(false);
		}
		return this.jSortTypeRadioButton;
	}

	/**
	 * <p>
	 * ソート対象ラジオボタン
	 * 
	 * @return ソート対象ラジオボタン
	 */
	public JPanel getSortTargetRadioButton() {
		if (this.jSortTargetRadioButton == null && this.sortTargetRadioGroup == null) {
			this.jSortTargetRadioButton = new JPanel();
			// ラジオボタンのタイトル
			setRadioButtonTitle(this.jSortTargetRadioButton, "ソート対象");
			// 各ラジオボタンの生成
			JRadioButton sortTargetRadioFromFilePath = new JRadioButton(SortTarget.FILEPATH.getSortTargetName(), true);
			JRadioButton sortTargetRadioFromTotalStep = new JRadioButton(SortTarget.TOTALSTEPCOUNT.getSortTargetName());
			JRadioButton sortTargetRadioFromExecStep = new JRadioButton(SortTarget.EXECSTEPCOUNT.getSortTargetName());
			JRadioButton sortTargetRadioFromCommentStep = new JRadioButton(
					SortTarget.COMMENTSTEPCOUNT.getSortTargetName());
			JRadioButton sortTargetRadioFromEmptyStep = new JRadioButton(SortTarget.EMPTYSTEPCOUNT.getSortTargetName());
			// 各ラジオボタンのアクション
			sortTargetRadioFromFilePath.setActionCommand(SortTarget.FILEPATH.getSortTargetName());
			sortTargetRadioFromTotalStep.setActionCommand(SortTarget.TOTALSTEPCOUNT.getSortTargetName());
			sortTargetRadioFromExecStep.setActionCommand(SortTarget.EXECSTEPCOUNT.getSortTargetName());
			sortTargetRadioFromCommentStep.setActionCommand(SortTarget.COMMENTSTEPCOUNT.getSortTargetName());
			sortTargetRadioFromEmptyStep.setActionCommand(SortTarget.EMPTYSTEPCOUNT.getSortTargetName());
			sortTargetRadioFromFilePath.setOpaque(false);
			sortTargetRadioFromTotalStep.setOpaque(false);
			sortTargetRadioFromExecStep.setOpaque(false);
			sortTargetRadioFromCommentStep.setOpaque(false);
			sortTargetRadioFromEmptyStep.setOpaque(false);
			// ラジオボタンのグルーピング
			this.sortTargetRadioGroup = new ButtonGroup();
			this.sortTargetRadioGroup.add(sortTargetRadioFromFilePath);
			this.sortTargetRadioGroup.add(sortTargetRadioFromTotalStep);
			this.sortTargetRadioGroup.add(sortTargetRadioFromExecStep);
			this.sortTargetRadioGroup.add(sortTargetRadioFromCommentStep);
			this.sortTargetRadioGroup.add(sortTargetRadioFromEmptyStep);
			// ラジオボタンの追加
			this.jSortTargetRadioButton.add(sortTargetRadioFromFilePath);
			this.jSortTargetRadioButton.add(sortTargetRadioFromTotalStep);
			this.jSortTargetRadioButton.add(sortTargetRadioFromExecStep);
			this.jSortTargetRadioButton.add(sortTargetRadioFromCommentStep);
			this.jSortTargetRadioButton.add(sortTargetRadioFromEmptyStep);
			this.jSortTargetRadioButton.setOpaque(false);
		}
		return this.jSortTargetRadioButton;
	}

	/**
	 * <p>
	 * ラジオボタンのタイトルレイアウト設定
	 * 
	 * @param target    コンポーネント対象
	 * @param titleName タイトル名
	 */
	private void setRadioButtonTitle(final JPanel target, final String titleName) {
		final Border outside = BorderFactory.createMatteBorder(0, 10, 2, 0, new Color(250, 128, 114));
		final Border inside = BorderFactory.createEmptyBorder(0, 5, 0, 0);
		target.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(outside, inside),
				titleName, TitledBorder.LEFT, TitledBorder.TOP, new Font(Font.SANS_SERIF, Font.BOLD, 14), Color.BLACK));
	}

	/**
	 * <p>
	 * ホームパネル４（ボタン）
	 * 
	 * @return ホームパネル４（ボタン）
	 */
	public JPanel getJp4() {
		if (this.jp4 == null) {
			this.jp4 = new JPanel();
			this.jp4.add(getStartButton(), null);
			this.jp4.setOpaque(false);
			this.jp4.setPreferredSize(new Dimension(300, 70));
			setGridBagLayout(this.jp4, GridBagConstraints.BOTH, 0, 4, 1, 1);
		}
		return this.jp4;
	}

	/**
	 * <p>
	 * 開始ボタン
	 * 
	 * @return 開始ボタン
	 */
	public JButton getStartButton() {
		if (this.jStartButton == null) {
			this.jStartButton = new JButton();
			this.jStartButton.addActionListener(this);
			this.jStartButton.setActionCommand("start");
			this.jStartButton.setText("count start !!");
			this.jStartButton.setFont(new Font("Arial", Font.PLAIN, 28));
			this.jStartButton.setForeground(FOREGROUND_BUTTON_COLOR);
			this.jStartButton.setBackground(BACKGROUND_BUTTON_COLOR);
			this.jStartButton.setPreferredSize(new Dimension(30, 30));
			this.jStartButton.setBorder(
					new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 228, 225), new Color(128, 128, 128)));
			this.jStartButton.setOpaque(true);
			this.jStartButton.setPreferredSize(new Dimension(300, 60));
		}
		return this.jStartButton;
	}

	/**
	 * <p>
	 * アクション監視
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final String cmd = e.getActionCommand();
		try {
			setButtonAble(false);
			switch (cmd) {
			case "sel1" -> directoriesSelect(this.getInput());
			case "sel2" -> fileSelect(this.getInput2());
			case "start" -> this.controller.stepCountGuiMode(makeStepCounterGuiRequestDto(), this);
			}
		} finally {
			setButtonAble(true);
		}
	}

	/**
	 * <p>
	 * GUIでステップカウント処理を実行する際に利用するDTOオブジェクトの作成メソッド
	 * 
	 * @return GUIでステップカウント処理を実行する際に利用するDTOオブジェクト
	 */
	private StepCounterGuiRequestDto makeStepCounterGuiRequestDto() {
		final SortType sortType = SortType.lookup(this.sortTypeRadioGroup.getSelection().getActionCommand(),
				SortType::getSortTypeName);
		final SortTarget sortTarget = SortTarget.lookup(this.sortTargetRadioGroup.getSelection().getActionCommand(),
				SortTarget::getSortTargetName);
		final String inputDirectoryPath = this.getInput().getText();
		final String outputFilePath = this.getInput2().getText();
		return new StepCounterGuiRequestDto(sortType, sortTarget, inputDirectoryPath, outputFilePath);
	}

	/**
	 * <p>
	 * ボタン抑止
	 * 
	 * @param flg ボタン抑止フラグ
	 */
	protected void setButtonAble(final boolean flg) {
		this.getStartButton().setEnabled(flg);
		this.getSelButton().setEnabled(flg);
		this.getSelButton2().setEnabled(flg);
	}

	/**
	 * <p>
	 * ファイル・ディレクトリ選択
	 * 
	 * @param text テキスト
	 */
	private void fileSelect(final JTextField text) {
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setCurrentDirectory(new File(text.getText()));

		final int selected = fc.showOpenDialog(this);
		if (selected == JFileChooser.APPROVE_OPTION) {
			text.setText(fc.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 * <p>
	 * ディレクトリ選択
	 * 
	 * @param text テキスト
	 */
	private void directoriesSelect(final JTextField text) {
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(text.getText()));

		final int selected = fc.showOpenDialog(this);
		if (selected == JFileChooser.APPROVE_OPTION) {
			text.setText(fc.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 * <p>
	 * コンポーネント設定
	 * 
	 * @param target     コンポーネント設定対象
	 * @param fill       初期 fill 値
	 * @param gridx      初期 gridx 値
	 * @param gridy      初期 gridy 値
	 * @param gridwidth  初期 gridwidth 値
	 * @param gridheight 初期 gridheight 値
	 */
	private void setGridBagLayout(JComponent target, int fill, int gridx, int gridy, int gridwidth, int gridheight) {
		this.gbc.fill = fill;
		this.gbc.gridx = gridx;
		this.gbc.gridy = gridy;
		this.gbc.gridwidth = gridwidth;
		this.gbc.gridheight = gridheight;
		this.gbl.setConstraints(target, gbc);
	}
	/**
	 * <p>
	 * ImageIcon取得
	 * 
	 * @param imageIconPath　画像のパス
	 * 
	 * @return ImageIconオブジェクト
	 */
	private ImageIcon getImageIcon(final String imageIconPath) {
		ImageIcon icon = null;
		try {
			final URL url = this.getClass().getResource(imageIconPath);
			final Image image = this.createImage((ImageProducer) url.getContent());
			icon = new ImageIcon(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icon;
	}
}
