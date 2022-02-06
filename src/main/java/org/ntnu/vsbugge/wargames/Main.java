package org.ntnu.vsbugge.wargames;

public class Main {
    public static void main(String[] args) {
        WargamesCLI cli = new WargamesCLI();
        cli.loadTestData();
        cli.start();
    }
}
