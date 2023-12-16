package Controller;

import Utils.FileManager;
import Utils.InputManger;
import dao.CartDAO;
import dao.ItemDAO;
import dao.UserDAO;

public class ShopController {

	private ItemDAO iDAO;
	private UserDAO uDAO;
	private CartDAO cDAO;
	private FileManager fm;
	private String log;
	
	public ShopController() {
		iDAO = new ItemDAO();
		iDAO.init();
		uDAO = new UserDAO();
		uDAO.init();
		cDAO = new CartDAO();
		cDAO.init();
		fm = new FileManager();
		fm.loadToFile(uDAO, iDAO, cDAO);
	}
	
	private void mainMenu() {
		while(true) {
			System.out.println("[1.가입] [2.탈퇴] [3.로그인] [4.로그아웃]" + "\n[100.관리자] [0.종료] ");  
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
					userMenu();
				}
			} else if(sel == 4) {
				System.out.println("[ 로그아웃 ]");
				if(log==null) {
					System.out.println("로그인 후 이용해 주세요");
					continue;
				}
				log=null;
			} else if(sel == 100) {
				adminMenu();
			} else if(sel == 0) {
				System.out.println("[ 프로그램 종료 ]");
				return;
			}
		}
	}
	private void userMenu() {
		while(true) {
			System.out.println("[ "+log+"님 로그인 ]");
			System.out.println("[1.쇼핑] [2.장바구니목록] [0.뒤로가기]");
			int sel = InputManger.getIntValue("메뉴 선택", 1, 2, 0);
			if(sel == 1) {
				System.out.println("[ 쇼 핑 ]"); // 카테고리 -> 아이템 출력
			} else if(sel == 2) {
				System.out.println("[ 장바구니 목록 ]"); // 해당 log 장바구니 목록 출력
				cartMenu();
			} else if(sel == 0) {
				return;
			}
		}
	}
	
	private void cartMenu() {
		while(true) {
			System.out.println("[1.내 장바구니] [2.삭제] [3.구입] [0.뒤로가기]");
			int sel = InputManger.getIntValue("메뉴 선택", 1, 3, 0);
			if(sel == 1) {
				System.out.println("[ 내 장바구니 ]"); // 장바구니 목록 출력 
				cDAO.printOneCartList(iDAO,log);
			} else if(sel == 2) {
				System.out.println("[ 삭 제 ]"); // 전체삭제,개별삭제
				cDAO.deleteAllCartList(iDAO,log);
			} else if(sel == 3) {
				System.out.println("[ 구 입 ]"); // 장바구니 목록 선택, 구입시 장바구니 목록 삭제
				cDAO.purchaseAllCartList(iDAO, log);
			} else if(sel == 0) {
				return;
			}
		}
	}
	
	private void adminMenu() {
		while(true) {
			System.out.println("[ 관리자 모드 ]");
			System.out.println("[1.아이템관리] [2.카테고리관리] [3.장바구니관리] [4.유저관리]\n[5.파일 저장] [6.파일 로드] [0.뒤로가기] ");
			int sel = InputManger.getIntValue("메뉴 선택", 1, 6, 0);
			if(sel == 1) {
				System.out.println("[ 아이템 관리 ]"); // 아이템 추가(중복 체크),수정(중복 체크),삭제(장바구니 같이 삭제)
				iDAO.itemList(cDAO);
			} else if(sel == 2) {
				System.out.println("[ 카테고리 관리 ]"); // 카테고리 추가(중복 체크),수정(중복 체크),삭제(아이템, 장바구니 같이 삭제)
				iDAO.categoryList(cDAO);
			} else if(sel == 3) {
				System.out.println("[ 장바구니 관리 ]"); // 전체 장바구니 목록 출력
				cDAO.printCartList();
			} else if(sel == 4) {
				System.out.println("[ 유저 관리 ]"); // 유저 목록 출력 / 유저 삭제(장바구니 같이 삭제)
				uDAO.adminControllToUser(cDAO);
			} else if(sel == 5) {
				fm.saveToFile(uDAO, iDAO, cDAO);
			} else if(sel == 6) {
				fm.loadToFile(uDAO, iDAO, cDAO);
			} else if(sel == 0) {
				return;
			}
		}
	}
	
	private void init() {
		
	}
	
	public void run() {
		init();
		mainMenu();
		
	}
}