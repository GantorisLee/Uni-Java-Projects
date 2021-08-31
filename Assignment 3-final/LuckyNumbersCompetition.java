/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */



import java.io.Serializable;
import java.util.Scanner;

public class LuckyNumbersCompetition extends Competition implements Serializable {
    public LuckyNumbersCompetition(Scanner keyboard, boolean testingMode) {
        super(keyboard, testingMode);
    }

    /*
    Getting information to add a entry to the competition.
    @param memberIdToAdd member
    @param CostToAdd bill cost
    @param memberNameToAdd member name
     */
    @Override
    public void addEntries(String memberIdToAdd, String billCostToAdd,
                           String memberNameToAdd) {
        memberId = memberIdToAdd;
        memberName = memberNameToAdd;
        int filledEntries = 0;
        //check if bill amount is more than $50
        double totalAmount = Double.parseDouble(billCostToAdd);
        if(totalAmount >= 50) {

            //Check for number of eligible entries
            eligibleEntries = (int) Math.round(totalAmount) / 50;
            boolean wrongEntry = true;
            System.out.println("This bill ($"+billCostToAdd+") is eligible for "
                    + eligibleEntries+" entries. " +
                    "How many manual entries did the customer fill up?: ");
            while (wrongEntry) {
                String manualInput = keyboard.nextLine();
                if (manualInput.matches("[0-9]+") && manualInput.length() == 1) {
                    filledEntries = Integer.parseInt(manualInput);
                    if (filledEntries > eligibleEntries) {
                        System.out.println("The number must be in the " +
                                "range from 0 to 6. Please try again.");
                    } else {
                        wrongEntry = false;
                    }
                } else {
                    System.out.println("Please input a number.");
                }

            }

            int autoEntries = eligibleEntries - filledEntries;

            //input manual entries
            AutoNumbersEntry en = new AutoNumbersEntry(keyboard, testingMode);
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
//                System.out.println("auto entry id: " + entryId);

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
    }

    /*
    Draw winners when option 3 is chosen in main body of code.
    Draw winners for LuckyNumberCompetition.
     */
    @Override
    public void drawWinners() {

        //add random competition numbers as string
        AutoNumbersEntry en = new AutoNumbersEntry(keyboard, testingMode);

        //generate the competition numbers automatically
        int[] competitionNumbers = en.autoCompetition(idCompetition);

        //convert the numbers to a string and tag with [Auto]
        String competitionNumbersString = arrayToString(competitionNumbers) + " [Auto]";


        //print out the competition details
        System.out.println("Competition ID: "+ idCompetition
                + ", Competition Name: " + name+ ", Type: LuckyNumbersCompetition");
        System.out.println("Lucky Numbers:"+ competitionNumbersString);
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
                    if (!winnerMemberId.equals(newMemberId)) {
                        winningEntries(memberIdList.get(entryPosition), entryPosition + 1,
                                entryString.get(entryPosition), highestWon);
                        numWinningEntries++;
                        totalPrize = totalPrize + prizeList(highestWon);
                        highestWon = numbersWon;
                        entryPosition = entry;
                        winnerMemberId = memberIdList.get(entry);
                    }
//                    } else {
//                        System.out.println("---help here 2");
//                        winningEntries(memberIdList.get(entryPosition), entryPosition + 1,
//                                entryString.get(entryPosition), highestWon);
//                        numWinningEntries++;
//                        totalPrize = totalPrize + prizeList(highestWon);
//                    }
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

    /*
    checks which division the entry won
    @param numbersWon number of numbers that matches the winning numbers
     */
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

    /*
    Add whitespaces to prizes
     */
    private String whiteSpacesPrize (int prizeAmount){
        String prize = Integer.toString(prizeAmount);
        int length = Integer.toString(prizeAmount).length();

        int minlength;
        for(minlength = length; minlength < 5; minlength++) {
            prize = prize + " ";
        }

        return prize;
    }

    /*
    Prints the winning entries
    @param winnerMemberId winner member id
    @param entryId entry id
    @param entryNumbers the numbers for the entry used
    @param prizeWin how much prize he won
     */
    private void winningEntries (String winnerMemberId, int entryId, String entryNumbers,
                                 int prizeWin) {
        System.out.println("Member ID: " + winnerMemberId +", Member Name: "
                + memberNameList.get(entryId-1) + ", " + "Prize: " +
                whiteSpacesPrize(prizeList(prizeWin)) +
                "\n" +
                "--> Entry ID: " + entryId + ", Numbers:" + entryNumbers);
    }


}