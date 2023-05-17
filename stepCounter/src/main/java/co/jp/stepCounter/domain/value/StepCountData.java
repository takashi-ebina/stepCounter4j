package co.jp.stepCounter.domain.value;

import java.io.File;
import java.util.Objects;

/**
 * <p>
 * 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public final class StepCountData {
	/** 1ファイル単位の総行数 */
	private final int totalStepCount;
	/** 1ファイル単位の実行行数 */
	private final int execStepCount;
	/** 1ファイル単位のコメント行数 */
	private final int commentStepCount;
	/** 1ファイル単位の空行数 */
	private final int emptyStepCount;
	/** ステップ数ファイル書き込みフラグ */
	private final boolean canWriteStepCount;
	/** カウント対象プログラムファイル */
	private final File inputFile;

	/**
	 * <p>
	 * コンストラクタ
	 * 
	 * @param totalStepCount 1ファイル単位の総行数 
	 * @param execStepCount 1ファイル単位の実行行数
	 * @param commentStepCount 1ファイル単位のコメント行数
	 * @param emptyStepCount 1ファイル単位の空行数
	 * @param canWriteStepCount ステップ数ファイル書き込みフラグ
	 * @param inputFile カウント対象プログラムファイル
	 * @throws IllegalArgumentException カウント対象プログラムファイルがNullの場合
	 */
	public StepCountData(final int totalStepCount, final int execStepCount, final int commentStepCount, final int emptyStepCount, 
			final boolean canWriteStepCount, final File inputFile) {
		if (totalStepCount < 0 || execStepCount < 0 || commentStepCount < 0 || emptyStepCount < 0) {
			throw new IllegalArgumentException("ステップ数の値が0未満です");
		}
		if (Objects.isNull(inputFile)) {
			throw new IllegalArgumentException("カウント対象プログラムファイルの値がNullです");
		}
		this.totalStepCount = totalStepCount;
		this.execStepCount = execStepCount;
		this.commentStepCount = commentStepCount;
		this.emptyStepCount = emptyStepCount;
		this.canWriteStepCount = canWriteStepCount;
		this.inputFile = inputFile;
	}

	/**
	 * <p>
	 * 1ファイル単位の総行数を返却するメソッド
	 * 
	 * @return 1ファイル単位の総行数
	 */
	public int getTotalStepCount() {
		return this.totalStepCount;
	}

	/**
	 * <p>
	 * 1ファイル単位の実行行数を返却するメソッド
	 * 
	 * @return 1ファイル単位の実行行数
	 */
	public int getExecStepCount() {
		return this.execStepCount;
	}

	/**
	 * <p>
	 * 1ファイル単位のコメント行数を返却するメソッド
	 * 
	 * @return 1ファイル単位のコメント行数
	 */
	public int getCommentStepCount() {
		return this.commentStepCount;
	}

	/**
	 * <p>
	 * 1ファイル単位の空行数を返却するメソッド
	 * 
	 * @return 1ファイル単位の空行数
	 */
	public int getEmptyStepCount() {
		return this.emptyStepCount;
	}

	/**
	 * <p>
	 * ステップ数ファイル書き込みフラグを返却するメソッド
	 * 
	 * @return ステップ数ファイル書き込みフラグ<br>
	 *         書き込み可能な場合はtrue。書き込み不可の場合はfalseを返却する。
	 */
	public boolean canWriteStepCount() {
		return this.canWriteStepCount;
	}

	/**
	 * <p>
	 * カウント対象プログラムファイルを返却するメソッド
	 * 
	 * @return カウント対象プログラムファイル
	 */
	public File getInputFile() {
		return this.inputFile;
	}

	/**
	 * <p>
	 * {@link StepCountData}で定義されている文字列表現を返却するメソッド
	 * 
	 * @return {@link StepCountData}で定義されている文字列表現
	 */
	@Override
	public String toString() {
		return new StringBuilder()
				.append("stepCountData [totalStepCount=").append(this.totalStepCount)
				.append(", execStepCount=").append(this.execStepCount)
				.append(", commentStepCount=").append(this.commentStepCount)
				.append(", emptyStepCount=").append(this.emptyStepCount)
				.append(", canWriteStepCount=").append(this.canWriteStepCount)
				.append(", inputFile=").append(this.inputFile).append("]").toString();
	}

	/**
	 * <p>
	 * ファイル名／1ファイル単位の総行数／実行行数／コメント行数／空行数をカンマ区切りで返却するメソッド
	 * <p>
	 * {@link StepCountData#canWriteStepCount}がfalseの場合は、ステップ数集計に失敗したとみなし、それぞれの値を0にして出力する。
	 * 
	 * @return {@link StepCountData#canWriteStepCount}がtrueの場合、ファイル名／1ファイル単位の総行数／実行行数／コメント行数／空行数をカンマ区切りにした文字列を返却する。<br>
	 *         falseの場合はファイル名+「(Failure to tally the number of
	 *         steps),0,0,0,0」を結合した文字列を返却する。
	 */
	public String outputDataCommaDelimited() {
		final StringBuilder sb;
		if (this.canWriteStepCount) {
			sb = new StringBuilder().append(this.inputFile.getAbsolutePath())
					.append(",").append(this.totalStepCount)
					.append(",").append(this.execStepCount)
					.append(",").append(this.commentStepCount)
					.append(",").append(this.emptyStepCount);
		} else {
			sb = new StringBuilder().append(this.inputFile.getAbsolutePath())
					.append("(Failure to tally the number of steps),0,0,0,0");
		}
		return sb.toString();
	}
}