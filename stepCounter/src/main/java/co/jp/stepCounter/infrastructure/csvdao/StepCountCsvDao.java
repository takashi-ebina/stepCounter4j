package co.jp.stepCounter.infrastructure.csvdao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.jp.stepCounter.constant.SystemConstant;
import co.jp.stepCounter.domain.repository.StepCountRepository;
import co.jp.stepCounter.domain.value.AllFilesStepCountData;
import co.jp.stepCounter.domain.value.StepCountData;

/**
 * <p>
 * ステップカウントのリポジトリクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 * 
 * @see StepCountRepository
 */
public class StepCountCsvDao implements StepCountRepository {
	
	/**
	 * <p>
	 * ステップカウントCSVのヘッダー名
	 */
	private final List<String> STEP_COUNT_HEADER_NAME = new ArrayList<String>(
			Arrays.asList("ファイルパス", "総行数", "実行行数", "コメント行数", "空行数"));

	/**
	 * <p>
	 * プログラムファイルのステップ数をステップカウント結果出力ファイルに書き込むメソッド
	 * <p>
	 * 引数の全ファイルの総行数／実行行数／コメント行数／空行数を集計するデータクラスを元に、<br>
	 * 集計結果を引数のカウント結果出力対象ファイルに書き込む処理を行います。
	 * <p>
	 * [書き込み内容]<br>
	 * ファイルパス,総行数,実行行数,コメント行数,空行数
	 * 
	 * @param allFilesStepCountData 全ファイルの総行数／実行行数／コメント行数／空行数を集計するデータクラス
	 * @param outputFile            カウント結果出力対象ファイル
	 */
	@Override
	public void save(AllFilesStepCountData allFilesStepCountData, File outputFile) {
		// CSVヘッダーの書き込み
		CsvDaoUtil.writeHeader(outputFile, STEP_COUNT_HEADER_NAME);
		// CSVデータの書き込み
		try (final BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true))) {
			for (final StepCountData stepCountData : allFilesStepCountData.getStepCountDatalist()) {
				// 各ファイル毎のステップ数出力
				bw.write(stepCountData.outputDataCommaDelimited() + SystemConstant.LINE_SEPARATOR);
			}
			// 全ファイル合計のステップ数出力
			bw.write(allFilesStepCountData.outputDataCommaDelimited() + SystemConstant.LINE_SEPARATOR);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
