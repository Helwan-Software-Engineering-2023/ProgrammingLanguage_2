package Assets;

import Persons.*;

import java.security.spec.ECField;
import java.util.*;

public final class Menu implements ColorSchema
{

    //=====================( attributes )====================

    static Scanner read = new Scanner(System.in);
    private static String enteredId = "" ;
    private static String enteredPassword = "" ;
    private static int choice;
    private static int roleNumber;
    public static boolean activeStatus = false;

    private static Person user;

    //=====================( Main Methods )====================

    public static void startApp()
    {
        do
        {
            //print role menu
            Menu.printRoleMenu();

            //continue as the person user choose
            switch (Menu.roleNumber)
            {
                case 1:
                    user = new Admin();
                    break;
                case 2:
                    user = new Lecturer();
                    break;
                case 3:
                    user = new Student();
                    break;
                case 4:
                    System.out.println(purple+bold+italic+"[!] Exiting....");
                    break;
                default:
                    System.out.println(lightYellow+"|----------("+red+" Invalid Input "+lightYellow+")----------|"+reset);
            }
        }while (Menu.roleNumber > 4 || Menu.roleNumber <= 0);


        //start authenticating user and then serve him with this needs
        Menu.serveUser(user);
    }
    public static void printRoleMenu()
    {
        System.out.println("\n"+highLightGray+"|=======================|"+reset);
        System.out.println(
                highLightGray+"|"+reset+red+italicUnderLined+"[!] "+yellow+"Choose Role"+reset+" :\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t1) Admin\t\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t2) Lecturer\t\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t3) Student\t\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+red+"\t4) Exit !\t\t\t"+reset+highLightGray+"|"+reset);
        System.out.println(reset+highLightGray+"|=======================|"+reset);


        do
        {
            try
            {
                System.out.print(yellow+"[?]"+reset+" Role Number : ");
                read = new Scanner(System.in);
                roleNumber = read.nextInt();
            }catch (Exception ignored)
            {

            }
        }while (roleNumber < 0 || roleNumber > 4);

        System.out.println("\n");

    }

    public static void printLoginForm(int fieldNumber)
    {
        System.out.println(lightGray+"|=======================|"+reset);
        switch (fieldNumber)
        {
            case 1:
                System.out.print(lightGray+"|"+reset+yellow+" [+]"+reset +" ID : ");
                break;
            case 2:
                System.out.print(lightGray+"|"+reset+yellow+" [+]"+reset +" Password : ");
                break;
        }

    }

    public static void loginValuesReader(Person user)
    {
        //read user id and password
        System.out.println("\n"+lightYellow+"|-------("+blue+bold+" Login "+lightYellow+")-------|"+reset);
        printLoginForm(1);
        enteredId = read.next();
        try
        {
            user.loadData(enteredId,user);
        }
        catch (Exception ignored)
        {
        }

        printLoginForm(2);
        enteredPassword = read.next();
        System.out.println(lightGray+"|=======================|"+reset);

    }

    public static void serveUser(Person user)
    {
        //number of failed login
        int tries = 0;

        //loop to check if user still active
        do {
            //read user id and password
            loginValuesReader(user);

            //set status to true if logged in
            activeStatus = user.login(enteredId, enteredPassword);

            if (activeStatus)
            {
                //greet user and display basic info
                System.out.println("\n"+user.toString()+"\n");

                //check for user type
                if(user instanceof Student)
                {
                    //loop on student menu
                    studentMenu(((Student)user));
                }
                else if (user instanceof Lecturer)
                {
                    //loop on lecturer menu
                    lecturerMenu(((Lecturer) user));
                }
                else if (user instanceof Admin)
                {
                    //loop on admin menu
                    adminMenu(((Admin) user));
                }


            }
            else
            {
                System.out.println(red+"[!] Wrong Id or Password , "+ yellow+(2-tries)+" tries"+red +" remaining"+reset);
                tries++;
            }
        }while (!activeStatus && tries != 3);

        //if failed  to log in exit and print error message
        if (tries == 3)
        {
            System.out.println(red + "[!] you have failed 3 times , please refer to you admin to restore your access  ");
        }
    }

    public static void userInfoMenu(Person user)
    {
        System.out.println(highLightGray+"+-----------------------------------+"+reset);
        //list user info changeable values
        System.out.println(lightRed+"[ !!! ] enter ( -1 ) to unchanged the old values [ !!! ] ");
        System.out.println(lightYellow+"+----------------------------------+"+reset);
        System.out.print(purple+"[?] New name: "+reset);
        read = new Scanner(System.in);
        String newName = read.nextLine();
        System.out.println(lightYellow+"+----------------------------------+"+reset);
        System.out.print(purple+"[?] New password: "+reset);
        String newPassword = read.nextLine();
        System.out.println(lightYellow+"+-----------------------------------+"+reset);

        //update user info in field not empty
        if(user instanceof Admin)
        {
            user.updateInfo(newName,newPassword);
        }
        else if ( user instanceof  Lecturer)
        {
            user.updateInfo(newName,newPassword);
        }
        else
        {
            user.updateInfo(newName,newPassword);
        }

        System.out.println("\n"+highLightGray+"+-----------------------------------+"+reset);
        System.out.println(bold+blue+"[!] user data updated ...."+reset);
        System.out.println(lightBlue+"new name : "+lightYellow +bold+ italic +user.getName()
                        +lightBlue+ ", new password : "+lightYellow+bold+italic+ user.getPassword() +reset);
    }

    public static String printSubjectName(String subjectId)
    {
        Subject subject = new Subject();
        subject.loadData(subjectId,subject);

        return subject.toString();
    }
    //=====================( Start Student Menus )=====================

