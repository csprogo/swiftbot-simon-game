// imports
import swiftbot.*; // imports swiftbot library from the JAR file
import java.util.Scanner; // imports java ability to take user input


// current references used 1. Java Does my swiftbot work


public class Main {
    static SwiftBotAPI swiftBot;
    public static void main(String[] args) throws InterruptedException {
        try {
            swiftBot = new SwiftBotAPI();
        } catch (Exception e) {
            /*
             * Outputs a warning if I2C is disabled. This only needs to be turned on once,
             * so you won't need to worry about this problem again!
             */
            System.out.println("\nI2C disabled!");
            System.out.println("Run the following command:");
            System.out.println("sudo raspi-config nonint do_i2c 0\n");
            System.exit(5);
        }
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

    }
}
