package Assets;

import java.io.*;
import java.util.*;

public class FileHandler {

    private File file;
    private final String homePath = "D:\\college\\Second Year\\First__Term\\Programming_Language_2\\Project\\SourceCode\\CollegeExamManagementSystem\\src\\Assets\\";

    private boolean update = false;
    public FileHandler()
    {
    }
    public FileHandler(String filePath)
    {
        file = new File(homePath+filePath);
        createFile(file);
    }

    public void createFile(File file)
    {
        try
        {
            if( !file.exists() )
            {
                file.createNewFile();
            }


        }
        catch (Exception e)
        {

        }

    }

    public String readFile()
    {
        String data = "";
        try
        {
            Scanner read = new Scanner(file);
            while(read.hasNextLine())
            {
                data += read.nextLine() + '\n';
            }
            read.close();

        }
        catch (Exception e)
        {

        }

        return data;

    }

    public void writeToFile(String data)
    {
        try
        {
            FileWriter fileWriter = new FileWriter( file, update);
            fileWriter.write(data);
            fileWriter.close();
        }
        catch (Exception e)
        {

        }

    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