    public static void studentMenu(Student student)
    {
        int choice = 0;
        do
        {
             choice = printMainStudentMenu();
             switch (choice)
             {
                 case 1:
                     //print subject menu
                     subjectMenu(student);
                     break;
                 case 2:
                     //print update user info
                     userInfoMenu(student);
                     break;
                 case 0:
                     //save session data
                     student.save(student.getId(),student);
                     System.out.println(green+"[+] Saving Data ..."+reset);
             }

        }while (choice != 0);


    }

    public static int printMainStudentMenu()
    {
        System.out.println("\n"+highLightGray+"|=======================|"+reset);
        System.out.println(
                highLightGray+"|"+reset+red+"[!] "+italicUnderLined+yellow+"Choose option"+reset+" :\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t1) Subject menu\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t2) User Info\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+red+"\t0) Exit !\t\t\t"+reset+highLightGray+"|"+reset);
        System.out.println(reset+highLightGray+"|=======================|"+reset);
        int choice = -1;
        do {
            try
            {
                System.out.print(yellow+"[?]"+reset+" choice Number : ");
                read = new Scanner(System.in);
                choice = read.nextInt();
            }catch (Exception e)
            {
                choice = -1;
            }
            if(choice > 2 || choice < 0)
            {
                System.out.println(yellow+"|------------("+red+" Invalid Option "+ yellow+")------------|"+reset);
            }
        }while (choice > 2 || choice < 0);

        return choice;
    }

    public static void subjectMenu(Student student)
    {
        do {
            //list student menu to choose subject to take
            choice = printStudentSubjects(student);
            if (choice != 0) {
                //if there is mark for exam it must be escaped
                if(student.isExamTaken(choice))
                {
                    System.out.println("\n"+red+"[!] Exam taken before "+reset);
                    continue;
                }

                //if not taken print exam
                String selected_exam_id = setSelectedExamID(student);

                //if exam not exist yet
                if(selected_exam_id.equals("-1"))
                {
                    System.out.println(red+"[!] No Exam Yet"+ reset );
                    continue;
                }

                //load exam
                Exam selected_exam = new Exam();
                selected_exam.loadData(selected_exam_id,selected_exam);

                //display exam then at the end of exam put grade to student
                printExamInfo(selected_exam);

                student.updateMark(choice, displayExam(selected_exam));


            }
        }while (choice != 0);
    }
    public static int printStudentSubjects(Object object)
    {
        System.out.println("\n"+purple+"[ ! ] Subject Student have: "+ reset);
        System.out.println(highLightGray+"|=============================|"+reset);
        for(int item = 0; item < ((Student) object).numberOfSubjects; item++)
        {
            String subjectId = ((Student) object).subjectId.split(",")[item];
            String mark = ((Student) object).mark.split(",")[item];
            //if mark = -1 then there is no exam mark
            if(mark.equals("-1"))
            {
                mark = "No Mark yet";
            }
            else
            {
                mark += " marks";
            }
            System.out.println(highLightGray+ "|"+ reset+ "\t " + yellow+ (item+1) + ") "+ printSubjectName(subjectId)
                    + green+" ( "+ mark +" ) "+reset );

        }
        System.out.println(highLightGray+ "|"+ reset+ "\t " + lightBlue+  "0) "+"Back  "+reset );
        System.out.println(highLightGray+"|=============================|"+reset);

        int choice;
        do {
            try
            {
                System.out.print(yellow+"[?] " + reset+ " choose : " );
                read = new Scanner(System.in);
                choice = read.nextInt();
            }catch (Exception e)
            {
                choice = -1;
            }
            if(choice > ((Student) object).numberOfSubjects || choice < 0)
            {
                System.out.println(yellow+"|------------("+red+" Invalid Option "+ yellow+")------------|"+reset);
            }
        }while (choice > ((Student) object).numberOfSubjects || choice < 0);

        return choice;
    }

    public static void printExamInfo(Exam exam)
    {
        System.out.println(highLightGray+"==============================="+reset+"\n");
        System.out.println(exam.toString());
    }

    public static int displayExam(Exam selected_exam)
    {
        System.out.println(lightYellow+italicUnderLined+"[!] Questions :"+reset);
        //create counter to correct submitted answers
        int grade  = 0;
        for(int question = 0; question < selected_exam.getNumberOfQuestions(); question++)
        {
            //print question with its answers
            System.out.println(  yellow+(question+1)+") " +lightBlue+ selected_exam.questions.get(question).getDescription()+ reset);
            System.out.print(yellow+"[?] "+reset+"Your Answer : " );
            //read answer
            char answer = read.next().charAt(0);
            //validate answer
            if(selected_exam.questions.get(question).checkAnswer(answer))
            {
                grade++;
            }
            System.out.println(lightPurple+"-------------------------------"+reset);
        }
        System.out.println(highLightGray+"==============================="+reset);
        return grade;
    }

    public static String setSelectedExamID(Student student)
    {

        //get and load exam id from id of subject
        Subject selected_subject = new Subject();
        String selected_subject_id = student.subjectId.split(",")[choice - 1];
        selected_subject.loadData(selected_subject_id, selected_subject);

        return selected_subject.getExamId();
    }

    //=====================( End Student Menus )=====================


    //=====================( Start Lecturer Menus )=====================
    public static void lecturerMenu(Lecturer lecturer)
    {
        int choice = 0;
        do
        {
            choice = printMainLecturerMenu();
            switch (choice)
            {
                case 1:
                    //print subject menu
                    subjectMenu(lecturer);
                    break;
                case 2:
                    //print update user info
                    userInfoMenu(lecturer);
                    break;
                case 0:
                    //save session data
                    lecturer.save(lecturer.getId(),lecturer);
                    System.out.println(green+"[+] Saving Data ..."+reset);
            }

        }while (choice != 0);


    }

