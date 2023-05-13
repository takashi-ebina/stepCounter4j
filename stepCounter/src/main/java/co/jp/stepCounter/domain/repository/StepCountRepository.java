package co.jp.stepCounter.domain.repository;

import java.io.File;

import co.jp.stepCounter.domain.value.AllFilesStepCountData;
/**
 * <p>
 * ステップカウントのリポジトリクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public interface StepCountRepository {
	/**
	 * <p>
	 * プログラムファイルのステップ数をステップカウント結果出力ファイルに書き込むメソッド
	 * 
	 * @param allFilesStepCountData 全ファイルの総行数／実行行数／コメント行数／空行数を集計するデータクラス
	 * @param outputFile            カウント結果出力対象ファイル
	 */
	public void save(final AllFilesStepCountData allFilesStepCountData, final File outputFile);
}
