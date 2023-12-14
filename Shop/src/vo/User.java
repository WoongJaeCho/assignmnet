package vo;

public class User {
	
	private String id;
	private String pw;
	private String name;
	
	public User(String id, String pw, String name) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
	}

	@Override
	public String toString() {
		return "%s\t%s\t%s\n".formatted(id,pw,name);
	}

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public String getName() {
		return name;
	}
	
	public String saveToData() {
		return "%s/%s/%s\n".formatted(id,pw,name);
	}
}
