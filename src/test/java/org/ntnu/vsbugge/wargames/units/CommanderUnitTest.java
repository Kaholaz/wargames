package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;

public class CommanderUnitTest extends TestCase {

    public void testAttack() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        test1.attack(test2);
        assertEquals(20, test1.getHealth());
        assertEquals(15, test2.getHealth());

        test1.attack(test2);
        assertEquals(20, test1.getHealth());
        assertEquals(15, test2.getHealth());

        test2.attack(test1);
        assertEquals(0, test1.getHealth());
        assertEquals(15, test2.getHealth());

    }

    public void testGetNameLongConstructor() {
        CommanderUnit test = new CommanderUnit("Test", 20, 10, 5);
        assertEquals("Test", test.getName());
    }

    public void testGetNameShortConstructor() {
        CommanderUnit test = new CommanderUnit("Test", 20);
        assertEquals("Test", test.getName());
    }

    public void testGetHealthLongConstructor() {
        CommanderUnit test = new CommanderUnit("Test", 20, 10, 5);
        assertEquals(20, test.getHealth());
    }

    public void testGetHealthShortConstructor() {
        CommanderUnit test = new CommanderUnit("Test", 20);
        assertEquals(20, test.getHealth());
    }

    public void testGetAttackLongConstructor() {
        CommanderUnit test = new CommanderUnit("Test", 20, 10, 5);
        assertEquals(10, test.getAttack());
    }

    public void testGetAttackShortConstructor() {
        CommanderUnit test = new CommanderUnit("Test", 20);
        assertEquals(CommanderUnit.DEFAULT_ATTACK, test.getAttack());
    }

    public void testGetArmorLongConstructor() {
        CommanderUnit test = new CommanderUnit("Test", 20, 10, 5);
        assertEquals(5, test.getArmor());
    }

    public void testGetArmorShortConstructor() {
        CommanderUnit test = new CommanderUnit("Test", 20);
        assertEquals(CommanderUnit.DEFAULT_ARMOR, test.getArmor());
    }

    public void testToString() {
        CommanderUnit test = new CommanderUnit("Test", 20, 10, 5);

        assertEquals("CommanderUnit{name='Test', health=20, attack=10, armor=5}", test.toString());
    }

    public void testCopyIsNotSame() {
        CommanderUnit test = new CommanderUnit("Test", 15, 10, 5);
        CommanderUnit copy = test.copy();

        assertNotSame(test, copy);
    }

    public void testCopyIsEqual() {
        CommanderUnit test = new CommanderUnit("Test", 15, 10, 5);
        CommanderUnit copy = test.copy();

        assertEquals(test, copy);
    }

    public void testCopyDifferentUnitsAreNotEqual() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15, 10 ,5);
        CommanderUnit test1Copy = test1.copy();

        CommanderUnit test2 = new CommanderUnit("Test2", 20, 25 ,30);
        CommanderUnit test2Copy = test2.copy();

        assertFalse(test1Copy.equals(test2Copy));
    }

    public void testCopyRetainsStats() {
        CommanderUnit test = new CommanderUnit("Test", 100, 10 ,5);
        CommanderUnit copy = test.copy();

        test.attack(copy);
        copy = test.copy();

        assertEquals(CommanderUnit.ATTACK_BONUS, copy.getAttackBonus());
    }

    public void testEqualsCommanderUnitIsNotEqualToCavalryUnit() {
        CommanderUnit test1 = new CommanderUnit("Test", 100, 10 ,5);
        CavalryUnit test2 = new CavalryUnit("Test", 100, 10,5); // same stats as test1

        assertFalse(test1.equals(test2));
    }

    public void testHashCodesDifferentForCommanderUnitAndCavalryUnit() {
        CommanderUnit test1 = new CommanderUnit("Test", 100, 10 ,5);
        CavalryUnit test2 = new CavalryUnit("Test", 100, 10,5); // same stats as test1

        assertFalse(test1.hashCode() == test2.hashCode());
    }
}
