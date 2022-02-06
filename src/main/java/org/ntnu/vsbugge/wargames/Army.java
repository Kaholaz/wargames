package org.ntnu.vsbugge.wargames;

import org.ntnu.vsbugge.wargames.units.Unit;

import java.util.*;

/**
 * A class that represents a single army
 */
public class Army {
    private final String name;
    private final List<Unit> units;

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
     * Adds a unit to the army the unit is copied before inserted into the object.
     * @param unit The unit to be added
     */
    public void add(Unit unit) {
        this.units.add(Unit.copyOf(unit));
    }

    /**
     * Adds all given units to the army
     * @param units The units to add
     */
    public void addAll(List<Unit> units) {
        for (Unit unit : units) {
            add(unit);
        }
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

    /**
     * @return A template that could be used to create an equivalent army.
     *
     * The template is a HashMap where the key is a unit in the army,
     * and the value is the amount of that specific unit in the army.
     *
     * Please note that since Unit.copyOf does not retain stats about
     * the number of times a unit has or has been attacked, and that
     * this function is called whenever a unit is added to an army;
     * the generated template can not necessarily be replicated fully
     * by using the parseArmyTemplate method.
     */
    public Map<Unit, Integer> getArmyTemplate() {
        Map<Unit, Integer> template = new HashMap<>();

        for (Unit unit : units) {
            int count = template.getOrDefault(unit, 0);
            count += 1;
            template.put(unit, count);
        }

        return template;
    }

    /**
     * Takes a template and returns an army based on that template
     * @param name The name of the army
     * @param template The template the army should be based on
     * @return An instance of Army based on the provided template
     *
     * The template should be a HashMap where the key is a unit in the army,
     * and the value is the amount of that specific unit in the army.
     */
    public static Army parseArmyTemplate(String name, Map<Unit, Integer> template) {
        Army army = new Army(name);

        for (Map.Entry<Unit, Integer> entry: template.entrySet()) {
            Unit unit = entry.getKey();
            int count = entry.getValue();

            for (int i = 0; i < count; i++) {
                army.add(unit);
            }
        }

        return army;
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

        // units are sorted because order does not matter.
        army.units.sort(Unit::compareTo);
        this.units.sort(Unit::compareTo);
        return name.equals(army.name) && units.equals(army.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }
}
