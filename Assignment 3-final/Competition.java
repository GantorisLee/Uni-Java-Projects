/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.Serializable;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class Competition implements Serializable{

    //parameters used for competitions
    transient final Scanner keyboard;
    String name;
    int idCompetition;
    final boolean testingMode;

    //arguments for entering entries
    String memberId;
    String memberName;
    int eligibleEntries;
    public int entryId = 0;
    int existingEntries;
    ArrayList<ArrayList<Integer>> entryIntegers = new ArrayList<>();
    ArrayList<String> entryString = new ArrayList<>(2);
    ArrayList<String> memberIdList = new ArrayList<>(2);
    ArrayList<String> memberNameList = new ArrayList<>(2);

    //arguments for final report
    int totalPrize;
    int numWinningEntries;

    /*
    @param keyboard scanner
    @param testingMode to check if it is normal or testing mode
    import from SimpleCompetitions
     */
    public Competition(Scanner keyboard, boolean testingMode) {
        this.testingMode = testingMode;
        this.keyboard = keyboard;
    }

    /*
    @param name competition Name
    @param id id number of the competition
    Bringing over the name and competition id information
    */
    public void addCompetitionEntry(String name, int id) {
        this.name = name;
        this.idCompetition = id;
    }


    /*
    Getting information to add a entry to the competition.
    @param memberIdToAdd member
    @param billCostToAdd bill cost
    @param memberNameToAdd member name
     */
    public abstract void addEntries(String memberIdToAdd, String billCostToAdd,
                                    String memberNameToAdd);

    /*
    Draw winners when option 3 is chosen in main body of code.
    Abstract class which has different draw winners for both LuckyNumber and Random
     */
    public abstract void drawWinners();


    /*
    Generates a report for the entry for summary report purposes.
     */
    public String report() {
        return "Competition ID: " + idCompetition + ", " + "name: " + name + ", active: no" + "\n"
                + "Number of entries: " + (entryId) + "\n"
                + "Number of winning entries: " + numWinningEntries + "\n"
                + "Total awarded prizes: " + totalPrize;
    }

    /*
    convert a numbers string with " " delimiter to an array
    @param input array used
    @return stringToReturn string produced from array
     */
    protected String arrayToString(int [] input){
        String[] entryInputString = new String[input.length];

        for (int entryLength = 0; entryLength < input.length; entryLength++) {
            entryInputString[entryLength] = Integer.toString(input[entryLength]);
        }
        String stringToReturn = "";

        for (int entryLength=0; entryLength<entryInputString.length; entryLength++) {
            if (input[entryLength] < 10){
                stringToReturn = stringToReturn + "  " + entryInputString[entryLength];
            } else {
                stringToReturn = stringToReturn + " " + entryInputString[entryLength];
            }
        }
        return stringToReturn;
    }

    /*
    adds the information of the entry numbers to a 2d array list
     */
    protected void addArray(int[] aNumbers) {
        for (Integer number : aNumbers) {
            // add new array lists as required in accordance to entryId
            while (entryIntegers.size() <= entryId) {
                entryIntegers.add(new ArrayList<>());
            }
            // add the integers to the array list.
            entryIntegers.get(entryId).add(number);
        }


        //add the member id for the entry
        memberIdList.add(memberId);
        //add the member name for the entry
        memberNameList.add(memberName);
        //increment for adding current entryId
        entryId++;
    }

    /*
    Print the member entries added
     */
    public void printEntries() {
        System.out.println("The following entries have been added:");
        existingEntries = entryId - eligibleEntries;
        for (int entriesPrint = 0; entriesPrint < eligibleEntries; entriesPrint++) {
            String currentEntry;
            //balancing whitespace
            if((existingEntries + entriesPrint +1)<10){
                currentEntry = (existingEntries + entriesPrint +1) + " " ;
            } else {
                currentEntry = Integer.toString(existingEntries + entriesPrint +1);
            }

            System.out.println("Entry ID: " + currentEntry + "     " +
                    "Numbers:"
                    + entryString.get(existingEntries + entriesPrint));


        }

    }
}