    public static int printMainLecturerMenu()
    {
        System.out.println("\n"+highLightGray+"|=======================|"+reset);
        System.out.println(
                highLightGray+"|"+reset+red+"[!] "+italicUnderLined+yellow+"Choose option"+reset+" :\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t1) Subject menu\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t2) User Info\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+red+"\t0) Exit !\t\t\t"+reset+highLightGray+"|"+reset);
        System.out.println(reset+highLightGray+"|=======================|"+reset);


        int MainChoice;
        do {
            try
            {
                System.out.print(yellow+"[?]"+reset+" choice Number : ");
                read = new Scanner(System.in);
                MainChoice = read.nextInt();
            }
            catch (Exception e)
            {
                MainChoice = -1;
            }

            if (MainChoice < 0 || MainChoice > 2)
            {
                System.out.println(yellow+"|------------("+red+" Invalid Option "+ yellow+")------------|"+reset);

            }

        }while (MainChoice < 0 || MainChoice > 2);
        return MainChoice;

    }

    public static void subjectMenu(Lecturer lecturer)
    {
        do {
            //list student menu to choose subject to take
            choice = printLecturerSubjects(lecturer);
            if (choice != 0)
            {
                //print subject option menu
                int controlOptionNumber;
                do
                {
                    //print subject name from exam name
                    System.out.println("\n"+bold+lightYellow+"|---------"+lightBlue+"( "
                            + printSubjectName(lecturer.subjectIds.split(",")[choice-1])
                            +lightBlue+ " )"+lightYellow+"---------|"+reset);

                    //print subject control menu
                    controlOptionNumber = printSubjectControlMenu();

                    switch (controlOptionNumber)
                    {
                        case 1:
                           if(!lecturer.isSubjectHasExam(choice))
                           {
                               makeExamMenu(lecturer);
                           }
                           else {
                               System.out.println(yellow+"|------------("+red+" Exam Exist Before "+yellow+")------------|"+reset);
                           }
                            break;
                        case 2:
                            lecturer.deleteExam(choice);
                            break;
                        case 3:
                            String updateValues = printUpdateExamMenu();
                            lecturer.updateExam
                                    (
                                        choice,
                                            updateSelectedExam
                                                    (
                                                            choice ,
                                                            lecturer,
                                                            updateValues.split(",")[0],
                                                            Integer.parseInt
                                                                    (
                                                                        updateValues.split(",")[1]
                                                                    )
                                                    )
                                    );
                            break;
                        case 4:
                            printMakeReportMenu(choice,lecturer);
                            break;
                        case 5:
                            printReport(choice, lecturer);
                            break;
                        case 0:
                            //do nothing

                    }
                }while (controlOptionNumber > 5 || controlOptionNumber < 0);

            }
        }while (choice != 0);
    }

    public static int printLecturerSubjects(Object object)
    {
        System.out.println("\n"+purple+"[ ! ] Subject Lecturer have: "+ reset);
        System.out.println(highLightGray+"|============================================|"+reset);
        int numberOfSubjects = ((Lecturer) object).numberOfSubjects;
        for(int item = 0; item < numberOfSubjects ; item++)
        {
            String subjectId = ((Lecturer) object).subjectIds.split(",")[item];
            String report = ((Lecturer) object).reportids.split(",")[item];
            String exam;
            //if report = -1 then there is no report
            if(((Lecturer) object).checkExamExist(subjectId))
            {
                exam = lightGreen+"Exam Exist"+reset;
            }
            else
            {
                exam = lightRed+"No Exam yet"+reset;
            }
            //if report = -1 then there is no report
            if(report.equals("-1"))
            {
                report = lightRed+"No Report yet"+reset;
            }
            else
            {
                report = green+"Report Exist"+reset;
            }
            System.out.println(highLightGray+ "|"+ reset+ "  " + yellow+ (item+1) + ") "+ printSubjectName(subjectId) +
                    yellow+" ( "+exam+ green+" , "+ report +yellow+" ) "+reset );

        }
        System.out.println(highLightGray+ "|"+ reset+ "  " + lightBlue +  "0) "+"Back  "+reset );
        System.out.println(highLightGray+"|============================================|"+reset);


        int choice = 0;
        do
        {
            try
            {
                System.out.print(yellow+"[?] " + reset+ " choose : " );
                read = new Scanner(System.in);
                choice = read.nextInt();
            }catch (Exception e)
            {
                choice = -1;
            }
            if(choice < 0 || choice > numberOfSubjects)
            {
                System.out.println(lightYellow+"|----------("+red+" Invalid Option "+lightYellow+")----------|"+reset);
            }
        }while (choice < 0 || choice > numberOfSubjects );


        return choice;
    }

    public static int printSubjectControlMenu()
    {
        System.out.println("\n"+highLightGray+"|=======================|"+reset);
        System.out.println(
                highLightGray+"|"+reset+purple+italicUnderLined+"[!] "+yellow+"Choose Option"+reset+" :\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t1) Add Exam\t\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t2) Delete Exam\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t3) Update Exam\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t4) Make Report\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t5) View Report\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+lightBlue+"\t0) Back \t\t\t"+reset+highLightGray+"|"+reset);
        System.out.println(reset+highLightGray+"|=======================|"+reset);


        int controlOptions  ;
        do {
            try
            {
                System.out.print(yellow+"[?]"+reset+" Option Number : ");
                read = new Scanner(System.in);
                controlOptions = read.nextInt();
            }catch (Exception e)
            {
                controlOptions = -1;
            }
            if(controlOptions < 0 || controlOptions > 5)
            {
                System.out.println(lightYellow+"|----------("+red+" Invalid Option "+lightYellow+")----------|"+reset);
            }
        }while (controlOptions < 0 || controlOptions > 5);

        return controlOptions;
    }

