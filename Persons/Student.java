package Persons;
import Assets.*;
import java.util.*;


public class Student extends Person  {

    //=====================( Attributes )=====================
    private String idHead = "std";
    public String  subjectId = "";
    public int numberOfSubjects;

    public String mark = "";
    public static int numberOfStudents;

    //=====================( Methods )=====================

    public Student()
    {
        //set files to read from
        setFilePath("Student.txt");
        setFolderPath(this);

        //start read and format data
        file = new FileHandler(getFolderPath()+getFilePath());
        formatData(file.readFile(),this);
    }

    public Student(String name, String level)
    {
        this();
        setName(name);
        setLevel(level);
        setId(idHead+numberOfUsers);
        setRole('s');
        setPassword(getId());
        newUser = true;
        numberOfSubjects=0;
        numberOfUsers++;
        Student.numberOfStudents++;
    }
    public boolean hasSubject(String selectedSubjectId)
    {
        //check if student has this subject to avoid assign it again
        for (String subject: subjectId.split(","))
        {
            if(selectedSubjectId.equals(subject))
            {
                return true;
            }
        }
        //false to assign subject
        return false;
    }
    public boolean isExamTaken(int choice)
    {
        return Integer.parseInt(mark.split(",")[choice - 1]) >= 0;
    }
    public void updateMark(int choice, int grade)
    {
        int markLength = mark.split(",").length;
        String tempMark = "";
        for(int indexOfExam = 0; indexOfExam < markLength; indexOfExam++)
        {
            if((choice-1) == indexOfExam)
            {
                //add new grade
                tempMark += String.valueOf(grade) +",";
                continue;
            }
            //copy old grades
            tempMark += mark.split(",")[indexOfExam] +",";
        }
        mark = tempMark;
    }
    public String toString()
    {
        return yellow+"[!] "+reset+"hello student " + bold + lightBlue +getName() +reset+ ", your Level : "+blue + getLevel()+reset;
    }
}



