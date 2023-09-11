package co.jp.stepCounter.presentation.view.custom;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
/**
 * <p>
 * 空アイコンのクラス
 * <p>
 * DefaultTreeCellRendererに対してレイアウトの設定を行う際に利用する。
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class EmptyIcon implements Icon {
	/**
	 * <p>
	 * アイコンを描画する処理
	 * <p>
	 * 空のアイコンのため特に何もしない
	 * 
	 * @param c Component
	 * @param g Graphics
	 * @param x X座標
	 * @param y Y座標
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		/* Empty icon */
	}
	
	/**
	 * <p>
	 * アイコンの高さを返します。
	 * 
	 * @return 0
	 */
	@Override
	public int getIconWidth() {
		return 0;
	}
	/**
	 * <p>
	 * アイコンの幅を返します。
	 * 
	 * @return 0
	 */
	@Override
	public int getIconHeight() {
		return 0;
	}

}
