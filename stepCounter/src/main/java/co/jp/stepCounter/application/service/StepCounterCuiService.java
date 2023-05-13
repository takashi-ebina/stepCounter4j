package co.jp.stepCounter.application.service;

import java.io.File;
import java.util.Scanner;

import co.jp.stepCounter.application.sharedService.StepCounterSharedService;
import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.constant.StepCounterConstant.ProcessResult;
import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import co.jp.stepCounter.presentation.controller.cui.StepCounterCuiRequestDto;
import co.jp.stepCounter.util.validator.ValidatorUtil;
/**
 * <p>
 * CUIでステップカウント処理を実行するサービスクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounterCuiService {

	/** 共通サービスクラス */
	private StepCounterSharedService sharedService;
	
	/**
	 * コンストラクタ
	 */
	public StepCounterCuiService() {
		sharedService = new StepCounterSharedService();
	}
	/**
	 * <p>
	 * ヘルプメッセージ出力メソッド
	 * <p>
	 * ヘルプメッセージをコンソールに出力する。
	 */
	public void printHelp() {
		System.out.println("Usage: java StepCounter");
		System.out.println("       java StepCounter [OPTIONS]");
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
		if (!ValidatorUtil.inputDirectoryCheck(inputDirectoryPath)) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> 入力フォルダに問題があります。");
			System.out.println("--> ------------------------------------------------");
			return;
		}

		System.out.println("--> ------------------------------------------------");
		System.out.println("--> カウント結果出力対象のファイルパスを入力してください");
		System.out.println("--> ファイル拡張子：CSV");
		System.out.println("--> ------------------------------------------------");
		final String outputFilePath = sn.next();
		if (!ValidatorUtil.outputFileCheck(outputFilePath, ExecuteMode.INTERACTIVE, sn)) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> 出力ファイルに問題があります。");
			System.out.println("--> ------------------------------------------------");
			return;
		}
		
		System.out.println("--> ------------------------------------------------");
		System.out.println("--> ソート区分を入力してください");
		System.out.println("--> 0：ソートなし、1：昇順ソート、2：降順ソート");
		System.out.println("--> ------------------------------------------------");
		SortType sortType = null;
		try {
			sortType = SortType.lookup(sn.next());
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
				sortTarget = SortTarget.lookup(sn.next());
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
				sharedService.execStepCount(new File(inputDirectoryPath), new File(outputFilePath), sortType, sortTarget);
		if (result == ProcessResult.SUCCESS) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> ステップカウント処理が完了しました。 処理結果：正常終了");
			System.out.println("--> ------------------------------------------------");
		} else if (result == ProcessResult.FAIL) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> ステップカウント処理が完了しました。 処理結果：異常終了");
			System.out.println("--> ------------------------------------------------");
		}
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
		final String inputDirectoryPath = dto.getInputDirectoryPath();
		final String outputFilePath = dto.getOutputFilePath();

		if (!ValidatorUtil.inputDirectoryCheck(inputDirectoryPath)) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> 入力フォルダに問題があります。");
			System.out.println("--> ------------------------------------------------");
			return;
		}
		if (!ValidatorUtil.outputFileCheck(outputFilePath, ExecuteMode.SCRIPT, null)) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> 出力ファイルに問題があります。");
			System.out.println("--> ------------------------------------------------");
			return;
		}
		final ProcessResult result = 
				sharedService.execStepCount(new File(inputDirectoryPath), new File(outputFilePath), dto.getStepCountSortType(), dto.getStepCountSortTarget());

		if (result == ProcessResult.SUCCESS) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> ステップカウント処理が完了しました。 処理結果：正常終了");
			System.out.println("--> ------------------------------------------------");
		} else if (result == ProcessResult.FAIL) {
			System.out.println("--> ------------------------------------------------");
			System.out.println("--> ステップカウント処理が完了しました。 処理結果：異常終了");
			System.out.println("--> ------------------------------------------------");
		}
	}
}
