package org.ntnu.vsbugge.wargames;

/**
 * Entry point for the application
 */
public class Main {

    /**
     * Entry point for this java application
     * Loads two armies into memory and simulates a battle between them.
     * @param args This function takes no args
     */
    public static void main(String[] args) {
        WargamesCLI cli = new WargamesCLI();
        cli.loadTestData();
        cli.start();
    }
}
