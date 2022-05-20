package org.ntnu.vsbugge.wargames.utils.funcinterfaces;

import org.ntnu.vsbugge.wargames.utils.enums.UnitEnum;

/**
 * A functional interface used to supply a setter of a UnitEnum field.
 *
 * @author vsbugge
 */
public interface UnitSetter {
    /**
     * Method used to invoke the setter.
     * @param unit The value to supply the setter.
     */
    void setUnit(UnitEnum unit);
}
