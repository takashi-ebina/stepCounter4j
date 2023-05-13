package co.jp.stepCounter.constant;

/**
 * <p>
 * ステップカウント処理関連の定数クラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterConstant {
	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * 定数クラスのため、インスタンス化は不可とする。
	 */
	private StepCounterConstant() {
	}

	/**
	 * <p>
	 * ファイルパス区分の種別をもつ列挙型クラス
	 */
	public static enum FilePathType {
		/** ディレクトリ */
		DIRECTORY,
		/** ファイル */
		FILE
	}

	/**
	 * <p>
	 * ステップカウント処理実行モードの種別をもつ列挙型クラス
	 */
	public static enum ExecuteMode {
		/** ヘルプモード */
		HELP,
		/** 対話モード */
		INTERACTIVE,
		/** スクリプトモード */
		SCRIPT,
		/** GUIモード */
		GUI
	}

	/**
	 * <p>
	 * ソート区分の種別をもつ列挙型クラス
	 */
	public static enum SortType {
		/** ソートなし */
		NO_SORT("0"),
		/** 昇順ソート */
		ASCENDING_ORDER("1"),
		/** 降順ソート */
		DESCENDING_ORDER("2");
		
		/** ソート区分コード */
		private final String sortTypeCode;
		
		/**
		 * <p>
		 * コンストラクタ
		 * 
		 * @param sortTypeCode ソート区分コード
		 */
		SortType(final String sortTypeCode) {
			this.sortTypeCode = sortTypeCode;
		}

		/**
		 * <p>
		 * ソート区分コードの値を返却するメソッド
		 * 
		 * @return ソート区分コード
		 */
		public String getSortTypeCode() {
			return this.sortTypeCode;
		}

		/**
		 * <p>
		 * ソート区分コードに対応するソート区分の種別を返却するメソッド
		 * <p>
		 * ソート区分コードから該当のEnumを取得する処理は{@link EnumReverseLookup#lookup(Object)}に移譲しています。
		 * 
		 * @param sortTypeCode ソート区分コード
		 * @throws IllegalArgumentException ソート区部コードに対応するソート区分の種別が存在しない場合
		 * @return ソート区分コードに対応するソート区分の種別
		 */
		public static SortType lookup(final String sortTypeCode) throws IllegalArgumentException {
			return new EnumReverseLookup<>(SortType.class, SortType::getSortTypeCode).lookup(sortTypeCode);
		}
	}

	/**
	 * <p>
	 * ソート対象の種別をもつ列挙型クラス
	 */
	public static enum SortTarget {
		/** ファイルパス */
		FILEPATH("0"),
		/** 総行数 */
		TOTALSTEPCOUNT("1"),
		/** 実行行数 */
		EXECSTEPCOUNT("2"),
		/** コメント行数 */
		COMMENTSTEPCOUNT("3"),
		/** 空行数 */
		EMPTYSTEPCOUNT("4");

		/** ソート対象コード */
		private final String sortTargetCode;

		/**
		 * <p>
		 * コンストラクタ
		 * 
		 * @param sortTargetCode ソート対象コード
		 */
		SortTarget(final String sortTargetCode) {
			this.sortTargetCode = sortTargetCode;
		}

		/**
		 * <p>
		 * ソート対象コードの値を返却するメソッド
		 * 
		 * @return ソート対象コード
		 */
		public String getSortTargetCode() {
			return this.sortTargetCode;
		}

		/**
		 * <p>
		 * ソート対象コードに対応するソート対象の種別を返却するメソッド
		 * <p>
		 * ソート対象コードから該当のEnumを取得する処理は{@link EnumReverseLookup#lookup(Object)}に移譲しています。
		 * 
		 * @param sortTargetCode ソート対象コード
		 * @throws IllegalArgumentException ソート対象コードに対応するソート対象の種別が存在しない場合
		 * @return ソート対象コードに対応するソート対象の種別
		 */
		public static SortTarget lookup(final String sortTargetCode) throws IllegalArgumentException {
			return new EnumReverseLookup<>(SortTarget.class, SortTarget::getSortTargetCode).lookup(sortTargetCode);
		}
	}
	
	/**
	 * <p>
	 * 処理結果の種別をもつ列挙型クラス
	 */
	public static enum ProcessResult {
		/** 処理成功 */
		SUCCESS,
		/** 処理失敗 */
		FAIL
	}
}
