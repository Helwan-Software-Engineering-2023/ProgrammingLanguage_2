package Assets;

import java.util.ArrayList;

public class Exam extends Common
{
    //=====================( Attributes )=====================
    private String idHead = "ex";
    private int duration;
    public ArrayList<Question> questions = new ArrayList<>();
    private int NumberOfQuestions;
    public static int numberOfExams;

    //=====================( Methods )=====================

    public Exam()
    {
        //set files to read from
        setFilePath("Exam.txt");
        setFolderPath(this);

        //start read and format data
        file = new FileHandler(getFolderPath()+getFilePath());
        formatData(file.readFile(),this);
    }

    public Exam(String name,int duration, ArrayList<Question> questions )
    {
        this();
        numberOfExams++;
        this.setId(idHead+numberOfExams);
        this.setName(name);
        this.setDuration(duration);
        this.questions = questions;
    }
    public int getDuration() {
        return duration;
    }

    public int getNumberOfQuestions() {
        return NumberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        NumberOfQuestions = numberOfQuestions;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String toString()
    {
        return lightPurple+"[!] Exam of " + getName() + " |  duration :  "+ getDuration()+" min "+reset;
    }


}
