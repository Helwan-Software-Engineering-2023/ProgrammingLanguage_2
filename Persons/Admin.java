package Persons;

import Assets.*;

import java.util.*;

public class Admin extends Person {

    //=====================( Attributes )=====================


    public ArrayList<Student> students = new ArrayList<>();
    public ArrayList<Subject> subjects = new ArrayList<>() ;
    public ArrayList<Lecturer> lecturers = new ArrayList<>() ;

    public static int  numberOfAdmins;

    //=====================( Methods )=====================

    public Admin()
    {

        //set files to read from
        setFilePath("Admin.txt");
        setFolderPath(this);

        //start read and format data
        file = new FileHandler(getFolderPath()+getFilePath());
        formatData(file.readFile(),this);

        loadAttributes();

        loadNumberOfUsers();


    }

    private void loadAttributes()
    {
        //first loop to  load students
        //then load lecturers and then load subjects
        loadStudents();
        loadLecturer();
        loadSubject();

    }
    private void loadStudents()
    {
        Student student = new Student();
        for (String record : student.record)
        {
            student = new Student();
            String currentId;
            try
            {
                currentId = record.split("\n")[1];
            }catch (Exception e)
            {
                currentId = "0";
                return;
            }
            //load data from record into student
            student.loadData(currentId,student);

            //add student
            students.add(student);
        }
    }
    private void loadLecturer()
    {
        Lecturer lecturer = new Lecturer();
        for (String record : lecturer.record)
        {
            lecturer = new Lecturer();
            String currentId;
            try
            {
                currentId = record.split("\n")[1];
            }catch (Exception e)
            {
                currentId = "0";
                return;
            }
            //load data from record into student
            lecturer.loadData(currentId,lecturer);

            //add student
            lecturers.add(lecturer);
        }
    }
    private void loadSubject()
    {
        Subject subject = new Subject();

        for (String record : subject.record)
        {
            subject = new Subject();
            String currentId;
            try
            {
                currentId = record.split("=")[0].trim();
            }catch (Exception e)
            {
                currentId = "0";
                return;
            }
            if(!currentId.isEmpty())
            {
                //load data from record into student

                subject.loadData(currentId, subject);

                //add student
                subjects.add(subject);
            }
        }
    }

    public void saveAttributes()
    {
        if(!students.isEmpty())
            saveStudents();

        if (!lecturers.isEmpty())
            saveLecturer();

        if(!subjects.isEmpty())
            saveSubjects();
    }

    private void saveStudents()
    {
        for (int student = 0 ; student < students.size(); student++)
        {
            //update record after saving then set it to the next user
            String studentId = students.get(student).getId();
            Student studentObject = students.get(student);

            students.get(student).save(studentId,studentObject);
            //load the new record variable
            if(student+1 != subjects.size())
            {
                students.get(student).loadData(studentId,studentObject);
            }

            //update record variable to avid undo the updates
            if(student != students.size() - 1)
            {
                if(!students.get(student + 1).newUser)
                {
                    students.get(student + 1).record = students.get(student).record;
                }else if (students.get(student).newSubject)
                {
                    StringBuilder newRecord = new StringBuilder();
                    for (String recordItem:students.get(student + 1).record)
                    {
                        int index = 0;
                        if(recordItem.charAt(0) == '\n')
                        {
                            index = 1;
                        }

                        if(recordItem.split("\n")[index].equals(studentId))
                        {
                            continue;
                        }

                        newRecord.append(recordItem).append(",");
                    }
                    newRecord.append(students.get(student).record[students.get(student).record.length-1]);
                    students.get(student + 1).record = newRecord.toString().split(",");

                }
            }

        }
        students.get(students.size()-1)
                .save
                        (
                                students.get(students.size()-1).getId(),
                                subjects.get(students.size()-1)
                        );


    }
    private void saveLecturer()
    {
        for (int lecturer = 0 ; lecturer < lecturers.size(); lecturer++)
        {
            //update record after saving then set it to the next user
            String lecturerId = lecturers.get(lecturer).getId();
            Lecturer lecturerObject = lecturers.get(lecturer);

            lecturers.get(lecturer).save(lecturerId,lecturerObject);
            //load the new record variable
            if(lecturer+1 != lecturers.size())
            {
                lecturers.get(lecturer).loadData(lecturerId,lecturerObject);
            }

            //update record variable to avid undo the updates
            if(lecturer != lecturers.size() - 1)
            {
                if(!lecturers.get(lecturer + 1).newUser)
                {
                    lecturers.get(lecturer + 1).record = lecturers.get(lecturer).record;
                }else if (lecturers.get(lecturer).newSubject)
                {
                    StringBuilder newRecord = new StringBuilder();

                    for (String recordItem:lecturers.get(lecturer + 1).record)
                    {
                        int index = 0;
                        if(recordItem.charAt(0) == '\n')
                        {
                            index = 1;
                        }

                        if(recordItem.split("\n")[index].equals(lecturerId))
                        {
                            continue;
                        }
                        newRecord.append(recordItem).append(",");
                    }
                    newRecord.append(lecturers.get(lecturer).record[lecturers.get(lecturer).record.length-1]);
                    lecturers.get(lecturer + 1).record = newRecord.toString().split(",");

                }
            }

        }
        lecturers.get(lecturers.size()-1)
                .save
                        (
                                lecturers.get(lecturers.size()-1).getId(),
                                lecturers.get(lecturers.size()-1)
                        );



    }
    private void saveSubjects()
    {
        for (Subject subject : subjects)
        {
            subject.save(subject.getId(),subject);
            //to empty the variable
            subject = new Subject();
        }
    }

