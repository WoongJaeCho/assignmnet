package school_조웅재_ver4;

import java.util.ArrayList;

public class SubjectDAO {
	
	private ArrayList<Subject> subList;
	private int cnt;
	

	public SubjectDAO() {
		subList = new ArrayList<Subject>();
	}
	
	public void printOneSubject(StudentDAO stuDAO) {
		if(cnt==0) return;
		String name = Util.getStringValue("과목 이름 입력 : ");
		int cnt =0;
		for(Subject sub : subList) {
			if(sub.getSubName().equals(name)) {
				cnt+=1;
			}
		}
		if(cnt==0) {
			System.out.println("해당 과목은 학생 데이터가 없습니다.");
			return;
		}
		ArrayList<Subject> copy = new ArrayList<Subject>();
		for(Subject sub : subList) {
			if(sub.getSubName().equals(name)) {
				copy.add(sub);
			}
		}
		
		stuDAO.OneSubjectToStudents(copy);
		
	}
	
	public void printscore(Student stu) {
		for(Subject sub : subList) {
			if(sub.getStuNo() == stu.getStuNo()) {
				System.out.print(sub +" ");
			}
		}
	}
	
	public void printAvgScore(Student stu) {
		if(addOneStudentscore(stu)>0) {
			System.out.println();
			System.out.printf("평균 : %.2f점\n",addOneStudentscore(stu));
		} else {
			System.out.printf("[ no subject data ]\n");
		}
	}
	
	public double addOneStudentscore(Student stu) {
		int total=0;
		int cnt =0;
		for(Subject sub : subList) {
			if(stu.getStuNo() == sub.getStuNo()) {
				cnt+=1;
				total += sub.getScore();
			}
		}
		
		return total*1.0 / cnt;
	}
	
	public void printOneStudentScore(Student stu) {
		for(Subject sub : subList) {
			if(stu.getStuNo() == sub.getStuNo()) {
				System.out.print(sub+" ");
			}
		}
	}
	
	public void deleteOneStudentOneSubject(Student stu) {
		int cnt =0;
		for(Subject sub : subList) {
			if(sub.getStuNo() == stu.getStuNo()) {
				cnt+=1;
			}
		}
		if(cnt==0) {
			Util.noDatamsg();
			return;
		}
		String Name = Util.getStringValue("과목 입력 : ");
		for(Subject sub : subList) {
			if(sub.getStuNo() == stu.getStuNo() && sub.getSubName().equals(Name)) {
				subList.remove(sub);
				printscore(stu);
				System.out.println("과목 삭제 완료");
				return;
			}
		}
		System.out.println("해당 과목이 존재하지 않습니다.");
		return;
	}
	
	public void addOneStudentOneSubject(Student stu) {
		if(cnt==0) return;
		String Name = Util.getStringValue("과목 입력 : ");
		for(Subject sub : subList) {
			if(sub.getStuNo() == stu.getStuNo() && sub.getSubName().equals(Name)) {
					System.out.println("해당 과목이 존재합니다.");
					return;
			}
		}
		int score = Util.getRandValue();
		Subject sub = new Subject(stu.getStuNo(), Name, score);
		
		subList.add(sub);
		printscore(stu);
		System.out.println("과목 추가 완료");
	}
	
	public void deleteSubjectOfOneStudent(Student stu) {
		for(int i =0; i<subList.size();i+=1) {
			if(subList.get(i).getStuNo() == stu.getStuNo()) {
				subList.remove(i);
				i-=1;
			}
		}
	}
	
	public String saveToData() {
		if (cnt==0) return null;
		String data="";
		for(Subject sub : subList) {
			data += sub.saveToData();
		}
		return data;
	}
	
	public void loadToData(String data) {
		String[] temp = data.split("\n");
		cnt = temp.length;
		
		for(int i=0; i<temp.length ;i+=1) {
			String[] info = temp[i].split("/");
			
			Subject sub = new Subject(Integer.parseInt(info[0]), info[1], Integer.parseInt(info[2]));
			subList.add(sub);
		}
	}
}
