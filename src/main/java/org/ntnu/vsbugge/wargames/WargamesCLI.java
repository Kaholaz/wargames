package org.ntnu.vsbugge.wargames;

import org.ntnu.vsbugge.wargames.files.ArmyFileUtil;
import org.ntnu.vsbugge.wargames.units.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class used to run the CLI of the application
 */
public class WargamesCLI {
    private Battle battle;

    /**
     * Loads two predetermined armies into memory.
     * This function must be run before running the start method.
     */
    public void loadTestData() throws IOException {
        ArmyFileUtil armyFileUtil = new ArmyFileUtil();
        armyFileUtil.setDefaultPath(new File("src/main/resources/testFiles"));

        Army humanArmy = armyFileUtil.loadFromPath(new File("HumanArmy.army"), true);
        Army orcishHorde = armyFileUtil.loadFromPath(new File("OrcishHorde.army"), true);

        battle = new Battle(humanArmy, orcishHorde);
    }


    /**
     * Starts the CLI. Data needs to be loaded into {@code battle} before this function is called
     * @throws IllegalStateException If start is called without data loaded, or if a simulation is started
     * where one or both of the armies contains 0 units.
     */
    public void start() throws IllegalStateException{
        if (battle == null) {
            throw new IllegalStateException("Data was not loaded before starting the battle");
        }

        boolean playing = true;
        while (playing){
            System.out.println("Welcome to the CLI for the battle simulator Wargames!");
            System.out.println("Press enter to start...");

            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();

            Army victor = battle.simulate();

            System.out.printf("The army '%s' won!\n", victor.getName());
            System.out.println("Here is the surviving army: ");
            System.out.println(armyToSimpleString(victor));

            System.out.println("Would you like to start again? [Y/n]");
            String response = scanner.nextLine();

            // This will exit if the user does not respond with 'y' or 'yes', regardless if they wrote 'n' or 'no'.
            // This is not optimal, but good enough for this simple implementation, since it will be replaced by a
            // GUI later down the road.
            // TODO: Improve the user interface
            if (response.toLowerCase(Locale.ROOT).equals("y") || response.toLowerCase(Locale.ROOT).equals("yes")) {
                // Reload test data before a reset
                try {
                    loadTestData();
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.exit(20);
                }
            } else {
                playing = false;
            }
        }
    }

    /**
     * Creates a simple text based representation of a unit
     *
     * The string is based on this format:
     * {unit.getName}({unit.getClass().getSimpleName()}) at {unit.getHealth()} hp
     * @param unit A Unit instance
     * @return A simple text based representation of a unit
     */
    protected static String unitToSimpleString(Unit unit) {
        return unit.getName() + "(" + unit.getClass().getSimpleName() +
                ")" +  " at " +
                unit.getHealth() + " hp";
    }

    /**
     * Creates a simple text based representation of an army based on its current template
     * @param army An army instance
     * @return A simple text based representation.
     */
    protected static String armyToSimpleString(Army army) {
        Map<Unit, Integer> template = army.getArmyTemplate();
        Unit[] units = template.keySet().toArray(Unit[]::new);
        Arrays.sort(units);

        // Converts each unit to a simple string and concatenates the amount of that unit in the army.
        // Each entry is then joined together to a single string separated by newlines.
        return Arrays.stream(units).map(unit -> template.get(unit) + "x " + unitToSimpleString(unit))
                .collect(Collectors.joining("\n"));
    }
}