    public Person searchUser(char role, String name)
    {

        switch (role)
        {
            case 'l':
                for (Lecturer lecturer: lecturers)
                {

                    if(lecturer.getName().equalsIgnoreCase(name))
                        return lecturer;
                }
                break;
            case 's':
                for (Student student:students)
                {
                    if(student.getName().equalsIgnoreCase(name))
                        return student;
                }

        }
        return null;
    }

    public void deleteUser(int selectedUser, char role)
    {
        int foundIndex = -1;
        switch (role)
        {
            case 'l':
                for (Lecturer lecturer: lecturers)
                {
                    if(lecturer.getId().equals
                            (
                                    lecturers.get(selectedUser-1).getId()
                            )
                    )
                    {
                        //store his index to remove it from record
                        foundIndex = lecturers.indexOf(lecturer);

                        lecturers.remove(lecturer);
                        break;

                    }
                }

                //remove user from record
                for (Lecturer lecturer : lecturers)
                {
                    StringBuilder newRecord = new StringBuilder();
                    for (int recordItem = 0 ; recordItem < lecturer.record.length; recordItem++)
                    {
                        //skip record from saving
                        if(recordItem == foundIndex)
                        {
                            continue;
                        }
                        newRecord.append(lecturer.record[recordItem]).append(",");
                    }

                    lecturer.record = newRecord.toString().split(",");
                    Lecturer.numberOfLecturers = lecturers.size();

                }

                break;
            case 's':
                for (Student student: students)
                {
                    if(student.getId().equals
                            (
                                    students.get(selectedUser-1).getId()
                            )
                    )
                    {
                        //store his index to remove it from record
                        foundIndex = students.indexOf(student);

                        students.remove(student);
                        break;

                    }
                }

                //remove user from record
                for (Student student : students)
                {
                    StringBuilder newRecord = new StringBuilder();
                    for (int recordItem = 0 ; recordItem < student.record.length; recordItem++)
                    {
                        //skip record from saving
                        if(recordItem == foundIndex)
                        {
                            continue;
                        }
                        newRecord.append(student.record[recordItem]).append(",");
                    }

                    student.record = newRecord.toString().split(",");
                    Student.numberOfStudents = students.size();

                }

        }
    }
    public void addUser(String name, int level, char role)
    {
        switch (role)
        {
            case 'l':
                lecturers.add(new Lecturer(name,String.valueOf(level)));

                break;
            case 's':
                students.add(new Student(name,String.valueOf(level)));


        }
    }
    public void updateLevel(Lecturer user ,String level)
    {

        if( !level.equals("-1") )
            user.setLevel(level);

    }
    public void updateLevel(Student user ,String level)
    {

        if( !level.equals("-1") )
            user.setLevel(level);

    }

    public void assignSubjectToUser(int selectedUserId,String subjectId, char role)
    {
        switch (role)
        {
            case 'l':
                lecturers.get(selectedUserId - 1).subjectIds += subjectId + ",";
                lecturers.get(selectedUserId - 1).numberOfSubjects++;
                lecturers.get(selectedUserId - 1).reportids += "-1,";
                lecturers.get(selectedUserId - 1).newSubject = true;
                break;
            case 's':
                students.get(selectedUserId - 1).subjectId += subjectId + ",";
                students.get(selectedUserId - 1).numberOfSubjects++;
                students.get(selectedUserId - 1).mark += "-1,";
                students.get(selectedUserId - 1).newSubject = true;

        }
    }
    @Override
    public String toString()
    {
        return yellow+"[!] "+reset+"hello Admin " + bold + lightBlue +getName() +reset;

    }
}