package school_조웅재_ver4;

public class Subject {
	private int stuNo;
	private String subName;
	private int score;
	
	public Subject(int stuNo, String subName, int score) {
		this.stuNo = stuNo;
		this.subName = subName;
		this.score = score;
	}
	
	public int getStuNo() {
		return stuNo;
	}

	public String getSubName() {
		return subName;
	}

	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return subName + " " + score + "점";
	}
	
	public String saveToData() {
		return "%d/%s/%d\n".formatted(stuNo,subName,score);
	}
}
