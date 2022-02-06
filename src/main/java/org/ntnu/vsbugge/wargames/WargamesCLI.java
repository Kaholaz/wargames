package org.ntnu.vsbugge.wargames;

import org.ntnu.vsbugge.wargames.units.*;

import java.util.*;
import java.util.stream.Collectors;

public class WargamesCLI {
    private Battle battle;

    public void loadTestData() {
        Map<Unit,Integer> armyOneTemplate = new HashMap<>();
        armyOneTemplate.put(new InfantryUnit("Footman", 100), 500);
        armyOneTemplate.put(new CavalryUnit("Knight", 100), 100);
        armyOneTemplate.put(new RangedUnit("Archer", 100), 200);
        armyOneTemplate.put(new CommanderUnit("Mountain King", 180), 1);
        Army armyOne = Army.parseArmyTemplate("Human Army", armyOneTemplate);

        Map<Unit, Integer> armyTwoTemplate = new HashMap<>();
        armyTwoTemplate.put(new InfantryUnit("Grunt", 100), 500);
        armyTwoTemplate.put(new CavalryUnit("Raider", 100), 100);
        armyTwoTemplate.put(new RangedUnit("Spearman", 100), 200);
        armyTwoTemplate.put(new CommanderUnit("Gul ́dan", 180), 1);
        Army armyTwo = Army.parseArmyTemplate("Orcish Horde", armyTwoTemplate);

        battle = new Battle(armyOne, armyTwo);
    }

    public void start() {
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

        if (response.toLowerCase(Locale.ROOT).equals("y") || response.toLowerCase(Locale.ROOT).equals("yes")) {
            loadTestData();
            start();
        }
    }

    private String unitToSimpleString(Unit unit) {
        return unit.getName() + "(" + unit.getClass().getSimpleName() +
                ")" +  " at " +
                unit.getHealth() + " hp";
    }

    private String armyToSimpleString(Army army) {
        Map<Unit, Integer> template = army.getArmyTemplate();
        Unit[] units = template.keySet().stream().toArray(Unit[]::new);
        Arrays.sort(units);

        // Converts each unit to a simple string and concats the count.
        // Each entry is then joined together to a single string separated by newlines.
        return Arrays.stream(units).map(unit -> template.get(unit) + "x " + unitToSimpleString(unit))
                .collect(Collectors.joining("\n"));
    }
}
