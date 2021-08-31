import java.util.Scanner;

public class ConsoleDrawing {

    public static void main(String[] args) {
        int Width;
        int Height;
        String Character;
        Scanner scan = new Scanner(System.in);
        // option list
        Width = Integer.parseInt(args[0]);
        Height = Integer.parseInt(args[1]);
        Character = args[2];

        //Welcome message
        System.out.println("---WELCOME TO MY CONSOLE DRAWING APP---");

        System.out.println(
                "Current drawing canvas settings:\n"
                        + "- Width: " + Width + "\n"
                        + "- Height: " + Height + "\n"
                        + "- Background character: " + Character + "\n"
        );

        //Menu interface
        //Loop to open new class methods
        String option = "";
        while (!option.equals("4")) {
            System.out.println(
                    """
                            Please select an option. Type 4 to exit.
                            1. Draw triangles
                            2. Draw squares
                            3. Update drawing canvas settings
                            4. Exit"""
            );
            option = scan.next();
            if ("1".equals(option)) {
                Triangle tri = new Triangle(Width, Height, scan, Character);
                tri.option1();
            } else if ("2".equals(option)) {
                Square sq = new Square(Width, Height, scan, Character);
                sq.option2();
            } else if ("3".equals(option)) {
                DrawingCanvas Canvas = new DrawingCanvas(Width, Height, scan, Character);
                Canvas.option3();
            } else if ("4".equals(option)) {
                option = "4";
                System.out.println("Goodbye!");
            } else {
                System.out.println("Unsupported option. Please try again!");
            }
        }


    }
}
