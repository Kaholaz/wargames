package org.ntnu.vsbugge.wargames.utils.funcinterfaces;

/**
 * A functional interface used to supply a setter of an integer field.
 *
 * @author vsbugge
 */
public interface IntSetter {
    /**
     * Method used to invoke the setter.
     *
     * @param value
     *            The value to supply the setter.
     */
    void setInt(int value);
}
