package school_조웅재_ver4;

import java.util.ArrayList;

public class StudentDAO {
	
	private ArrayList<Student> stuList;
	private int cnt;
	private int maxNo;
	
	public StudentDAO() {
		stuList = new ArrayList<Student>();
		maxNo = 1001;
	}
	
	public void OneSubjectToStudents(ArrayList<Subject> sub) {
		ArrayList<Student> temp = new ArrayList<Student>();
		
		for(int i =0; i<sub.size();i+=1) {
			for(int k =0; k<stuList.size();k+=1) {
				if(sub.get(i).getStuNo() == stuList.get(k).getStuNo()) {
					temp.add(stuList.get(k));
				}
			}
		}
		for(int i=0; i<temp.size();i+=1) {
			for(int k=0; k<temp.size()-1; k+=1) {
				if(temp.get(k).getStuName().compareTo(temp.get(k+1).getStuName())>0) {
					Student info = temp.get(k);
					temp.set(k, temp.get(k+1));
					temp.set(k+1, info);
				}
			}
		}
		for(Student stu : temp) {
			System.out.println(stu);
		}
		
	}
	
	public void printAllstudentscore(SubjectDAO subDAO) {
		if(cnt==0) return;
		ArrayList<Student> temp = new ArrayList<Student>();
		for(Student stu : stuList) {
			temp.add(stu);  
		}
		Double[] scores = new Double[cnt];
		
		int idx=0;
		for(Student stu : stuList) {
			scores[idx++] = subDAO.addOneStudentscore(stu);
		}
		
		for(int i=0; i<stuList.size(); i+=1) {
			double max=0;
			int max_idx=i;
			for(int k=i; k<stuList.size() ; k+=1) {
				if(max < scores[k]) {
					max = scores[k];
					max_idx=k;
				}
			}
			Student info = temp.get(i);
			temp.set(i, temp.get(max_idx));
			temp.set(max_idx, info); 
			
			double score = scores[i];
			scores[i] = scores[max_idx];
			scores[max_idx] = score;
		}
		for(Student stu : temp) {
			System.out.println("-------------------");
			System.out.println(stu);
			subDAO.printscore(stu);
			subDAO.printAvgScore(stu);
		}
	}
	
	public Student checkStuNo(int stuNo) {
		if(cnt==0) return null;
		for(Student stu : stuList) {
			if(stu.getStuNo() == stuNo) {
				return stu;
			}
		}
		return null;
	}
	
	public void deleteStudentOneSubject(SubjectDAO subDAO) {
		if(cnt==0) {
			Util.noDatamsg();
			return;
		}
		int stuNo = Util.getValue("학번 입력 : ", 1001, maxNo);
		Student stu = checkStuNo(stuNo);
		if(stu==null) {
			System.out.println("해당 학번이 존재하지 않습니다."); 
			return;
		}
		System.out.println(stu);
		subDAO.printscore(stu);
		subDAO.deleteOneStudentOneSubject(stu);
	}
	
	public void addOneStudentOneSubject(SubjectDAO subDAO) {
		if(cnt==0) {
			Util.noDatamsg();
			return;
		}
		int stuNo = Util.getValue("학번 입력 : ", 1001, maxNo);
		Student stu = checkStuNo(stuNo);
		if(stu==null) {
			System.out.println("해당 학번이 존재하지 않습니다."); 
			return;
		}
		System.out.println(stu);
		subDAO.printscore(stu);
		subDAO.addOneStudentOneSubject(stu);
	}
	
	private Student checkStudentId(String id) {
		if(cnt==0) return null;
		for(int i =0; i<cnt ; i+=1) {
			if(stuList.get(i).getStuId().equals(id)) {
				return stuList.get(i);
			}
		}
		return null;
	}
	
	public void addOneStudent() {
		String id = Util.getStringValue("ID 입력 : ");
		Student stu = checkStudentId(id); 
		if(stu != null) {
			System.out.println("중복된 ID가 있습니다.");
			return;
		}
		
		String name = Util.getStringValue("이름 입력 : ");
		
		Student s = new Student(maxNo++, name, id);
		stuList.add(s);
		System.out.println(s);
		cnt+=1;
		System.out.println(name + " 학생 추가 완료");
	}
	
	public void deleteOneStudent(SubjectDAO subDAO) {
		if(cnt==0) {
			Util.noDatamsg();
			return;
		}
		String id = Util.getStringValue("ID 입력 : ");
		Student stu = checkStudentId(id); 
		if(stu == null) {
			System.out.println("해당 ID가 없습니다.");
			return;
		}
		subDAO.deleteSubjectOfOneStudent(stu);
		stuList.remove(stu);
		System.out.println(stu);
		System.out.println(stu.getStuName() + " 학생 삭제 완료");
		cnt-=1;
	}
	
	public String saveToData() {
		if (cnt==0) return null;
		String data="";
		for(Student stu : stuList) {
			data += stu.saveToData();
		}
		return data;
	}
	
	public void loadToData(String data) {
		String[] temp = data.split("\n");
		cnt = temp.length;
		
		for(int i=0; i<temp.length ;i+=1) {
			String[] info = temp[i].split("/");
			
			Student stu = new Student(Integer.parseInt(info[0]), info[1], info[2]);
			stuList.add(stu);
		}
		
	}
	
	public void updataMaxNo() {
		if(cnt==0) return;
		int maxNo = 0;
		for(Student stu : stuList) {
			if(maxNo < stu.getStuNo()) {
				maxNo = stu.getStuNo();
			}
		}
		this.maxNo = maxNo+1;
	}
	
	
}
