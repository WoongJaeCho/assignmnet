package Controller;

import Utils.FileManager;
import Utils.InputManger;
import dao.ItemDAO;
import dao.UserDAO;

public class ShopController {

	private ItemDAO iDAO;
	private UserDAO uDAO;
	private FileManager fm;
	private String log;
	
	public ShopController() {
		iDAO = new ItemDAO();
		iDAO.init();
		uDAO = new UserDAO();
		uDAO.init();
		fm = new FileManager();
	}
	
	private void mainMenu() {
		System.out.println("[1.가입] [2.탈퇴] [3.로그인] [4.로그아웃]" + "\n[100.관리자] [0.종료] ");  
	}
	private void userMenu() {
		System.out.println("[1.쇼핑] [2.장바구니목록] [0.뒤로가기]");
	}
	
	private void cartMenu() {
		System.out.println("[1.내 장바구니] [2.삭제] [3.구입] [0.뒤로가기]");
	}
	
	private void adminMenu() {
		System.out.println("[1.아이템관리] [2.카테고리관리] [3.장바구니관리] [4.유저관리]\n[5.파일 저장] [6.파일 로드] [0.뒤로가기] ");
	}
	
	private void init() {
		
	}
	
	public void run() {
		init();
		while(true) {
			mainMenu();
			int sel = InputManger.getIntValue("메뉴 선택", 0, 4, 100);
			if(sel == 1) {
				System.out.println("[ 가 입 ]");
				uDAO.joinUser();
			} else if(sel == 2) {
				System.out.println("[ 탈 퇴 ]");
				uDAO.deleteUser(log);
			} else if(sel == 3) {
				System.out.println("[ 로그인 ]");
				log = uDAO.login();
				if(log!=null) {
					while(true) {
						System.out.println("[ "+log+"님 로그인 ]");
						userMenu();
						sel = InputManger.getIntValue("메뉴 선택", 1, 2, 0);
						if(sel == 1) {
							System.out.println("[ 쇼 핑 ]");
						} else if(sel == 2) {
							System.out.println("[ 장바구니 목록 ]");
							while(true) {
								cartMenu();
								sel = InputManger.getIntValue("메뉴 선택", 1, 3, 0);
								if(sel == 1) {
									System.out.println("[ 내 장바구니 ]");
								} else if(sel == 2) {
									System.out.println("[ 삭 제 ]");
								} else if(sel == 3) {
									System.out.println("[ 구 입 ]");
								} else if(sel == 0) {
									break;
								}
							}
						} else if(sel == 0) {
							break;
						}
					}
				}
			} else if(sel == 4) {
				System.out.println("[ 로그아웃 ]");
				if(log==null) {
					System.out.println("로그인 후 이용해 주세요");
					continue;
				}
				log=null;
			} else if(sel == 100) {
				while(true) {
					System.out.println("[ 관리자 모드 ]");
					adminMenu();
					sel = InputManger.getIntValue("메뉴 선택", 1, 6, 0);
					if(sel == 1) {
						System.out.println("[ 아이템 관리 ]");
					} else if(sel == 2) {
						System.out.println("[ 카테고리 관리 ]");
					} else if(sel == 3) {
						System.out.println("[ 장바구니 관리 ]");
					} else if(sel == 4) {
						System.out.println("[ 유저 관리 ]");
					} else if(sel == 5) {
						
					} else if(sel == 6) {
						
					} else if(sel == 0) {
						break;
					}
				}
			} else if(sel == 0) {
				System.out.println("[ 프로그램 종료 ]");
				return;
			}
		}
	}
}