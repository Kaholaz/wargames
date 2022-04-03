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
     *
     * @throws IllegalArgumentException Throws an exception if it is attempted to add a dead unit to the army.
     */
    public void add(Unit unit) throws IllegalArgumentException {
        if (unit.getHealth() == 0) {
            throw new IllegalArgumentException("Cannot add a dead unit (A unit with 0 health) to a unit.");
        }
        this.units.add(unit.copy());
    }

    /**
     * Adds a given number of a unit into the army. The unit is copied before each add
     * @param unit The unit to be added
     * @param count The number of that unit
     *
     * @throws IllegalArgumentException Throws an exception if it is attempted to add a dead unit to the army.
     */
    public void add(Unit unit, int count) {
        for (int i = 0; i < count; i++){
            add(unit);
        }
    }

    /**
     * Adds all given non-dead units (health != 0) to the army
     * @param units The units to add
     */
    public void addAll(List<Unit> units) {
        units.stream().filter(unit -> unit.getHealth() > 0).forEach(this::add);
    }

    /**
     * Removes a unit from the army
     * @param unit The unit to remove
     */
    public void remove(Unit unit) {
        units.remove(unit);
    }

    /**
     * Removes all dead units (units with zero health) from an army.
     */
    public void removeAllDeadUnits() {
        for (int i = 0; i < units.size();) {
            if (units.get(i).getHealth() == 0) {
                units.remove(i);
            }
            else {
                ++i;
            }
        }
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
     * that are of the given class {@code tClass}. If the army does not contain any units of the supplied type,
     * this method will return an empty list.
     *
     * <br><br>
     * This method is called using this syntax:<br>
     * {@code army.getUnitOfType(RangedUnit.class)}<br>
     * This specific piece of code will return a list of type {@code List&lt;RangedUnit&gt;}
     * @param tClass The class to filter for.
     * @param <T> The type of the supplied class has to be a subclass of {@code Unit}.
     * @return A complete list that contains all units in the army of the specified class.
     */
    public <T extends Unit> List<T> getUnitsOfType(Class<T> tClass) {
        return units.stream().filter(unit -> unit.getClass() == tClass).map(tClass::cast).toList();
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

        units.stream().map(Unit::copy).forEach(unit -> {
            // Unit specific stats are reset to properly group units
            unit.resetStats();

            // This increments the count of the unit in the template by one
            // (or sets the count to 1 if no equal unit already is in the map)
            template.merge(unit, 1, Integer::sum);
        });

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
     * <br><br>
     * Dead units are removed during creation.
     *
     * @param name The name of the army
     * @param template The template the army should be based on
     * @return An instance of Army based on the provided template
     */
    public static Army parseArmyTemplate(String name, Map<Unit, Integer> template) {
        Army army = new Army(name);
        template.entrySet().stream()
                .filter(entry -> entry.getKey().getHealth() > 0)
                // Adds an amount of units to the army equal to the count
                .forEach(entry -> army.add(entry.getKey(), entry.getValue()));

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
                ", armyTemplate=" + getArmyTemplate() +
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
