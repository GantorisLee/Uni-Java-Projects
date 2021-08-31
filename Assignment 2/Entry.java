import java.util.Arrays;
import java.util.Scanner;

public class Entry {
    private final Scanner keyboard;
    private int[] numbers;
    boolean testingMode;

    public Entry(Scanner keyboard, boolean testingMode) {
        this.keyboard = keyboard;
        this.testingMode = testingMode;
    }


    //Entering self-filled entries
    protected int[] manualEntries() {
        String input;

        //start to check for correct input
        boolean correctInput = true;
        while (correctInput) {
            //Initial scan for input(string)
            System.out.println("Please enter 7 different numbers (from the range 1 to 35) " +
                    "separated by whitespace. ");
            input = keyboard.nextLine();


            //splitting the input into string array
            String[] arrayStr = input.split(" ", 10);

            //changing the string array into a int array
            numbers = new int[arrayStr.length];
            for (int digits = 0; digits < arrayStr.length; digits++) {
                numbers[digits] = Integer.parseInt(arrayStr[digits]);
            }
            Arrays.sort(numbers);

            if (arrayStr.length > 7) {
                System.out.println("Invalid input! More than 7 numbers are provided. " +
                        "Please try again!");
            } else if (arrayStr.length < 7) {
                System.out.println("Invalid input! Fewer than 7 numbers are provided. " +
                        "Please try again!");
            } else {
                correctInput = false;
            }
        }
        return numbers;
    }
}