    public static void makeExamMenu(Lecturer lecturer)
    {

        //take exam name
        System.out.println("\n"+highLightGray+"|=======================|"+reset);
        System.out.print(blue+"[?] Exam name : "+reset );
        String name = read.next();
        System.out.print(blue+"[?] Exam duration : "+reset );
        int duration = 0;
        do {

            try {
                duration = read.nextInt();
            }catch (Exception ignored){}
        }while (duration <= 0);

        System.out.println("\n"+highLightGray+"|------------------------|"+reset);
        System.out.println(lightPurple+"[!] add Questions: "+reset);

        //add Questions then make exam
        lecturer.addExam(choice,name,duration,addQuestion());



    }
    public static ArrayList<Question> addQuestion()
    {
        ArrayList<Question> questions = new ArrayList<>() ;
        int add = 1;
        System.out.println(lightGray+"Example: 1+1 equal a) 1 b) 2 c) 4"+reset);
        do {
            System.out.println(lightPurple + "-------------------------------------------------" + reset);
            //add questions if lecture did not enter -1
            if (add == 1) {
                System.out.print(yellow + "[?] Enter Question with Choices : " + reset);
                //to clear buffer
                read = new Scanner(System.in);
                String question = read.nextLine();

                System.out.print(yellow + "[?] Enter Correct Answer : " + reset);
                String answer = read.nextLine();

                //add question to the list
                questions.add(new Question(question, answer.charAt(0)));
            }

            System.out.println(lightPurple + "-------------------------------------------------" + reset);
            System.out.println(red + "[!] to stop  -1 , to Continue press 1 " + reset);
            System.out.print(lightPurple + "[?] Continue : ");
            try {
                add = read.nextInt();
            } catch (Exception e) {
                System.out.println("[!] Invalid Operation");
                add = 0;
            }

            if (add != 1 && add != (-1) )
            {
                System.out.println(lightYellow+"|----------("+red+" Invalid Option "+lightYellow+")----------|"+reset);

            }

        }while (add != -1);


        return questions;
    }

    public static String printUpdateExamMenu()
    {
        System.out.println(highLightGray+"+-----------------------------------+"+reset);
        //list user info changeable values
        System.out.println(lightRed+"[ !!! ] enter ( -1 ) to unchanged the old values [ !!! ] ");
        //update exam name
        System.out.println(highLightGray+"|=======================|"+reset);
        System.out.print(blue+"[?] New Exam name : "+reset );
        String name = read.next();
        System.out.print(blue+"[?] Exam duration : "+reset );
        int duration = 0;
        do {
            try
            {
                duration = read.nextInt();
            }
            catch (Exception ignored){}
        }while (duration <= 0 && duration != (-1));

        System.out.println("\n"+lightYellow+"|---------"+lightBlue+"( Exam Updated )"+lightYellow+"---------|"+reset);
        return name+","+duration;
    }

    public static Exam updateSelectedExam(int selectedSubject, Lecturer lecturer, String name, int duration)
    {
        //load exam to update its values
        Subject subject = new Subject();
        subject.loadData(
                String.valueOf(
                                lecturer.subjectIds.split(",")[selectedSubject-1]
                                ), subject);
        Exam exam = new Exam();
        exam.loadData(subject.getExamId(),exam);

        //set update values
        if(!name.equals("-1"))
        {
            exam.setName(name);
        }
        if(duration != (-1))
        {
            exam.setDuration(duration);
        }

        return exam;
    }

    public static void printMakeReportMenu(int selectedSubject, Lecturer lecturer)
    {

        String selectedReportId = lecturer.reportids.split(",")[selectedSubject-1];
        String selectedSubjectId = lecturer.subjectIds.split(",")[selectedSubject-1];



        //build report description
        String description = lecturer.makeReport(selectedSubjectId);

        Report report ;

        if(!selectedReportId.equals("-1"))
        {
            report = new Report(description,true);
            report.setId(selectedReportId);
        }
        else
        {
            report = new Report();
            report.loadData(selectedReportId,report);
            report.setReportDescription(description);
        }

        //update subject with report id
        lecturer.reportids = updateLecturerReports(report.getId(),lecturer,selectedSubject);
        //store report
        report.save(selectedReportId,report);
        System.out.println("\n"+lightYellow+"|---------"+lightBlue+"( Report Build Complete )"+lightYellow+"---------|"+reset);
    }
    public static String updateLecturerReports(String newReportId, Lecturer lecturer, int selectedSubject )
    {
        StringBuilder newReportIds = new StringBuilder();

        for(int report = 0; report < lecturer.numberOfSubjects; report++)
        {
            if(lecturer.subjectIds.split(",")[selectedSubject-1].equals(lecturer.subjectIds.split(",")[report]))
            {
                newReportIds.append(newReportId);
            }
            else
            {
                newReportIds.append(lecturer.reportids.split(",")[report]);
            }
            newReportIds.append(",");
        }

        return newReportIds.toString();
    }
    public static void printReport(int selectedSubject,Lecturer lecturer)
    {
        //if report does not exist exit
        if(!isReportExist(selectedSubject,lecturer))
        {
            System.out.println("\n"+lightYellow+"|---------------("+red+"No Report"+lightYellow+")---------------|"+reset);
            return;
        }

        //create object from the report
        Report report = new Report();
        report.loadData(lecturer.viewReport(selectedSubject),report);

        System.out.println("\n"+lightYellow+italic+"|------( "+purple+"id | mark "+lightYellow+")------|"+reset);
        System.out.println("\t\t"+lightBlue+italic+report.getReportDescription()+reset);
    }
    public static boolean isReportExist(int selectedSubject,Lecturer lecturer)
    {
        return !lecturer.viewReport(selectedSubject).equals("-1");
    }

    //===================( End Lecturer Menus )=================



    //=====================( Start Admin Menus )====================
    public static void adminMenu(Admin admin)
    {
        int choice = 0;
        do
        {
            choice = printMainAdminMenu();
            switch (choice)
            {
                case 1:
                    //print subject menu
                    subjectMenu(admin);
                    break;
                case 2:
                    //print update user info
                    userInfoMenu(admin);
                    break;
                case 3:
                    //print user menu
                    usersMenu(admin);
                    break;
                case 0:
                    //save session data
                    admin.save(admin.getId(),admin);
                    System.out.println(green+"[+] Saving Data ..."+reset);
            }

        }while (choice != 0);


    }

