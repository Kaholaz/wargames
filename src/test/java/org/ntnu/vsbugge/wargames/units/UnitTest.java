package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;

import java.util.*;

public class UnitTest extends TestCase {

    public void testCompareTo() {
        RangedUnit ranged1 = new RangedUnit("Test1", 20,10,10);
        RangedUnit ranged2 = (RangedUnit) Unit.copyOf(ranged1); ranged2.takeDamage(0); // different unit specific stat
        InfantryUnit infantryUnit1 = new InfantryUnit("Test2", 20, 10, 10);
        InfantryUnit infantryUnit2 = new InfantryUnit("Test1", 20, 11, 10);
        InfantryUnit infantryUnit3 = new InfantryUnit("Test1", 20, 10, 12);
        InfantryUnit infantryUnit4 = new InfantryUnit("Test1", 20, 10, 10);
        InfantryUnit infantryUnit5 = new InfantryUnit("Test1", 15, 10, 10);
        InfantryUnit infantryUnit6 = new InfantryUnit("Test1", 100, 10, 10);

        List<Unit> list = new LinkedList<>(Arrays.asList(
                ranged2,
                ranged1,
                infantryUnit1,
                infantryUnit2,
                infantryUnit3,
                infantryUnit4,
                infantryUnit5,
                infantryUnit5,
                infantryUnit6
        ));

        // list is shuffled to make initial order of equal items (where compareTo returns 0) non-deterministic
        Collections.shuffle(list);

        list.sort(Unit::compareTo);
        assertSame(infantryUnit5, list.get(0)); // First based on Name
        assertSame(infantryUnit5, list.get(1)); // then attack
        assertSame(infantryUnit4, list.get(2)); // then armor
        assertSame(infantryUnit6, list.get(3)); // lastly health
        assertSame(infantryUnit3, list.get(4));
        assertSame(infantryUnit2, list.get(5));
        assertSame(infantryUnit1, list.get(6));
        assertSame(ranged1, list.get(7));
        assertSame(ranged2, list.get(8));
    }

    public void testCopyOfThrowsException() {
        Unit testUnit = new Unit("Test",1, 2, 3) {
            public void resetStats() {}
            public int getResistBonus(){ return 0; }
            public int getAttackBonus(){ return 0; }
        };

        try {
            Unit.copyOf(testUnit);
            fail("Unit.copyOf should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Cloning not supported for this unit", e.getMessage());
        } catch (Exception e) {
            fail("Unit.copyOf should throw IllegalArgumentException");
        }
    }

    public void testEqualsReturnsTrueWhenSame() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        InfantryUnit test = (InfantryUnit) Unit.copyOf(base);

        assertEquals(base, test);
    }

    public void testEqualsReturnsFalseWhenDifferentName() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        InfantryUnit test = new InfantryUnit("Jim", 1,2,3);

        assertFalse(base.equals(test));
    }

    public void testEqualsReturnsFalseWhenDifferentHealth() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        InfantryUnit test = new InfantryUnit("Test", 4,2,3);

        assertFalse(base.equals(test));
    }

    public void testEqualsReturnsFalseWhenDifferentAttack() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        InfantryUnit test = new InfantryUnit("Test", 1,4,3);

        assertFalse(base.equals(test));
    }

    public void testEqualsReturnsFalseWhenDifferentArmor() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        InfantryUnit test = new InfantryUnit("Test", 1,2,4);

        assertFalse(base.equals(test));
    }

    public void testEqualsReturnsFalseWhenDifferentSubClass() {
        Unit base = new InfantryUnit("Test", 1,2,3);
        Unit test = new RangedUnit("Test", 1,2,3);

        assertFalse(base.equals(test));
    }

    public void testHashCodeReturnsSameHashWhenSame() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        InfantryUnit test = (InfantryUnit) Unit.copyOf(base);

        assertEquals(base.hashCode(), test.hashCode());
    }

    public void testHashCodeReturnsDifferentHashWhenDifferentName() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        InfantryUnit test = new InfantryUnit("Jim", 1,2,3);

        assertFalse(base.hashCode() == test.hashCode());
    }

    public void testHashCodeReturnsDifferentHashWhenDifferentHealth() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        InfantryUnit test = new InfantryUnit("Test", 4,2,3);

        assertFalse(base.hashCode() == test.hashCode());
    }

    public void testHashCodeReturnsDifferentHashWhenDifferentAttack() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        InfantryUnit test = new InfantryUnit("Test", 1,4,3);

        assertFalse(base.hashCode() == test.hashCode());
    }

    public void testHashCodeReturnsDifferentHashWhenDifferentArmor() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        InfantryUnit test = new InfantryUnit("Test", 1,2,4);

        assertFalse(base.hashCode() == test.hashCode());
    }

    public void testHashCodeReturnsDifferentHashWhenDifferentSubClass() {
        InfantryUnit base = new InfantryUnit("Test", 1,2,3);
        RangedUnit test = new RangedUnit("Test", 1,2,3);

        assertFalse(base.hashCode() == test.hashCode());
    }
}