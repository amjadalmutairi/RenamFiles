package me.amjad;


/**
 * Created by amjadalmutairi on 6/1/17.
 */

import java.io.File;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private final static String ANSI_BLUE = "\u001B[34m";
    private final static String ANSI_PURPLE = "\u001B[35m";
    private final static String ANSI_RED = "\u001B[31m";
    private final static String ANSI_GREEN = "\u001B[32m";
    private final static String ANSI_CYNE = "\u001B[36m";
    private final static String HIGH_INTENSITY = "\u001B[1m"; //bold

    public static void main(String[] argv)  {

        File Desktop = new File(System.getProperty("user.home") + File.separator + "Desktop");

        String folderName;
        System.out.print(HIGH_INTENSITY + ANSI_BLUE + "\nEnter the (folder) name: ");
        Scanner sc = new Scanner(System.in);
        folderName = sc.nextLine();


        File folder = new File(Desktop + File.separator + folderName);

        if (folder.exists()) {
            //Filtering .DS_Store file for mac OS  ->  https://en.wikipedia.org/wiki/.DS_Store
            File[] listOfFiles = folder.listFiles((dir, name) -> !name.equals(".DS_Store"));

            //sort files by date
            if (listOfFiles != null) {
                Arrays.sort( listOfFiles, (o1, o2) -> {

                    if (o1.lastModified() > o2.lastModified()) {
                        return -1;
                    } else if (o1.lastModified() < o2.lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                });
            }

            if (listOfFiles.length > 0 ) {

                try {


                    System.out.print(ANSI_PURPLE + "- Enter the new naming schema: ");
                    String newName = sc.nextLine();

                    System.out.print(ANSI_PURPLE + "- Enter start counter number: ");
                    int count = sc.nextInt();

                    int countIndex = 0;
                    if (newName.length() != 0) {

                        System.out.println(ANSI_PURPLE + "- Enter the place (index) of the count number in the naming schema, form 0 to " + newName.length() + " : ");
                        System.out.println(ANSI_GREEN + "  Example: The new naming schema is (NEW__NAME) \n           You need the naming to be NEW_1_NAME and NEW_2_NAME ,... etc. \n           You must enter 4 (start counting chars from 0)  ");

                        do {
                            System.out.println(ANSI_PURPLE + "> Now, enter the number: ");
                            countIndex = sc.nextInt();
                            if (countIndex > newName.length() || countIndex < 0)
                                System.out.println(ANSI_RED + ">> Error: The number must be between 0  and " + newName.length() + " : ");
                        } while (countIndex > newName.length() || countIndex < 0);


                    }


                    for (int i = 0; i < listOfFiles.length; i++) {

                        if (listOfFiles[i].isFile()) {

                            String fname;
                            String OrgFileName = listOfFiles[i].getAbsolutePath();
                            String extension = OrgFileName.substring(OrgFileName.lastIndexOf('.'), OrgFileName.length());

                            if (countIndex == 0)
                                fname = count + newName;
                            else if (countIndex == 1)
                                fname = newName.charAt(0) + String.valueOf(count) + newName.substring(countIndex, newName.length());
                            else
                                fname = newName.substring(0, countIndex) + count + newName.substring(countIndex, newName.length());

                            File f1 = new File(Desktop + File.separator + folderName + File.separator + listOfFiles[i].getName());
                            f1.renameTo(new File(Desktop + File.separator + folderName + File.separator + fname + extension));

                            count++;

                        }


                    }
                    System.out.println(ANSI_CYNE + HIGH_INTENSITY + "\n" + "****************  Done Successfully! ****************");
                }
                catch (InputMismatchException e)
                {
                    System.out.println(ANSI_RED + HIGH_INTENSITY + "\n"  + "Error: Incorrect input!");
                }
            }
            else
            {
                System.out.println(ANSI_CYNE + "\nThere is no files to be renamed :) ");
                System.out.println(ANSI_CYNE + ">> The entered folder name: " + folderName);
            }
        }
        else
        {
            System.out.println(ANSI_RED + HIGH_INTENSITY + "\n"  + "Error: The folder name is not correct! or the folder is not in your desktop!");
            System.out.println(ANSI_RED + ">> The entered folder name: " + folderName);
        }

    }
}