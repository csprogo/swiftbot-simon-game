import swiftbot.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static SwiftBotAPI swiftBot;
    static Random random = new Random();
    static List<Button> sequence = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final int MAX_LEVELS = 5; // Maximum number of levels

    public static void main(String[] args) throws InterruptedException {
        // Initialize SwiftBot API
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

        // Start the game
        playSimonGame();
    }

    public static void playSimonGame() throws InterruptedException {
        int level = 1;

        while (level <= MAX_LEVELS) {
            System.out.println("\nLevel " + level);

            // Add a random button to the sequence for the current level
            addToSequence();

            // Display the sequence for the user to memorize
            displaySequence();

            // Check if the user correctly replicates the sequence
            if (!getUserInput()) {
                System.out.println("\nGame Over! You reached level " + level);
                System.out.println("Exiting the program...");
                System.exit(0); // Exit the program after losing
            }

            System.out.println("SUCCESS! Moving to the next level.");
            level++;
        }

        // Player has successfully completed all levels
        System.out.println("\nCongratulations! You've completed all 5 levels of the Simon Game!");
        System.out.println("Exiting the program...");
        System.exit(0); // Exit the program after winning
    }

    public static void addToSequence() {
        Button[] buttons = {Button.A, Button.B, Button.X, Button.Y};
        sequence.add(buttons[random.nextInt(buttons.length)]); // Add a random button to the sequence
    }

    public static void displaySequence() throws InterruptedException {
        System.out.println("Watch the sequence!");
        for (Button button : sequence) {
            swiftBot.setButtonLight(button, true); // Light up the button
            Thread.sleep(1000); // Wait for 1 second
            swiftBot.setButtonLight(button, false); // Turn off the light
            Thread.sleep(500); // Wait for half a second before the next light
        }
    }

    public static boolean getUserInput() throws InterruptedException {
        System.out.println("Now it's your turn! Press the buttons in the same order.");
        List<Button> userSequence = new ArrayList<>();

        // Define the list of available buttons manually
        Button[] buttons = {Button.A, Button.B, Button.X, Button.Y};

        // Enable all buttons for user input
        for (Button button : buttons) {
            swiftBot.enableButton(button, () -> {
                userSequence.add(button);
                System.out.println("You pressed: " + button);
            });
        }

        // Wait for user input until the sequence is complete
        while (userSequence.size() < sequence.size()) {
            Thread.sleep(100); // Check every 100ms
        }

        // Disable all buttons
        for (Button button : buttons) {
            swiftBot.disableButton(button);
        }

        // Validate user input
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
