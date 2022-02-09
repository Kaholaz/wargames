package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UnitTest extends TestCase {

    public void testCompareTo() {
        RangedUnit ranged1 = new RangedUnit("Test1", 20,10,10);
        InfantryUnit infantryUnit1 = new InfantryUnit("Test2", 20, 10, 10);
        InfantryUnit infantryUnit2 = new InfantryUnit("Test1", 20, 11, 10);
        InfantryUnit infantryUnit3 = new InfantryUnit("Test1", 20, 10, 12);
        InfantryUnit infantryUnit4 = new InfantryUnit("Test1", 20, 10, 10);
        InfantryUnit infantryUnit5 = new InfantryUnit("Test1", 15, 10, 10);
        InfantryUnit infantryUnit6 = new InfantryUnit("Test1", 100, 10, 10);

        List<Unit> list = new LinkedList<>(Arrays.asList(
                ranged1,
                infantryUnit1,
                infantryUnit2,
                infantryUnit3,
                infantryUnit4,
                infantryUnit5,
                infantryUnit5,
                infantryUnit6
        ));

        list.sort(Unit::compareTo);
        assertSame(infantryUnit5, list.get(0)); // First based on Name
        assertSame(infantryUnit5, list.get(1)); // then attack
        assertSame(infantryUnit4, list.get(2)); // then armor
        assertSame(infantryUnit6, list.get(3)); // lastly health
        assertSame(infantryUnit3, list.get(4));
        assertSame(infantryUnit2, list.get(5));
        assertSame(infantryUnit1, list.get(6));
        assertSame(ranged1, list.get(7));
    }

    public void testCopyOf() {
        class TestUnit extends Unit {
            public TestUnit(){
                super("Hello", 1,1,1);
            }
            public int getAttackBonus() {return 0;}
            public int getResistBonus() {return 0;}
        }

        TestUnit testUnit = new TestUnit();

        try {
            Unit.copyOf(testUnit);
            fail("Unit.copyOf should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Cloning not supported for this unit", e.getMessage());
        } catch (Exception e) {
            fail("Unit.copyOf should throw IllegalArgumentException");
        }
    }
}