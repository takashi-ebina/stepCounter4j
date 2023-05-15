package co.jp.stepCounter.infrastructure.csvdao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.StringJoiner;

import co.jp.stepCounter.constant.SystemConstant;
/**
 * <p>
 * CSVファイルの登録・更新・削除処理のユーティリティメソッドを提供するクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author takashi.ebina
 */
public class CsvDaoUtil {
	
	/**
	 * <p>
	 * コンストラクタ
	 * <p>
	 * ユーティリティメソッドを提供するクラスのため、インスタンス化は不可とする。
	 */
	private CsvDaoUtil() {
	}
	/**
	 * <p>
	 * CSVヘッダー書き込みメソッド
	 * <p>
	 * 引数のカウント結果出力対象ファイルに対して、CSVのヘッダーの書き込み処理を行う。
	 * 
	 * @param outputFile カウント結果出力対象ファイル
	 * @param headerName ヘッダー名を定義しているリスト
	 */
	public static void writeHeader(final File outputFile, final List<String> headerName) {
		try (final BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
			final StringJoiner sj = new StringJoiner(",");
			headerName.stream().forEach(r -> sj.add(r));
			bw.write(sj.toString() + SystemConstant.LINE_SEPARATOR);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
