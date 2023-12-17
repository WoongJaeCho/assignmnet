package dao;

import java.util.ArrayList;

import Utils.InputManger;
import vo.Item;

public class ItemDAO {
	
	private ArrayList<Item> iList;
	private ArrayList<String> cateList;
	private int cnt;
	
	public void init() {
		iList = new ArrayList<Item>();
		cateList = new ArrayList<String>();
	}
	
	public int priceOneItem(String name) {
		for(Item i : iList) {
			if(i.getName().equals(name)) {
				return i.getPrice();
			}
		}
		return -1;
	}
	
	public void addOneUserShoppingList(CartDAO cDAO, String id) {
		String category = printCategory();
		if(category == null) return;
		String item = InputManger.getStringValue("아이템");
		int idx = checkDuplItem(item);
		if(idx==-1) {
			System.out.println("해당 아이템이 없습니다.");
			return;
		}
		cDAO.addOneUserOneCart(id, item);
		System.out.printf("[ 장바구니 (%s) 추가 완료 ]\n",item);
	}

	public int oneItemPriceTotal(String name) {
		int total=0;
		for(Item i : iList) {
			if(i.getName().equals(name)) {
				total += i.getPrice();
			}
		}
		return total;
	}
	
	private int checkDuplicate(String category) {
		if(cateList.size()==0) return -1;
		for(int i =0; i<cateList.size() ; i+=1) {
			if(cateList.get(i).equals(category)) {
				return i;
			}
		}
		return -1;
	}
	
	private void printCategoryList() {
		if(cateList.size()!=0) {
			for(int i=0;i<cateList.size();i+=1 ) {
				System.out.printf("(%d) %s\n",(i+1),cateList.get(i));
			}
		}
	}
	
	private void categoryChange(String category, String newCategory) {
		for(int k=0; k<cnt; k+=1) {
			if(iList.get(k).getCategory().equals(category)) {
				String name = iList.get(k).getName();
				int price = iList.get(k).getPrice();		
				iList.set(k, new Item(name, price, newCategory));
			}
		}
	}
	
	public void categoryList(CartDAO cDAO) {
		while(true) {
			if(cateList.size()!=0) printCategoryList();
			System.out.println("[1.카테고리 추가] [2.카테고리 수정] [3.카테고리 삭제] [0.뒤로가기]");
			int sel = InputManger.getIntValue("메뉴 선택", 1, 3, 0);
			if(sel == 1) {
				System.out.println("[ 카테고리 추가 ]"); // 추가(중복 체크)
				String category = InputManger.getStringValue("카테고리");
				int idx = checkDuplicate(category);
				if(idx!=-1) {
					System.out.println("중복된 카테고리가 있습니다.");
					continue;
				}
				cateList.add(category);
				//cnt+=1;
				System.out.println("[ 카테고리 추가 완료 ]");
			} else if(sel == 2) {
				System.out.println("[ 카테고리 수정 ]");
				if(cateList.size()!=0) {
					System.out.println("(0) 뒤로가기");
					printCategoryList();
				} else {
					InputManger.noDataSign();
					continue;
				}
				sel = InputManger.getIntValue("수정할 카테고리 번호 선택", 1, cateList.size(), 0)-1;
				if(sel == -1) continue;;
				String category = cateList.get(sel);
				String newCategory = InputManger.getStringValue("[수정] 카테고리");
				int idx = checkDuplicate(newCategory);
				if(idx!=-1) {
					System.out.println("중복된 카테고리가 있습니다.");
					continue;
				}
				categoryChange(category, newCategory);
				cateList.set(sel, newCategory);
				System.out.println("[ 카테고리 수정 완료 ]");
			} else if(sel == 3) {
				System.out.println("[ 카테고리 삭제 ]");
				if(cateList.size()!=0) {
					System.out.println("(0) 뒤로가기");
					printCategoryList();
				} else {
					InputManger.noDataSign();
					continue;
				}
				sel = InputManger.getIntValue("삭제할 카테고리 선택", 1, cateList.size(), 0)-1;
				if(sel == -1) continue;;
				String category = cateList.get(sel);
				deleteitems(cDAO,category);
				cateList.remove(sel);
				System.out.println("[ 카테고리 삭제 완료 ]");
			} else if(sel == 0) {
				return;
			}
		}
	}
	private void deleteitems(CartDAO cDAO, String category) {
		if(iList.size()==0) return;
		for(int k=0; k<iList.size() ;k+=1) {
			if(iList.get(k).getCategory().equals(category)) {
				cDAO.deleteItems(iList.get(k).getName());
				iList.remove(k);
				k-=1;
			}
		}
	}
	
