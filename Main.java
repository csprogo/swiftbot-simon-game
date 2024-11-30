//imports

import swiftbot.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
// main class
public class Main {
    static SwiftBotAPI swiftBot;
    static Random random = new Random();
    static List<Button> sequence = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    // creates a max number of levels
    static final int MAX_LEVELS = 5; 

    public static void main(String[] args) throws InterruptedException {
        // initializes the swiftbot api, taken and modified from zears program
        try {
            swiftBot = new SwiftBotAPI();
        } catch (Exception e) {
            System.out.println("\nI2C disabled!");
            System.out.println("Run the following command:");
            System.out.println("sudo raspi-config nonint do_i2c 0\n");
            System.exit(5);
        }

        System.out.println("\nWelcome to the Simon Game!");
        System.out.println("Follow the light sequence and press the buttons in the same order.");
        System.out.println("Press ENTER to start the game.");
        scanner.nextLine();

        // game starts here
        playSimonGame();
    }

    public static void playSimonGame() throws InterruptedException {
        int level = 1;

        while (level <= MAX_LEVELS) {
            System.out.println("\nLevel " + level);

            
            addToSequence();

           
            displaySequence();

            // checks if the user entered the correct input 
            if (!getUserInput()) {
                System.out.println("\nGame Over! You reached level " + level);
                System.out.println("Exiting the program...");
                System.exit(0); // closes the program when the user gets wronng
            }

            System.out.println("SUCCESS! Moving to the next level.");
            level++;
        }

        // once the player wins the whole game
        System.out.println("\nCongratulations! You've completed all 5 levels of the Simon Game!");
        System.out.println("Exiting the program...");
        System.exit(0); 
    }

    public static void addToSequence() {
        Button[] buttons = {Button.A, Button.B, Button.X, Button.Y};
        // adds a randomly generated number to the sequence
        sequence.add(buttons[random.nextInt(buttons.length)]); 
    }

    public static void displaySequence() throws InterruptedException {
        System.out.println("Watch the sequence!");
        for (Button button : sequence) {
            swiftBot.setButtonLight(button, true); //button lights up
            Thread.sleep(1000); // 1 second wait
            swiftBot.setButtonLight(button, false); // light turns off
            Thread.sleep(500); //program waits half a second before the next light
        }
    }

    public static boolean getUserInput() throws InterruptedException {
        System.out.println("Now it's your turn! Press the buttons in the same order.");
        List<Button> userSequence = new ArrayList<>();

        // array of potential buttons in the swifbot
        Button[] buttons = {Button.A, Button.B, Button.X, Button.Y};

        
        for (Button button : buttons) {
            swiftBot.enableButton(button, () -> {
                userSequence.add(button);
                System.out.println("You pressed: " + button);
            });
        }

        // waits to get user input
        while (userSequence.size() < sequence.size()) {
            Thread.sleep(100); // runs a check every 100ms
        }

       
        for (Button button : buttons) {
            swiftBot.disableButton(button);
        }

        
        for (int i = 0; i < sequence.size(); i++) {
            if (sequence.get(i) != userSequence.get(i)) {
                System.out.println("Oops! You pressed the wrong button.");
                return false;
            }
        }
        return true;
    }

    public static void resetGame() {
        sequence.clear(); // Clear the sequence for a new game
    }
}
