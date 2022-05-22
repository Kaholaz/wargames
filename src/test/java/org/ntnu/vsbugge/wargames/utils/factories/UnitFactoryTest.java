package org.ntnu.vsbugge.wargames.utils.factories;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.models.units.CommanderUnit;
import org.ntnu.vsbugge.wargames.models.units.RangedUnit;
import org.ntnu.vsbugge.wargames.models.units.Unit;

import java.util.ArrayList;

public class UnitFactoryTest extends TestCase {

    public void testGetUnit() {
        Unit unit = UnitFactory.getUnit("RangedUnit", "Test", 3);
        assertEquals(new RangedUnit("Test", 3), unit);
    }

    public void testGetUnits() {
        int count = 10;
        ArrayList<Unit> expected = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            expected.add(new CommanderUnit("Test", 2));
        }

        assertEquals(expected, UnitFactory.getUnits("CommanderUnit", "Test", 2, 10));
    }
}