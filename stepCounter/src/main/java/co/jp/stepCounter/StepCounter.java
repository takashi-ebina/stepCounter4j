package co.jp.stepCounter;

import java.util.ArrayList;
import java.util.Objects;

import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.constant.StepCounterConstant.SortTarget;
import co.jp.stepCounter.constant.StepCounterConstant.SortType;
import co.jp.stepCounter.presentation.controller.cui.StepCounterCuiController;
import co.jp.stepCounter.presentation.controller.cui.StepCounterCuiRequestDto;
import co.jp.stepCounter.presentation.view.StepCounterGuiMainView;

/**
 * <p>
 * ステップカウント処理を呼び出すクラス
 * <p>
 * 対話形式または引数にカウント対象のディレクトリパス、カウント結果出力対象のファイルパスを入力し、<br>
 * フォルダ内に存在するプログラムファイルのステップ数を集計し、CSV形式でファイルに出力します。
 * <p>
 * [使い方]<br>
 * java -jar StepCounter-jar-with-dependencies.jar<br>
 * java -jar StepCounter-jar-with-dependencies.jar [オプション] <br>
 * [オプション]<br>
 * {@literal -h}:このメッセージを表示して終了する。<br>
 * {@literal -i}:対話モードで実行する。（オプションを指定しない場合はGUIモード）<br>
 * {@literal -s}:スクリプトモードで実行する。（オプションを指定しない場合はGUIモード）<br>
 * {@literal -input=[inputDirectoryPath]}:ステップカウント対象のディレクトリパスを指定する。
 * ※「{@literal -s}」オプションを利用する場合に指定してください。<br>
 * {@literal -output=[outputFilePath]}:カウント結果出力対象のファイルパスを指定する。
 * ※「{@literal -s}」オプションを利用する場合に指定してください。<br>
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

	/**
	 * <p>
	 * ステップカウント処理を行うエントリーポイントメソッド
	 * <p>
	 * コマンドライン引数の値を元にオプションの解析を行う。
	 * <ul>
	 * <li>コマンドライン引数の長さが0の場合、ステップカウント処理実行モードがGUIモードになる。</li>
	 * <li>コマンドライン引数に{@literal -h}が存在する場合、ステップカウント処理実行モードがヘルプモードになる。</li>
	 * <li>コマンドライン引数に{@literal -i}が存在する場合、ステップカウント処理実行モードが対話モードになる。</li>
	 * <li>コマンドライン引数に{@literal -s}が存在する場合、ステップカウント処理実行モードがスクリプトモードになる。<br>
	 * <li>スクリプトモードで利用する引数は{@literal -input=}および{@literal -output=}を指定する。</li>
	 * <li>{@literal -input=}：「=」以降にカウント対象のディレクトリパスを指定する。</li>
	 * <li>{@literal -output=}：「=」以降にカウント結果出力対象のファイルパスを指定する。</li>
	 * <li>ステップカウント処理の出力順をソートさせたい場合は{@literal -asc=}または{@literal -desc=}を指定する。</li>
	 * <li>{@literal -asc=}：「=」以降にソート対象の項目を指定する</li>
	 * <li>{@literal -desc=}：「=」以降にソート対象の項目を指定する</li>
	 * <li>ソート対象の項目は0:ファイルパス、1:総行数、2:実行行数、3:コメント行数、4:空行数で指定する。</li>
	 * </ul>
	 * 
	 * @param args 未入力の場合、GUIモードで処理を実行。それ以外の場合は引数に応じて処理が変動する。
	 */
	public static void main(String[] args) {
		if (Objects.isNull(args)|| args.length == 0) {
			new StepCounterGuiMainView();	// GUIモードとして実行
			return;
		}
		// CUIモードとして実行
		final StepCounterCuiRequestDto requestDto = makeStepCounterCuiRequestDto(args);
		final StepCounterCuiController controller = new StepCounterCuiController();
		switch (requestDto.getStepCountExecuteMode()) {
		case HELP:
			controller.printHelp();
			break;
		case INTERACTIVE:
			controller.stepCountInteractiveMode();
			break;
		case SCRIPT:
			controller.stepCountScriptMode(requestDto);
			break;
		default:
			break;
		}
	}
	/**
	 * <p>
	 * CUIでステップカウント処理を実行する際に利用するDTOオブジェクトの生成
	 * 
	 * @param args コマンドライン引数
	 * @return CUIでステップカウント処理を実行する際に利用するDTOオブジェクト
	 * 
	 * @see StepCounterCuiRequestDto
	 */
	private static StepCounterCuiRequestDto makeStepCounterCuiRequestDto(String[] args) {
		String inputDirectoryPath = null;
		String outputFilePath = null;
		ExecuteMode stepCountExecuteMode = ExecuteMode.NOTHING;
		SortType stepCountSortType = SortType.NO_SORT;
		SortTarget stepCountSortTarget = SortTarget.FILEPATH;
		for (final String commandOption : args) {
			if (Objects.equals(commandOption, "-h")) {
				// ヘルプモードの場合
				stepCountExecuteMode = ExecuteMode.HELP;
			} else if (Objects.equals(commandOption, "-i")) {
				// 対話モードの場合
				stepCountExecuteMode = ExecuteMode.INTERACTIVE;
			} else if (Objects.equals(commandOption, "-s")) {
				// スクリプトモードの場合
				stepCountExecuteMode = ExecuteMode.SCRIPT;
			} else if (commandOption.startsWith("-input=")) {
				// カウント対象のディレクトリパスを指定している場合
				inputDirectoryPath = split(commandOption, "=")[1];
			} else if (commandOption.startsWith("-output=")) {
				// カウント結果出力対象のファイルパスを指定している場合
				outputFilePath = split(commandOption, "=")[1];
			} else if (commandOption.startsWith("-asc=")) {
				// 昇順ソートを指定している場合
				stepCountSortType = SortType.ASCENDING_ORDER;
				stepCountSortTarget = SortTarget.lookup(split(commandOption, "=")[1], SortTarget::getSortTargetCode);
			} else if (commandOption.startsWith("-desc=")) {
				// 降順ソートを指定している場合
				stepCountSortType = SortType.DESCENDING_ORDER;
				stepCountSortTarget = SortTarget.lookup(split(commandOption, "=")[1], SortTarget::getSortTargetCode);
			}
		}
		if (stepCountExecuteMode == ExecuteMode.SCRIPT) {
			if (Objects.isNull(inputDirectoryPath) || Objects.isNull(outputFilePath)) {
				throw new IllegalArgumentException("カウント対象のディレクトリパスまたはカウント結果出力対象のファイルパスの値がNullです");
			}
		}
		return new StepCounterCuiRequestDto(stepCountExecuteMode, stepCountSortType, stepCountSortTarget,
				inputDirectoryPath, outputFilePath);
	}
	
	/**
	 * 文字列を指定文字列で分割し、配列で返却します。
	 *
	 * @param str 文字列
	 * @param del 区切り文字列
	 * @return 分割された文字列を格納した配列
	 */
	private static String[] split(final String str, final String del){
		final ArrayList<String> list = new ArrayList<String>();
		int pos   = 0;
		int index = 0;
		while ((index = str.indexOf(del, pos)) !=- 1) {
			list.add(str.substring(pos, index));
			pos = index + del.length();
		}
		list.add(str.substring(pos, str.length()));
		return (String[])list.toArray(new String[list.size()]);
	}
}
