/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

public class RandomPickCompetition extends Competition implements Serializable {
    private final int FIRST_PRIZE = 50000;
    private final int SECOND_PRIZE = 5000;
    private final int THIRD_PRIZE = 1000;
    private final int[] prizes = {FIRST_PRIZE, SECOND_PRIZE, THIRD_PRIZE};



    private final int MAX_WINNING_ENTRIES = 3;

    public RandomPickCompetition(Scanner keyboard, boolean testingMode) {
        super(keyboard, testingMode);
    }

    /*
    Getting information to add a entry to the competition.
    @param memberIdToAdd member
    @param billCostToAdd bill cost
    @param memberNameToAdd member name
     */
    @Override
    public void addEntries(String memberIdToAdd, String billCostToAdd, String memberNameToAdd) {
        Scanner keyboard = new Scanner(System.in);
        memberId = memberIdToAdd;
        memberName = memberNameToAdd;
        //check if bill amount is more than $50
        double totalAmount = Double.parseDouble(billCostToAdd);
        if(totalAmount >= 50) {

            //Check for number of eligible entries
            eligibleEntries = (int) Math.round(totalAmount) / 50;
            System.out.println("This bill ($"+ totalAmount +") is eligible for "
                    + eligibleEntries + " entries."+"\n"+
                    "The following entries have been automatically generated:");
            for(int entryPrint = 1; entryPrint <= eligibleEntries; entryPrint++){
                //increment for adding current entryId
                entryId++;
                //add the member id for the entry
                memberIdList.add(memberId);
                //add the member name for the entry
                memberNameList.add(memberName);

                //whitespace fix
                String entryIdPrint;
                if (entryId < 10) {
                    entryIdPrint = entryId + "     ";
                } else {
                    entryIdPrint = entryId + "    ";
                }
                //prints entry
                System.out.println("Entry ID: " + entryIdPrint);
            }

        } else {
            System.out.println("This bill is not eligible for an entry. " +
                    "The total amount is smaller than $50.0");
        }
    }

    /*
    Draw winners when option 3 is chosen in main body of code.
    Draw winners for RandomPickCompetition.
     */
    @Override
    public void drawWinners() {
        String[] winnerMembersId = new String[MAX_WINNING_ENTRIES];
        String[] winnerMembersName = new String[MAX_WINNING_ENTRIES];
        int[] winnerEntryId = new int[MAX_WINNING_ENTRIES];
        String memberName;
        String memberId;
        int memberEntryId;

        //Random number generator
        Random randomGenerator = null;
        if (testingMode) {
            randomGenerator = new Random(idCompetition);
        } else {
            randomGenerator = new Random(50);
        }

        //print competition details
        System.out.println(("Competition ID: "+ idCompetition
                + ", Competition Name: " + name+ ", Type: RandomPickCompetition"));
        System.out.println("Winning entries:");

        //generating the winning entries
        int winningEntryCount = 0;
        while (winningEntryCount < MAX_WINNING_ENTRIES) {
            int winningEntryIndex = randomGenerator.nextInt(entryId);

            memberName = memberNameList.get(winningEntryIndex);
            memberId = memberIdList.get(winningEntryIndex);
            memberEntryId = winningEntryIndex + 1;

            winnerMembersId[winningEntryCount] = memberId;
            winnerMembersName[winningEntryCount] = memberName;
            winnerEntryId[winningEntryCount] = memberEntryId;
            winningEntryCount++;

        }

        //print winners
        for (int winner = MAX_WINNING_ENTRIES-1; winner >= 0; winner--){
            boolean onlyWinner = true;

            for (int oldWinners = winner - 1; oldWinners >= 0; oldWinners--){
                if (winnerMembersId[winner].equals(winnerMembersId[oldWinners])){
                    onlyWinner = false;
                }
            }
            if(onlyWinner){
                //add details for report
                numWinningEntries++;
                totalPrize = totalPrize + prizes[winner];

                String newPrize;
                if(prizes[winner] < 50000) {
                    newPrize = prizes[winner] + " ";
                } else {
                    newPrize = Integer.toString(prizes[winner]);
                }

                //prints the drawWinners
                System.out.println("Member ID: " + winnerMembersId[winner]
                        + ", Member Name: " + winnerMembersName[winner] + ", Entry ID: "
                        + winnerEntryId[winner] + ", Prize: " + newPrize);
            }
        }
    }
}
