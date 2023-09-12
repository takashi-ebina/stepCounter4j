package co.jp.stepCounter.presentation.view;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.net.URL;

import javax.swing.ImageIcon;
/**
 * <p>
 * GUI生成処理のユーティリティメソッドを提供するクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class ViewUtil {
	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * ユーティリティメソッドを提供するクラスのため、インスタンス化は不可とする。
	 */
	private ViewUtil() {
	}
	/**
	 * <p>
	 * ImageIcon取得
	 * 
	 * @param imageIconPath 画像のパス
	 * @param component コンポーネント
	 * 
	 * @return ImageIconオブジェクト
	 */
	public static ImageIcon getImageIcon(final String imageIconPath, final Component component) {
		ImageIcon icon = null;
		try {
			final URL url = component.getClass().getResource(imageIconPath);
			final Image image = component.createImage((ImageProducer) url.getContent());
			icon = new ImageIcon(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icon;
	}
}
