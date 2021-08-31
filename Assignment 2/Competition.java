import java.util.Scanner;
import java.util.ArrayList;

public class Competition{
    private final Scanner keyboard;
    private String name; //competition name ?? suppose to be private but changed to public
    private int idCompetition; //competition identifier
    private final boolean testingMode;

    //arguments for entries!
    String memberId;
    String billId;
    Double totalAmount;
    private int eligibleEntries;
    public int entryId = 0;
    private int existingEntries;
    ArrayList<ArrayList<Integer>> entryIntegers = new ArrayList<>();
    ArrayList<String> entryString = new ArrayList<>(2);
    ArrayList<String> memberIdList = new ArrayList<>(2);

    //arguments for final report
    private int totalPrize;
    private int numWinningEntries;

    //For SimpleCompetitions
    public Competition(Scanner keyboard, boolean testingMode) {
        this.testingMode = testingMode;
        this.keyboard = keyboard;
    }

    //Bringing over the name and competition id information
    public void addCompetitionEntry(String name, int id) {
        this.name = name;
        this.idCompetition = id;
    }

    //adding entries on option 2
    public void addMemberEntries() {

        boolean continueAddEntry = true;
        while (continueAddEntry) {

            //input Member Id
            keyboard.nextLine();
            inputMemberId();

            //input Bill Id
            inputBillId();

            //input total amount
            inputTotalAmount();

            //check if bill amount is more than $50
            if(totalAmount >= 50) {

                //Check for number of eligible entries
                eligibleEntries = (int) Math.round(totalAmount) / 50;
                System.out.println("This bill is eligible for " + eligibleEntries + " entries. " +
                        "How many manual entries did the customer fill up?: ");
                int filledEntries = this.keyboard.nextInt();
                keyboard.nextLine();

                int autoEntries = eligibleEntries - filledEntries;

                //input manual entries
                AutoEntry en = new AutoEntry(keyboard, testingMode);
                for (int entries = 0; entries < filledEntries; entries++) {

                    //Check the string for exceptions/errors and convert into an array type
                    int[] entryInput = en.manualEntries();

                    //add the array into the 2d arraylist
                    addArray(entryInput);

                    //add the 6 digits into a string list to differentiate between manual vs [Auto]
                    entryString.add(arrayToString(entryInput));
                }


                //input for auto member entries
                for (int entriesDone = 0; entriesDone < autoEntries; entriesDone++) {
/////////////////////                    System.out.println("auto entry id: " + entryId);

                    //create the auto numbers
                    int[] autoInput = en.createNumbers(entryId);

                    //add the array into the 2d arraylist
                    addArray(autoInput);

                    //convert into a string and add [Auto]
                    String arrayString = arrayToString(autoInput) + " [Auto]";

                    //add the 6 digits into a string list to differentiate between manual vs [Auto]
                    entryString.add(arrayString);
                }

                //print entries
                printEntries();

            } else {
                System.out.println("This bill is not eligible for an entry. " +
                        "The total amount is smaller than $50.0");
            }

            //Request to continue or stop adding entries
            boolean wrong_yes_no = true;
            while (wrong_yes_no) {
                System.out.println("Add more entries (Y/N)?");
                String yes_no = keyboard.next();
                if (yes_no.equalsIgnoreCase("Y")) {
                    wrong_yes_no = false;
                    existingEntries = entryId;
                } else if (yes_no.equalsIgnoreCase("N")) {
                    wrong_yes_no = false;
                    continueAddEntry = false;
                } else {
                    System.out.println("Unsupported option. Please try again!");
                }
            }
        }
    }

    //add to the 2d array list for the entry numbers
    protected void addArray(int[] aNumbers) {
        for (Integer number : aNumbers) {
            // add new array lists as required in accordance to entryId
            while (entryIntegers.size() <= entryId) {
                entryIntegers.add(new ArrayList<>());
            }
            // add the integers to the array list.
            entryIntegers.get(entryId).add(number);
        }
////////////////////////        System.out.println("EntryId increment addArray: "+entryId);


        //add the member id for the entry
        memberIdList.add(memberId);
        //increment for adding current entryId
        entryId++;
    }

