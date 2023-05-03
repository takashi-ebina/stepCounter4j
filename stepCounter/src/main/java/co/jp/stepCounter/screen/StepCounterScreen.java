package co.jp.stepCounter.screen;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.jp.stepCounter.main.StepCounter;
/**
 * <p>
 * ステップ数の集計を実施するGUIクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterScreen extends JFrame implements ActionListener {

	private JPanel jContentPane = null;
	private JPanel jp1 = null;
	private JPanel jp2 = null;
	private JPanel jp3 = null;
	private JButton jStartBtn = null;
	private JTextField input = null;
	private JButton jSelBtn = null;
	private JTextField input2 = null;
	private JButton jSelBtn2 = null;
	private JButton jExitBtn = null;
	
	/**
	 * コンストラクタ
	 */
	public StepCounterScreen() {
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
		this.setSize(650, 150);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * <p>
	 * メインパネルの生成処理
	 * 
	 * @return メインパネル
	 */
	private JPanel getJContentPane() {
		if (this.jContentPane == null) {
			this.jContentPane = new JPanel();
			this.jContentPane.setLayout(new GridLayout(3, 1, 5, 5));
			this.jContentPane.add(getJp1(), null);
			this.jContentPane.add(getJp2(), null);
			this.jContentPane.add(getJp3(), null);
		}
		return this.jContentPane;
	}
	/**
	 * <p>
	 * ファイル入力選択
	 * 
	 * @return ファイル入力選択
	 */
	private JPanel getJp1() {
		if (this.jp1 == null) {
			this.jp1 = new JPanel();
			this.jp1.setLayout(new BorderLayout());
			this.jp1.add(new JLabel("入力フォルダ"), BorderLayout.WEST);
			this.jp1.add(getInput(), BorderLayout.CENTER);
			this.jp1.add(getSelBtn(), BorderLayout.EAST);
		}
		return this.jp1;
	}
	/**
	 * <p>
	 * ファイルインプット（ディレクトリ）
	 * 
	 * @return ファイルインプット（ディレクトリ）
	 */
	private JTextField getInput() {
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
	private JButton getSelBtn() {
		if (this.jSelBtn == null) {
			this.jSelBtn = new JButton();
			this.jSelBtn.setText("...");
			this.jSelBtn.addActionListener(this);
			this.jSelBtn.setActionCommand("sel1");
		}
		return this.jSelBtn;
	}
	/**
	 * <p>
	 * 結果出力選択
	 * 
	 * @return 結果出力選択
	 */
	private JPanel getJp2() {
		if (this.jp2 == null) {
			this.jp2 = new JPanel();
			this.jp2.setLayout(new BorderLayout());
			this.jp2.add(new JLabel("出力ファイル"), BorderLayout.WEST);
			this.jp2.add(getInput2(), BorderLayout.CENTER);
			this.jp2.add(getSelBtn2(), BorderLayout.EAST);
		}
		return this.jp2;
	}
	/**
	 * <p>
	 * ファイルインプット（ファイル）
	 * 
	 * @return ファイルインプット（ファイル）
	 */
	private JTextField getInput2() {
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
	private JButton getSelBtn2() {
		if (this.jSelBtn2 == null) {
			this.jSelBtn2 = new JButton();
			this.jSelBtn2.setText("...");
			this.jSelBtn2.addActionListener(this);
			this.jSelBtn2.setActionCommand("sel2");
		}
		return this.jSelBtn2;
	}
	/**
	 * <p>
	 * ボタン
	 * 
	 * @return ボタン
	 */
	private JPanel getJp3() {
		if (this.jp3 == null) {
			this.jp3 = new JPanel();
			this.jp3.setLayout(new GridLayout(1, 2));
			this.jp3.add(getStartBtn(), null);
			this.jp3.add(getExitBtn(), null);
		}
		return this.jp3;
	}
	/**
	 * <p>
	 * 開始ボタン
	 * 
	 * @return 開始ボタン
	 */
	private JButton getStartBtn() {
		if (this.jStartBtn == null) {
			this.jStartBtn = new JButton();
			this.jStartBtn.setText("開始");
			this.jStartBtn.addActionListener(this);
			this.jStartBtn.setActionCommand("start");
		}
		return this.jStartBtn;
	}
	/**
	 * <p>
	 * 終了ボタン
	 * 
	 * @return 終了ボタン
	 */
	private JButton getExitBtn() {
		if (this.jExitBtn == null) {
			this.jExitBtn = new JButton();
			this.jExitBtn.setText("終了");
			this.jExitBtn.addActionListener(this);
			this.jExitBtn.setActionCommand("exit");
		}
		return this.jExitBtn;
	}
	/**
	 * <p>
	 * アクション監視
	 */
	public void actionPerformed(final ActionEvent e) {
		final String cmd = e.getActionCommand();
		if ("exit".equals(cmd)) {
			dispose();
			System.exit(0);
		} else if ("sel1".equals(cmd)) {
			buttonAble(false);
			directoriesSelect(this.input);
			buttonAble(true);
		} else if ("sel2".equals(cmd)) {
			buttonAble(false);
			fileSelect(this.input2);
			buttonAble(true);
		} else if ("start".equals(cmd)) {
			buttonAble(false);
			StepCounter.stepCountGuiMode(this.input.getText(), this.input2.getText(), this);
			buttonAble(true);
		}
		
	}
	/**
	 * <p>
	 * ボタン抑止
	 * @param flg ボタン抑止フラグ
	 */
	protected void buttonAble(boolean flg) {
		this.jStartBtn.setEnabled(flg);
		this.jSelBtn.setEnabled(flg);
		this.jSelBtn2.setEnabled(flg);
		this.jExitBtn.setEnabled(flg);
	}
	/**
	 * <p>
	 * ファイル・ディレクトリ選択
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
