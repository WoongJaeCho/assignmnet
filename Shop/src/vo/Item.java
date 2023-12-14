package vo;

public class Item {
	
	private String name;
	private int price;
	private String category; // 카테고리 // 육류 , 과자 , 어류 , 과일 등등
	
	public Item(String name, int price, String category) {
		super();
		this.name = name;
		this.price = price;
		this.category = category;
	}

	@Override
	public String toString() {
		return "%s\t%s\t%s\n".formatted(name,price,category);
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getCategory() {
		return category;
	}
	
	public String saveToData() {
		return "%s/%s/%s\n".formatted(name,price,category);
	}
	
}