    public static int printMainAdminMenu()
    {
        System.out.println("\n"+highLightGray+"|=======================|"+reset);
        System.out.println(
                highLightGray+"|"+reset+red+"[!] "+italicUnderLined+yellow+"Choose option"+reset+" :\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t1) Subject menu\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t2) User Info\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t3) Users Menu\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+red+"\t0) Exit !\t\t\t"+reset+highLightGray+"|"+reset);
        System.out.println(reset+highLightGray+"|=======================|"+reset);



        int MainChoice;
        do {
            try
            {
                System.out.print(yellow+"[?]"+reset+" choice Number : ");
                read = new Scanner(System.in);
                MainChoice = read.nextInt();
            }
            catch (Exception e)
            {
                MainChoice = -1;
            }
            if(MainChoice > 3 || MainChoice < 0)
            {
                System.out.println(lightYellow+"|----------("+red+" Invalid Option "+lightYellow+")----------|"+reset+"\n");
            }
        }while (MainChoice < 0 || MainChoice > 3);
        return MainChoice;

    }

    public static void usersMenu(Admin admin)
    {
        //user option menu
        int option;
        do
        {
            option = printUserControlMenu();

            char role = ' ';
            switch (option)
            {
                case 1:
                    //add user
                    do
                    {
                        role = printUserTypeControlMenu();

                        if (role != ' ') {
                            addUserMenu(role, admin);
                        }
                    }while (role != ' ');
                    break;
                case 2:
                    //delete user
                    role = printUserTypeControlMenu();
                    deleteMenu(role,admin);
                    break;
                case 3:
                    //update user
                    int selectedUser = 0;
                    do {
                        role = printUserTypeControlMenu();

                       if(role != ' ')
                       {
                           do
                           {

                                   listSystemUser(role, admin);

                                   try
                                   {
                                       System.out.print(yellow + "[?] Option : ");
                                       read = new Scanner(System.in);
                                       selectedUser = read.nextInt();
                                   }catch (Exception e)
                                   {
                                       selectedUser = -1;
                                       System.out.println(yellow+"|-----------("+red+" Invalid Option "+yellow+")-----------|"+reset);
                                       continue;
                                   }
                                   if (selectedUser == 0)
                                   {
                                       continue;
                                   }


                               if(selectedUser > 0 && (
                                       ( (role == 'l') && (admin.lecturers.size() >= selectedUser)) ||
                                       ( (role == 's') && (admin.students.size() >= selectedUser) )
                                                        )
                               )
                               {
                                   switch (role) {
                                       case 'l':
                                           updateUser(admin.lecturers.get(selectedUser-1));
                                           break;
                                       case 's':
                                           updateUser(admin.students.get(selectedUser-1));

                                   }
                               }
                               else
                               {
                                   System.out.println(yellow+"|-----------("+red+" Invalid Option "+yellow+")-----------|"+reset);

                               }
                           }while (selectedUser != 0);
                       }

                    }while (role != ' ');
                    break;
                case 4:
                    //list users
                    do
                    {

                        role = printUserTypeControlMenu();

                        if (role != ' ')
                        {
                            listSystemUser(role, admin);

                        }

                    }while (role != ' ');

                    break;
                case 5:
                    //search user
                    searchMenu(admin);

            }

        }while (option != 0);

    }

    public static int printUserControlMenu()
    {
        System.out.println("\n"+highLightGray+"|=======================|"+reset);
        System.out.println(
                highLightGray+"|"+reset+purple+italicUnderLined+"[!] "+yellow+"Choose Option"+reset+" :\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t1) Add User\t\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t2) Delete User\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t3) Update User\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t4) List User\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t5) Search User\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+lightBlue+"\t0) Back \t\t\t"+reset+highLightGray+"|"+reset);
        System.out.println(reset+highLightGray+"|=======================|"+reset);


        int controlOptions  ;
        do {
            try
            {
                System.out.print(yellow+"[?]"+reset+" Option Number : ");
                read = new Scanner(System.in);
                controlOptions = read.nextInt();
            }catch (Exception e)
            {
                controlOptions = -1;
            }
            if(controlOptions < 0 || controlOptions > 5)
            {
                System.out.println(lightYellow+"|----------("+red+" Invalid Option "+lightYellow+")----------|"+reset);
            }
        }while (controlOptions < 0 || controlOptions > 5);

        return controlOptions;
    }

    public static void subjectMenu(Admin admin)
    {
        do {
            //list student menu to choose subject to take
            choice = printAdminSubjects();
            if (choice != 0)
            {
                switch (choice)
                {
                    case 1:
                        //list subjects with capability to choose one of them to assign user to it
                        int selectedSubjectToAssign = listSystemSubjects(admin);
                        if(selectedSubjectToAssign != 0)
                        {
                            char role = ' ';
                            do
                            {
                                role = printUserTypeControlMenu();


                                if (role != ' ')
                                {
                                    assignSubjectTo(selectedSubjectToAssign, role, admin);
                                }


                            }while (role != ' ');

                        }
                        break;
                    case 2:
                        //add subject with auto generate id printing subject id at the end
                        addSubject(admin);
                        break;
                    default:
                        System.out.println(yellow +"|==========("+red+" Invalid input "+yellow+")==========|"+ reset);

                }
            }
        }while (choice != 0);
    }

