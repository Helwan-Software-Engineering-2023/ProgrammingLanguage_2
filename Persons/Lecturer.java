package Persons;

import Assets.*;
import Persons.*;
import java.util.*;

public class Lecturer extends Person {

    //=====================( Attributes )=====================
    private String idHead = "lec";
    public String subjectIds ="";

    public int numberOfSubjects;

    public String reportids ="";

    public static int numberOfLecturers ;

    //=====================( Methods )=====================

    public Lecturer()
    {
        //set files to read from
        setFilePath("Lecturer.txt");
        setFolderPath(this);

        //start read and format data
        file = new FileHandler(getFolderPath()+getFilePath());
        formatData(file.readFile(),this);
    }

    public Lecturer(String name, String level)
    {
        this();
        setName(name);
        setLevel(level);
        setId(idHead+numberOfUsers);
        setRole('l');
        setPassword(getId());
        newUser = true;
        numberOfSubjects=0;
        numberOfUsers++;
        Lecturer.numberOfLecturers++;
    }
    public boolean checkExamExist(String subjectId)
    {
        //load subjects from file
        Subject subject = new Subject();
        subject.loadData(subjectId,subject);
        //return true if exam does not exist false wise
        return !subject.getExamId().equals("-1");
    }



    public String toString()
    {
        String level ;
        if(getLevel().equals("1"))
        {
            level = "Doctor";
        }
        else
        {
            level = "Doctor Assistant";
        }
        return yellow+"[!] "+reset+"hello lecturer " + bold + lightBlue +getName() +reset+ ", your Level : "+blue + level +reset;
    }

    public void addExam(int selectedSubject,String name,int duration,ArrayList<Question> questions)
    {
        //create the exam
        //load old exams first
        Exam exam  = new Exam(name,duration,questions);
        exam.setNumberOfQuestions(questions.size());

        //change exam and subject files
        updateExam(selectedSubject,exam);

    }
    public void updateExam(int selectedSubject, Exam exam)
    {
        //then update subject with new exam id
        Subject subject = new Subject();
        //load updated subject
        subject.loadData(String.valueOf(subjectIds.split(",")[selectedSubject-1]),subject);


        //check if it has the correct subject id
        if(
                subject.getId().equals(subjectIds.split(",")[selectedSubject-1]) &&
                exam.getId().charAt(0) != '-'
            )
        {
            subject.setExamId(exam.getId());
        }
        else
        {
            //to delete exam
            subject.setExamId("-1");
        }

        //save updated Subject
        //then save the exam in file
        exam.save(exam.getId(),exam);
        subject.save(subject.getId(),subject);
    }

    public void deleteExam(int selectedSubject)
    {
        Exam exam = new Exam();

        Subject subject = new Subject();

        //load subject to get the exam id to delete it
        subject.loadData(subjectIds.split(",")[(selectedSubject-1)],subject);
        exam.loadData(subject.getExamId(),exam);

        exam.setId('-'+subject.getExamId());

        //then update exam and subject files
        //before save update number of exams
        Exam.numberOfExams--;
        updateExam(selectedSubject,exam);

    }

    public String makeReport(String selectedSubjectId)
    {
        //loop on all students and read if they
        Student student = new Student();

        //buffer to store report description
        StringBuilder stringBuilder = new StringBuilder();
        for (String record : student.record )
        {
            //get student id
            String selectedStudentId ;
            try
            {
                selectedStudentId = record.split("\n")[1];
            }catch (Exception e)
            {
                selectedStudentId = "-1";
            }

            //load it for the check
            student = new Student();
            student.loadData(selectedStudentId,student);


            for (int index = 0; index < student.numberOfSubjects; index++)
            {
                String subjectId = student.subjectId.split(",")[index];
                String  subjectMark = student.mark.split(",")[index];

                if(selectedSubjectId.equals(subjectId))
                {
                    //add id = mark
                    stringBuilder.append(selectedStudentId).append(" | ")
                            .append(subjectMark).append("\n");

                }

            }
        }

        return stringBuilder.toString();
    }
    public String viewReport(int selectedSubject)
    {
        return reportids.split(",")[selectedSubject-1];
    }

    public boolean hasSubject(String selectedSubjectId)
    {
        //check if student has this subject to avoid assign it again
        for (String subject: subjectIds.split(","))
        {
            if(selectedSubjectId.equals(subject))
            {
                return true;
            }
        }
        //false to assign subject
        return false;
    }

    public boolean isSubjectHasExam(int selectedSubject)
    {

        Subject subject = new Subject();

        subject.loadData(subjectIds.split(",")[selectedSubject-1],subject);

        return !subject.getExamId().equals("-1");
    }
}