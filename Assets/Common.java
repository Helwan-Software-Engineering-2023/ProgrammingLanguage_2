package Assets;
import Persons.*;

abstract class Common implements ColorSchema{

    //==================( attributes )==================

    private String id;

    private String name;

    private String folderPath;


    private String filePath;


    public FileHandler  file;


    public String data ;


    public String [] record;


    //==================( methods )==================


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(Object object) {
        String className = object.getClass().getName();
        if ( className.equals("Assets.Subject") ||
                className.equals("Assets.Exam") ||
                className.equals("Assets.Report")
        )
        {
            folderPath = "Service\\";
        }
        else
        {
            folderPath = "Users\\";
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    //this method used to split head data from body data and set records in array
    public void formatData(String data, Object o)
    {
        if(o instanceof Student)
        {
            String fileHead = data.split("@")[0];

            Student.numberOfStudents = Integer.parseInt(fileHead.trim());

            //remove newline from begin of record
            this.data = data.split("@")[1];

            //fill students record
            record =  this.data.split(",");

        }
        else if (o instanceof Lecturer)
        {
            String fileHead = data.split("@")[0];

            Lecturer.numberOfLecturers = Integer.parseInt(fileHead.trim());

            //remove newline from begin of record
            this.data = data.split("@")[1];

            //fill students record
            record =  this.data.split(",");
        }
        else if (o instanceof Admin)
        {
            String fileHead = data.split("@")[0];

            Admin.numberOfAdmins = Integer.parseInt(fileHead.trim());

            //remove newline from begin of record
            this.data = data.split("@")[1];

            //fill students record
            record =  this.data.split(",");
        }
        else if (o instanceof Subject)
        {
            String fileHead = data.split("@")[0];

            Subject.numberOfSubjects = Integer.parseInt(fileHead.trim());

            //remove newline from begin of record
            this.data = data.split("@")[1];

            //fill students record
            record =  this.data.split(",");
        }
        else if (o instanceof Report)
        {
            String fileHead = data.split("@")[0];

            Report.numberOfReports = Integer.parseInt(fileHead.trim());

            //remove newline from begin of record
            this.data = data.split("@")[1];

            //fill students record
            record =  this.data.split(",");
        }
        else if (o instanceof Exam)
        {
            String fileHead = data.split("@")[0];

            Exam.numberOfExams = Integer.parseInt(fileHead.trim());

            //remove newline from begin of record
            this.data = data.split("@")[1];

            //fill students record
            record =  this.data.split(",");
        }

    }

    //this method is used to load specific record to object using id of that object
    public void loadData(String objectID, Object object)
    {

        if(object instanceof Student)
        {

            for( int record_id = 0; record_id < record.length ; record_id++)
            {
                int item = 1;
                String id_in_record ;
                try
                {
                    id_in_record= record[record_id].split("\n")[item++];
                }
                catch (Exception e)
                {
                    id_in_record = "-0";
                }


                //search for correct id
                if(objectID.equals(id_in_record))
                {
                    ((Student) object).setId(id_in_record);
                    ((Student) object).setPassword(record[record_id].split("\n")[item++]);
                    ((Student) object).setName(record[record_id].split("\n")[item++]);
                    ((Student) object).setLevel(record[record_id].split("\n")[item++]);
                    ((Student) object).setRole(record[record_id].split("\n")[item++].charAt(0));
                    try
                    {
                        ((Student) object).numberOfSubjects = Integer.parseInt(record[record_id].split("\n")[item++]);
                    }catch (Exception e)
                    {
                        return;
                    }

                    //load student subject and there marks
                    for(int value = item, sub = 0; sub < ((Student) object).numberOfSubjects; value++, sub++)
                    {
                        //set subject ids
                        ((Student) object).subjectId +=
                                        record[record_id]
                                        .split("\n")[value]
                                        .split("=")[0]+","
                        ;
                        //set marks
                        ((Student) object).mark +=
                                record[record_id]
                                        .split("\n")[value]
                                        .split("=")[1]+","

                        ;
                    }

                    return;

                }
            }

        }
        else if(object instanceof Lecturer)
        {

            for( int record_id = 0; record_id < record.length ; record_id++)
            {
                int item = 1;
                String id_in_record ;
                try
                {
                    id_in_record= record[record_id].split("\n")[item++];
                }
                catch (Exception e)
                {
                    id_in_record = "-0";
                }

                //search for correct id
                if(objectID.equals(id_in_record))
                {
                    ((Lecturer) object).setId(id_in_record);
                    ((Lecturer) object).setPassword(record[record_id].split("\n")[item++]);
                    ((Lecturer) object).setName(record[record_id].split("\n")[item++]);
                    ((Lecturer) object).setLevel(record[record_id].split("\n")[item++]);
                    ((Lecturer) object).setRole(record[record_id].split("\n")[item++].charAt(0));
                    try {
                        ((Lecturer) object).numberOfSubjects = Integer.parseInt(record[record_id].split("\n")[item++]);
                    }catch (Exception e)
                    {
                        return;
                    }
                    //load lecturer subject and there report
                    for(int value = item, sub = 0; sub < ((Lecturer) object).numberOfSubjects ; value++ , sub++)
                    {
                        //set subject ids
                        ((Lecturer) object).subjectIds +=
                                record[record_id]
                                        .split("\n")[value]
                                        .split("=")[0]+","
                        ;
                        //set marks
                        ((Lecturer) object).reportids +=
                                record[record_id]
                                    .split("\n")[value]
                                    .split("=")[1]+ ","

                        ;

                    }


                    return;
                }
            }
        }
        else if(object instanceof Admin)
        {

            for( int record_id = 0; record_id < record.length ; record_id++)
            {
                int item = 1;
                String id_in_record ;
                try
                {
                    id_in_record= record[record_id].split("\n")[item++];
                }
                catch (Exception e)
                {
                    id_in_record = "-0";
                }

                //search for correct id
                if(objectID.equals(id_in_record))
                {
                    ((Admin) object).setId(id_in_record);
                    ((Admin) object).setPassword(record[record_id].split("\n")[item++]);
                    ((Admin) object).setName(record[record_id].split("\n")[item++]);
                    ((Admin) object).setRole(record[record_id].split("\n")[item++].charAt(0));
                    return;
                }
            }
        }
        else if(object instanceof Subject)
        {

            for( int record_id = 0; record_id < record.length ; record_id++)
            {
                int item = 1;

                String id_in_record ;
                try
                {
                    id_in_record= record[record_id].split("\n")[item].split("=")[0];
                }
                catch (Exception e)
                {
                    id_in_record = "-0";
                }

                //search for correct id
                if(objectID.equals(id_in_record))
                {
                    ((Subject) object).setId(id_in_record);
                    ((Subject) object).setExamId(record[record_id].split("\n")[item].split("=")[1]);
                    return;
                }
            }
        }
        else if(object instanceof Exam)
        {

            for( int record_id = 0; record_id < record.length ; record_id++)
            {
                int item = 1;

                String id_in_record ;
                try
                {
                    id_in_record= record[record_id].split("\n")[item++];
                }
                catch (Exception e)
                {
                    id_in_record = "-0";
                }



                //search for correct id
                if(objectID.equals(id_in_record))
                {
                    ((Exam) object).setId(id_in_record);
                    ((Exam) object).setName(record[record_id].split("\n")[item++]);
                    ((Exam) object).setDuration( Integer.parseInt(record[record_id].split("\n")[item++]));
                    ((Exam) object).setNumberOfQuestions( Integer.parseInt(record[record_id].split("\n")[item++]));

                    for(int value = item, countQuestions = 0; countQuestions < ((Exam) object).getNumberOfQuestions(); value++ , countQuestions++)
                    {
                        ((Exam) object).questions.add
                                (
                                        //question description
                                        new Question
                                        (
                                            record[record_id].split("\n")[value].split("=")[0],
                                            record[record_id].split("\n")[value].split("=")[1].charAt(0)
                                        )
                                );
                    }

                    return;
                }
            }
        }
        else if(object instanceof Report)
        {

            for( int record_id = 0; record_id < record.length ; record_id++)
            {
                int item = 1;

                String id_in_record ;
                try
                {
                    id_in_record= record[record_id].split("\n")[item++];
                }
                catch (Exception e)
                {
                    id_in_record = "-0";
                }


                //search for correct id
                if(objectID.equals(id_in_record))
                {
                    ((Report) object).setId(id_in_record);
                    ((Report) object).setNumberOfRecordsInReport( Integer.parseInt(record[record_id].split("\n")[item++]));

                    StringBuilder loadDescription = new StringBuilder();
                    for(int value = item, report = 0; report < ((Report) object).getNumberOfRecordsInReport(); report++ , value++)
                    {
                         loadDescription.append(record[record_id].split("\n")[value].split("=")[0])
                                        .append(" | ")
                                        .append(record[record_id].split("\n")[value].split("=")[1])
                                        .append("\n\t\t");

                    }
                    ((Report) object).setReportDescription(loadDescription.toString());

                    return;
                }
            }
        }


    }

    public void saveObjectData(String objectID, Object object)
    {
        //create buffer to store all old data with the updated one

        StringBuilder newRecord = new StringBuilder();
        StringBuilder collectRecord = new StringBuilder();
        boolean updated = false;

        if(object instanceof Student)
        {
            for( int record_id = 0; record_id < record.length ; record_id++)
            {

                int item = 1;
                String id_in_record;

                if(record[record_id].charAt(0) != '\n')
                {
                    item = 0;
                }
                try
                {
                     id_in_record = record[record_id].split("\n")[item];
                }catch (Exception e) {

                    //set id with dump value to skip error
                    id_in_record = "-1";
                }


                //search for correct id
                if (objectID.equals(id_in_record))
                {
                    updated = true;
                    collectRecord.append(((Student) object).getId()).append("\n");
                    collectRecord.append(((Student) object).getPassword()).append("\n");
                    collectRecord.append(((Student) object).getName()).append("\n");
                    collectRecord.append(((Student) object).getLevel()).append("\n");
                    collectRecord.append(((Student) object).getRole()).append("\n");
                    collectRecord.append(((Student) object).numberOfSubjects).append("\n");
                    for (int value = 0; value < ((Student) object).numberOfSubjects ; value++)
                    {
                        collectRecord.append(((Student) object).subjectId.split(",")[value]).append("=").append(((Student) object).mark.split(",")[value]).append("\n");

                    }


                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
                else
                {
                    if (!id_in_record.equals("-1"))
                    {
                        //if it is not the updated record then store it
                        newRecord.append(record[record_id].trim()).append(",");
                        continue;
                    }
                }

                if(!updated && id_in_record.equals("-1"))
                {
                    updated=true;
                    collectRecord.append(((Student) object).getId()).append("\n");
                    collectRecord.append(((Student) object).getPassword()).append("\n");
                    collectRecord.append(((Student) object).getName()).append("\n");
                    collectRecord.append(((Student) object).getLevel()).append("\n");
                    collectRecord.append(((Student) object).getRole()).append("\n");
                    collectRecord.append(((Student) object).numberOfSubjects).append("\n");
                    if(((Student) object).numberOfSubjects != 0)
                    {
                        for (int value = 0; value < ((Student) object).numberOfSubjects ; value++)
                        {
                            collectRecord.append(((Student) object).subjectId.split(",")[value]).append("=").append(((Student) object).mark.split(",")[value]).append("\n");

                        }
                    }


                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
            }



        }
        else if (object instanceof Exam)
        {
            for(int record_id = 0; record_id < record.length ; record_id++)
            {

                int item = 1;
                String id_in_record;


                try
                {
                    id_in_record = record[record_id].split("\n")[item];
                }catch (Exception e) {

                    //set id with dump value to skip error
                    id_in_record = "-1";
                }


                //search for correct id
                if (objectID.equals(id_in_record))
                {
                    updated = true;
                    collectRecord.append(((Exam) object).getId()).append("\n");
                    collectRecord.append(((Exam) object).getName()).append("\n");
                    collectRecord.append(((Exam) object).getDuration()).append("\n");
                    collectRecord.append(((Exam) object).getNumberOfQuestions()).append("\n");

                    for (int value = 0; value < ((Exam) object).getNumberOfQuestions() ; value++)
                    {

                        collectRecord.append(((Exam) object).questions.get(value).getDescription()).append("=")
                                .append(((Exam) object).questions.get(value).getCorrectChoice()).append("\n");
                    }


                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
                else
                {
                    //to delete exam send exam id mixed with -  example: -ex5 so ex5 to delete
                    if (!id_in_record.equals("-1") &&
                            !( objectID.charAt(0) == '-' && objectID.substring(1).equals(id_in_record) )
                    )
                    {
                        //if it is not the updated record then store it
                        newRecord.append(record[record_id].trim()).append(",");

                    }
                }

                //mean exam not exist then add it
                if(id_in_record.equals("-1") && !updated && !( objectID.charAt(0) == '-' ))
                {
                    collectRecord.append(((Exam) object).getId()).append("\n");
                    collectRecord.append(((Exam) object).getName()).append("\n");
                    collectRecord.append(((Exam) object).getDuration()).append("\n");
                    collectRecord.append(((Exam) object).getNumberOfQuestions()).append("\n");

                    for (int value = 0; value < ((Exam) object).getNumberOfQuestions() ; value++)
                    {

                        collectRecord.append(((Exam) object).questions.get(value).getDescription()).append("=")
                                .append(((Exam) object).questions.get(value).getCorrectChoice()).append("\n");
                    }


                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
            }
        }
        else if (object instanceof Subject)
        {
            for(int record_id = 0; record_id < record.length ; record_id++)
            {

                int item = 1;
                String id_in_record;

                try
                {
                    id_in_record = record[record_id].split("\n")[item].split("=")[0];
                }catch (Exception e) {

                    //set id with dump value to skip error
                    id_in_record = "-1";
                }


                //search for correct id
                if (objectID.equals(id_in_record))
                {
                    updated = true;

                    collectRecord.append(((Subject) object).getId()).append("=")
                            .append(((Subject) object).getExamId()).append("\n");


                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
                else
                {
                    if (!id_in_record.equals("-1"))
                    {
                        //if it is not the updated record then store it
                        newRecord.append(record[record_id].trim()).append(",");
                    }
                }

                if(id_in_record.equals("-1") && !updated)
                {
                    collectRecord.append(((Subject) object).getId()).append("=")
                            .append(((Subject) object).getExamId()).append(",");


                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
            }
        }
        else if(object instanceof Lecturer)
        {
            for( int record_id = 0; record_id < record.length ; record_id++)
            {

                int item = 1;
                String id_in_record;

                if(record[record_id].charAt(0) != '\n')
                {
                    item = 0;
                }

                try
                {
                    id_in_record = record[record_id].split("\n")[item];
                }catch (Exception e) {

                    //set id with dump value to skip error
                    id_in_record = "-1";
                }


                //search for correct id
                if (objectID.equals(id_in_record))
                {
                    updated = true;
                    collectRecord.append(((Lecturer) object).getId()).append("\n");
                    collectRecord.append(((Lecturer) object).getPassword()).append("\n");
                    collectRecord.append(((Lecturer) object).getName()).append("\n");
                    collectRecord.append(((Lecturer) object).getLevel()).append("\n");
                    collectRecord.append(((Lecturer) object).getRole()).append("\n");
                    collectRecord.append(((Lecturer) object).numberOfSubjects).append("\n");

                    if(((Lecturer) object).numberOfSubjects != 0) {
                        for (int value = 0; value < ((Lecturer) object).numberOfSubjects; value++) {
                            collectRecord.append(((Lecturer) object).subjectIds.split(",")[value]).append("=").
                                    append(((Lecturer) object).reportids.split(",")[value]).append("\n");

                        }
                    }


                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
                else
                {
                    if (!id_in_record.equals("-1"))
                    {
                        //if it is not the updated record then store it
                        newRecord.append(record[record_id].trim()).append(",");
                        continue;
                    }
                }
                if(!updated && id_in_record.equals("-1") )
                {
                    collectRecord.append(((Lecturer) object).getId()).append("\n");
                    collectRecord.append(((Lecturer) object).getPassword()).append("\n");
                    collectRecord.append(((Lecturer) object).getName()).append("\n");
                    collectRecord.append(((Lecturer) object).getLevel()).append("\n");
                    collectRecord.append(((Lecturer) object).getRole()).append("\n");
                    collectRecord.append(((Lecturer) object).numberOfSubjects).append("\n");

                    if(((Lecturer) object).numberOfSubjects != 0)
                    {
                        for (int value = 0; value < ((Lecturer) object).numberOfSubjects ; value++)
                        {
                            collectRecord.append(((Lecturer) object).subjectIds.split(",")[value]).append("=").
                                    append(((Lecturer) object).reportids.split(",")[value]).append("\n");

                        }
                    }


                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
            }



        }
        else if (object instanceof Report)
        {
            for(int record_id = 0; record_id < record.length ; record_id++)
            {

                int item = 1;
                String id_in_record;


                try
                {
                    id_in_record = record[record_id].split("\n")[item];
                }catch (Exception e) {

                    //set id with dump value to skip error
                    id_in_record = "-1";
                }


                //search for correct id
                if (objectID.equals(id_in_record))
                {
                    updated = true;
                    collectRecord.append(((Report) object).getId()).append("\n");
                    collectRecord.append(((Report) object).getNumberOfRecordsInReport()).append("\n");

                    for (int value = 0; value < ((Report) object).getNumberOfRecordsInReport() ; value++)
                    {

                        String [] reportRecord = ((Report) object).getReportDescription().split("\n")[value].split("\\|");
                        collectRecord.append(reportRecord[0]).append("=").append(reportRecord[1]).append("\n");
                    }


                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
                else
                {
                    //to delete exam send exam id mixed with -  example: -ex5 so ex5 to delete
                    if (!id_in_record.equals("-1") &&
                            !( objectID.charAt(0) == '-' && objectID.substring(1).equals(id_in_record) )
                    )
                    {
                        //if it is not the updated record then store it
                        newRecord.append(record[record_id].trim()).append(",");

                    }
                }

                //mean exam not exist then add it
                if(id_in_record.equals("-1") && !updated && !( objectID.charAt(0) == '-' ))
                {
                    collectRecord.append(((Report) object).getId()).append("\n");
                    collectRecord.append(((Report) object).getNumberOfRecordsInReport()).append("\n");

                    for (int value = 0; value < ((Report) object).getNumberOfRecordsInReport() ; value++)
                    {
                        String [] reportRecord = ((Report) object).getReportDescription().split("\n")[value].split("|");
                        collectRecord.append(reportRecord[0]).append("=").append(reportRecord[1]).append("\n");
                    }


                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
            }
        }
        else if (object instanceof Admin)
        {
            for(int record_id = 0; record_id < record.length ; record_id++)
            {

                int item = 1;
                String id_in_record;


                try
                {
                    id_in_record = record[record_id].split("\n")[item];
                }catch (Exception e) {

                    //set id with dump value to skip error
                    id_in_record = "-1";
                }


                //search for correct id
                if (objectID.equals(id_in_record))
                {
                    updated = true;
                    collectRecord.append(((Admin) object).getId()).append("\n");
                    collectRecord.append(((Admin) object).getPassword()).append("\n");
                    collectRecord.append(((Admin) object).getName()).append("\n");
                    collectRecord.append(((Admin) object).getRole()).append("\n");

                    //save the new record
                    newRecord.append(collectRecord.toString().trim()).append(",");
                }
                else
                {
                    //to delete exam send exam id mixed with -  example: -ex5 so ex5 to delete
                    if (!id_in_record.equals("-1") &&
                            !( objectID.charAt(0) == '-' && objectID.substring(1).equals(id_in_record) )
                    )
                    {
                        //if it is not the updated record then store it
                        newRecord.append(record[record_id].trim()).append(",");

                    }
                }


            }
        }

        //at the end put the new records in the old record attribute
        record = newRecord.toString().split(",");
    }

    private String deformatData(Object object)
    {
        //collect new records with header
        String newDataToStore ;
        String fileHead="";
        StringBuilder allInoneRecord = new StringBuilder();

        //convert array of record to 1 string of record
        for (int value = 0; value < record.length; value++)
        {
            //add record separator while combining records
            allInoneRecord.append(record[value]).append("\n,\n");
        }

        //determine from where we get file head ( number of objects )
        if (object instanceof Student)
        {
            fileHead =  String.valueOf(Student.numberOfStudents);
        }
        else if (object instanceof Exam)
        {
            fileHead = String.valueOf(Exam.numberOfExams);
        }
        else if (object instanceof Subject)
        {
            fileHead = String.valueOf(Subject.numberOfSubjects);
        }
        else if (object instanceof Lecturer)
        {
            fileHead = String.valueOf(Lecturer.numberOfLecturers);
        }
        else if (object instanceof Report)
        {
            fileHead = String.valueOf(Report.numberOfReports);
        }
        else if (object instanceof Admin)
        {
            fileHead = String.valueOf(Admin.numberOfAdmins);
        }
        //store the new data
        newDataToStore = fileHead+"\n@\n"+allInoneRecord;

        return newDataToStore;
    }

    public void save(String objectId,Object object)
    {
        if( object instanceof Student)
        {
            ((Student) object).saveObjectData(objectId,((Student) object));
            ((Student) object).file.writeToFile(deformatData(((Student) object)));
        }
        else if ( object instanceof Exam)
        {
            ((Exam) object).saveObjectData(objectId,((Exam) object));
            ((Exam) object).file.writeToFile(deformatData(((Exam) object)));
        }
        else if ( object instanceof Subject)
        {
            ((Subject) object).saveObjectData(objectId,((Subject) object));
            ((Subject) object).file.writeToFile(deformatData(((Subject) object)));
        }
        else if ( object instanceof Lecturer)
        {
            ((Lecturer) object).saveObjectData(objectId,((Lecturer) object));
            ((Lecturer) object).file.writeToFile(deformatData(((Lecturer) object)));
        }
        else if ( object instanceof Report)
        {
            ((Report) object).saveObjectData(objectId,((Report) object));
            ((Report) object).file.writeToFile(deformatData(((Report) object)));
        }
        else if ( object instanceof Admin)
        {
            ((Admin) object).saveObjectData(objectId,((Admin) object));
            ((Admin) object).saveAttributes();
            ((Admin) object).file.writeToFile(deformatData(((Admin) object)));
        }
    }
}
