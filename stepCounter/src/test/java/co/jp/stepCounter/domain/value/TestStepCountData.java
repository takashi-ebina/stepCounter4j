package co.jp.stepCounter.domain.value;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TestStepCountData {
	private File inputFile;

	@BeforeEach
	void setUp() {
		inputFile = new File(
				"/Applications/Eclipse_2021-06.app/Contents/workspace/stepCount_ver2/work/input/character/Characte.java");
	}

	@Nested
	@Disabled
	class ToString {
		@DisplayName("【正常系】StepCountDataで定義されている文字列表現のテスト")
		@Test
		void success1() {
			// 【事前準備】
//			StepCountData stepCountData = new StepCountData(1364654354,1,56889554,22455665,true,inputFile);
			// 【実行】
			// 【検証】
			// FIXME inputFileの文字列表現をどう確認する？？
//			assertEquals(
//					"stepCountData [totalStepCount=1364654354, execStepCount=1, commentStepCount=56889554, emptyStepCount=22455665, canWriteStepCount=true]",
//					stepCountData.toString());
			// 【後処理】
		}
	}

	@Nested
	class OutputDataCommaDelimited {
		@DisplayName("【正常系】ファイル名／1ファイル単位の総行数／実行行数／コメント行数／空行数をカンマ区切りにした文字列のテスト")
		@Test
		void success1() {
			// 【事前準備】
			StepCountData stepCountData = new StepCountData(0,0,0,0,true,inputFile);
			// 【実行】
			// 【検証】
			assertEquals(
					"/Applications/Eclipse_2021-06.app/Contents/workspace/stepCount_ver2/work/input/character/Characte.java,0,0,0,0",
					stepCountData.outputDataCommaDelimited());
			// 【後処理】
		}

		@DisplayName("【正常系】ファイル名,1ファイル単位の総行数／実行行数／コメント行数／空行数をカンマ区切りにした文字列のテスト（ステップ数ファイル書き込み不可）")
		@Test
		void success3() {
			// 【事前準備】
			StepCountData stepCountData = new StepCountData(0,0,0,0,false,inputFile);
			// 【実行】
			// 【検証】
			assertEquals(
					"/Applications/Eclipse_2021-06.app/Contents/workspace/stepCount_ver2/work/input/character/Characte.java(Failure to tally the number of steps),0,0,0,0",
					stepCountData.outputDataCommaDelimited());
			// 【後処理】
		}
	}
}
