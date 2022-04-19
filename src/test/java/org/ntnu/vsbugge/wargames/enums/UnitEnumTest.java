package org.ntnu.vsbugge.wargames.enums;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.units.*;

public class UnitEnumTest extends TestCase {

    public void testGetUnitClass() {
        assertEquals(CavalryUnit.class, UnitEnum.CavalryUnit.getUnitClass());
        assertEquals(CommanderUnit.class, UnitEnum.CommanderUnit.getUnitClass());
        assertEquals(InfantryUnit.class, UnitEnum.InfantryUnit.getUnitClass());
        assertEquals(RangedUnit.class, UnitEnum.RangedUnit.getUnitClass());
    }

    public void testFromString() {
        assertEquals(UnitEnum.CavalryUnit, UnitEnum.fromString("CavalryUnit"));
        assertEquals(UnitEnum.CommanderUnit, UnitEnum.fromString("CommanderUnit"));
        assertEquals(UnitEnum.InfantryUnit, UnitEnum.fromString("InfantryUnit"));
        assertEquals(UnitEnum.RangedUnit, UnitEnum.fromString("RangedUnit"));
    }

    public void testFromStringThrowsException() {
        try {
            UnitEnum.fromString("NotAUnit");
            fail("fromString should throw an exception.");
        }
        catch (IllegalArgumentException e) {
            // success!
        }
        catch (Exception e) {
            fail("fromString should throw IllegalArgumentException");
        }
    }
}