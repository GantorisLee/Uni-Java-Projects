/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class SimpleCompetitions implements Serializable {
    Competition currentCompetition;
    private int id = 0;
    boolean competitionType;
    String competitionTypeString;
    String memberIdToAdd;
    String billCostToAdd;
    String memberNameToAdd;
    ArrayList<String> competitionName = new ArrayList<>(1);
    ArrayList<Integer> competitionId = new ArrayList<>(1);
    ArrayList<String> finalReport = new ArrayList<>(1);
    ArrayList<ArrayList<String>> memberIdListMain = new ArrayList<>();
    ArrayList<ArrayList<String>> billIdListMain = new ArrayList<>();
    transient private Scanner keyboard;
    private boolean testingMode;


    /*
    imports the scanner
     */
    public SimpleCompetitions(Scanner keyboard) {
        this.keyboard = keyboard;
    }

    /*
    adds new competition
     */
    public void addNewCompetition(Scanner keyboard, boolean testingMode) {
        this.keyboard = keyboard;
        this.testingMode = testingMode;
        String name;


        //Loop for getting if it is a LuckyNumbersCompetition or RandomPickCompetition
        competitionTypeLoop:
        while (true) {
            System.out.println("Type of competition (L: LuckyNumbers, R: RandomPick)?:");
            String competitionOption = keyboard.nextLine();
            competitionOption = competitionOption.toUpperCase();

            switch (competitionOption) {
                case "L":
                    competitionType = true;
                    competitionTypeString = "LuckyNumbersCompetition";
                    break competitionTypeLoop;
                case "R":
                    competitionType = false;
                    competitionTypeString = "RandomPickCompetition";
                    break competitionTypeLoop;
                default:
                    System.out.println("Invalid competition type! Please choose again.");
                    break;
            }
        }

        System.out.println("Competition name: ");
        name = keyboard.nextLine();
        id++;
        System.out.println("A new competition has been created!");
        System.out.println("Competition ID: " + id + ", Competition Name: " + name + ", Type: " +
                competitionTypeString);
        competitionId.add(id);
        competitionName.add(name);
        if (competitionType) {
            currentCompetition  = new LuckyNumbersCompetition(keyboard, testingMode);
        } else {

            currentCompetition = new RandomPickCompetition(keyboard, testingMode);
        }
        currentCompetition.addCompetitionEntry(name, id);
    }



    /*
    Check bill for errors, cross check with memberId, incite adding entries
    @param keyboard is the scanner
     */
    public void checkBillId(Scanner keyboard) {
        String billId = null;
        int idLength = 6;
        boolean billIdInput = true;
        boolean continueAddEntry = true;

        while (continueAddEntry) {
            boolean memberExist = true;
            while(memberExist) {
                boolean usedBill = false;
                boolean noMemberId = false;
                boolean matchBill = false;

                System.out.println("Bill ID: ");
                billId = keyboard.nextLine();

                if (billId.matches("[0-9]+") && billId.length() == 6) {

                    //reiterate all the bills
                    for (int entry = 0; entry < billIdListMain.size(); entry++) {

                        //check if there is a matching bill
                        if (billId.equals(billIdListMain.get(entry).get(0))) {
                            matchBill = true;

                            //check if there is a member in the bill
                            if (!billIdListMain.get(entry).get(1).isBlank()) {

                                memberIdToAdd = billIdListMain.get(entry).get(1);
                                billCostToAdd = billIdListMain.get(entry).get(2);

                                if (billIdListMain.get(entry).get(3).equals("TRUE")) {
                                    usedBill = true;
                                    break;
                                } else {
                                    usedBill = false;
                                    for (int memberEntry = 0;
                                         memberEntry < memberIdListMain.size();
                                         memberEntry++) {
                                        if (memberIdToAdd.equals
                                                (memberIdListMain.get(memberEntry).get(0))) {
                                            memberNameToAdd = memberIdListMain.
                                                    get(memberEntry).get(1);
                                        }
                                    }
                                    memberExist = false;
                                    noMemberId = false;
                                    billIdListMain.get(entry).set(3, "TRUE");
                                    break;
                                }

                            } else {
                                noMemberId = true;
                            }

                        }
                    }

                    if (noMemberId){
                        System.out.println("This bill has no member id. Please try again.");
                    } else if (memberExist && usedBill) {
                        System.out.println("This bill has already been " +
                                "used for a competition. Please try again.");
                    } else if (!matchBill){
                        System.out.println("This bill does not exist. Please try again.");
                    }
                } else {
                    System.out.println("Invalid bill id! It must be a 6-digit number." +
                            " Please try " + "again.");
                }
            }
            currentCompetition.addEntries(memberIdToAdd,billCostToAdd,
                    memberNameToAdd);

            //Request to continue or stop adding entries
            boolean wrong_yes_no = true;
            while (wrong_yes_no) {
                System.out.println("Add more entries (Y/N)?");
                String yes_no = keyboard.nextLine();
                if (yes_no.equalsIgnoreCase("Y")) {
                    wrong_yes_no = false;
                    currentCompetition.existingEntries = currentCompetition.entryId;
                } else if (yes_no.equalsIgnoreCase("N")) {
                    wrong_yes_no = false;
                    continueAddEntry = false;
                } else {
                    System.out.println("Unsupported option. Please try again!");
                }
            }
        }
    }

    /*
    get a summary if all report, when option 4 is chosen
    @param openCompetition checks if a competition is open and required to report.
    */
    public void report(boolean openCompetition) {
        int currentOpenComp = 0;
        if (openCompetition) {
            currentOpenComp = 1;
        }

        System.out.println("----SUMMARY REPORT----" + "\n"
                + "+Number of completed competitions: " + (id-currentOpenComp) + "\n"
                + "+Number of active competitions: " + currentOpenComp);

        if (finalReport.size() != 0) {
            String joined = String.join("\n\n", finalReport);
            System.out.println();
            System.out.println(joined);
        } else{
            System.out.print("\n");
        }

        if(openCompetition) {
            int finalEntryId = 0;
            if(currentCompetition.entryId < 0){
            } else {
                finalEntryId = currentCompetition.entryId;
            }
            if (finalReport.size() != 0) {
                System.out.println();
            }
            System.out.println("Competition ID: " + id + ", "
                    + "name: " + competitionName.get(competitionName.size()-1) +
                    ", active: yes" + "\n" +"Number of entries: " + (finalEntryId));
        }

    }


    /*
    main body of code when run
     */
    public static void main(String[] args) {
        boolean openCompetition = false;
        boolean testingMode = true;
        boolean loadFileMode = true;
        String memberFile;
        String billFile = null;
        Scanner keyboard = new Scanner(System.in);
        SimpleCompetitions sc = new SimpleCompetitions(keyboard);
        DataProvider dp = new DataProvider();

        System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");

        /*
        Loop for asking user the option to load file
         */
        loadFileLoop:
        while (true) {
            System.out.println("Load competitions from file? (Y/N)?");
            String option = keyboard.nextLine();
            option = option.toUpperCase();

            switch (option) {
                case "Y":
                    loadFileMode = true;
                    break loadFileLoop;
                case "N":
                    loadFileMode = false;
                    break loadFileLoop;
                default:
                    System.out.println("Unsupported option. Please try again!");
                    break;
            }
        }

        /*
        If loads file, check if input is correct.
        Loop created to asks for testing or normal mode.
         */
        if (loadFileMode) {
            System.out.println("File name:");
            String fileName = keyboard.nextLine();
            try {
                ObjectInputStream inputStream =
                        new ObjectInputStream(new FileInputStream(fileName));

                SimpleCompetitions readOne = (SimpleCompetitions) inputStream.readObject();
                Competition readTwo =
                        (Competition) inputStream.readObject();
                LuckyNumbersCompetition readThree =
                        (LuckyNumbersCompetition) inputStream.readObject();

                DataProvider readTen = (DataProvider) inputStream.readObject();

                System.out.println("The following were read from the file:");
                System.out.println(readOne); //the toString() method will be invoked
                System.out.println(readTwo);
                System.out.println(readTen);

                inputStream.close();
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find datafile.");
                System. exit(0);
            } catch(ClassNotFoundException e) {
                System.out.println("Problems with reading objects from file.");
                System. exit(0);
            } catch (IOException e) {
                System.out.println("Problems with file input.");
                System. exit(0);
            }
        } else {
            testingLoop:
            while (true) {
                System.out.println("Which mode would you like to run? " +
                        "(Type T for Testing, and N for Normal mode):");
                String option = keyboard.nextLine();

                option = option.toUpperCase();

                switch (option) {
                    case "T":
//                    System.out.println("Testing mode");
                        testingMode = true;
                        break testingLoop;
                    case "N":
//                    System.out.println("Normal mode");
                        testingMode = false;
                        break testingLoop;
                    default:
                        System.out.println("Invalid mode! Please choose again.");
                        break;
                }
            }
        }

        /*
        Asks user for Member file and bill file input.
        Imports the member and bill file from Data Provider
         */
        boolean done = false;
        while (!done) {
            System.out.println("Member file: ");
            memberFile = keyboard.nextLine();
            System.out.println("Bill file: ");
            billFile = keyboard.nextLine();

            try {
                dp.dataCheck(memberFile, billFile);
                sc.memberIdListMain =
                        (ArrayList<ArrayList<String>>)dp.memberIdList.clone();
                sc.billIdListMain =
                        (ArrayList<ArrayList<String>>)dp.billIdList.clone();
                done = true;
            } catch (DataAccessException e) {
                System.out.println(e.getMessage());
            } catch (DataFormatException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        /*
        Main Menu.
        Contains the 5 options to choose from.
        If 1 is input, start a new competition.
        2 is input, add new entries.
        3 is input, draw winners.
        4 is input, get a summary report.
        5 to exit program.
         */
        mainLoop:
        while (true) {
            System.out.println("Please select an option. Type 5 to exit.");
            System.out.println("1. Create a new competition");
            System.out.println("2. Add new entries");
            System.out.println("3. Draw winners");
            System.out.println("4. Get a summary report");
            System.out.println("5. Exit");
            String option = keyboard.nextLine();
            switch (option) {
                case "1":
                    if(openCompetition){
                        System.out.println("There is an active competition. SimpleCompetitions " +
                                "does not support " + "concurrent competitions!");
                    } else {
                        sc.addNewCompetition(keyboard, testingMode);
                        openCompetition = true;
                    }
                    break;
                case "2":
//                    System.out.println("adds new entries");
                    if(!openCompetition){
                        System.out.println("There is no active competition. Please create one!");
                    } else {
                        sc.checkBillId(keyboard);
                    }

                    break;
                case "3":
                    if(!openCompetition){
                        System.out.println("There is no active competition. Please create one!");
                    } else {
                        if (sc.currentCompetition.entryId == 0) {
                            System.out.println("The current competition has no entries yet!");
                        } else {
                            sc.currentCompetition.drawWinners();
                            openCompetition = false;
                            sc.finalReport.add(sc.currentCompetition.report());
                        }
                    }
                    break;
                case "4":
                    if(sc.id == 0){
                        System.out.println("No competition has been created yet!");
                    } else {
                        sc.report(openCompetition);
                    }
                    break;
                case "5":
                    saveOptionLoop:
                    while (true) {
                        System.out.println("Save competitions to file? (Y/N)?");
                        String saveOption = keyboard.nextLine();
                        saveOption = saveOption.toUpperCase();

                        switch (saveOption) {
                            case "Y":
                                //Writes the Serialisation outputStream file
                                System.out.println("File name:");
                                String fileName = keyboard.nextLine();
                                try {
                                    ObjectOutputStream outputStream =
                                            new ObjectOutputStream
                                                    (new FileOutputStream(fileName));

                                    outputStream.writeObject(sc);
                                    outputStream.writeObject(sc.currentCompetition);
                                    outputStream.writeObject(sc.currentCompetition);
                                    outputStream.writeObject(dp);
                                    outputStream.close();

                                    System.out.println("Competitions have been saved to file.");

                                    dp.writesCSV(sc.billIdListMain, billFile);
                                    System.out.println("The bill file has also been automatically" +
                                            " updated.");
                                    break saveOptionLoop;


                                } catch (IOException e) {
                                    System.out.println("Problem with file output.");
                                    System.exit(0);
                                }
                                break saveOptionLoop;
                            case "N":
                                break saveOptionLoop;
                            default:
                                System.out.println("Invalid mode! Please choose again.");
                                break;
                        }
                    }
                    break mainLoop;
                default:
                    System.out.println("Unsupported option. Please try again!");
                    break;
            }
        }

        System.out.println("Goodbye!");
        keyboard.close();




    }

}

