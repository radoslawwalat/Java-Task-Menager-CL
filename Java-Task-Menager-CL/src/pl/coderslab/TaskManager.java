package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    static final String[] usableCommands = {"add", "remove", "list", "exit"};

    static String[][] tasks;

    public static void main(String[] args) throws IOException {

        tasks = updateCSVData();

        showUsableCommands();

        Scanner scan = new Scanner(System.in);


        while (scan.hasNextLine()) {
            String typedCommand = scan.nextLine();

        switch (typedCommand) {
            case "list" : listsCVSData();
                break;
            case "add" : addsToCVSData();
                break;

            case "exit" :
                save2dTabToCSV("tasks.csv", tasks);
                System.out.println(ConsoleColors.RED + "Bye bye! :) ");

                System.exit(0); // ukradłem to z gotowego projektu, bo nie wiem jak to obejść :///

                break;

            case "remove" : removesFromCVS(tasks, getNumber());
                System.out.println("Index succesfully deleted.");
                break;


            default:
                System.out.println("Please select a correct option.");
        }
            showUsableCommands();
    }



    }

    public static void showUsableCommands () {

        System.out.println(ConsoleColors.BLUE + "Please select an option: ");
        for (int i = 0; i < usableCommands.length; i++) {
            System.out.println(ConsoleColors.RESET + usableCommands[i]);

        }

    }

    public static String[][] updateCSVData() throws IOException {
        File file = new File("tasks.csv");
        FileReader fr = new FileReader(file);
        char temparr[] = new char[(int) file.length()];
        fr.read(temparr,0,(int) file.length());
        String [] tempString = (new String(temparr)).split("\n");
        String array2d[][] = new String [tempString.length][];
        for(int i=0 ; i<tempString.length; i++)
        {
            array2d[i]=tempString[i].split(", ");
        }

        return array2d;
        // czyta co jest w tasks.csv i uzupełnia tym arreya głównego z taskami.
    }
    public static void listsCVSData() {


        int nrIndeksu = 0;
        for (String[] row : tasks) {
            System.out.print(nrIndeksu + " : ");
            System.out.print(Arrays.toString(row));
            System.out.print("\n");
            nrIndeksu++;

        }
    }

    public static void addsToCVSData()  {


        Scanner in = new Scanner(System.in);
        String[] temporaryArr = new String[3];

        System.out.print("Please add task description: ");
        temporaryArr[0] = in.nextLine();

        System.out.print("Please add task due date: ");
        temporaryArr[1] = in.nextLine();

        System.out.print("Is your task important? (true/false): ");
        temporaryArr[2] = in.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = temporaryArr[0];
        tasks[tasks.length - 1][1] = temporaryArr[1];
        tasks[tasks.length - 1][2] = temporaryArr[2];

    }

    public static void removesFromCVS(String[][] tab, int index) {

        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("index does not exist :/");
        }
    }


    public static int getNumber() {

        Scanner scanRemove = new Scanner(System.in);
        System.out.println("Please select number to remove: ");
        String indexRemove = scanRemove.nextLine();
        while (!isNumberGreaterEqualZero(indexRemove)) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            scanRemove.nextLine();
        }
        return Integer.parseInt(indexRemove);

    }

    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }



    public static void save2dTabToCSV(String fileName, String[][] tab) throws IOException {

        Path dir = Paths.get(fileName);
        String[] lines = new String[tasks.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }

        Files.write(dir, Arrays.asList(lines));



    }
}
