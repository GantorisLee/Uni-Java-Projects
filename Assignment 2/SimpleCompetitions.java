import java.util.ArrayList;
import java.util.Scanner;


public class SimpleCompetitions {
    Competition currentCompetition;
    private int id = 0;
    ArrayList<String> competitionName = new ArrayList<>(1);
    ArrayList<Integer> competitionId = new ArrayList<>(1);
    ArrayList<String> finalReport = new ArrayList<>(1);


    public void addNewCompetition(Scanner keyboard, boolean testingMode) {
        String name;

        System.out.println("Competition name: ");
        keyboard.nextLine();
        name = keyboard.nextLine();
        id++;
        System.out.println("A new competition has been created!");
        System.out.println("Competition ID: " + id + ", Competition Name: " + name);
        competitionId.add(id);
        competitionName.add(name);
        currentCompetition = new Competition(keyboard, testingMode);
        currentCompetition.addCompetitionEntry(name, id);
    }

    /*get a summary report, option 4*/
    public void report(boolean openCompetition) {
        int currentOpenComp = 0;
        if (openCompetition) {
            currentOpenComp = 1;
        }
        System.out.println("----SUMMARY REPORT----" + "\n"
                + "+Number of completed competitions: " + (id-currentOpenComp) + "\n"
                + "+Number of active competitions: " + currentOpenComp);

        if (finalReport.size() != 0) {
            String joined = String.join("\n", finalReport);
            System.out.println(joined);
        } else{
            System.out.println("/n");
        }

        if(openCompetition) {
            System.out.println("Competition ID : " + id + ", "
                    + "name: " + competitionName.get(competitionName.size()-1) +
                    ", active: yes" + "\n" +"Number of entries: " + (currentCompetition.entryId-1));
        }

    }



    public static void main(String[] args) {

        boolean openCompetition = false;
        boolean testingMode = true;
        Scanner keyboard = new Scanner(System.in);
        SimpleCompetitions sc = new SimpleCompetitions();



        System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");


        testingLoop:
        while (true) {
            System.out.println("Which mode would you like to run? (Type T for Testing, and N for Normal mode):");
            String option = keyboard.next();
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
                case "4":
                    break testingLoop;
                default:
                    System.out.println("Invalid mode! Please choose again.");
                    break;
            }
        }

        mainLoop:
        while (true) {
            System.out.println("Please select an option. Type 5 to exit.");
            System.out.println("1. Create a new competition");
            System.out.println("2. Add new entries");
            System.out.println("3. Draw winners");
            System.out.println("4. Get a summary report");
            System.out.println("5. Exit");
            int option = keyboard.nextInt();

            switch (option) {
                case 1:
                    if(openCompetition){
                        System.out.println("There is an active competition. SimpleCompetitions " +
                                "does not support " + "concurrent competitions!");
                    } else {
                        sc.addNewCompetition(keyboard, testingMode);
                        openCompetition = true;
                    }
                    break;
                case 2:
//                    System.out.println("adds new entries");
                    if(!openCompetition){
                        System.out.println("There is no active competition. Please create one!");
                    } else {
                        sc.currentCompetition.addMemberEntries();
                    }

                    break;
                case 3:
                    if(!openCompetition){
                        System.out.println("There is no active competition. Please create one!");
                    } else {
                        sc.currentCompetition.drawWinners();
                        sc.finalReport.add(sc.currentCompetition.report());
                    }
                    openCompetition = false;
                    break;
                case 4:
                    if(sc.id == 0){
                        System.out.println("No competition has been created yet!");
                    } else {
                        sc.report(openCompetition);
                    }
                    break;
                case 5:
                    System.out.println("Save competitions to file? (Y/N)?");
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