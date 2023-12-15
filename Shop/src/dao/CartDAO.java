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
		if(cnt==0) {
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
