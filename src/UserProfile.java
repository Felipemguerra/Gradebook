import java.io.*;
import java.util.Scanner;

/*Implemented by Daniel and Redden*/

/**
 * This class offers a way to access the user profile and
 * is where further iterations will continue.
 * */
public class UserProfile{

    public static String userPath = Gradebook.userPath+"user";

    /**
     * stores first name from user profile
     * */
    private String FirstName;

    /**
     * stores last name from user profile
     * */
    private String LastName;

    private int semesterCount = 0;

    private String[] semesters = {};

    private String C_DASH = "0";
    private String C_MAJOR = "1";
    private String C_PROG = "2";
    private String C_CHANGE = "3";
    private String C_GPA = "4";
    private String C_SEM = "5";
    private String C_QUIT = "q";
    private String C_TEST = "t";
    private String C_ADD = "\\+";

    /**
     * Starts up the command menu and allows user to select several options
     * to access and edit user profile.
     * */
    public void startup() {
        getName();
        Major UserMajor = new Major();
        String input;
        printProfileDashboard();
        while(!(input = getInput()).matches(C_QUIT)) {
            if(input.matches(C_DASH)) printProfileDashboard();
            else if(input.matches(C_MAJOR)) UserMajor.ShowMajor();
            else if(input.matches(C_PROG)) UserMajor.ShowProgress();
            else if(input.matches(C_CHANGE)) UserMajor.ChangeMajor(FirstName, LastName);
            else if(input.matches(C_GPA)) System.out.println("Showing GPA(Not Yet Implemented)");
            else if(input.matches(C_SEM)) Semesters();
            else System.out.println("Sorry, Try Again.");
            System.out.println("Press 0 For Menu");
        }
    }

    /**takes the first and last name from the user profile in a given user directory
    * to populate the User Profile
     * */
    private void getName() {
        File user = new File(System.getProperty("user.dir")+"/user_profile/user");
        try {
            FileReader InputStream = new FileReader(user);
            BufferedReader BuffReader = new BufferedReader(InputStream);
            FirstName = BuffReader.readLine();
            LastName = BuffReader.readLine();
            BuffReader.close();
        }
        catch (FileNotFoundException FNF) {}
        catch (IOException IOE) {}
    }

    /**
     * takes in input corresponding to Dashboard options
     * @return input code, -1 if bad input
     * */
    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Command option printing method.
     * */
    private void printProfileDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("Hello " + FirstName + " " + LastName);
        System.out.println("\tEnter the Option Number");
        System.out.println("\t0: Show Menu");
        System.out.println("\t1: Show Major");
        System.out.println("\t2: Show Progress");
        System.out.println("\t3: Change Major");
        System.out.println("\t4: Show GPA(Not Yet Implemented)");
        System.out.println("\t5: Go To Semesters");
        System.out.println("\tq: Go Back");
        System.out.println("---------------------------------------");
    }

    private void Semesters() {
        getSemesters();
        printSemestersDashboard();
        String input;
        while(!(input = getInput()).matches(C_QUIT)) {
            if(input.matches(C_ADD)) addSemester();
            else if(input.matches(C_DASH)) printSemestersDashboard();
            else if(input.matches(C_TEST)) makeTestSemesters();
            else if(input.matches("[1-9]+")) {
                int sem = Integer.parseInt(input);
                if(sem > 0 && sem <= semesterCount) new Semester().startup(semesters[sem-1]);
                else System.out.println("Sorry, Try Again.");
            }
            else System.out.println("Sorry, Try Again.");
            System.out.println("Press 0 For Menu");
        }
    }

    private void printSemestersDashboard() {
        System.out.println("---------------------------------------");
        System.out.println("\t"+C_QUIT+": Go Back");
        System.out.println("\t"+C_TEST+": Make Test Semesters");
        System.out.println("\t"+C_ADD+": Add Semester");
        System.out.println("\t0: Show Menu");
        printSemesters();
        System.out.println("---------------------------------------");
    }

    private void printSemesters() {
        for(int i = 0; i < semesterCount; ++i) {
            System.out.println("\t" + (i+1) + ": " + semesters[i]);
        }
    }

    private void getSemesters() {
        File semesters = new File(System.getProperty("user.dir")+"/user_profile/semesters");
        if(!semesters.exists()){ semesters.mkdir(); }
        this.semesters = semesters.list();
        semesterCount = this.semesters.length;
    }

    private void makeTestSemesters() {
        //populate from valid_semesters file
        System.out.println("making test semesters(not yet implemented)");
    }

    private void addSemester() {
        File dir = new File(System.getProperty("user.dir") + "/user_profile/semesters");
        boolean get = true;
        String input = "";
        while(get) {
            get = false;
            input = getSemesterFromConsole();
            for(String i : semesters) {
                if(i.matches(input)) {
                    get = true;
                    System.out.println("Semester Already Exists, Try Again");
                    break;
                }}
        }
        new Semester().addNewSemester(input);
        getSemesters();
    }

    private String getSemesterFromConsole() {
        Scanner scanner = new Scanner(System.in);
        String semester = "", buffer = "";
        boolean get = true;
        while(get) {
            get = false;
            System.out.println("Enter Semester Name: ");
            buffer = scanner.nextLine();
            if(!buffer.matches("(?i)Fall") && !buffer.matches("(?i)Spring") && !buffer.matches("(?i)Spring")) {
                get = true;
                System.out.println("Bad Name, Try Again");
            }
        }
        buffer = buffer.substring(0, 1).toUpperCase() + buffer.substring(1);
        semester += buffer + " ";
        get = true;
        while(get) {
            get = false;
            System.out.println("Enter Semester Year: ");
            buffer = scanner.nextLine();
            if(!buffer.matches("[0-9][0-9][0-9][0-9]")) {
                get = true;
                System.out.println("Bad Year, Try Again");
            }
        }
        semester += buffer;
        return semester;
    }
}
