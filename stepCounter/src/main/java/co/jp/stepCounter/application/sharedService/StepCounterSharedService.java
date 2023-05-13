package co.jp.stepCounter.application.sharedService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import co.jp.stepCounter.constant.StepCounterConstant.ProcessResult;
import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import co.jp.stepCounter.domain.model.stepCountExecutor.StepCountExecutor;
import co.jp.stepCounter.domain.repository.StepCountRepository;
import co.jp.stepCounter.domain.value.AllFilesStepCountData;
import co.jp.stepCounter.domain.value.StepCountData;
import co.jp.stepCounter.infrastructure.csvdao.StepCountCsvDao;
import co.jp.stepCounter.infrastructure.log.Log4J2;

/**
 * <p>
 * ステップカウント処理を実行する共通サービスクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterSharedService {
	
	/** Log4J2インスタンス */
	private final Log4J2 logger = Log4J2.getInstance();
	/** ステップカウント処理のリポジトリクラス */
	private StepCountRepository stepCountRepository;
	/** ステップ数集計処理実行クラス */
	private StepCountExecutor stepCountExecutor;
	
	/**
	 * コンストラクタ
	 */
	public StepCounterSharedService() {
		stepCountRepository = new StepCountCsvDao();
		stepCountExecutor = new StepCountExecutor();
	}
	
	/**
	 * <p>
	 * ステップカウント処理実行メソッド
	 * <p>
	 * 引数のカウント対象のディレクトリ、カウント結果出力対象のファイルを元にステップカウント処理を実行する。
	 * <p>
	 * 処理中に例外が発生した場合はエラーメッセージを出力し処理を終了する。
	 * 
	 * @param inputDirectory      カウント対象のディレクトリ
	 * @param outputFile          カウント結果出力対象のファイル
	 * @param stepCountSortType   ソート区分
	 * @param stepCountSortTarget ソート対象
	 * @return 処理結果。正常終了の場合は{@link ProcessResult#SUCCESS}を返却。それ以外の場合は{@link ProcessResult#FAIL}を返却する。
	 */
	public ProcessResult execStepCount(final File inputDirectory, final File outputFile,
			final SortType stepCountSortType, final SortTarget stepCountSortTarget) {
		try {
			logger.logInfo("-- ステップカウント処理 開始 ---------------------------------------");
			
			// ステップ数の集計処理
			final List<StepCountData> stepCountDataList = stepCountExecutor.execStepCountInDirectory(inputDirectory, new ArrayList<StepCountData>());
			// ステップ数集計結果の書き込み処理
			stepCountRepository.save(new AllFilesStepCountData(stepCountDataList, stepCountSortType, stepCountSortTarget), outputFile);
			
			logger.logInfo("-- ステップカウント処理 終了 ---------------------------------------");
			
			return ProcessResult.SUCCESS;
		} catch (Exception e) {
			logger.logError("ステップカウント処理で異常発生", e);
			return ProcessResult.FAIL;
		}
	}
}
