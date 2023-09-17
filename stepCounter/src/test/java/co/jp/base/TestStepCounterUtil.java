package co.jp.base;

import co.jp.base.TestStepCounterConstant.TestCaseInOutDiv;
/**
 * <p>
 * テストコードのユーティリティメソッドを提供するクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class TestStepCounterUtil {
	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * ユーティリティメソッドを提供するクラスのため、インスタンス化は不可とする。
	 */
	private TestStepCounterUtil() {
	}
	/**
	 * <p>
	 * クラス名取得メソッド
	 * <p>
	 * スタックトレースからクラス名を取得する。
	 * 
	 * @param i スタックトレースのインデックス 
	 * @return クラス名
	 */
	public static String getClassName(final int i) {
		return Thread.currentThread().getStackTrace()[i].getClassName();
	}
	/**
	 * <p>
	 * メソッド名取得メソッド
	 * <p>
	 * スタックトレースからメソッド名を取得する。
	 * 
	 * @param i スタックトレースのインデックス 
	 * @return メソッド名
	 */
	public static String getMethodName(final int i) {
		return Thread.currentThread().getStackTrace()[i].getMethodName();
	}
	/**
	 * <p>
	 * テストケース毎のパス取得メソッド
	 * <p>
	 * テストケース毎のパスを取得する。
	 * 
	 * @param inoutDiv 入出力の区分の種別をもつ列挙型クラス
	 * @return テストケース毎のパス
	 */
	public static String getTestcasePath(final TestCaseInOutDiv inoutDiv) {
		final String className = getClassName(3);
		final String testMethodName = getMethodName(3);
		final int pos = className.lastIndexOf(".");
		final String testClassName = className.substring(pos + 1, className.length());
		final StringBuffer testcasePath = new StringBuffer();
		return testcasePath
				.append(TestStepCounterConstant.BASE_FILE_PATH)
				.append(TestStepCounterConstant.TESTCASE_PATH)
				// クラスがネストしている構造の場合、＄で表記されるため置換する
				.append("/").append(testClassName.replaceAll("\\$", "/"))
				.append("/").append(testMethodName)
				.append("/").append(inoutDiv.getFolderName()).toString();
	}
	/**
	 * <p>
	 * 共通のテストケースのパス取得メソッド
	 * <p>
	 * 共通のテストケースのパスを取得する。
	 * 
	 * @param inoutDiv 入出力の区分の種別をもつ列挙型クラス
	 * @return 共通のテストケースのパス
	 */
	public static String getCommonTestcasePath(final TestCaseInOutDiv inoutDiv) {
		final StringBuffer testcasePath = new StringBuffer();
		return testcasePath
				.append(TestStepCounterConstant.BASE_FILE_PATH)
				.append(TestStepCounterConstant.COMMON_TESTCASE_PATH)
				.append("/").append(inoutDiv.getFolderName()).toString();
	}
}
