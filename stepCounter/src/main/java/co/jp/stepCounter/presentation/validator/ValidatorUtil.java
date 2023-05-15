package co.jp.stepCounter.presentation.validator;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.infrastructure.log.Log4J2;

/**
 * <p>
 * 入力チェック処理のユーティリティメソッドを提供するクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class ValidatorUtil {
	
	/** Log4J2インスタンス */
	private static final Log4J2 logger = Log4J2.getInstance();
	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * ユーティリティメソッドを提供するクラスのため、インスタンス化は不可とする。
	 */
	private ValidatorUtil() {
	}

	/**
	 * <p>
	 * 入力ディレクトリパスチェック
	 * <p>
	 * 以下のチェックを実施する。
	 * <ol>
	 * <li>空文字チェック<br>
	 * <li>ディレクトリ判定チェック<br>
	 * </ol>
	 * 
	 * @param inputPath 入力ディレクトリパス
	 * @return 全ての入力チェックが正常の場合はtrueを返却。それ以外の場合はfalseを返却する。
	 */
	public static boolean inputDirectoryCheck(final String inputPath) {

		logger.logDebug("入力ディレクトリパスチェック ファイルパス：" + inputPath);
		
		if ("".equals(inputPath)) {
			logger.logDebug("チェック結果：入力ファイルパスが未指定");
			return false;
		}
		final File file = new File(inputPath);
		if (!file.isDirectory()) {
			logger.logDebug("チェック結果：入力ファイルパスがファイル");
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * 出力ファイルパスチェック
	 * <p>
	 * 以下のチェックを実施する。
	 * <ol>
	 * <li>空文字チェック<br>
	 * <li>ファイル判定チェック<br>
	 * <li>CSV形式チェック<br>
	 * </ol>
	 * 
	 * @param outputPath 出力ファイルパス
	 * @param mode       コマンドオプション区分
	 * @param sn         Scanner
	 * @return 全ての入力チェックが正常の場合はtrueを返却。それ以外の場合はfalseを返却する。
	 */
	public static boolean outputFileCheck(final String outputPath, final ExecuteMode mode, final Scanner sn) {
		
		logger.logDebug("出力ファイルパスチェック ファイルパス：" + outputPath);
		
		if ("".equals(outputPath)) {
			logger.logDebug("チェック結果：出力ファイルパスが未指定");
			return false;
		}
		final File file = new File(outputPath);
		if (file.isDirectory()) {
			logger.logDebug("チェック結果：出力ファイルパスがディレクトリ");
			return false;
		}
		if (!isExtension(file, "csv")) {
			logger.logDebug("チェック結果：拡張子がCSV以外");
			return false;
		}
		if (mode == ExecuteMode.INTERACTIVE &&
				new File(outputPath).exists() && sn != null) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> 該当のファイルが既に存在します。 上書きされてしまいますがよろしいですか？ y / n");
			System.out.println("--> ファイルパス：" + outputPath);
			System.out.println("--> ------------------------------------------------");
			while (true) {
				final String decide = sn.next().toLowerCase().trim();
				if (Objects.equals(decide, "y")) {
					break;
				} else if (Objects.equals(decide, "n")) {
					return false;
				} else {
					System.out.println("--> y または n を入力してください");
				}
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 拡張子チェック
	 * <p>
	 * 引数のファイルの拡張子が想定通りであるか判定する。
	 * 
	 * @param file チェック対象のファイル
	 * @param expectedExtension 期待値となる拡張子
	 * @return ファイルの拡張子が想定通りの場合はtrueを返却。それ以外の場合はfalseを返却する。
	 */
	private static boolean isExtension(final File file, final String expectedExtension) {
		final String fileName = file.getName();
		final String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase().trim();
		return Objects.equals(extension, expectedExtension);
	}
}
