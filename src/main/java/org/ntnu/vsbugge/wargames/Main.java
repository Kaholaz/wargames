package org.ntnu.vsbugge.wargames;

import java.io.IOException;

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
        try {
            cli.loadTestData();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(20);
        }
        cli.start();
    }
}