    //draw winners on option 3
    public void drawWinners() {
        //add random competition numbers as string
        AutoEntry en = new AutoEntry(keyboard, testingMode);

        //generate the competition numbers automatically
        int[] competitionNumbers = en.autoCompetition(idCompetition);

        //convert the numbers to a string and tag with [Auto]
        String competitionNumbersString = arrayToString(competitionNumbers) + " [Auto]";


        //print out the competition details
        System.out.println("Lucky entry for Competition ID: "+ idCompetition
                + ", Competition Name: " + name);
        System.out.println("Numbers: "+ competitionNumbersString);
        System.out.println("Winning entries:");

        //finding the winning entries and printing them
        //iterate over the each entry
        int highestWon = 0;
        int entryPosition = 0;
        String winnerMemberId = null;

        for (int entry =0; entry < entryIntegers.size() ; entry++ ){
            int numbersWon = 0;

            //iterate over each winning number
            for (int digitCompetition : competitionNumbers) {

                //iterate over each entry's digit
                for (int digit = 0; digit < entryIntegers.get(entry).size(); digit++) {
                    //check of any number is the same
                    if (digitCompetition == entryIntegers.get(entry).get(digit)) {
                        numbersWon++;
                    }
                }

            }

            //finding maximum winner prize for each memberId
            if (numbersWon >1){
                String newMemberId = memberIdList.get(entry);
                if(entry == 0){
                    highestWon = numbersWon;
                    winnerMemberId = memberIdList.get(entry);
                } else if (memberIdList.get(entry).equals(memberIdList.get(entry - 1))
                        && numbersWon > highestWon) {
                    highestWon = numbersWon;
                    entryPosition = entry;
                    winnerMemberId = memberIdList.get(entry);
                } else {
                    assert winnerMemberId != null;
                    if (!winnerMemberId.equals(newMemberId)){
                        winningEntries(memberIdList.get(entryPosition), entryPosition + 1,
                                entryString.get(entryPosition), highestWon);
                        numWinningEntries++;
                        totalPrize = totalPrize + prizeList(highestWon);
                        highestWon = numbersWon;
                        entryPosition = entry;
                        winnerMemberId = memberIdList.get(entry);
                    } else {
                        winningEntries(memberIdList.get(entryPosition), entryPosition + 1,
                                entryString.get(entryPosition), highestWon);
                        numWinningEntries++;
                        totalPrize = totalPrize + prizeList(highestWon);
                    }
                }
            }
        }
        if (highestWon != 0) {
            winningEntries(memberIdList.get(entryPosition), entryPosition + 1,
                    entryString.get(entryPosition), highestWon);
            numWinningEntries++;
            totalPrize = totalPrize + prizeList(highestWon);
        }
    }

    //convert a numbers string with " " delimiter to an array
    public String arrayToString(int [] input){
        String[] entryInputString = new String[input.length];

        for (int entryLength = 0; entryLength < input.length; entryLength++) {
            entryInputString[entryLength] = Integer.toString(input[entryLength]);
        }

        return String.join(" ", entryInputString);
    }

    //print the member entries
    public void printEntries() {
        System.out.println("The following entries have been added:");
        for (int entriesPrint = 0; entriesPrint < eligibleEntries; entriesPrint++) {
            System.out.println("Entry ID: " + (existingEntries + entriesPrint +1) + "      " +
                    "Numbers:  "
                    + entryString.get(existingEntries + entriesPrint));
        }
    }

    //Input of memberId and check for errors
    public void inputMemberId() {
        int idLength = 6;
        boolean memberIdInput = true;
        while (memberIdInput) {
            System.out.println("Member ID: ");
            memberId = keyboard.nextLine();
            String memberId2 = memberId;
            if (memberId == null || memberId.equals("")) {
                System.out.println("Invalid member id! It must be a 6-digit number. Please try again.");
                break;
            } else if (memberId.length() != idLength) {
                System.out.println("Invalid member id! It must be a 6-digit number. Please" +
                        " " +
                        "try again.");
                break;
            } else if (memberId2.matches("[0-9]+")){
                memberIdInput = false;
            } else {
                System.out.println("Invalid member id! It must be a 6-digit number. Please try " +
                        "again.");
            }

        }
    }


    //Input of billId and check for errors
    private void inputBillId() {
        int billLength = 6;
        boolean billIdInput = true;
        while (billIdInput) {
            System.out.println("Bill ID: ");
            billId = keyboard.nextLine();
            if (billId == null || billId.equals("")) {
                System.out.println("Invalid bill id! It must be a 6-digit number. Please try again.");
            } else if (billId.length() != billLength) {
                System.out.println("Invalid bill id! It must be a 6-digit number. Please try again.");
            } else {
                for (int i = 0; i < billId.length(); i++) {
                    char c = billId.charAt(i);
                    if (c < '0' || c > '9') {
                        System.out.println("Invalid bill id! It must be a 6-digit number. Please try again.");
                    } else billIdInput = false;
                }
            }
        }
    }

    //Input of totalAmount and check for errors
    private void inputTotalAmount() {
        boolean totalAmountInput = true;
        while (totalAmountInput) {
            System.out.println("Total amount: ");
            totalAmount = this.keyboard.nextDouble();
            keyboard.nextLine();
            if (totalAmount == null) {
                System.out.println("Invalid total amount! Please try again.");
            } else {
                totalAmountInput = false;
            }
        }
    }

    //check which division the entry won
    private int prizeList(int numbersWon) {
        //Prize points
        int division1 = 50000;
        int division2 = 5000;
        int division3 = 1000;
        int division4 = 500;
        int division5 = 100;
        int division6 = 50;

        //check which division the entry won
        int prize = 0;
        if(numbersWon == 2 ){
            prize = division6;
        } else if (numbersWon == 3){
            prize = division5;
        } else if (numbersWon == 4){
            prize = division4;
        } else if (numbersWon == 5){
            prize = division3;
        } else if (numbersWon == 6){
            prize = division2;
        } else if (numbersWon == 7){
            prize = division1;
        }
        return prize;


    }

    private void winningEntries (String winnerMemberId, int entryId, String entryNumbers,
                                 int prizeWin) {
        System.out.println("Member ID: " + winnerMemberId +", Entry ID: "
                + entryId + "     , " + "Prize: " + prizeList(prizeWin) + ", Numbers: "
                + entryNumbers);
    }
    public String report() {

        return "Competition ID: " + idCompetition + ", " + "name: " + name + ", active: no" + "\n"
                + "Number of entries: " + (entryId - 1) + "\n"
                + "Number of winning entries: " + numWinningEntries + "\n"
                + "Total awarded prizes: " + totalPrize;
    }


}