	private int printItemList(String category) {
		int cnt=0;
		if(iList.size()!=0) {
			System.out.printf("    %-5s\t%s\n","Item","Price");
			for(int i=0;i<iList.size();i+=1 ) {
				if(iList.get(i).getCategory().equals(category)) {
					System.out.printf("(%d) %-5s\t%d원\n",(cnt+1),iList.get(i).getName(),iList.get(i).getPrice());
					cnt+=1;
				}
			}
			return cnt;
		} else return 0;
	}
	
	private int checkDuplItem(String item) {
		if(iList.size()==0) return -1;
		for(int k=0; k<iList.size();k+=1) {
			if(iList.get(k).getName().equals(item)) {
				return k;
			}
		}
		return -1;
	}
	
	private int getIdxToName(String msg) {
		String name = InputManger.getStringValue(msg);
		for(int k =0; k<iList.size() ; k+=1 ) {
			if(iList.get(k).getName().equals(name)) {
				return k;
			}
		}
		return -1;
	}
	
	private String printCategory() {
		System.out.println("(0) 뒤로가기");
		printCategoryList();
		int sel = InputManger.getIntValue("카테고리 선택", 1, cateList.size(), 0)-1;
		if(sel == -1) return null;
		String category = cateList.get(sel);
		printItemList(category);
		return category;
	}
	
	public void itemList(CartDAO cDAO) {
		if(cateList.size()==0) {
			System.err.println("카테고리 입력할 것.");
			return;
		}
		
		if(cnt==0) {
			InputManger.noDataSign();
			return;
		}
		System.out.println("[ 아이템 전체 목록 ]");
		for(String cate : cateList) {
			System.out.println("==[ "+cate+" ]==");
			for(Item i : iList) {
				if(i.getCategory().equals(cate)) {
					System.out.print(i);
				}
			}
		}
		while(true) {
			System.out.println("[1.아이템 추가] [2.아이템 가격 수정] [3.아이템 삭제] [0.뒤로가기]");
			int sel = InputManger.getIntValue("메뉴 선택", 1, 4, 0);
			if(sel == 1) {
				System.out.println("[ 아이템 추가 ]");
				String category = printCategory();
				if(category == null) continue;
				String item = InputManger.getStringValue("아이템");
				int idx = checkDuplItem(item);
				if(idx!=-1) {
					System.out.println("중복된 아이템이 있습니다.");
					continue;
				}
				int price = InputManger.getIntValue("가격");
				Item i = new Item(item, price, category);
				iList.add(i);
				cnt+=1;
				System.out.println("[ 아이템 추가 완료 ]");
			} else if(sel == 2) {
				System.out.println("[ 아이템 수정 ]");
				if(cnt==0) {
					InputManger.noDataSign();
					continue;
				}
				String category = printCategory();
				if(category == null) continue;
				int index = getIdxToName("수정할 아이템 이름");
				if(index == -1) {
					System.out.println("해당 아이템이 존재하지 않습니다.");
					continue;
				}
				String item = iList.get(index).getName();
				int price = InputManger.getIntValue("[수정] 가격");
				iList.set(index, new Item(item, price, category));
				System.out.println("[ 아이템 수정 완료 ]");
			} else if(sel == 3) {
				if(cnt==0) {
					InputManger.noDataSign();
					continue;
				}
				System.out.println("[ 아이템 삭제 ]");
				String category = printCategory();
				if(category == null) continue;
				System.out.println("(0) 뒤로가기");
				printItemList(category);
				int index = getIdxToName("삭제할 아이템 이름");
				if(index == -1) {
					System.out.println("해당 아이템이 존재하지 않습니다.");
					continue;
				}
				String item = iList.get(index).getName();
				cDAO.deleteItems(item);
				iList.remove(index);
				cnt-=1;
				System.out.println("[ 아이템 삭제 완료 ]");
			} else if(sel == 0) {
				return;
			}
		}
	}
	
	public String saveToData() {
		if(cnt==0) {
			InputManger.noDataSign();
			return null;
		}
		String data="";
		for(Item i : iList) {
			data += i.saveToData();
		}
		return data;
	}
	
	private void loadDataCategory() {
		if(cnt==0) return;
		int idx=1;
		cateList.add(iList.get(0).getCategory());
		for(int k = 1 ; k<cnt ; k+=1) {
			boolean check = true;
			for(int j = 0 ; j<idx ; j+=1) {
				if(cateList.get(j).equals(iList.get(k).getCategory())) {
					check = false;
				}
			}
			if(check) {
				cateList.add(iList.get(k).getCategory());
				idx+=1;
			}
		}
	}
	
	public void loadToFile(String data) {
		String[] temp = data.split("\n");
		cnt = temp.length;
		
		for(int k=0 ; k<cnt ; k+=1) {
			String[] info = temp[k].split("/");
			
			Item i = new Item(info[0], Integer.parseInt(info[1]), info[2]);
			iList.add(i);
		}	
		loadDataCategory();
	}
}
