package programmingTraining4Java.answer.kadai2;

public class Kadai2_12 {
	public static void main(String[] args) {
		// 1行目
		System.out.printf("   |");
		for (int i = 1; i <= 9 ; i++) {
			System.out.printf("%3d",i);
		}
		System.out.printf("%n");
		
		// 2行目
		System.out.println("---+---------------------------");
		
		// iの段
		for (int i = 1; i <= 9 ; i++) {
			System.out.printf("%2d |",i);
			for (int j = 1; j <= 9; j++) {
				System.out.printf("%3d",i*j);
			}
			System.out.printf("%n");
		}
	}
}
