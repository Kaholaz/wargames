package org.ntnu.vsbugge.wargames.enums;

import org.ntnu.vsbugge.wargames.units.*;

/**
 * This is an enum to keep track of all valid subclasses of Unit.
 * This enum is created to make expanding the number of supported classes easier.
 * To add a subclass to the list of supported subclasses, the only necessary change would be to add it to this enum.
 */
public enum UnitEnum {
    CavalryUnit(org.ntnu.vsbugge.wargames.units.CavalryUnit.class),
    CommanderUnit(org.ntnu.vsbugge.wargames.units.CommanderUnit.class),
    InfantryUnit(org.ntnu.vsbugge.wargames.units.InfantryUnit.class),
    RangedUnit(org.ntnu.vsbugge.wargames.units.RangedUnit.class);

    // The corresponding Unit subclass.
    final Class<? extends Unit> unitClass;

    /**
     * Specifies the corresponding Unit subclass for the enum constant.
     * @param unitClass The corresponding Unit subclass.
     */
    UnitEnum(Class<? extends Unit> unitClass) {
        this.unitClass = unitClass;
    }

    /**
     * @return The class that corresponds to the enum constant.
     *         This would fore example be {@code CommanderUnit.class} for {UnitEnum.CommanderUnit}
     */
    public Class<? extends Unit> getUnitClass() {
        return unitClass;
    }

    /**
     * This method functions similarly to UnitEnum::valueOf except it sends a custom
     * exception message if the enum is not found.
     * @param unit The name of the unit type (class name)
     * @return The enum constant that matches the supplied unit type.
     */
    public static UnitEnum fromString(String unit) {
        try {
            return UnitEnum.valueOf(unit);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Unit type '%s' not found.", unit));
        }
    }
}