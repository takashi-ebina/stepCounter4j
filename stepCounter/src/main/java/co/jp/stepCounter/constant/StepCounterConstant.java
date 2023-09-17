package co.jp.stepCounter.constant;

import java.util.function.Function;

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
		GUI,
		/** 該当なし */
		NOTHING
	}

	/**
	 * <p>
	 * ソート区分の種別をもつ列挙型クラス
	 */
	public static enum SortType {
		/** ソートなし */
		NO_SORT("0", "無"),
		/** 昇順ソート */
		ASCENDING_ORDER("1", "昇順"),
		/** 降順ソート */
		DESCENDING_ORDER("2", "降順");
		
		/** ソート区分コード */
		private final String sortTypeCode;
		/** ソート区分名称 */
		private final String sortTypeName;
		
		/**
		 * <p>
		 * コンストラクタ
		 * 
		 * @param sortTypeCode ソート区分コード
		 * @param sortTypeName ソート区分名称
		 */
		SortType(final String sortTypeCode, final String sortTypeName) {
			this.sortTypeCode = sortTypeCode;
			this.sortTypeName = sortTypeName;
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
		 * ソート区分コードの値を返却するメソッド
		 * 
		 * @return ソート区分名称
		 */
		public String getSortTypeName() {
			return this.sortTypeName;
		}

		/**
		 * <p>
		 * ソート区分コードまたはソート区分名称に対応するソート区分の種別を返却するメソッド
		 * <p>
		 * 該当のEnumを取得する処理は{@link EnumReverseLookup#lookup(Object)}に移譲しています。
		 * 
		 * @param target 逆引き対象の値
		 * @param getter 逆引きに利用するgetterメソッド
		 * 
		 * @throws IllegalArgumentException ソート区部コードに対応するソート区分の種別が存在しない場合
		 * @return ソート区分コードに対応するソート区分の種別
		 */
		public static SortType lookup(final String target, final Function<SortType, String> getter) throws IllegalArgumentException {
			return (SortType) new EnumReverseLookup<>(SortType.class, getter).lookup(target);
		}
	}

	/**
	 * <p>
	 * ソート対象の種別をもつ列挙型クラス
	 */
	public static enum SortTarget {
		/** ファイルパス */
		FILEPATH("0", "ファイルパス"),
		/** 総行数 */
		TOTALSTEPCOUNT("1", "総行数"),
		/** 実行行数 */
		EXECSTEPCOUNT("2", "実行行数"),
		/** コメント行数 */
		COMMENTSTEPCOUNT("3", "コメント行数"),
		/** 空行数 */
		EMPTYSTEPCOUNT("4", "空行数");

		/** ソート対象コード */
		private final String sortTargetCode;
		/** ソート対象名称 */
		private final String sortTargetName;

		/**
		 * <p>
		 * コンストラクタ
		 * 
		 * @param sortTargetCode ソート対象コード
		 * @param sortTargetName ソート対象名称
		 */
		SortTarget(final String sortTargetCode, final String sortTargetName) {
			this.sortTargetCode = sortTargetCode;
			this.sortTargetName = sortTargetName;
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
		 * ソート対象名称の値を返却するメソッド
		 * 
		 * @return ソート対象名称
		 */
		public String getSortTargetName() {
			return this.sortTargetName;
		}

		/**
		 * <p>
		 * ソート対象コードまたはソート対象名称からソート対象の種別を返却するメソッド
		 * <p>
		 * 該当のEnumを取得する処理は{@link EnumReverseLookup#lookup(Object)}に移譲しています。
		 * @param target 逆引き対象の値
		 * @param getter 逆引きに利用するgetterメソッド
		 * 
		 * @throws IllegalArgumentException ソート対象コードに対応するソート対象の種別が存在しない場合
		 * @return ソート対象コードに対応するソート対象の種別
		 */
		public static SortTarget lookup(final String target, final Function<SortTarget, String> getter) throws IllegalArgumentException {
			return (SortTarget) new EnumReverseLookup<>(SortTarget.class, getter).lookup(target);
		}
	}
	
	/**
	 * <p>
	 * 処理結果の種別をもつ列挙型クラス
	 */
	public static enum ProcessResult {
		/** 処理成功 */
		SUCCESS("正常終了"),
		/** 処理失敗 */
		FAIL("異常終了");
		
		/** メッセージ */
		private final String message;

		/**
		 * <p>
		 * コンストラクタ
		 * 
		 * @param message メッセージ
		 */
		ProcessResult(final String message) {
			this.message = message;
		}
		/**
		 * <p>
		 * メッセージの値を返却するメソッド
		 * 
		 * @return メッセージ
		 */
		public String getMessage() {
			return this.message;
		}
	}
}