    public static int printAdminSubjects()
    {
        int option = -1 ;
        do
        {
            System.out.println("\n"+highLightGray+"|===========================|"+reset);
            System.out.println(highLightGray+"|"+reset+red+"[!] "+italicUnderLined+yellow+"Choose option"+reset+" :\t\t"+reset+ highLightGray+"|"+reset+"\n"+
                            highLightGray+"|"+reset+yellow+"\t1) List Subjects\t\t"+reset+highLightGray+"|"+reset+"\n"+
                            highLightGray+"|"+reset+yellow+"\t2) Add Subject\t\t\t"+reset+highLightGray+"|"+reset+"\n"+
                            highLightGray+"|"+reset+lightBlue+"\t0) Back \t\t\t\t"+reset+highLightGray+"|"+reset);
            System.out.println(highLightGray+"|===========================|"+reset);

            try
            {
                System.out.print(yellow+"[?] " + reset+ " choose : " );
                option = read.nextInt();
            }
            catch (Exception e)
            {
                //set to -1 to stop input mismatch
                read.nextLine();
            }
            if(option < 0 || option > 2)
            {
                System.out.println(yellow +"|==========("+red+" Invalid input "+yellow+")==========|"+ reset);

            }

        }while (option < 0 || option > 2 );


        return option;
    }

    public static int listSystemSubjects(Admin admin)
    {
        System.out.println("\n"+purple+"[ ! ] Subject System have : "+ reset);
        System.out.println(lightYellow+"|----------("+blue+italic+" Choose Subject to Assign Users to it "+lightYellow+")----------|"+reset+"\n");
        System.out.println(highLightGray+"|===============================================|"+reset);
        int numberOfSubjects = admin.subjects.size();

        for(int item = 0; item < numberOfSubjects ; item++)
        {
            String subjectId = admin.subjects.get(item).getId();

            System.out.println(highLightGray+ "|"+ reset+ "  " + yellow+ (item+1) + ") "+ subjectId + reset + italic+
                    lightGreen+" Has -> Lecturer : "+ lightYellow +numberOfLecturer(subjectId,admin)+lightGreen + " , Students : "+
                    lightYellow+ numberOfStudent(subjectId,admin)  +"\t"+reset +highLightGray+ "|"+ reset);

        }

        System.out.println(highLightGray+ "|"+ reset+ "  " + lightBlue +  "0) "+"Back   "+reset +"\t\t\t\t\t\t\t\t\t" +highLightGray+ "|"+ reset);
        System.out.println(highLightGray+"|===============================================|"+reset);


        int choice = 0;
        do
        {
            try
            {
                System.out.print(yellow+"[?] " + reset+ " choose : " );
                read = new Scanner(System.in);
                choice = read.nextInt();
            }catch (Exception e)
            {
                choice = -1;
            }
            if(choice < 0 || choice > numberOfSubjects)
            {
                System.out.println(lightYellow+"|----------("+red+" Invalid Option "+lightYellow+")----------|"+reset);
            }
        }while (choice < 0 || choice > numberOfSubjects );


        return choice;
    }

    public static int numberOfLecturer(String subjectId, Admin admin)
    {
        int count = 0;
        for (int item = 0; item < admin.lecturers.size(); item++)
        {
            for(String subjectIdInLecturer : admin.lecturers.get(item).subjectIds.split(","))
            {
                if(subjectId.equals(subjectIdInLecturer))
                {
                    count++;
                }
            }
        }

        return count;
    }

    public static int numberOfStudent(String subjectId, Admin admin)
    {
        int count = 0;
        for (int item = 0; item < admin.students.size(); item++)
        {
            for(String subjectIdInStudent : admin.students.get(item).subjectId.split(","))
            {
                if(subjectId.equals(subjectIdInStudent))
                {
                    count++;
                }
            }
        }

        return count;
    }

    public static void addSubject(Admin admin)
    {
        admin.subjects.add(new Subject(""));

        String subjectId = admin.subjects.get(admin.subjects.size()-1).getId();
        System.out.println("\n"+yellow +"|==========("+green+" Subject Added with ID : "+blue+bold+ subjectId +yellow+" )==========|"+ reset);

    }

    public static char printUserTypeControlMenu()
    {
        System.out.println(lightYellow+"|----------("+blue+italic+" Choose User Role  "+lightYellow+")----------|"+reset+"\n");
        System.out.println("\n"+highLightGray+"|=======================|"+reset);
        System.out.println(
                highLightGray+"|"+reset+red+"[!] "+italicUnderLined+yellow+"Choose option"+reset+" :\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t1) Lecturer\t\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+yellow+"\t2) Student\t\t\t"+reset+highLightGray+"|"+reset+"\n"+
                        highLightGray+"|"+reset+blue+"\t0) Back  \t\t\t"+reset+highLightGray+"|"+reset);
        System.out.println(reset+highLightGray+"|=======================|"+reset);
        int option = -1;
        do
        {
            try
            {
                System.out.print(yellow+"[?] Choose : "+reset);
                read = new Scanner(System.in);
                option = read.nextInt();
            }
            catch (Exception e)
            {
                option = -1;
            }

            if(option > 2 || option < 0)
            {
                System.out.println(lightYellow+"|----------("+red+" Invalid Option "+lightYellow+")----------|"+reset+"\n");
            }

        }while (option > 2 || option < 0);

