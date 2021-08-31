/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.Serializable;
import java.util.*;

public class NumbersEntry extends Entry implements Serializable{

    /*
    creates an instance of the numbersEntry
    @param keyboard scanner
    @param testingMode if it is testing mode or normal mode
     */
    public NumbersEntry(Scanner keyboard, boolean testingMode) {
        super(keyboard, testingMode);
    }

    public int[] createNumbers(int seed) {
        ArrayList<Integer> validList = new ArrayList<>();
        int[] tempNumbers = new int[7];
        for (int i = 1; i <= 35; i++) {
            validList.add(i);
        }
        Collections.shuffle(validList, new Random(seed));
        for (int i = 0; i < 7; i++) {
            tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);

        return tempNumbers;
    }


    /*
    auto create winning numbers for the competition
    @param seed the instance used for the competition.
     */
    public int[] autoCompetition(int seed) {
        int[] competitionNumbers;
        if (testingMode) {
            competitionNumbers = createNumbers(seed);
        } else {
            competitionNumbers = createNumbers(50);
        }
        return competitionNumbers;
    }
}