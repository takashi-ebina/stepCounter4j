package co.jp.stepCounter.constant;
/**
 * <p>
 * メッセージの定数クラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class messageConstant {
	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * 定数クラスのため、インスタンス化は不可とする。
	 */
	private messageConstant() {
	}

	/**
	 * <p>
	 * エラーメッセージの種別をもつ列挙型クラス
	 */
	public static enum ErrorMessageDiv {
		/** メッセージ内容 : {0}　未入力です。*/
		BLANK_MESSAGE,
		/** メッセージ内容 : {0}　ファイルパスを指定しています。*/
		FILEPATH_MESSAGE,
		/** メッセージ内容 : {0}　フォルダパスを指定しています。*/
		FOLDERPATH_MESSAGE,
		/** メッセージ内容 : {0}　拡張子が{1}ではありません。*/
		EXTENSION_MESSAGE,
		/** メッセージ内容 : {0}　{1}文字を超えています。*/
		MAXLENGTH_MESSAGE;
	}
	/**
	 * <p>
	 * メッセージの種別をもつ列挙型クラス
	 */
	public static enum InfoMessageDiv {
		/** メッセージ内容 : ステップカウント処理が完了しました。 処理結果：{0} */
		RESULT_MESSAGE,
		/** メッセージ内容 : ステップカウント処理が中断しました。 */
		SUSPENTION_MESSAGE;
	}
}
