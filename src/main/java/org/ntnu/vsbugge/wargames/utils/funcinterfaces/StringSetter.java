package org.ntnu.vsbugge.wargames.utils.funcinterfaces;

/**
 * A functional interface used to supply a setter of an string field.
 *
 * @author vsbugge
 */
public interface StringSetter {
    /**
     * Method used to invoke the setter.
     * @param string The value to supply the setter.
     */
    void setString(String string);
}
