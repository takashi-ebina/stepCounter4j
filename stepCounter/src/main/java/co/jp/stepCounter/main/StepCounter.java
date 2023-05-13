package co.jp.stepCounter.main;

import co.jp.stepCounter.constant.StepCounterConstant.ExecuteMode;
import co.jp.stepCounter.main.arg.Arguments;
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
 * java StepCounter<br>
 * java StepCounter [オプション] <br>
 * [オプション]<br>
 * {@literal -h}:このメッセージを表示して終了する。<br>
 * {@literal -i}:対話モードで実行する。（オプションを指定しない場合はGUIモード）<br>
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

	/**
	 * <p>
	 * ステップカウント処理を行うエントリーポイントメソッド
	 * 
	 * @param args 未入力の場合、GUIモードで処理を実行。それ以外の場合は引数に応じて処理が変動する。
	 */
	public static void main(String[] args) {
		final Arguments arguments = new Arguments(args);

		if (arguments.getStepCountExecuteMode() == ExecuteMode.GUI) {
			new StepCounterGuiMainView();
		} else {			
			new StepCounterCuiController()
				.executeStepCount(
						new StepCounterCuiRequestDto(
							arguments.getStepCountExecuteMode(),
							arguments.getStepCountSortType(),
							arguments.getStepCountSortTarget(),
							arguments.getInputDirectoryPath(),
							arguments.getOutputFilePath())
						);
		}

	}
}
