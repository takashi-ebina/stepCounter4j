package co.jp.stepCounter.presentation.validator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import co.jp.stepCounter.constant.MessageConstant.ErrorMessageDiv;
import co.jp.stepCounter.constant.MessageConstant.InfoMessageDiv;
import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.infrastructure.messages.StepCounterMessages;

/**
 * <p>
 * 入力チェック処理のユーティリティメソッドを提供するクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class ValidatorUtil {
	
	/** StepCounerMessagesインスタンス */
	private static final StepCounterMessages messages = StepCounterMessages.getInstance();
	/** チェック処理成功時に返却する空リスト */
	private static final List<String> SUCCESS_LIST = Collections.unmodifiableList(new ArrayList<String>()); 
	/** 入力ディレクトリパスの最大文字数 */
	private static final int INPUT_PATH_MAX_LENGTH = 200;
	/** 出力ファイルパスの最大文字数 */
	private static final int OUTPUT_PATH_MAX_LENGTH = 255;
	
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
	 * @return 全ての入力チェックが正常の場合は空のリストを返却。それ以外の場合はエラーメッセージのリストを返却する。
	 */
	public static List<String> inputDirectoryCheck(final String inputPath) {

		final List<String> errorMessageList = new ArrayList<String>(); 
		
		if (StringUtils.isEmpty(inputPath)) {
			errorMessageList.add(messages.getMessageText(ErrorMessageDiv.BLANK_MESSAGE.name(), "入力フォルダ"));
			return errorMessageList;
		}
		
		if (inputPath.length() > INPUT_PATH_MAX_LENGTH) {
			errorMessageList.add(messages.getMessageText(ErrorMessageDiv.MAXLENGTH_MESSAGE.name(), "入力フォルダ", INPUT_PATH_MAX_LENGTH));
			return errorMessageList;
		}
		
		final File file = new File(inputPath);
		if (!file.isDirectory()) {
			errorMessageList.add(messages.getMessageText(ErrorMessageDiv.FILEPATH_MESSAGE.name(), "入力フォルダ"));
			return errorMessageList;
		}
		
		return SUCCESS_LIST;
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
	 * @return 全ての入力チェックが正常の場合は空のリストを返却。それ以外の場合はエラーメッセージのリストを返却する。
	 */
	public static List<String> outputFileCheck(final String outputPath, final ExecuteMode mode, final Scanner sn) {
		
		final List<String> errorMessageList = new ArrayList<String>(); 
		
		if (StringUtils.isEmpty(outputPath)) {
			errorMessageList.add(messages.getMessageText(ErrorMessageDiv.BLANK_MESSAGE.name(), "出力ファイル"));
			return errorMessageList;
		}
		
		if (outputPath.length() > OUTPUT_PATH_MAX_LENGTH) {
			errorMessageList.add(messages.getMessageText(ErrorMessageDiv.MAXLENGTH_MESSAGE.name(), "出力ファイル", OUTPUT_PATH_MAX_LENGTH));
			return errorMessageList;
		}
		
		final File file = new File(outputPath);
		if (file.isDirectory()) {
			errorMessageList.add(messages.getMessageText(ErrorMessageDiv.FOLDERPATH_MESSAGE.name(), "出力ファイル"));
			return errorMessageList;
		}
		if (!isExtension(file, "csv")) {
			errorMessageList.add(messages.getMessageText(ErrorMessageDiv.EXTENSION_MESSAGE.name(), "出力ファイル", "csv"));
			return errorMessageList;
		}
		if (mode == ExecuteMode.INTERACTIVE &&
				new File(outputPath).exists() && Objects.nonNull(sn)) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> 該当のファイルが既に存在します。 上書きされてしまいますがよろしいですか？ y / n");
			System.out.println("--> ファイルパス：" + outputPath);
			System.out.println("--> ------------------------------------------------");
			while (true) {
				final String decide = sn.next().toLowerCase().trim();
				if (Objects.equals(decide, "y")) {
					break;
				} else if (Objects.equals(decide, "n")) {
					errorMessageList.add(messages.getMessageText(InfoMessageDiv.SUSPENTION_MESSAGE.name()));
					return errorMessageList;
				} else {
					System.out.println("--> y または n を入力してください");
				}
			}
		}
		return SUCCESS_LIST;
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
