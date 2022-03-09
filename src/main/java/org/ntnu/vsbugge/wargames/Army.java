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
        this.units.add(unit.copy());
    }

    /**
     * Adds a given number of a unit into the army. The unit is copied before each add
     * @param unit The unit to be added
     * @param count The number of that unit
     */
    public void add(Unit unit, int count) {
        for (int i = 0; i < count; i++){
            add(unit);
        }
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
     * @return A copy of a list of all the units in the army
     */
    public List<Unit> getAllUnits() {
        return new ArrayList<>(units);
    }


    /**
     * Iterates over all units in the army and returns a list that only contains the units
     * that are of the given class {@code tClass}.
     *
     * <br><br>
     * This method is called using this syntax:<br>
     * {@code army.getUnitOfType(RangedUnit.class)}<br>
     * This code will return a list of type {@code List&lt;RangedUnit&gt;}
     * @param tClass The class to filter for.
     * @param <T> The type of the supplied class has to be a subclass of {@code Unit}.
     * @return A complete list that contains all units in the army of the specified class.
     */
    public <T extends Unit> List<T> getUnitsOfType(Class<T> tClass) {
        // All classes are filtered, so every element should be of type T. The unchecked cast is therefore fine here.
        return (List<T>) units.stream().filter(unit -> unit.getClass() == tClass).toList();
    }

    /**
     * The template is a HashMap where the key is a unit in the army,
     * and the value is the amount of that specific unit in the army.
     *
     * <br><br>
     * Please note that because templates groups units that only differ
     * in unit specific stats, unit specific stats are reset when the
     * template is constructed. The army template is thereby not a
     * one to one representation of an army. In other words:
     * If the returned army template is passed into the parseArmyTemplate,
     * the returned army is then not necessarily a perfect recreation of the
     * original army.
     *
     * <br><br>
     * This implementation of an 'army template' is just implemented to increase QOL when
     * retrieving information about or when creating armies by enabling
     * these actions to be completed using an easy-to-use format. These templates
     * are inadequate when an <i>exact</i> representation of an army is needed.
     * If this is the case, the getAllUnits method should be used instead.
     *
     * @return A template that could be used to create an equivalent army.
     */
    public Map<Unit, Integer> getArmyTemplate() {
        Map<Unit, Integer> template = new HashMap<>();

        for (Unit unit : units) {
            // Stats are reset to properly group
            // units that only differ in unit specific stats.
            unit = unit.copy();
            unit.resetStats(); // <- here
            int count = template.getOrDefault(unit, 0);

            count += 1;
            template.put(unit, count); // Would not correctly group units if stats had not been reset
        }

        return template;
    }

    /**
     * Takes a template and returns an army based on that template
     *
     * <br><br>
     * The template should be a HashMap where the key is a unit in the army,
     * and the value is the amount of that specific unit in the army.
     *
     * <br><br>
     * This implementation of an 'army template' is just implemented to increase QOL when
     * retrieving information about or when creating armies by enabling
     * these actions to be completed using an easy-to-use format. These templates
     * are inadequate when an <i>exact</i> representation of an army is needed.
     * If this is the case, the getAllUnits method should be used instead.
     *
     * @param name The name of the army
     * @param template The template the army should be based on
     * @return An instance of Army based on the provided template
     */
    public static Army parseArmyTemplate(String name, Map<Unit, Integer> template) {
        Army army = new Army(name);

        for (Map.Entry<Unit, Integer> entry: template.entrySet()) {
            Unit unit = entry.getKey();
            int count = entry.getValue();

            army.add(unit, count);
        }

        return army;
    }

    /**
     * This method should be used for debugging purposes only. {@code WargamesCLI.armyToSimpleString(Army army)}
     * should be used create a human-readable representation of a unit, ready to be printed to console.
     *
     * @return A string representation of an instance (used for debugging)
     */
    @Override
    public String toString() {
        return "Army{" +
                "name='" + name + '\'' +
                '}';
    }

    /**
     * Checks whether two objects are equivalent
     * @param o The other object to compare with
     * @return True if the objects are equivalent, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;

        // units are sorted because order does not matter when comparing.
        List<Unit> armyUnits = army.getAllUnits().stream().sorted(Unit::compareTo).toList();
        List<Unit> thisUnits = this.getAllUnits().stream().sorted(Unit::compareTo).toList();
        return name.equals(army.name) && thisUnits.equals(armyUnits);
    }

    /**
     * @return A HashCode for the Army. This method hashes the name and the collection of units.
     */
    @Override
    public int hashCode() {
        Unit[] hashUnits = units.toArray(Unit[]::new);
        Arrays.sort(hashUnits);
        return Objects.hash(name, Arrays.hashCode(hashUnits));
    }
}
