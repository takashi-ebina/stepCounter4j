package co.jp.stepCounter.presentation.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import co.jp.stepCounter.presentation.controller.gui.StepCounterGuiMainController;
import co.jp.stepCounter.presentation.controller.gui.StepCounterGuiRequestDto;

/**
 * <p>
 * ステップ数の集計を実施するGUIクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterGuiMainView extends JFrame implements ActionListener {

	private JPanel jContentPane = null;
	private JPanel jMainContentPane = null;
	private JPanel jHeaderContentPane = null;
	
	private JPanel jp1 = null;
	private JPanel jp2 = null;
	private JPanel jp3 = null;
	private JPanel jp4 = null;
	
	private JTextField input = null;
	private JButton jSelButton = null;
	private JTextField input2 = null;
	private JButton jSelButton2 = null;
	
	private JButton jStartButton = null;
	private JButton jExitButton = null;
	
	private JPanel jSortTypeRadioButton = null;
	private JPanel jSortTargetRadioButton = null;
	
	private ButtonGroup sortTypeRadioGroup = null;
	private ButtonGroup sortTargetRadioGroup = null;
	
	private final String TITLE_IMG_PATH = "./src/main/resources/img/icon_ebi.png";
	
	private final StepCounterGuiMainController controller;

	/**
	 * コンストラクタ
	 */
	public StepCounterGuiMainView() {
		this.controller = new StepCounterGuiMainController();
		init();
	}

	/**
	 * <p>
	 * 初期化処理
	 */
	private void init() {
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("ステップ数集計ツール");
		this.setSize(750, 375);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * <p>
	 * メインパネル（ヘッダー部 + メイン部）の生成処理
	 * 
	 * @return メインパネル（ヘッダー部 + メイン部）
	 */
	private JPanel getJContentPane() {
		if (this.jContentPane == null) {
			this.jContentPane = new JPanel();
			this.jContentPane.add(getJContentHeaderPane());
			this.jContentPane.add(getJContentMainPane());
		}
		return this.jContentPane;
	}

	/**
	 * <p>
	 * メインパネル（ヘッダー部）の生成処理
	 * 
	 * @return メインパネル（ヘッダー部）
	 */
	private JPanel getJContentHeaderPane() {
		if (this.jHeaderContentPane == null) {
			this.jHeaderContentPane = new JPanel();
			ImageIcon icon1 = new ImageIcon(TITLE_IMG_PATH);
			JLabel label1 = new JLabel(icon1);
			this.jHeaderContentPane.add(label1, null);
		}
		return this.jHeaderContentPane;
	}

	/**
	 * <p>
	 * メインパネル（メイン部）の生成処理
	 * 
	 * @return メインパネル（メイン部）
	 */
	private JPanel getJContentMainPane() {
		if (this.jMainContentPane == null) {
			this.jMainContentPane = new JPanel();
			this.jMainContentPane.setLayout(new GridLayout(5, 1, 4, 4));
			this.jMainContentPane.add(getJp1(), null);
			this.jMainContentPane.add(getJp2(), null);
			this.jMainContentPane.add(getJp3(), null);
			this.jMainContentPane.add(getJp4(), null);
		}
		return this.jMainContentPane;
	}

	/**
	 * <p>
	 * ファイル入力選択
	 * 
	 * @return ファイル入力選択
	 */
	public JPanel getJp1() {
		if (this.jp1 == null) {
			this.jp1 = new JPanel();
			this.jp1.setLayout(new BorderLayout());
			this.jp1.add(new JLabel("入力フォルダ"), BorderLayout.WEST);
			this.jp1.add(getInput(), BorderLayout.CENTER);
			this.jp1.add(getSelButton(), BorderLayout.EAST);
		}
		return this.jp1;
	}

	/**
	 * <p>
	 * ファイルインプット（ディレクトリ）
	 * 
	 * @return ファイルインプット（ディレクトリ）
	 */
	public JTextField getInput() {
		if (this.input == null) {
			this.input = new JTextField();
			File f = new File(".").getAbsoluteFile().getParentFile();
			this.input.setText(f.getPath());
		}
		return this.input;
	}

	/**
	 * <p>
	 * ファイル選択ボタン
	 * 
	 * @return ファイル選択ボタン
	 */
	public JButton getSelButton() {
		if (this.jSelButton == null) {
			this.jSelButton = new JButton();
			this.jSelButton.setText("...");
			this.jSelButton.addActionListener(this);
			this.jSelButton.setActionCommand("sel1");
		}
		return this.jSelButton;
	}

	/**
	 * <p>
	 * 結果出力選択
	 * 
	 * @return 結果出力選択
	 */
	public JPanel getJp2() {
		if (this.jp2 == null) {
			this.jp2 = new JPanel();
			this.jp2.setLayout(new BorderLayout());
			this.jp2.add(new JLabel("出力ファイル"), BorderLayout.WEST);
			this.jp2.add(getInput2(), BorderLayout.CENTER);
			this.jp2.add(getSelButton2(), BorderLayout.EAST);
		}
		return this.jp2;
	}

	/**
	 * <p>
	 * ファイルインプット（ファイル）
	 * 
	 * @return ファイルインプット（ファイル）
	 */
	public JTextField getInput2() {
		if (this.input2 == null) {
			this.input2 = new JTextField();
			File f = new File(".").getAbsoluteFile().getParentFile();
			this.input2.setText(f.getPath());
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
		}
		return this.jSelButton2;
	}

	/**
	 * <p>
	 * ラジオボタン
	 * 
	 * @return ラジオボタン
	 */
	public JPanel getJp3() {
		if (this.jp3 == null) {
			this.jp3 = new JPanel();
			this.jp3.setLayout(new BorderLayout());
			this.jp3.add(getSortTypeRadioButton(), BorderLayout.WEST);
			this.jp3.add(getSortTargetRadioButton(), BorderLayout.CENTER);
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
			this.jSortTypeRadioButton.setBorder(BorderFactory.createTitledBorder("ソート区分"));
			JRadioButton sortTypeRadioFromNoSort = 
					new JRadioButton(SortType.NO_SORT.getSortTypeName(), true);
			JRadioButton sortTypeRadioFromAscOrder = 
					new JRadioButton(SortType.ASCENDING_ORDER.getSortTypeName());
			JRadioButton sortTypeRadioFromDesOrder = 
					new JRadioButton(SortType.DESCENDING_ORDER.getSortTypeName());
			sortTypeRadioFromNoSort.setActionCommand(SortType.NO_SORT.getSortTypeName());
			sortTypeRadioFromAscOrder.setActionCommand(SortType.ASCENDING_ORDER.getSortTypeName());
			sortTypeRadioFromDesOrder.setActionCommand(SortType.DESCENDING_ORDER.getSortTypeName());
			this.sortTypeRadioGroup = new ButtonGroup();
			this.sortTypeRadioGroup.add(sortTypeRadioFromNoSort);
			this.sortTypeRadioGroup.add(sortTypeRadioFromAscOrder);
			this.sortTypeRadioGroup.add(sortTypeRadioFromDesOrder);
			this.jSortTypeRadioButton.add(sortTypeRadioFromNoSort);
			this.jSortTypeRadioButton.add(sortTypeRadioFromAscOrder);
			this.jSortTypeRadioButton.add(sortTypeRadioFromDesOrder);
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
		if (this.jSortTargetRadioButton == null) {
			this.jSortTargetRadioButton = new JPanel();
			jSortTargetRadioButton.setBorder(BorderFactory.createTitledBorder("ソート対象"));
			JRadioButton sortTargetRadioFromFilePath = 
					new JRadioButton(SortTarget.FILEPATH.getSortTargetName(), true);
			JRadioButton sortTargetRadioFromTotalStep = 
					new JRadioButton(SortTarget.TOTALSTEPCOUNT.getSortTargetName());
			JRadioButton sortTargetRadioFromExecStep = 
					new JRadioButton(SortTarget.EXECSTEPCOUNT.getSortTargetName());
			JRadioButton sortTargetRadioFromCommentStep = 
					new JRadioButton(SortTarget.COMMENTSTEPCOUNT.getSortTargetName());
			JRadioButton sortTargetRadioFromEmptyStep = 
					new JRadioButton(SortTarget.EMPTYSTEPCOUNT.getSortTargetName());
			sortTargetRadioFromFilePath.setActionCommand(SortTarget.FILEPATH.getSortTargetName());
			sortTargetRadioFromTotalStep.setActionCommand(SortTarget.TOTALSTEPCOUNT.getSortTargetName());
			sortTargetRadioFromExecStep.setActionCommand(SortTarget.EXECSTEPCOUNT.getSortTargetName());
			sortTargetRadioFromCommentStep.setActionCommand(SortTarget.COMMENTSTEPCOUNT.getSortTargetName());
			sortTargetRadioFromEmptyStep.setActionCommand(SortTarget.EMPTYSTEPCOUNT.getSortTargetName());
			this.sortTargetRadioGroup = new ButtonGroup();
			this.sortTargetRadioGroup.add(sortTargetRadioFromFilePath);
			this.sortTargetRadioGroup.add(sortTargetRadioFromTotalStep);
			this.sortTargetRadioGroup.add(sortTargetRadioFromExecStep);
			this.sortTargetRadioGroup.add(sortTargetRadioFromCommentStep);
			this.sortTargetRadioGroup.add(sortTargetRadioFromEmptyStep);
			this.jSortTargetRadioButton.add(sortTargetRadioFromFilePath);
			this.jSortTargetRadioButton.add(sortTargetRadioFromTotalStep);
			this.jSortTargetRadioButton.add(sortTargetRadioFromExecStep);
			this.jSortTargetRadioButton.add(sortTargetRadioFromCommentStep);
			this.jSortTargetRadioButton.add(sortTargetRadioFromEmptyStep);
		}
		return this.jSortTargetRadioButton;
	}

	/**
	 * <p>
	 * ボタン
	 * 
	 * @return ボタン
	 */
	public JPanel getJp4() {
		if (this.jp4 == null) {
			this.jp4 = new JPanel();
			this.jp4.setLayout(new GridLayout(1, 2, 8, 8));
			this.jp4.add(getStartButton(), null);
			this.jp4.add(getExitButton(), null);
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
			this.jStartButton.setText("開始");
			this.jStartButton.addActionListener(this);
			this.jStartButton.setActionCommand("start");
		}
		return this.jStartButton;
	}

	/**
	 * <p>
	 * 終了ボタン
	 * 
	 * @return 終了ボタン
	 */
	public JButton getExitButton() {
		if (this.jExitButton == null) {
			this.jExitButton = new JButton();
			this.jExitButton.setText("終了");
			this.jExitButton.addActionListener(this);
			this.jExitButton.setActionCommand("exit");
		}
		return this.jExitButton;
	}

	/**
	 * <p>
	 * アクション監視
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final String cmd = e.getActionCommand();
		if ("exit".equals(cmd)) {
			this.dispose();
			System.exit(0);
		}
		
		try {
			buttonAble(false);
			if ("sel1".equals(cmd)) {
				directoriesSelect(this.getInput());
			} else if ("sel2".equals(cmd)) {
				fileSelect(this.getInput2());
			} else if ("start".equals(cmd)) {
				this.controller.stepCountGuiMode(makeStepCounterGuiRequestDto(), this);
			}
		} finally {
			buttonAble(true);
		}
	}
	
	/**
	 * <p>
	 * GUIでステップカウント処理を実行する際に利用するDTOオブジェクトの作成メソッド
	 * 
	 * @return GUIでステップカウント処理を実行する際に利用するDTOオブジェクト
	 */
	private StepCounterGuiRequestDto makeStepCounterGuiRequestDto() {
		final SortType sortType = 
				SortType.lookup(this.sortTypeRadioGroup.getSelection().getActionCommand(), SortType::getSortTypeName);
		final SortTarget sortTarget = 
				SortTarget.lookup(this.sortTargetRadioGroup.getSelection().getActionCommand(), SortTarget::getSortTargetName);
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
	protected void buttonAble(boolean flg) {
		this.getStartButton().setEnabled(flg);
		this.getSelButton().setEnabled(flg);
		this.getSelButton2().setEnabled(flg);
		this.getExitButton().setEnabled(flg);
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

}
