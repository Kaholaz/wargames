package org.ntnu.vsbugge.wargames;

import org.ntnu.vsbugge.wargames.units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * A class that represents a single army
 */
public class Army {
    private final String name;
    private List<Unit> units;

    /**
     * Shot-hand constructor for an Army object
     * Sets the name of the army and initializes the Unit ArrayList
     * @param name The name of the army
     */
    public Army(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * Constructor of and Army object
     * @param name The name of the army
     * @param units The units in the army
     */
    public Army(String name, List<Unit> units) {
        this.name = name;
        this.units = new ArrayList<>();
        addAll(units);
    }

    /**
     * Adds a unit to the army
     * @param unit The unit to be added
     */
    public void add(Unit unit) {
        this.units.add(unit);
    }

    /**
     * Adds all given units to the army
     * @param units The units to add
     */
    public void addAll(List<Unit> units) {
        this.units.addAll(List.copyOf(units));
    }

    /**
     * Removes a unit from the army
     * @param unit The unit to remove
     */
    public void remove(Unit unit) {
        units.remove(unit);
    }

    /**
     * @return true if the army consists of units, false if not
     */
    public boolean hasUnits() {
        return units.size() != 0;
    }

    /**
     * @return a random unit from the army
     * @throws IllegalStateException Throws an exception if there are no units in the army
     */
    public Unit getRandomUnit() throws IllegalStateException {
        if (!hasUnits()) {
            throw new IllegalStateException("The army has no units");
        }

        Random random = new Random();
        int index = random.nextInt(units.size());

        return units.get(index);
    }

    /**
     * @return The name of the army
     */
    public String getName() {
        return name;
    }

    /**
     * @return A list of all the units in the army
     */
    public List<Unit> getAllUnits() {
        return units;
    }

    @Override
    public String toString() {
        return "Army{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;
        return name.equals(army.name) && units.equals(army.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }
}
