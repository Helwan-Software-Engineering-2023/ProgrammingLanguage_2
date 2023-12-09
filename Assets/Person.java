package Assets;
import Persons.*;
import Assets.*;

public abstract class Person extends Common
{
    //=====================( Attributes )=====================

    private String password;
    private char role;
    private String level;

    public boolean newUser = false;
    public boolean newSubject = false;
    public static int numberOfUsers;


    //=====================( Methods )=====================

    public Person()
    {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public char getRole() {
        return role;
    }

    public void setRole(char role) {
        this.role = role;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean login(String id, String password) {

            if ( password.equals(getPassword()) && id.equals(getId()) )
            {
                return true;
            }

        return false;
    }

    public void loadNumberOfUsers() {
        //sum all system users
        Person.numberOfUsers = Student.numberOfStudents + Lecturer.numberOfLecturers + Admin.numberOfAdmins;

    }

    public void updateInfo(String name, String newPassword)
    {

        if( !name.equals("-1") )
            setName(name);

        if( !newPassword.equals("-1") )
            setPassword(newPassword);

    }

    public void updateLevel(String level)
    {

        if( !level.equals("-1") )
            setLevel(level);

    }

    public String toString()
    {
        return "This is Person Class";
    }
}


