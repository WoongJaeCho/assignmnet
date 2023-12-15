package Utils;

import java.util.Scanner;

public class InputManger {
	
	private static Scanner scan = new Scanner(System.in);
	
	private static InputManger instance = new InputManger();
	
	public static InputManger getinstance() {
		return instance;
	}
	
	public static int getIntValue(String msg, int start, int end, int ex) {
		while(true) {
			System.out.printf("▶︎ %s : ",msg);
			try {
				int input = scan.nextInt();
				if(input == ex) return input;
				if(input<start || input>end) {
					System.out.println("입력 범위 오류");
					continue;
				}
				return input;
			} catch (Exception e) {
				System.out.println("정수 값 입력할 것.");
			} finally {
				scan.nextLine();
			}
			
		}
	}
	
}
