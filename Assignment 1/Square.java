import java.util.Scanner;

public class Square {

    //variable used
    private String printing_character;
    private int side_length;
    private int col_index;
    private final int Width;
    private final int Height;
    private final Scanner scan;
    private final String Character;
    private String alignment;

    //declaring instance variables from the calling object
    public Square(int width, int height, Scanner scan, String Character) {
        this.Width = width;
        this.Height = height;
        this.scan = scan;
        this.Character = Character;

    }

    //Running through option 2 selected from ConsoleDrawing
    public void option2() {

        //Scanning for the Side length input, printing character, alignment)
        boolean exceed_width=true;
        while (exceed_width) {
            System.out.println("Side length:");
            side_length = scan.nextInt();
            if (side_length > Width || side_length > Height) {
                System.out.println("Error! The side length is too long (Current canvas size is " + Width + "x" + Height + "). Please try again.");
            } else {
                System.out.println("Printing character:");
                printing_character = scan.next();
                System.out.println("Alignment (left, middle, right)");
                alignment = scan.next();

                //calculate alignment of square
                calculate_col();

                //Prints the square
                square_print(side_length);
                //prints remainder of width below the square
                remainder_width();
                //Asks user if he/she wants to use the zoom feature
                zooming();

                //request to draw another square or not
                boolean wrong_yes_no = true;
                while (wrong_yes_no) {
                    System.out.println("Draw another square (Y/N)?");
                    String yes_no = scan.next();
                    if (yes_no.equalsIgnoreCase("Y")) {
                        wrong_yes_no = false;
                    } else if (yes_no.equalsIgnoreCase("N")) {
                        exceed_width = false;
                        wrong_yes_no = false;
                    } else {
                        System.out.println("Unsupported option. Please try again!");
                    }
                }


            }
        }
    }

    //Prints the square
    public void square_print(int length){
        for(int i = 0; i < length; i++){
            calculate_col();
            int z = col_index;
            while(z>1) {
                System.out.print(Character);
                z--;
            }
            for(int a = 0; a < length; a++){
                System.out.print(printing_character);
            }
            int b = Width - col_index + 1 - length;
            while (b>0) {
                System.out.print(Character);
                b--;
            }
            System.out.println();
        }
    }

    //Ask if user wants to zoom, in (input I) or out (input O) of the square
    public void zooming() {
        boolean isZoom = true;
        while (isZoom) {
            System.out.println("Type I/O to zoom in/out. Use other keys to continue");
            String zooming = scan.next();

            if (zooming.equalsIgnoreCase("I")) {
                side_length = side_length + 1;
                if (side_length > Math.min(Width, Height)) {
                    side_length = Math.min(Width, Height);
                }
                square_print(side_length);
                remainder_width();
            }
            else if (zooming.equalsIgnoreCase("O")) {
                side_length = side_length - 1;
                if (side_length < 1) {
                    side_length = 1;
                }
                square_print(side_length);
                remainder_width();

            }
            else { isZoom = false;
            }
        }
    }

    //calculate alignment of square
    public void calculate_col() {
        boolean is_wrong = true;
        while (is_wrong) {
            if (side_length >= Width && side_length >= Height) {
                col_index = 1;
                is_wrong = false;
            }
            else if ("left".equals(alignment)) {
                col_index = 1;
                is_wrong = false;
            }
            else if ("middle".equals(alignment)) {
                col_index = (Width - side_length) / 2 + 1;
                is_wrong = false;
            }
            else if ("right".equals(alignment)) {
                col_index = (Width - side_length) + 1;
                is_wrong = false;
            }
            else {
                col_index = 1;
                is_wrong = false;
            }
        }
    }

    //Drawing the remainder of the canvas at the bottom (code taken from Triangle.class)
    private void remainder_width() {
        Triangle.remainder_width(Height, side_length, Width, Character);
    }

}
