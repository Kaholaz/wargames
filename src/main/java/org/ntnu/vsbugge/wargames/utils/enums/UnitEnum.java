package org.ntnu.vsbugge.wargames.utils.enums;

import org.ntnu.vsbugge.wargames.units.Unit;

/**
 * This is an enum to keep track of all valid subclasses of Unit. This enum is created to make expanding the number of
 * supported classes easier. To add a subclass to the list of supported subclasses, the only necessary change would be
 * to add it to this enum.
 *
 * @author vsbugge
 */
public enum UnitEnum {
    CavalryUnit(org.ntnu.vsbugge.wargames.units.CavalryUnit.class, "Cavalry unit"),
    CommanderUnit(org.ntnu.vsbugge.wargames.units.CommanderUnit.class, "Commander unit"),
    InfantryUnit(org.ntnu.vsbugge.wargames.units.InfantryUnit.class, "Infantry unit"),
    RangedUnit(org.ntnu.vsbugge.wargames.units.RangedUnit.class, "Ranged unit");

    // The corresponding Unit subclass.
    final Class<? extends Unit> unitClass;
    final String properName;

    /**
     * Specifies the corresponding Unit subclass for the enum constant.
     *
     * @param unitClass
     *            The corresponding Unit subclass.
     */
    UnitEnum(Class<? extends Unit> unitClass, String properName) {
        this.unitClass = unitClass;
        this.properName = properName;
    }

    /**
     * Gets the class that corresponds to the enum constant.
     *
     * @return The class that corresponds to the enum constant. This would fore example be {@code CommanderUnit.class}
     *         for {UnitEnum.CommanderUnit}
     */
    public Class<? extends Unit> getUnitClass() {
        return unitClass;
    }

    /**
     * Retrieves the proper name string. This string is the 'proper' name of the unit (i.e. Cavalry unit instead of
     * CavalryUnit)
     *
     * @return The proper name of the unit.
     */
    public String getProperName() {
        return properName;
    }

    public static UnitEnum fromProperName(String properName) {
        for (UnitEnum unit : UnitEnum.values()) {
            if (unit.properName.equals(properName)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("No unit with this name was found!");
    }

    /**
     * This method functions similarly to UnitEnum::valueOf except it sends a custom exception message if the enum is
     * not found.
     *
     * @param unit
     *            The name of the unit type (class name)
     *
     * @return The enum constant that matches the supplied unit type.
     */
    public static UnitEnum fromString(String unit) {
        try {
            return UnitEnum.valueOf(unit);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Unit type '%s' not found.", unit));
        }
    }
}
