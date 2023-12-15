package Assets;

public class Question extends Common
{
    //=====================( Attributes )=====================

    private String description;
    private char correctChoice;



    //=====================( Methods )=====================
    Question( String description, char correctChoice)
    {
        this.description = description;
        this.correctChoice =correctChoice;
    }

    public char getCorrectChoice() {
        return correctChoice;
    }



    public String getDescription() {
        return description;
    }


    public boolean checkAnswer(char choice)
    {
        if(choice == getCorrectChoice())
        {
            return true;
        }

        return false;
    }


    public String toString()
    {
        return "This is Question Class";
    }
}