        switch (option)
        {
            case 1:
                return 'l';
            case 2:
                return 's';
        }
        //if user want to go back do not set roles
        return ' ';

    }

    public static void assignSubjectTo(int selectedSubjectToAssign,char role ,Admin admin )
    {
        int selectedUserId;
        String subjectId = admin.subjects.get(selectedSubjectToAssign-1).getId();

        switch (role)
        {
            case 'l':
                do {
                    listSystemUser(role,admin);
                    selectedUserId = selectLecturer(admin);



                    if (selectedUserId != 0)
                    {
                        if(admin.lecturers.get(selectedUserId-1).hasSubject(subjectId))
                        {
                            System.out.println(lightYellow+"|----------("+red+italic+" Lecturer Exist before ! "+lightYellow+")----------|"+reset+"\n");

                            continue;
                        }

                        //add subject to the lecturer
                        admin.lecturers.get(selectedUserId - 1).subjectIds += subjectId + ",";
                        admin.lecturers.get(selectedUserId - 1).numberOfSubjects++;
                        admin.lecturers.get(selectedUserId - 1).reportids += "-1,";
                        admin.lecturers.get(selectedUserId - 1).newSubject = true;
                        System.out.println(lightYellow+"|----------("+red+italic+" Lecturer Add to Subject "+lightYellow+")----------|"+reset+"\n");

                    }
                }while (selectedUserId != 0);
                break;
            case 's':
                do {
                    listSystemUser(role,admin);

                    selectedUserId = selectStudent(admin);



                    if (selectedUserId != 0)
                    {
                        if(admin.students.get(selectedUserId-1).hasSubject(subjectId))
                        {
                            System.out.println(lightYellow+"|----------("+red+italic+" Student Exist before ! "+lightYellow+")----------|"+reset+"\n");

                            continue;
                        }

                        //add subject to the student
                        admin.students.get(selectedUserId - 1).subjectId += subjectId + ",";
                        admin.students.get(selectedUserId - 1).numberOfSubjects++;
                        admin.students.get(selectedUserId - 1).mark += "-1,";
                        admin.students.get(selectedUserId - 1).newSubject = true;
                        System.out.println(lightYellow+"|----------("+red+italic+" Student Add to Subject "+lightYellow+")----------|"+reset+"\n");

                    }
                }while (selectedUserId != 0);
        }
    }

    public static void listSystemUser(char role,Admin admin)
    {
        System.out.println("\n");
        switch (role)
        {
            case 'l':
                System.out.println(lightYellow+"|----------("+italic+" Lecturers"+blue+ " ID : Name : level "+lightYellow+")----------|"+reset+"\n");
                System.out.println(highLightGray+"|===============================================|"+reset);
                int numberOfLecturers = admin.lecturers.size();
                for(int item = 0; item < numberOfLecturers ; item++)
                {
                    String lecturerId = admin.lecturers.get(item).getId();
                    String lecturerName = admin.lecturers.get(item).getName();
                    String lecturerlevel = admin.lecturers.get(item).getLevel();

                    System.out.println(highLightGray+ "|"+ reset+ " " + yellow+ (item+1) + ") ID: "+ lightGreen+lecturerId + reset + italic+
                            lightYellow+" ,Name: "+lightGreen +lecturerName +lightYellow+" ,Level: "+lightGreen +lecturerlevel +"\t\t\t"+reset );

                }

                System.out.println(highLightGray+ "|"+ reset+ "  " + lightBlue +  "0) "+"Back   "+reset +"\t\t\t\t\t\t\t\t\t" + reset);
                System.out.println(highLightGray+"|===============================================|"+reset);

                break;
            case 's':
                System.out.println(lightYellow+"|----------("+italic+" Student"+blue+" ID : Name "+lightYellow+")----------|"+reset+"\n");

                System.out.println(highLightGray+"|===============================================|"+reset);
                int numberOfStudents = admin.students.size();
                for(int item = 0; item < numberOfStudents ; item++)
                {
                    String studentId = admin.students.get(item).getId();
                    String studentName = admin.students.get(item).getName();
                    String studentLevel = admin.students.get(item).getLevel();

                    System.out.println(highLightGray+ "|"+ reset+ " " + yellow+ (item+1) + ") ID: "+ lightGreen+studentId + reset + italic+
                            lightYellow+" ,Name: "+lightGreen+ studentName +lightYellow+" ,Level: "+lightGreen+ studentLevel +"\t\t\t"+ reset);

                }

                System.out.println(highLightGray+ "|"+ reset+ "  " + lightBlue +  "0) "+"Back   "+reset +"\t\t\t\t\t\t\t\t\t" + reset);
                System.out.println(highLightGray+"|===============================================|"+reset+"\n");

        }
    }

    public static int selectLecturer(Admin admin)
    {
        int option = -1;
        do
        {
            try
            {
                System.out.print(yellow+"[?] Choose : ");
                read = new Scanner(System.in);
                option = read.nextInt();
            }
            catch (Exception e)
            {
                option = -1;
            }

            if(option > admin.lecturers.size() || option < 0)
            {
                System.out.println(lightYellow+"|----------("+red+" Invalid Option "+lightYellow+")----------|"+reset+"\n");

            }
        }while (option > admin.lecturers.size() || option < 0);

        return option;
    }
    public static int selectStudent(Admin admin)
    {
        int option = -1;
        do
        {
            try
            {
                System.out.print(yellow+"[?] Choose : ");
                read = new Scanner(System.in);
                option = read.nextInt();
            }
            catch (Exception e)
            {
                option = -1;
            }

            if(option > admin.students.size() || option < 0)
            {
                System.out.println(lightYellow+"|----------("+red+" Invalid Option "+lightYellow+")----------|"+reset+"\n");

            }
        }while (option > admin.students.size() || option < 0);

        return option;
    }

    public static void searchMenu(Admin admin)
    {
        char option = printUserTypeControlMenu();
        do
        {
            if(option != ' ')
            {
                try
                {
                    System.out.println(yellow+"|----------("+lightPurple+"Search user"+yellow+")----------|"+reset);

                    System.out.print(yellow+"[?] "+reset+"Name : ");

                    //call grabage collector
                    System.gc();
                    read = new Scanner(System.in);
                    String name = read.nextLine();



                    Person foundedUser = admin.searchUser(option,name);
                    if(foundedUser == null)
                    {
                        System.out.println("\n"+reset+yellow+"|--------("+red+"User Not Found !"+yellow+")--------|"+reset+"\n");
                        name = "";
                    }
                    if(name.isEmpty())
                    {
                        int continueSearch = 0;
                        do {
                            try
                            {
                                System.out.println("\n"+reset+"|--------("+red+"Enter 1 to continue search / 0  to Exit"+yellow+")--------|"+reset);
                                System.out.print(yellow+"[?] Continue: ");
                                read = new Scanner(System.in);
                                continueSearch = read.nextInt();
                            }catch (Exception e)
                            {

                            }
                            if(continueSearch != 1 )
                            {
                                break;
                            }
                            else if (continueSearch < 0 || continueSearch > 1)
                            {
                                System.out.println(yellow+"|------------("+red+" Invalid Option "+yellow+")------------|"+reset);

                            }
                        }while (continueSearch < 0 || continueSearch > 1 );

                        continue;
                    }

                    System.out.println(yellow+"|----------("+lightGreen+"ID : Name : level "+yellow+")----------|"+reset+"\n");

                    System.out.print(green+italicUnderLined+"[=] Result:"+reset);
                    System.out.println(
                                " "+lightBlue+foundedUser.getId()+yellow+" : "+
                                    lightBlue+foundedUser.getName()+yellow+" : "+
                                    lightBlue+foundedUser.getLevel()
                    );



                    name = "";
                    if(name.isEmpty())
                    {
                        System.out.println("\n"+reset+yellow+"|--------("+red+"Enter 1 to continue search / 0  to Exit"+yellow+")--------|"+reset);
                        System.out.print(yellow+"[?] Continue: "+reset);
                        read = new Scanner(System.in);
                        if(read.nextInt() != 1)
                        {
                            break;
                        }
                    }
                }
                catch (Exception ignored){ }



            }

        }while (option != ' ');
    }

    public static void deleteMenu(char role, Admin admin)
    {
        int option = 0;
        do
        {
            try
            {
                listSystemUser(role,admin);
                try {
                    System.out.print(yellow + "[?] Choose: " + reset);
                    option = read.nextInt();
                }catch (Exception e){}

                if(option < 0 )
                {
                    System.out.println(yellow+"|------------("+red+" Invalid Option "+ yellow+")------------|"+reset);

                }
                else if ( role == 's' && !(option < admin.students.size()))
                {
                    System.out.println(yellow+"|------------("+red+" Invalid Option "+ yellow+")------------|"+reset);

                }
                else if( role == 'l' && !( option < admin.lecturers.size()))
                {
                    System.out.println(yellow+"|------------("+red+" Invalid Option "+ yellow+")------------|"+reset);

                }

                if(option != 0)
                {
                    admin.deleteUser(option,role);
                }
            }catch (Exception ignored)
            {
            }



        }while (option != 0);
    }

    public static void updateUser(Person user)
    {
        System.out.println(highLightGray+"+-----------------------------------+"+reset);
        //list user info changeable values
        System.out.println(lightRed+"[ !!! ] enter ( -1 ) to unchanged the old values [ !!! ] ");
        System.out.println(lightYellow+"+----------------------------------+"+reset);
        String level = " ";
        try {


            System.out.print(purple + "[?] New Level: " + reset);
            read = new Scanner(System.in);
            level = read.nextLine();

            user.updateLevel(level);
        }catch (Exception e)
        {
            System.out.println(yellow+"|----------------("+red+" Invalid option "+yellow+")----------------|"+reset);

        }

        System.out.println("\n"+highLightGray+"+-----------------------------------+"+reset);
        System.out.println(bold+blue+"[!] user data updated ...."+reset);
        System.out.println(lightBlue+"name : "+lightYellow +bold+ italic +user.getName()
                +lightBlue+ ", new Level : "+lightYellow+bold+italic+ user.getLevel() +reset);
    }

    public static void addUserMenu(char role , Admin admin)
    {
        String name = "";

        System.out.println(yellow+"|------------("+lightPurple+"[!] add Users "+yellow+")------------|"+reset);
        System.out.println("\n"+highLightGray+"|=======================|"+reset);
        do
        {
            System.out.print(blue + "[?] name : " + reset);
            read = new Scanner(System.in);
            name = read.nextLine();

            if (name.isEmpty())
            {
                System.out.println(yellow+"|------------("+lightRed+" please Enter Name "+yellow+")------------|"+reset);

            }
            else {
                break;
            }
        }while(name.isEmpty());

        int level = 0;
        do {

            try {
                System.out.print(blue+"[?] Level : "+reset );
                level = read.nextInt();
            }catch (Exception ignored){
                System.out.println(yellow+"|------------("+red+" Invalid Option "+yellow+")------------|"+reset);
            }
        }while (level <= 0);

        System.out.println("\n"+highLightGray+"|------------------------|"+reset);
        //update number of users to avoid having same id
        admin.loadNumberOfUsers();
        switch (role)
        {
            case 'l':
                admin.lecturers.add(new Lecturer(name,String.valueOf(level)));

                break;
            case 's':
                admin.students.add(new Student(name,String.valueOf(level)));


        }

        System.out.println(yellow+"|-----------("+green+" User added "+yellow+")-----------|"+reset);

        String username = "" ;
        String password = "";
        String userLevel = "";
        String userID = "";


        System.out.print(lightGreen+"[+] User Info: "+reset);
        switch (role)
        {
            case 'l':

                userID = admin.lecturers.get(admin.lecturers.size()-1).getId();
                username = admin.lecturers.get(admin.lecturers.size()-1).getName();
                password = admin.lecturers.get(admin.lecturers.size()-1).getPassword();
                userLevel = admin.lecturers.get(admin.lecturers.size()-1).getLevel();

                break;
            case 's':
                userID = admin.students.get(admin.students.size()-1).getId();
                username = admin.students.get(admin.students.size()-1).getName();
                password = admin.students.get(admin.students.size()-1).getPassword();
                userLevel = admin.students.get(admin.students.size()-1).getLevel();


        }

        System.out.println(
                "\n"+yellow+"ID : "+ blue+userID +
                        yellow+" , Name : "+blue+username+
                        yellow+" , Password : "+blue+password+
                        yellow+" , Level : "+ blue+ userLevel+"\n"
        );

    }

    //=====================( End Admin Menus )=====================
}
