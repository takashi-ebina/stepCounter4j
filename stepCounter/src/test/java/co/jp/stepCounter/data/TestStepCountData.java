package co.jp.stepCounter.data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TestStepCountData {
	private File inputFile;
	private StepCountData stepCountData;

	@BeforeEach
	void setUp() {
		inputFile = new File(
				"/Applications/Eclipse_2021-06.app/Contents/workspace/stepCount_ver2/work/input/character/Characte.java");
		stepCountData = new StepCountData(inputFile);
	}

	@Nested
	class IncrementTotalStepCount {
		@DisplayName("incrementTotalStepCountメソッド実装時、totalStepCount（総行数）の値がインクリメントされる")
		@Test
		void success1() {
			// 【事前準備】
			// 【実行】
			stepCountData.incrementTotalStepCount();
			// 【検証】
			assertEquals(1, stepCountData.getTotalStepCount());
			assertEquals(0, stepCountData.getExecStepCount());
			assertEquals(0, stepCountData.getCommentStepCount());
			assertEquals(0, stepCountData.getEmptyStepCount());
			// 【後処理】
		}
	}

	@Nested
	class IncrementExecStepCount {
		@DisplayName("IncrementExecStepCountメソッド実装時、execStepCount（実行行数）の値がインクリメントされる")
		@Test
		void success1() {
			// 【事前準備】
			// 【実行】
			stepCountData.incrementExecStepCount();
			// 【検証】
			assertEquals(0, stepCountData.getTotalStepCount());
			assertEquals(1, stepCountData.getExecStepCount());
			assertEquals(0, stepCountData.getCommentStepCount());
			assertEquals(0, stepCountData.getEmptyStepCount());
			// 【後処理】
		}
	}

	@Nested
	class IncrementCommentStepCount {
		@DisplayName("incrementCommentStepCountメソッド実装時、commentStepCount（コメント行数）の値がインクリメントされる")
		@Test
		void success1() {
			// 【事前準備】
			// 【実行】
			stepCountData.incrementCommentStepCount();
			// 【検証】
			assertEquals(0, stepCountData.getTotalStepCount());
			assertEquals(0, stepCountData.getExecStepCount());
			assertEquals(1, stepCountData.getCommentStepCount());
			assertEquals(0, stepCountData.getEmptyStepCount());
			// 【後処理】
		}
	}

	@Nested
	class IncrementEmptyStepCount {
		@DisplayName("incrementEmptyStepCountメソッド実装時、 emptyStepCount（空行数）の値がインクリメントされる")
		@Test
		void success1() {
			// 【事前準備】
			// 【実行】
			stepCountData.incrementEmptyStepCount();
			// 【検証】
			assertEquals(0, stepCountData.getTotalStepCount());
			assertEquals(0, stepCountData.getExecStepCount());
			assertEquals(0, stepCountData.getCommentStepCount());
			assertEquals(1, stepCountData.getEmptyStepCount());
			// 【後処理】
		}
	}

	@Nested
	@Disabled
	class ToString {
		@DisplayName("StepCountDataで定義されている文字列表現のテスト（初期値）")
		@Test
		void success1() {
			// 【事前準備】
			// 【実行】
			// 【検証】
			// FIXME inputFileの文字列表現をどう確認する？？
			assertEquals(
					"stepCountData [totalStepCount=0, execStepCount=0, commentStepCount=0, emptyStepCount=0, canWriteStepCount=true]",
					stepCountData.toString());
			// 【後処理】
		}

		@DisplayName("StepCountDataで定義されている文字列表現のテスト（インクリメント実施後）")
		@Test
		void success2() {
			// 【事前準備】
			stepCountData.incrementTotalStepCount();
			stepCountData.incrementExecStepCount();
			stepCountData.incrementCommentStepCount();
			stepCountData.incrementEmptyStepCount();
			// 【実行】
			// 【検証】
			// FIXME inputFileの文字列表現をどう確認する？？
			assertEquals(
					"stepCountData [totalStepCount=1, execStepCount=1, commentStepCount=1, emptyStepCount=1, canWriteStepCount=ture]",
					stepCountData.toString());
			// 【後処理】
		}
	}

	@Nested
	class OutputDataCommaDelimited {
		@DisplayName("ファイル名／1ファイル単位の総行数／実行行数／コメント行数／空行数をカンマ区切りにした文字列のテスト（初期値）")
		@Test
		void success1() {
			// 【事前準備】
			// 【実行】
			// 【検証】
			assertEquals(
					"/Applications/Eclipse_2021-06.app/Contents/workspace/stepCount_ver2/work/input/character/Characte.java,0,0,0,0",
					stepCountData.outputDataCommaDelimited());
			// 【後処理】
		}

		@DisplayName("ファイル名／1ファイル単位の総行数／実行行数／コメント行数／空行数をカンマ区切りにした文字列のテスト（インクリメント実施後）")
		@Test
		void success2() {
			// 【事前準備】
			stepCountData.incrementTotalStepCount();
			stepCountData.incrementExecStepCount();
			stepCountData.incrementCommentStepCount();
			stepCountData.incrementEmptyStepCount();
			// 【実行】
			// 【検証】
			assertEquals(
					"/Applications/Eclipse_2021-06.app/Contents/workspace/stepCount_ver2/work/input/character/Characte.java,1,1,1,1",
					stepCountData.outputDataCommaDelimited());
			// 【後処理】
		}

		@DisplayName("ファイル名,1ファイル単位の総行数／実行行数／コメント行数／空行数をカンマ区切りにした文字列のテスト（ステップ数ファイル書き込み不可）")
		@Test
		void success3() {
			// 【事前準備】
			stepCountData.incrementTotalStepCount();
			stepCountData.incrementExecStepCount();
			stepCountData.incrementCommentStepCount();
			stepCountData.incrementEmptyStepCount();
			stepCountData.setCanWriteStepCount(false);
			// 【実行】
			// 【検証】
			assertEquals(
					"/Applications/Eclipse_2021-06.app/Contents/workspace/stepCount_ver2/work/input/character/Characte.java(Failure to tally the number of steps),0,0,0,0",
					stepCountData.outputDataCommaDelimited());
			// 【後処理】
		}
	}
}
