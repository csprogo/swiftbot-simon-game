// imports
import swiftbot.*; // imports swiftbot library from the JAR file
import java.util.Scanner; // imports java ability to take user input


// current references used 1. Java Does my swiftbot work


public class Main {
    public static void main(String[] args)  {
        SwiftBotAPI robot = new SwiftBotAPI();

        // creates menu for user interaction
        System.out.println("\n*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println("");
        System.out.println("\t\t\tWelcome to the Simon Game!");
        System.out.println("");
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************\n");

        Scanner reader = new Scanner(System.in);
        // takes user input

        System.out.println("Enter a number to continue:\n");
        System.out.println(
                "0 = Play Game \t|\t 1 = Exit\n" );
        System.out.print("Enter a number: ");
        int input;

        int[][] colours = {
                { 255, 0, 0 }, // Red
                { 0, 255, 0 }, // Green
                { 0, 0, 255 }, // Blue
                { 255, 255, 255 } // White
        };
        input=reader.nextInt();
	    if(input==0) {
        // This for loop iterates through all colours in the colours array.
        	for (int[] rgb : colours) {
            	robot.fillUnderlights(rgb);
            	Thread.sleep(300);
        	}
      	    }
            else{
		   break;
	    }
    }
}
