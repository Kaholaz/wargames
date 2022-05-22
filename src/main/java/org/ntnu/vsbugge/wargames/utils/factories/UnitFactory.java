package org.ntnu.vsbugge.wargames.utils.factories;

import org.ntnu.vsbugge.wargames.models.units.Unit;
import org.ntnu.vsbugge.wargames.utils.enums.UnitEnum;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * A factory class for creating different Unit subclasses.
 *
 * @author vsbugge
 */
public class UnitFactory {

    /**
     * Creates a new Unit instance according to the given unit type. To add support for new units, the Unit will need to
     * be added to the UnitEnum enum.
     *
     * @param unitType
     *            The name of the type of the Unit. This name must be a name of a subclass of the Unit class.
     * @param name
     *            The name of the unit.
     * @param health
     *            The health of the unit.
     *
     * @return A unit created by the given parameters. The unit is created using the constructor of the subclass with
     *         the name that matches the supplied unitType.
     *
     * @throws java.lang.IllegalArgumentException
     *             Throws an exception if the name of the class could not be found in UnitEnum.
     * @throws java.lang.IllegalStateException
     *             Throws an exception if the unit could not be constructed.
     */
    public static Unit getUnit(String unitType, String name, int health)
            throws IllegalArgumentException, IllegalStateException {
        // Reflection and the enum is used to make the application more scalable and expandable.
        UnitEnum unit = UnitEnum.fromString(unitType);
        try {
            return unit.getUnitClass().getConstructor(String.class, int.class).newInstance(name, health);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            throw new IllegalStateException("Something wrong happened at unit construction!");
        }
    }

    /**
     * Creates a new list of copies of a Unit according to the given unit type and parameters. To add support for new
     * units, the Unit will need to be added to the UnitEnum enum.
     *
     * @param unitType
     *            The name of the type of the Unit. This name must be a name of a subclass of the Unit class.
     * @param name
     *            The name of the unit.
     * @param health
     *            The health of the unit.
     * @param count
     *            How many copies of the unit in the list.
     *
     * @return A list of identical units created by the given parameters. The unit is created using the constructor of
     *         the subclass of Unit with a name that matches the supplied unitType.
     *
     * @throws java.lang.IllegalArgumentException
     *             Throws an exception if the name of the class could not be found in UnitEnum.
     * @throws java.lang.IllegalStateException
     *             Throws an exception if the unit could not be constructed.
     */
    public static List<Unit> getUnits(String unitType, String name, int health, int count)
            throws IllegalArgumentException, IllegalStateException {
        Unit unit = getUnit(unitType, name, health);
        ArrayList<Unit> units = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            units.add(unit.copy());
        }
        return units;
    }
}
