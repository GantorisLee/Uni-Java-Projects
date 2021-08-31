import java.util.Scanner;

public class Triangle {

    //Variable used
    private String printing_character;
    private int length;
    private int rotation_number = 1;
    private int col_index;
    private final int Width;
    private final int Height;
    private final Scanner scan;
    private final String Character;

    //declaring instance variables from the calling object
    public Triangle(int width, int height, Scanner scan, String Character) {
        this.Width = width;
        this.Height = height;
        this.scan = scan;
        this.Character = Character;

    }
    //Running through option 2 selected from ConsoleDrawing
    public void option1() {

        //Scanning for the Side length input, printing character, alignment)
        boolean exceed_width=true;
        while (exceed_width) {
            System.out.println("Side length:");
            length = scan.nextInt();
            if (length > Width || length > Height) {
                System.out.println("Error! The side length is too long (Current canvas size is " + Width + "x" + Height + "). Please try again.");
            } else {
                System.out.println("Printing character:");
                printing_character = scan.next();


                //Start of code to print out the triangle

                //calculating the alignment of the triangle (left, middle, right)
                calculate_col();
                //Drawing the triangle
                draw_triangle_top_left(length);
                //and the remainder of the canvas
                remainder_width();

                //input asking if user wants to rotate the triangle of the canvas. Prints it the triangle + canvas, and loops if continue to rotate
                rotation_prints();

                //request to draw another triangle or not
                boolean wrong_yes_no = true;
                while (wrong_yes_no) {
                    System.out.println("Draw another triangle (Y/N)?");
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





    //Use to calculate alignment of the Triangle from left, middle, right to the the appropriate column index for the triangle.
    public void calculate_col() {
        boolean is_wrong=true;
        while (is_wrong) {
            System.out.println("Alignment (left, middle, right)");
            String alignment = scan.next();
            if ("left".equals(alignment)) {
                col_index = 1;
                is_wrong = false;
            } else if ("middle".equals(alignment)) {
                col_index = (Width - length) / 2 + 1;
                is_wrong = false;
            } else if ("right".equals(alignment)) {
                col_index = (Width - length) + 1;
                is_wrong = false;
            } else {
                System.out.println("Your input is wrong");
            }
        }
    }


    //Drawing of triangle on top left
    private void draw_triangle_top_left(int length) {

        int x = length;
        for (int i = 0; i < length; i++) {
            int z = col_index;
            while (z > 1) {
                System.out.print(Character);
                z--;
            }
            int u = 0;
            while (u < x) {
                System.out.print(printing_character);
                u++;
            }
            int y = length - x;
            while (y > 0) {
                System.out.print(Character);
                y--;
            }
            int b = Width - col_index + 1 - this.length;
            while (b > 0) {
                System.out.print(Character);
                b--;
            }
            x--;
            System.out.println();
        }
    }


    //Drawing of triangle on top right
    private void draw_triangle_top_right(int length) {

        int x = length;
        for (int i = 0; i < length; i++) {
            int z = col_index;
            while (z > 1) {
                System.out.print(Character);
                z--;
            }
            int y = length - x;
            while (y > 0) {
                System.out.print(Character);
                y--;
            }
            int u = 0;
            while (u < x) {
                System.out.print(printing_character);
                u++;
            }
            int b = Width - col_index + 1 - this.length;
            while (b > 0) {
                System.out.print(Character);
                b--;
            }
            x--;
            System.out.println();
        }
    }

    //Drawing of triangle on bottom left
    private void draw_triangle_bottom_left(int length) {
        int x = length;
        for (int i = 0; i < length; i++) {
            int z = col_index;
            while (z > 1) {
                System.out.print(Character);
                z--;
            }
            int y = length - x;
            while (y >= 0) {
                System.out.print(printing_character);
                y--;
            }
            int u = 1;
            while (u < x) {
                System.out.print(Character);
                u++;
            }
            int b = Width - col_index + 1 - this.length;
            while (b > 0) {
                System.out.print(Character);
                b--;
            }
            x--;
            System.out.println();
        }
    }

    //Drawing of triangle on bottom right
    private void draw_triangle_bottom_right(int length) {
        int x = length;
        for (int i = 0; i < length; i++) {
            int z = col_index;
            while (z > 1) {
                System.out.print(Character);
                z--;
            }
            int u = 1;
            while (u < x) {
                System.out.print(Character);
                u++;
            }
            int y = length - x;
            while (y >= 0) {
                System.out.print(printing_character);
                y--;
            }
            int b = Width - col_index + 1 - this.length;
            while (b > 0) {
                System.out.print(Character);
                b--;
            }
            x--;
            System.out.println();
        }
    }

    //Converting the input of Left/Right into the 4 types of rotations, and print the correct rotation. Repeats until exit.
    public void rotation_prints() {
        boolean isRotate = true;
        while (isRotate) {
            System.out.println("Type R/L to rotate clockwise/anti-clockwise. Use other keys to continue.");
            String rotation_option = scan.next();

            if (rotation_option.equalsIgnoreCase("L")) {
                rotation_number = rotation_number - 1;
                if (rotation_number > 4) {
                    rotation_number = 1;
                } else if (rotation_number < 1) {
                    rotation_number = 4;
                }
                triangle_rotation();
                remainder_width();

            } else if (rotation_option.equalsIgnoreCase("R")) {
                rotation_number = rotation_number + 1;
                if (rotation_number > 4) {
                    rotation_number = 1;
                } else if (rotation_number < 1) {
                    rotation_number = 4;
                }
                triangle_rotation();
                remainder_width();
            }
            else { isRotate = false;
            }
        }

    }


    //Considering the input of the rotation type of Triangle and draws it
    private void triangle_rotation() {
        if (rotation_number == 1) {
            draw_triangle_top_left(length);
        } else if (rotation_number == 2) {
            draw_triangle_top_right(length);
        } else if (rotation_number == 3) {
            draw_triangle_bottom_right(length);
        } else if (rotation_number == 4) {
            draw_triangle_bottom_left(length);
        } else {
            System.out.println("dude error");
        }
    }


    //Drawing the remainder of the canvas at the bottom
    public void remainder_width() {
        remainder_width(Height, length, Width, Character);
    }

    public static void remainder_width(int height, int length, int width, String character) {
        int remainder = height - length;
        for (int i = 0; i < remainder; i++) {
            for (int x = 0; x < width; x++) {
                System.out.print(character);
            }
            System.out.println();
        }
    }


}