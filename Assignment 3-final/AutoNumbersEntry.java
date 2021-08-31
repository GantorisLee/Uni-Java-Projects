/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.Serializable;
import java.util.*;

public class AutoNumbersEntry extends NumbersEntry implements Serializable{
    private final int NUMBER_COUNT = 7;
    private final int MAX_NUMBER = 35;

    public AutoNumbersEntry(Scanner keyboard, boolean testingMode) {
        super(keyboard, testingMode);
    }

    /*
    create bill entry numbers randomly based on code provided previously by teacher.
    @param seed uses entry Id as required
     */
    public int[] createNumbers (int seed) {
        ArrayList<Integer> validList = new ArrayList<Integer>();
        int[] tempNumbers = new int[NUMBER_COUNT];
        for (int i = 1; i <= MAX_NUMBER; i++) {
            validList.add(i);
        }
        Collections.shuffle(validList, new Random(seed));
        for (int i = 0; i < NUMBER_COUNT; i++) {
            tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        return tempNumbers;
    }


}
