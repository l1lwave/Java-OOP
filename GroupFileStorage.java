package lesson3firstex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class GroupFileStorage {
	
	public String toStringRepresentation(Student student) {
        return student.getName() + "," + student.getLastName() + "," + student.getGender() + "," + student.getId() + "," + student.getGroupName();
    }
	
	public void saveGroupToCSV(Group gr) {
		String f = gr.getGroupName() + ".csv";
		
		File file = new File(f);
		Student[] students = gr.getStudents();
		
		StringBuilder csvRepresentation = new StringBuilder();
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Student student : students) {
                if (student != null) {
                    writer.write(toStringRepresentation(student));
                    writer.newLine();
                }
            }
            System.out.println("Saving group to csv complete");
        } catch (IOException e) {
            e.printStackTrace();
        }	
	}
	
	public Group loadGroupFromCSV(File file) throws GroupOverflowException {
		Group group = new Group();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String temp = "";
			for (;;) {
				temp = br.readLine();
			
				if (temp == null) {
					break;
				}
				String[] studentData = temp.split(",");
				Gender gender = null;
				if (studentData.length == 5) {
					if(studentData[2].equals("MAN")) {
						gender = Gender.MAN;
					} else {
						gender = Gender.WOMAN;
					}
                    Student student = new Student(studentData[0], studentData[1], gender, Integer.parseInt(studentData[3]), studentData[4]);
                    try {
                    	group.addStudent(student);
                    } catch(GroupOverflowException e){
            			System.out.println(e.getMessage());
            		}
                }
			}
		} catch (IOException e) {
			 e.printStackTrace();
		}
		return group;
	}
	
//	public File findFileByGroupName(String groupName, File workFolder) {
//		
//	}
}