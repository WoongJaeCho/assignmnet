package school_조웅재_ver4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Util {
	
	private static final String CUR_PATH = System.getProperty("user.dir")+"/src/"+new Util().getClass().getPackageName()+"/";
	private static Scanner scan = new Scanner(System.in);
	
	private static Util instance = new Util();
	
	public static Util getInstance() {
		return instance;
	}
	
	public static void noDatamsg() {
		System.out.println("[ no student data ]");
	}
	
	public static int getRandValue() {
		Random rand = new Random();
		
		return rand.nextInt(51)+50;
	}
	
	public static String getStringValue(String msg) {
		System.out.print("▶ "+msg);
		String input = scan.next();
		scan.nextLine();
		
		return input;
	}
	
	public static int getValue(String msg, int start, int end) {
		while(true) {
			System.out.print("▶ "+msg);
			try{
				int input = scan.nextInt();
				if(input<start || input > end) {
					System.out.println("입력 범위 오류");
					continue;
				}
				return input;
			}catch(Exception e){
				System.out.println("정수 값 입력");
			}finally {
				scan.nextLine();
			}
		}
	}
	
	private static void saveToFileData(String fileName, String data) {
		try(FileWriter fw = new FileWriter(CUR_PATH + fileName)){
			fw.write(data);
			System.out.println(fileName+"파일 저장 성공");
		} catch (IOException e) {
			System.out.println(fileName+"파일 저장 실패");
		}
	}
	
	public static void saveToFile(StudentDAO stuDAO, SubjectDAO subDAO) {
		String stuData = stuDAO.saveToData();
		String subData = subDAO.saveToData();
		saveToFileData("student.txt", stuData);
		saveToFileData("subject.txt", subData);
	}
	
	private static String loadToFileData(String fileName) {
		try(FileReader fr = new FileReader(CUR_PATH + fileName);
				BufferedReader br = new BufferedReader(fr);){
			String data="";
			while(true) {
				int read = br.read();
				if(read == -1) break;
				
				data += (char)read;
			}
			System.out.println(fileName+"파일 로드 성공");
			return data;
		} catch (IOException e) {
			System.out.println(fileName+"파일 로드 실패");
			return null;
		}
	}
	
	public static void loadToFile(StudentDAO stuDAO, SubjectDAO subDAO) {
		String studata = loadToFileData("student.txt");
		String subdata = loadToFileData("subject.txt");
		stuDAO.loadToData(studata);
		subDAO.loadToData(subdata);
		stuDAO.updataMaxNo();
	}
}
