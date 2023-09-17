package co.jp.stepCounter.presentation.view;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * <p>
 * 情報パネルのクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class InfoPanel extends JPanel {
	/**
	 * <p>
	 * コンストラクタ
	 */
	public InfoPanel() {
		init();
	}
	/**
	 * <p>
	 * 初期化処理
	 */
	public void init() {
		final String strHtml = 
				"""
				<html><h1><font Size=6>stepcounterについて</font></h1>
				<p>プログラムのステップ数を集計するツールです。</p>
				<br>
				<p>対応プログラムファイル：Java、Cs、sql<br>
				作成者：takashi.ebina<br>
				GitHubリポジトリ：https://github.com/takashi-ebina/stepCounterforJava/</p>
				<br>
				<h2><font Size=5>変更履歴</font></h2>
				<h3><font Size=4>1.0.0</font></h3>
				新規リリース
				""";
		this.add(new JLabel(strHtml));
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(750, 440));
	}
}
