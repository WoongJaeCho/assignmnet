package dao;

import java.util.ArrayList;

import Utils.InputManger;
import vo.User;

public class UserDAO {
	
	private ArrayList<User> uList;
	private int cnt;
	
	public void init() {
		uList = new ArrayList<User>();
	}
	
	private User checkId(String id) {
		if(cnt==0) return null;
		for(User u : uList) {
			if(u.getId().equals(id)) {
				return u;
			}
		}
		return null;
	}
	public void joinUser() {
		String id = InputManger.getStringValue("아이디");
		User u = checkId(id);
		if(u!=null) {
			System.err.println("중복된 ID가 있습니다.");
			return;
		}
		String pw = InputManger.getStringValue("비밀번호");
		String name = InputManger.getStringValue("이름");
		
		User user = new User(id, pw, name);
		uList.add(user);
		cnt+=1;
		System.out.printf("[ %s 님 가입 완료 ]\n",name);
	}
	
	private int checkIdx(String id) {
		for(int i =0; i<cnt ; i+=1) {
			if(uList.get(i).getId().equals(id)) {
				return i;
			}
		}
		return 0;
	}
	
	public void deleteUser(String id) {
		if(id==null) {
			System.out.println("로그인 후 이용해 주세요");
			return;
		}
		User u = checkId(id);
		String pw = InputManger.getStringValue("비밀번호");
		if(!u.getPw().equals(pw)) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		int delIdx = checkIdx(id);
		uList.remove(delIdx);
		cnt-=1;
		System.out.printf("[ %s 님 탈퇴 완료 ]\n",id);
	}
	public String login() {
		if(cnt == 0) {
			InputManger.noDataSign();
			return null;
		}
		String id = InputManger.getStringValue("아이디");
		String pw = InputManger.getStringValue("비밀번호");
		User u = checkId(id);
		if(u==null || !u.getPw().equals(pw) ) {
			System.err.println("ID 또는 PW를 확인하세요.");
			return null;
		}
		return id;
	}
}
