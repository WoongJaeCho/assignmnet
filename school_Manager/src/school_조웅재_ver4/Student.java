package school_조웅재_ver4;

public class Student {
	private int stuNo;
	private String stuName;
	private String stuId;
	
	public Student(int stuNo, String stuName, String stuId) {
		this.stuNo = stuNo;
		this.stuName = stuName;
		this.stuId = stuId;
	}

	public int getStuNo() {
		return stuNo;
	}

	public String getStuName() {
		return stuName;
	}

	public String getStuId() {
		return stuId;
	}

	@Override
	public String toString() {
		return stuNo + "\t" + stuName + "\t" + stuId;
	}
	
	public String saveToData() {
		return "%d/%s/%s\n".formatted(stuNo,stuName,stuId);
	}
}
