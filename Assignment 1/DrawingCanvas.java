import java.util.Scanner;

public class DrawingCanvas {

    //variable used
    int Width;
    int Height;
    public String Character;
    private final Scanner scan;

    //declaring instance variables from the calling object
    public DrawingCanvas(int width, int height, Scanner scan, String Character) {
        this.Width = width;
        this.Height = height;
        this.scan = scan;
        this.Character = Character;

    }

    //Running through option 3 selected from ConsoleDrawing
    public void option3() {
        System.out.print("Canvas width: ");
        Width = scan.nextInt();
        System.out.print("Canvas height: ");
        Height = scan.nextInt();
        System.out.print("Background character: ");
        Character = scan.next();
        System.out.println("Drawing canvas has been updated!" + "\n");

        System.out.println(
                "Current drawing canvas settings:\n"
                        + "- Width: " + Width + "\n"
                        + "- Height: " + Height + "\n"
                        + "- Background character: " + Character + "\n"
        );



    }


}
