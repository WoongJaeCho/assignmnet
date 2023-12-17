package dao;

import java.util.ArrayList;

import Utils.InputManger;
import vo.Cart;

public class CartDAO {

	private ArrayList<Cart> cList;
	private int cnt;
	
	public void init() {
		cList = new ArrayList<Cart>();
	}
	
	public void addOneUserOneCart(String userId, String itemName) {
		cList.add(new Cart(userId, itemName));
		cnt+=1;
	}
	
	private int countOneUserCart(String id) {
		int count=0;
		for(Cart c : cList) {
			if(c.getUserId().equals(id)) {
				count+=1;
			}
		}
		return count;
	}
	
	private void deleteAllCart(String id) {
		for(int i=0;i<cnt;i+=1) {
			if(cList.get(i).getUserId().equals(id)) {
				cList.remove(i);
				cnt-=1;
				i-=1;
			}
		}
	}
	
	private void deleteOneCart(String id,String name) {
		boolean check = false;
		for(Cart c : cList) {
			if(c.getItemName().equals(name)) {
				check =true;
			}
		}
		if(check) {
			for(int i=0;i<cnt;i+=1) {
				if(cList.get(i).getUserId().equals(id) && cList.get(i).getItemName().equals(name)) {
					cList.remove(i);
					cnt-=1;
					System.out.printf("[ %s 삭제 완료 ]\n",name);
					return;
				}
			}
		}else {
			System.err.println("해당 아이템이 없습니다.");
		}
	}
	
	public void purchaseAllCartList(ItemDAO iDAO,String id) {
		int count = countOneUserCart(id);
		if(count==0) {
			InputManger.noDataSign();
			return;
		}
		System.out.println("========================");
		System.out.printf("%s\t%s\t%s\n","Id","Item","가격");
		System.out.println("------------------------");
		int total = 0;
		for(Cart c : cList) {
			int price =0;
			if(c.getUserId().equals(id)) {
				price += iDAO.oneItemPriceTotal(c.getItemName());
				total += price;
				System.out.printf("%s\t%s\t%d원\n",c.getUserId(),c.getItemName(),price);
			}
		}
		System.out.println("------------------------");
		System.out.printf("===[ 합계 : %d원 ]===\n",total);
		System.out.println("========================");
		System.out.println("[1.전체구입] [2.개별구입] [0.뒤로가기]");
		int sel = InputManger.getIntValue("메뉴 선택", 1, 2, 0);
		if(sel == 1) {
			deleteAllCart(id);
			System.out.println("[ 전체 장바구니 구입 완료 ]");
		} else if(sel == 2) {
			String name = InputManger.getStringValue("구입할 아이템");
			deleteOneCart(id,name);
		} else if(sel == 0) {
			return;
		}
	}
	
	public void deleteAllCartList(ItemDAO iDAO,String id) {
		int count = countOneUserCart(id);
		if(count==0) {
			InputManger.noDataSign();
			return;
		}
		System.out.println("========================");
		System.out.printf("%s\t%s\t%s\n","Id","Item","가격");
		System.out.println("------------------------");
		for(Cart c : cList) {
			int price =0;
			if(c.getUserId().equals(id)) {
				price += iDAO.oneItemPriceTotal(c.getItemName());
				System.out.printf("%s\t%s\t%d원\n",c.getUserId(),c.getItemName(),price);
			}
		}
		System.out.println("========================");
		System.out.println("[1.전체삭제] [2.개별삭제] [0.뒤로가기]");
		int sel = InputManger.getIntValue("메뉴 선택", 1, 2, 0);
		if(sel == 1) {
			deleteAllCart(id);
			System.out.println("[ 전체 장바구니 삭제 완료 ]");
		} else if(sel == 2) {
			String name = InputManger.getStringValue("삭제할 아이템");
			deleteOneCart(id,name);
		} else if(sel == 0) {
			return;
		}
	}
	
	public void printOneCartList(ItemDAO iDAO, String id) {
		int count = countOneUserCart(id);
		if(count==0) {
			InputManger.noDataSign();
			return;
		}
		System.out.println("========================");
		System.out.printf("%s\t%s\t%s\n","Id","Item","가격");
		System.out.println("------------------------");
		int total = 0;
		for(Cart c : cList) {
			int price =0;
			if(c.getUserId().equals(id)) {
				price += iDAO.oneItemPriceTotal(c.getItemName());
				total += price; 
				System.out.printf("%s\t%s\t%d원\n",c.getUserId(),c.getItemName(),price);
			}
		}
		System.out.println("------------------------");
		System.out.printf("===[ 합계 : %d원 ]===\n",total);
		System.out.println("========================");
	}
	
	public void printCartList() {
		if(cnt==0) {
			InputManger.noDataSign();
			return;
		}
		System.out.println("========================");
		System.out.printf("%s\t\t%s\n","Id","Item");
		System.out.println("------------------------");
		for(Cart c : cList) {
			System.out.println(c);
		}
		System.out.println("========================");
	}
	
	public void deleteOneUser(String id) {
		int count = countOneUserCart(id);
		if(count==0) {
			InputManger.noDataSign();
			return;
		}
		for(int i=0;i<cnt;i+=1) {
			if(cList.get(i).getUserId().equals(id)) {
				cList.remove(i);
				i-=1;
				cnt-=1;
			}
		}
	}
	
	public void deleteItems(String itemName) {
		for(int k=0; k<cList.size();k+=1) {
			if(cList.get(k).getItemName().equals(itemName)) {
				cList.remove(k);
				k-=1;
			}
		}
	}
	
	public String saveToData() {
		if(cnt==0) {
			InputManger.noDataSign();
			return null;
		}
		String data="";
		for(Cart c : cList) {
			data += c.saveToData();
		}
		return data;
	}
	
	public void loadToFile(String data) {
		String[] temp = data.split("\n");
		cnt = temp.length;
		
		for(int i=0 ; i<cnt ; i+=1) {
			String[] info = temp[i].split("/");
			
			Cart c = new Cart(info[0], info[1]);
			cList.add(c);
		}
			
	}
}
