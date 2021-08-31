/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public class Entry implements java.io.Serializable {
    transient private final Scanner keyboard;
    private int[] numbers;
    boolean testingMode;

    /*
    imports the information
    @param keyboard scanner
    @param testingMode whether it is testing mode or normal mode
     */
    public Entry(Scanner keyboard, boolean testingMode) {
        this.keyboard = keyboard;
        this.testingMode = testingMode;
    }


    /*
    Used when entering manual entries
     */
    protected int[] manualEntries() {
        String input;

        //start to check for correct input
        boolean correctInput = true;
        while (correctInput) {
            //Initial scan for input(string)
            System.out.println("Please enter 7 different numbers (from the range 1 to 35) " +
                    "separated by whitespace.");
            input = keyboard.nextLine();

            if (!input.matches("[0-9 ]+")){
                System.out.println("Invalid input! Numbers are expected. " +
                        "Please try again!");
            } else{
                //splitting the input into string array
                String[] arrayStr = input.split(" ", 10);

                //changing the string array into a int array
                numbers = new int[arrayStr.length];
                for (int digits = 0; digits < arrayStr.length; digits++) {
                    numbers[digits] = Integer.parseInt(arrayStr[digits]);
                }
                Arrays.sort(numbers);

                boolean repeatNumbers = false;
                boolean overThirtyFive = false;

                for (int digits = 0; digits < arrayStr.length - 1; digits++) {
                    for (int compare = digits + 1; compare < arrayStr.length; compare++){
                        if (numbers[digits] == numbers[compare]) {
                            repeatNumbers = true;
                            break;
                        }
                    }
                }

                for (int digits = 0; digits < arrayStr.length; digits++) {
                    if (numbers[digits] >35) {
                        overThirtyFive = true;
                        break;
                    }
                }

                if (arrayStr.length > 7) {
                    System.out.println("Invalid input! More than 7 numbers are provided. " +
                            "Please try again!");
                } else if (arrayStr.length < 7) {
                    System.out.println("Invalid input! Fewer than 7 numbers are provided. " +
                            "Please try again!");
                } else if (repeatNumbers){
                    System.out.println("Invalid input! All numbers must be different!");
                } else if (overThirtyFive){
                    System.out.println("Invalid input! All numbers must be in the range from 1 to 35!");
                } else {
                    correctInput = false;
                }
            }

        }
        return numbers;
    }

}
