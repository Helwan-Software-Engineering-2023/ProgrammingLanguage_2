package Assets;

public class Subject extends Common{

    //=====================( Attributes )=====================

    private String idHead = "subj";
    private String examId;
    public static int numberOfSubjects;

    //=====================( Methods )=====================

    public Subject()
    {
        //set files to read from
        setFilePath("Subject.txt");
        setFolderPath(this);

        //start read and format data
        file = new FileHandler(getFolderPath()+getFilePath());
        formatData(file.readFile(),this);
    }
    public Subject(String name)
    {
        this();
        numberOfSubjects++;
        setId(idHead+numberOfSubjects);
        setExamId("-1");
        setName(name);
    }
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }


    public String toString()
    {
        if(!getExamId().equals("-1"))
        {
            Exam exam = new Exam();
            exam.loadData(getExamId(),exam);
            setName(exam.getName());
        }
        else
        {
            setName(getId());
        }
        return blue+bold+ getName() +reset;
    }
}
