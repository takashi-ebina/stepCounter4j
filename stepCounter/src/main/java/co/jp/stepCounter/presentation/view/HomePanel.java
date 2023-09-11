package co.jp.stepCounter.presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import co.jp.stepCounter.presentation.controller.gui.StepCounterGuiMainController;
import co.jp.stepCounter.presentation.controller.gui.StepCounterGuiRequestDto;
import co.jp.stepCounter.presentation.view.custom.JPlaceholderTextField;
/**
 * <p>
 * ホームパネルのクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class HomePanel extends JPanel implements ActionListener {

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
	/** GridBagLayout */
	private final GridBagLayout gbl = new GridBagLayout();
	/** GridBagConstraints */
	private final GridBagConstraints gbc = new GridBagConstraints();
	/** ボタンの背景色 */
	private final Color BACKGROUND_BUTTON_COLOR = new Color(240, 128, 128);
	/** ボタンの前景色 */
	private final Color FOREGROUND_BUTTON_COLOR = new Color(248, 248, 255);

	/** コントローラー */
	private final StepCounterGuiMainController controller = new StepCounterGuiMainController();
	/** タイトルロゴ */
	private final String TITLE_IMG_PATH = "/img/icon_ebi.png";

	/**
	 * <p>
	 * コンストラクタ
	 */
	public HomePanel() {
		init();
	}
	/**
	 * <p>
	 * 初期化処理
	 */
	public void init() {
		this.add(getJContentHeaderPane());
		this.add(getJp1());
		this.add(getJp2());
		this.add(getJp3());
		this.add(getJp4());
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(750, 440));
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
			this.jHeaderContentPane.add(new JLabel(ViewUtil.getImageIcon(TITLE_IMG_PATH, this)), null);
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

}
