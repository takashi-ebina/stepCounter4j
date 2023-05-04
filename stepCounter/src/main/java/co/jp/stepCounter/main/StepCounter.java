package co.jp.stepCounter.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.swing.JOptionPane;

import co.jp.stepCounter.constant.Constant;
import co.jp.stepCounter.constant.Constant.ExecuteMode;
import co.jp.stepCounter.constant.Constant.SortTarget;
import co.jp.stepCounter.constant.Constant.SortType;
import co.jp.stepCounter.data.AllFilesStepCountData;
import co.jp.stepCounter.data.StepCountData;
import co.jp.stepCounter.logic.commentPatternMatch.CommentPatternMatchFactory.CommentPatternMatchType;
import co.jp.stepCounter.logic.commentPatternMatch.IfCommentPatternMatch;
import co.jp.stepCounter.logic.stepCount.IfStepCount;
import co.jp.stepCounter.logic.stepCount.StepCountFactory.StepCountType;
import co.jp.stepCounter.main.arg.Arguments;
import co.jp.stepCounter.screen.StepCounterScreen;
import co.jp.stepCounter.util.Util;
import co.jp.stepCounter.util.log.Log4J2;
import co.jp.stepCounter.util.validator.ValidatorUtil;

/**
 * <p>
 * ステップカウント処理を行うメインクラス
 * <p>
 * 対話形式または引数にカウント対象のディレクトリパス、カウント結果出力対象のファイルパスを入力し、<br>
 * フォルダ内に存在するプログラムファイルのステップ数を集計し、CSV形式でファイルに出力します。
 * <p>
 * [使い方]<br>
 * java StepCounter<br>
 * java StepCounter [オプション] <br>
 * [オプション]<br>
 * {@literal -h}:このメッセージを表示して終了する。<br>
 * {@literal -s}:スクリプトモードで実行する。（オプションを指定しない場合はGUIモード）<br>
 * {@literal -input=[inputDirectoryPath]}:ステップカウント対象のディレクトリパスを指定する。 ※「{@literal -s}」オプションを利用する場合に指定してください。<br>
 * {@literal -output=[outputFilePath]}:カウント結果出力対象のファイルパスを指定する。 ※「{@literal -s}」オプションを利用する場合に指定してください。<br>
 * {@literal -asc=[sortTarget]}:ステップカウント処理の出力順を[sortTarget]をキーとして昇順ソートする。<br>
 * {@literal -desc=[sortTarget]}:ステップカウント処理の出力順を[sortTarget]をキーとして降順ソートする。<br>
 * [sortTarget]: 0:ファイルパス、1:総行数、2:実行行数、3:コメント行数、4:空行数<br>
 * <p>
 * [出力ファイルイメージ]<br>
 * ファイルパス,総行数,実行行数,コメント行数,空行数<br>
 * /Users/xxx.java,30,20,4,6<br>
 * .. 中略 ..<br>
 * 合計,60,40,8,12<br>
 * <p>
 * [対応プログラムファイル]<br>
 * <ul>
 * <li>Java</li>
 * <li>Cs</li>
 * </ul>
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class StepCounter {
	
	/** Log4J2インスタンス */
	private static final Log4J2 logger = Log4J2.getInstance();

	/**
	 * <p>
	 * ステップカウント処理を行うエントリーポイントメソッド
	 * 
	 * @param args 未入力の場合、GUIモードで処理を実行。それ以外の場合は引数に応じて処理が変動する。
	 */
	public static void main(String[] args) {
		final Arguments arguments = new Arguments(args);
		switch (arguments.getStepCountExecuteMode()) {
		case HELP:
			printHelp();
			break;
		case GUI:
			new StepCounterScreen();
			break;
		case SCRIPT:
			stepCountScriptMode(arguments);
			break;
		default:
			System.out.println("--> ------------------------------");
			System.out.println("--> オプションの指定に問題があります。");
			System.out.println("--> ------------------------------");
			printHelp();
			break;
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
	 * <li>カウント結果出力対象のファイルパスのCSVヘッダーに書き込む処理<br>
	 * <li>プログラムファイルのステップ数をステップカウント結果出力ファイルに書き込む処理<br>
	 * </ol>
	 * 
	 * @param args コマンドライン引数の値、オプションを簡易に解析するクラス。
	 * @see Arguments
	 */
	private static void stepCountScriptMode(final Arguments args) {
		final String inputDirectoryPath = args.getScriptArguments().getInputDirectoryPath();
		final String outputFilePath = args.getScriptArguments().getOutputFilePath();

		if (!ValidatorUtil.inputDirectoryCheck(inputDirectoryPath)) {
			System.out.println("--> ------------------------------");
			System.out.println("--> 入力フォルダに問題があります。");
			System.out.println("--> ------------------------------");
			return;
		}
		if (!ValidatorUtil.outputFileCheck(outputFilePath, ExecuteMode.SCRIPT)) {
			System.out.println("--> ------------------------------");
			System.out.println("--> 出力ファイルに問題があります。");
			System.out.println("--> ------------------------------");
			return;
		}
		execStepCount(new File(inputDirectoryPath), new File(outputFilePath), args.getStepCountSortType(), args.getStepCountSortTarget());
		System.out.println("--> ------------------------------");
		System.out.println("--> ステップカウント処理が完了しました。");
		System.out.println("--> ------------------------------");
	}

	/**
	 * <p>
	 * GUIモードのステップカウントメソッド
	 * <p>
	 * [処理概要]<br>
	 * <ol>
	 * <li>カウント対象のディレクトリパスの入力チェック<br>
	 * <li>カウント結果出力対象のファイルパスの入力チェック<br>
	 * <li>カウント結果出力対象のファイルパスのCSVヘッダーに書き込む処理<br>
	 * <li>プログラムファイルのステップ数をステップカウント結果出力ファイルに書き込む処理<br>
	 * </ol>
	 * 
	 * @param inputDirectoryPath カウント対象のディレクトリ
	 * @param outputFilePath カウント結果出力対象のファイル
	 * @param parent 親画面
	 */
	public static void stepCountGuiMode(final String inputDirectoryPath, final String outputFilePath ,final StepCounterScreen parent) {

		if (!ValidatorUtil.inputDirectoryCheck(inputDirectoryPath)) {
			JOptionPane.showMessageDialog(parent, "入力フォルダに問題があります。");
			return;
		}
		if (!ValidatorUtil.outputFileCheck(outputFilePath, ExecuteMode.INTERACTIVE)) {
			JOptionPane.showMessageDialog(parent, "出力ファイルに問題があります。");
			return;
		}
		execStepCount(new File(inputDirectoryPath), new File(outputFilePath), SortType.NO_SORT, SortTarget.FILEPATH);
		JOptionPane.showMessageDialog(parent, "ステップカウント処理が完了しました。");
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
	 */
	private static void execStepCount(final File inputDirectory, final File outputFile,
			final SortType stepCountSortType, final SortTarget stepCountSortTarget) {
		try {
			logger.logInfo("ステップカウント処理開始");
			// ステップ数の集計処理
			final List<StepCountData> stepCountDataList = execStepCountInDirectory(inputDirectory, new ArrayList<StepCountData>());
			// ステップ数集計結果の書き込み処理
			writeHeaderStepCount(outputFile);
			writeStepCountResult(new AllFilesStepCountData(stepCountDataList, stepCountSortType, stepCountSortTarget), outputFile);
			logger.logInfo("ステップカウント処理正常終了");
		} catch (Exception e) {
			logger.logError("ステップカウント処理異常終了", e);
			// System.err.println("--> ステップカウント処理で異常が発生しました。");
			return;
		}
	}

	/**
	 * <p>
	 * CSVヘッダー書き込みメソッド
	 * <p>
	 * 引数のカウント結果出力対象ファイルに対して、CSVのヘッダーの書き込み処理を行う。
	 * <p>
	 * CSVヘッダーの定義は{@link Constant#STEP_COUNT_HEADER_NAME}参照。
	 * 
	 * @param outputFile カウント結果出力対象ファイル
	 * @see Constant#STEP_COUNT_HEADER_NAME
	 */
	private static void writeHeaderStepCount(final File outputFile) {
		try (final BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
			final StringJoiner sj = new StringJoiner(",");
			Constant.STEP_COUNT_HEADER_NAME.stream().forEach(r -> sj.add(r));
			bw.write(sj.toString() + Constant.LINE_SEPARATOR);
		} catch (IOException e) {
			logger.logError(e);
		}
	}

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
	private static void writeStepCountResult(final AllFilesStepCountData allFilesStepCountData, final File outputFile) {
		try (final BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true))) {
			for (final StepCountData stepCountData : allFilesStepCountData.getStepCountDatalist()) {
				logger.logInfo("ステップカウント結果出力開始。 ファイルパス：" + stepCountData.getInputFile().getAbsolutePath());
				// 各ファイル毎のステップ数出力
				bw.write(stepCountData.outputDataCommaDelimited() + Constant.LINE_SEPARATOR);
			}
			// 全ファイル合計のステップ数出力
			bw.write(allFilesStepCountData.outputDataCommaDelimited() + Constant.LINE_SEPARATOR);
		} catch (IOException e) {
			logger.logError("ステップカウント結果出力処理で例外発生。 カウント結果出力対象ファイル： " + outputFile.getAbsolutePath(), e);
		}
	}

	/**
	 * <p>
	 * ディレクトリに対するステップ数カウントメソッド
	 * <p>
	 * 引数のカウント対象のディレクトリに対して再帰処理を行い、ステップカウント対象の拡張子をもつファイルに対して、<br>
	 * 総行数,実行行数,コメント行数,空行数を集計したデータクラスを返却する。
	 * 
	 * @param inputDirectory    カウント対象ディレクトリ
	 * @param stepCountDatalist 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラスリスト
	 * @return stepCountDatalist 1ファイル単位の総行数／実行行数／コメント行数／空行数を集計するデータクラスリスト
	 * @see CommentPatternMatchType#containsExtension
	 * @see StepCountType#containsExtension
	 * @see CommentPatternMatchType#of
	 * @see StepCountType#of
	 * @see IfCommentPatternMatch
	 * @see IfStepCount#stepCount
	 */
	private static List<StepCountData> execStepCountInDirectory(final File inputDirectory, final List<StepCountData> stepCountDatalist) {
		for (final File inputFile : inputDirectory.listFiles()) {
			if (inputFile.isDirectory()) {
				execStepCountInDirectory(inputFile, stepCountDatalist);
			} else if (inputFile.isFile()) {
				final String extension = Util.getExtension(inputFile);
				if (CommentPatternMatchType.containsExtension(extension) && StepCountType.containsExtension(extension)) {
					IfStepCount stepCountObj = StepCountType.of(extension, CommentPatternMatchType.of(extension));
					logger.logInfo("ステップカウント処理開始。 ファイル名：" + inputFile.getName());
					final StepCountData stepCountData = stepCountObj.stepCount(inputFile);
					stepCountDatalist.add(stepCountData);
				} else {
					logger.logWarn("ファイルの拡張子が未対応。ステップカウント処理をスキップ。 ファイル名：" + inputFile.getName());
				}
			}
		}
		return stepCountDatalist;
	}
	/**
	 * <p>
	 * ヘルプメッセージ出力メソッド
	 * <p>
	 * ヘルプメッセージをコンソールに出力する。
	 */
	private static void printHelp() {
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
}
