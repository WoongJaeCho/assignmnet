package Utils;

import java.util.Scanner;

public class InputManger {
	
	private static Scanner scan = new Scanner(System.in);
	
	private static InputManger instance = new InputManger();
	
	public static InputManger getinstance() {
		return instance;
	}
	
	
}
