package co.jp.stepCounter.presentation.controller.cui;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringJoiner;

import co.jp.stepCounter.application.service.StepCounterCuiService;
import co.jp.stepCounter.application.service.impl.StepCounterCuiServiceImpl;
import co.jp.stepCounter.constant.MessageConstant.InfoMessageDiv;
import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.constant.StepCounterConstant.ProcessResult;
import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import co.jp.stepCounter.constant.SystemConstant;
import co.jp.stepCounter.domain.model.stepCountExecutor.StepCountExecutor;
import co.jp.stepCounter.infrastructure.csvdao.StepCountCsvDao;
import co.jp.stepCounter.infrastructure.log.Log4J2;
import co.jp.stepCounter.infrastructure.messages.StepCounterMessages;
import co.jp.stepCounter.presentation.validator.ValidatorUtil;
/**
 * <p>
 * CUIでステップカウント処理を実行するコントローラクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterCuiController {
	
	/** Log4J2インスタンス */
	private final Log4J2 logger = Log4J2.getInstance();
	/** StepCounerMessagesインスタンス */
	private final StepCounterMessages messages = StepCounterMessages.getInstance();
	/** CUIでステップカウント処理を実行するサービスクラス*/
	private final StepCounterCuiService service; 
	
	/**
	 * コンストラクタ
	 */
	public StepCounterCuiController() {
		this.service = new StepCounterCuiServiceImpl(new StepCountCsvDao(), new StepCountExecutor());
	}

	/**
	 * <p>
	 * ヘルプメッセージ出力メソッド
	 * <p>
	 * ヘルプメッセージをコンソールに出力する。
	 */
	public void printHelp() {
		System.out.println("Usage: java -jar StepCounter-jar-with-dependencies.jar");
		System.out.println("       java -jar StepCounter-jar-with-dependencies.jar [OPTIONS]");
		System.out.println("OPTIONS");
		System.out.println("       -h:このメッセージを表示して終了する。");
		System.out.println("       -s:スクリプトモードで実行する。（オプションを指定しない場合はGUIモード）");
		System.out.println("       -input=[inputDirectoryPath]:ステップカウント対象のディレクトリパスを指定する。 ※「-s」オプションを利用する場合に指定してください。");
		System.out.println("       -output=[outputFilePath]:カウント結果出力対象のファイルパスを指定する。 ※「-s」オプションを利用する場合に指定してください。");
		System.out.println("       -asc=[sortTarget]:ステップカウント処理の出力順を[sortTarget]をキーとして昇順ソートする。");
		System.out.println("       -desc=[sortTarget]:ステップカウント処理の出力順を[sortTarget]をキーとして降順ソートする。");
		System.out.println("       [sortTarget]: 0:ファイルパス、1:総行数、2:実行行数、3:コメント行数、4:空行数");
	}
	
	/**
	 * <p>
	 * 対話モードのステップカウントメソッド
	 * <p>
	 * [処理概要]<br>
	 * <ol>
	 * <li>カウント対象のディレクトリパスの入力<br>
	 * <li>カウント対象のディレクトリパスの入力チェック<br>
	 * <li>カウント結果出力対象のファイルパスの入力<br>
	 * <li>カウント結果出力対象のファイルパスの入力チェック<br>
	 * <li>ソート区分の入力<br>
	 * <li>ソート対象の入力<br>
	 * <li>カウント対象ディレクトリのステップカウント処理及びカウント結果をカウント結果出力ファイルに書き込む処理の呼出し<br>
	 * </ol>
	 */
	@SuppressWarnings("resource")
	public void stepCountInteractiveMode() {
		System.out.println("--> ------------------------------------------------");
		System.out.println("--> カウント対象のディレクトリパスを入力してください");
		System.out.println("--> ------------------------------------------------");
		final Scanner sn = new Scanner(System.in);
		final String inputDirectoryPath = sn.next();
		// カウント対象のディレクトリパスの入力チェック
		List<String> errorMessageList =  ValidatorUtil.inputDirectoryCheck(inputDirectoryPath);
		if (Objects.nonNull(errorMessageList) && errorMessageList.size() > 0) {
			final StringJoiner sj = new StringJoiner(SystemConstant.LINE_SEPARATOR);
			errorMessageList.stream().forEach(r -> sj.add(r));
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> " + sj.toString());
			System.out.println("--> ------------------------------------------------");
			return;
		}

		System.out.println("--> ------------------------------------------------");
		System.out.println("--> カウント結果出力対象のファイルパスを入力してください");
		System.out.println("--> ファイル拡張子：CSV");
		System.out.println("--> ------------------------------------------------");
		final String outputFilePath = sn.next();
		// カウント結果出力対象のファイルパスの入力チェック
		errorMessageList =  ValidatorUtil.outputFileCheck(outputFilePath, ExecuteMode.INTERACTIVE, sn);
		if (Objects.nonNull(errorMessageList) && errorMessageList.size() > 0) {
			final StringJoiner sj = new StringJoiner(SystemConstant.LINE_SEPARATOR);
			errorMessageList.stream().forEach(r -> sj.add(r));
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> " + sj.toString());
			System.out.println("--> ------------------------------------------------");
			return;
		}
		
		System.out.println("--> ------------------------------------------------");
		System.out.println("--> ソート区分を入力してください");
		System.out.println("--> 0：ソートなし、1：昇順ソート、2：降順ソート");
		System.out.println("--> ------------------------------------------------");
		SortType sortType = null;
		try {
			sortType = SortType.lookup(sn.next(), SortType::getSortTypeCode);
		} catch (IllegalArgumentException e) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> ソート区分に問題があります。");
			System.out.println("--> ------------------------------------------------");
			return;
		}
		
		SortTarget sortTarget = null;
		if (sortType != SortType.NO_SORT) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> ソート対象を入力してください");
			System.out.println("--> 0：ファイルパス、1：総行数、2：実行行数、3：コメント行数、4：空行数");
			System.out.println("--> ------------------------------------------------");
			try {
				sortTarget = SortTarget.lookup(sn.next(), SortTarget::getSortTargetCode);
			} catch (IllegalArgumentException e) {
				System.out.println("--> ------------------------------------------------");
				System.out.println("--> ソート対象に問題があります。");
				System.out.println("--> ------------------------------------------------");
				return;
			}
		} else {
			sortTarget = SortTarget.FILEPATH;
		}
		
		final ProcessResult result = 
				service.execStepCount(new File(inputDirectoryPath), new File(outputFilePath), sortType, sortTarget);
		
		System.out.println("--> ------------------------------------------------");
		System.out.println("--> " + messages.getMessageText(InfoMessageDiv.RESULT_MESSAGE.name(),result.getMessage()));
		System.out.println("--> ------------------------------------------------");
	}
	/**
	 * <p>
	 * スクリプトモードのステップカウントメソッド
	 * <p>
	 * [処理概要]<br>
	 * <ol>
	 * <li>カウント対象のディレクトリパスの入力チェック<br>
	 * <li>カウント結果出力対象のファイルパスの入力チェック<br>
	 * <li>カウント対象ディレクトリのステップカウント処理及びカウント結果をカウント結果出力ファイルに書き込む処理の呼出し<br>
	 * </ol>
	 * 
	 * @param dto CUIでステップカウント処理を実行する際に利用するDTOクラス
	 * @see StepCounterCuiRequestDto
	 */
	public void stepCountScriptMode(final StepCounterCuiRequestDto dto) {
		logger.logInfo(String.format("[START]StepCounterCuiRequestDto=%s", dto.toString()));
		
		final String inputDirectoryPath = dto.getInputDirectoryPath();
		final String outputFilePath = dto.getOutputFilePath();

		// カウント対象のディレクトリパスの入力チェック
		List<String> errorMessageList =  ValidatorUtil.inputDirectoryCheck(inputDirectoryPath);
		if (Objects.nonNull(errorMessageList) && errorMessageList.size() > 0) {
			final StringJoiner sj = new StringJoiner(SystemConstant.LINE_SEPARATOR);
			errorMessageList.stream().forEach(r -> sj.add(r));
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> " + sj.toString());
			System.out.println("--> ------------------------------------------------");
			return;
		}
		// カウント結果出力対象のファイルパスの入力チェック
		errorMessageList =  ValidatorUtil.outputFileCheck(outputFilePath, ExecuteMode.SCRIPT, null);
		if (Objects.nonNull(errorMessageList) && errorMessageList.size() > 0) {
			final StringJoiner sj = new StringJoiner(SystemConstant.LINE_SEPARATOR);
			errorMessageList.stream().forEach(r -> sj.add(r));
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> " + sj.toString());
			System.out.println("--> ------------------------------------------------");
			return;
		}
		// サービス呼出し
		final ProcessResult result = 
				service.execStepCount(new File(inputDirectoryPath), new File(outputFilePath), dto.getStepCountSortType(), dto.getStepCountSortTarget());
		
		System.out.println("--> ------------------------------------------------");
		System.out.println("--> " + messages.getMessageText(InfoMessageDiv.RESULT_MESSAGE.name(),result.getMessage()));
		System.out.println("--> ------------------------------------------------");
	}
}
