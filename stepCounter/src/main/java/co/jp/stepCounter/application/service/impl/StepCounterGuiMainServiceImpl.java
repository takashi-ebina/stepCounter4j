package co.jp.stepCounter.application.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.jp.stepCounter.application.service.StepCounterGuiMainService;
import co.jp.stepCounter.constant.StepCounterConstant.ProcessResult;
import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import co.jp.stepCounter.domain.model.stepCountExecutor.StepCountExecutor;
import co.jp.stepCounter.domain.repository.StepCountRepository;
import co.jp.stepCounter.domain.value.AllFilesStepCountData;
import co.jp.stepCounter.domain.value.StepCountData;
import co.jp.stepCounter.infrastructure.log.Log4J2;

/**
 * <p>
 * GUIでステップカウント処理を実行するサービスの実装クラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterGuiMainServiceImpl implements StepCounterGuiMainService {

	/** Log4J2インスタンス */
	private final Log4J2 logger = Log4J2.getInstance();
	/** ステップカウント処理のリポジトリクラス */
	private final StepCountRepository stepCountRepository;
	/** ステップ数集計処理実行クラス */
	private final StepCountExecutor stepCountExecutor;

	/**
	 * コンストラクタ
	 * 
	 * @param stepCountRepository      ステップカウント処理のリポジトリクラス 
	 * @param stepCountExecutor        ステップ数集計処理実行クラス
	 */
	public StepCounterGuiMainServiceImpl(final StepCountRepository stepCountRepository, final StepCountExecutor stepCountExecutor) {
		if (Objects.isNull(stepCountRepository) || Objects.isNull(stepCountExecutor)) {
			throw new IllegalArgumentException("コンストラクタの引数の値がNullです");
		}
		this.stepCountRepository = stepCountRepository;
		this.stepCountExecutor = stepCountExecutor;
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
			logger.logInfo(String.format("[START]StepCount execute {inputDirectory:%s ,outputFile:%s ,SortType: %s,SortTarget:%s}"
					,inputDirectory.toString(), outputFile.toString(), stepCountSortType.getSortTypeCode(), stepCountSortTarget.getSortTargetCode()));

			// ステップ数の集計処理
			final List<StepCountData> stepCountDataList = 
					stepCountExecutor.execStepCountInDirectory(inputDirectory, new ArrayList<StepCountData>());
			// ステップ数集計結果の書き込み処理
			stepCountRepository.save(
					new AllFilesStepCountData(stepCountDataList, stepCountSortType, stepCountSortTarget), outputFile);

			logger.logInfo("[END]StepCount execute ");

			return ProcessResult.SUCCESS;
		} catch (Exception e) {
			logger.logError("ステップカウント処理で異常発生", e);
			return ProcessResult.FAIL;
		}
	}
}
