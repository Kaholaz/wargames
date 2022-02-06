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
                infantryUnit6
        ));

        list.sort(Unit::compareTo);
        assertSame(list.get(0), infantryUnit5); // First based on Name
        assertSame(list.get(1), infantryUnit4); // then attack
        assertSame(list.get(2), infantryUnit6); // then armor
        assertSame(list.get(3), infantryUnit3); // lastly health
        assertSame(list.get(4), infantryUnit2);
        assertSame(list.get(5), infantryUnit1);
        assertSame(list.get(6), ranged1);
    }
}