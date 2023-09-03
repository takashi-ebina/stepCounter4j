package co.jp.stepCounter.presentation.view;

import java.awt.BorderLayout;
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
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import co.jp.stepCounter.presentation.controller.gui.StepCounterGuiMainController;
import co.jp.stepCounter.presentation.controller.gui.StepCounterGuiRequestDto;
import co.jp.stepCounter.presentation.view.custom.JPlaceholderTextField;

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

	private JPanel jSortTypeRadioButton = null;
	private JPanel jSortTargetRadioButton = null;

	private ButtonGroup sortTypeRadioGroup = null;
	private ButtonGroup sortTargetRadioGroup = null;

	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints gbc = new GridBagConstraints();

	/** タイトルロゴ */
	private final String TITLE_IMG_PATH = "/img/icon_ebi.png";

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
		this.setTitle("StepCounter");
		this.setSize(750, 450);
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
			this.jContentPane = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					Graphics2D g2d = (Graphics2D) g;
					Color start = new Color(255, 240, 245);
					Color end = new Color(255, 192, 203);
					// グラデーションでペイントする
					Paint paint = new GradientPaint(0.0f, 0.0f, start, 0.0f, getHeight(), end);
					g2d.setPaint(paint);
					g2d.fillRect(0, 0, getWidth(), getHeight());
				}
			};

			this.jContentPane.add(getJContentHeaderPane());
			this.jContentPane.add(getJp1());
			this.jContentPane.add(getJp2());
			this.jContentPane.add(getJp3());
			this.jContentPane.add(getJp4());
			this.jContentPane.setOpaque(false);
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
			try {
				final URL url = this.getClass().getResource(TITLE_IMG_PATH);
				final Image image = this.createImage((ImageProducer) url.getContent());
				final ImageIcon icon = new ImageIcon(image);
				this.jHeaderContentPane.add(new JLabel(icon), null);
				this.jHeaderContentPane.setOpaque(false);

				gbc.fill = GridBagConstraints.BOTH;
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.gridwidth = 1;
				gbc.gridheight = 1;
				gbl.setConstraints(this.jHeaderContentPane, gbc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.jHeaderContentPane;
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
			this.jp1.add(getInput(), BorderLayout.CENTER);
			this.jp1.add(getSelButton(), BorderLayout.EAST);
			this.jp1.setOpaque(false);
			this.jp1.setPreferredSize(new Dimension(750, 70));

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
			this.input = new JPlaceholderTextField("入力フォルダ");
			this.input.setOpaque(false);
			File f = new File(".").getAbsoluteFile().getParentFile();
			this.input.setText(f.getPath());
			this.input.setPreferredSize(new Dimension(650, 50));
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbl.setConstraints(this.input, gbc);
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
			this.jSelButton.setPreferredSize(new Dimension(50, 50));
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbl.setConstraints(this.jSelButton, gbc);
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
			this.jp2.add(getInput2(), BorderLayout.CENTER);
			this.jp2.add(getSelButton2(), BorderLayout.EAST);
			this.jp2.setOpaque(false);
			this.jp2.setPreferredSize(new Dimension(750, 70));
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbl.setConstraints(this.jp2, gbc);
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
			this.input2 = new JPlaceholderTextField("出力ファイル（拡張子はCSVを指定してください）");
			File f = new File(".").getAbsoluteFile().getParentFile();
			this.input2.setText(f.getPath());
			this.input2.setPreferredSize(new Dimension(650, 50));
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
			this.jp3.add(getSortTypeRadioButton(), BorderLayout.WEST);
			this.jp3.add(getSortTargetRadioButton(), BorderLayout.CENTER);
			this.jp3.setOpaque(false);
			this.jp3.setPreferredSize(new Dimension(750, 70));
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbl.setConstraints(this.jp3, gbc);
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
			// ラジオボタンのパネル
			this.jSortTypeRadioButton = new JPanel();
			// ラジオボタンのタイトル
			this.jSortTypeRadioButton.setBorder(BorderFactory.createTitledBorder("ソート区分"));
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
			// ラジオボタンのパネル
			this.jSortTargetRadioButton = new JPanel();
			// ラジオボタンのタイトル
			jSortTargetRadioButton.setBorder(BorderFactory.createTitledBorder("ソート対象"));
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
	 * ボタン
	 * 
	 * @return ボタン
	 */
	public JPanel getJp4() {
		if (this.jp4 == null) {
			this.jp4 = new JPanel();
			this.jp4.add(getStartButton(), null);
			this.jp4.setOpaque(false);
			this.jp4.setPreferredSize(new Dimension(300, 70));
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbl.setConstraints(this.jp4, gbc);

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

			this.jStartButton.setForeground(new Color(224, 255, 255));
			this.jStartButton.setBackground(new Color(240,128,128));
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
			if ("sel1".equals(cmd)) {
				directoriesSelect(this.getInput());
			} else if ("sel2".equals(cmd)) {
				fileSelect(this.getInput2());
			} else if ("start".equals(cmd)) {
				this.controller.stepCountGuiMode(makeStepCounterGuiRequestDto(), this);
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

}
