package co.jp.base;

import java.io.File;

/**
 * <p>
 * テストコードの定数クラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class TestStepCounterConstant {
	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * 定数クラスのため、インスタンス化は不可とする。
	 */
	private TestStepCounterConstant() {
	}
	/**
	 * <p>
	 * 入出力の区分の種別をもつ列挙型クラス
	 */
	public static enum TestCaseInOutDiv {
		/** 入力 */
		INPUT("/input"),
		/** 出力 */
		OUTPUT("/output");
		/** フォルダ名 */
		private final String folderName;
		
		/**
		 * <p>
		 * コンストラクタ
		 * 
		 * @param folderName フォルダ名 
		 */
		TestCaseInOutDiv(final String folderName) {
			this.folderName = folderName;
		}
		/**
		 * <p>
		 * フォルダ名の値を返却するメソッド
		 * 
		 * @return フォルダ名 
		 */
		public String getFolderName() {
			return this.folderName;
		}
	}
	
	/**
	 * <p>
	 * テストケースのフォルダパス
	 */
	public static final String TESTCASE_PATH = "/src/test/resources/testcase";
	
	/**
	 * <p>
	 * 共通テストケースのフォルダパス
	 */
	public static final String COMMON_TESTCASE_PATH = "/src/test/resources/testcase/common";
	
	/**
	 * <p>
	 * ベースファイルパス
	 */
	public static final String BASE_FILE_PATH = new File(".").getAbsoluteFile().getParentFile().getPath();
	
	/**
	 * <p>
	 * ステップ数集計結果出力ファイル名
	 */
	public static final String RESULT_FILE_NAME = "/result.csv";
}
