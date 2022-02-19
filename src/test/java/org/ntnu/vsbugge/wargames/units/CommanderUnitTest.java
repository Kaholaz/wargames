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
        assertEquals(-5, test1.getHealth());
        assertEquals(15, test2.getHealth());

    }

    public void testGetNameLongConstructor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        assertEquals("Test1", test1.getName());
    }

    public void testGetNameShortConstructor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20);
        assertEquals("Test1", test1.getName());
    }

    public void testGetHealthLongConstructor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        assertEquals(20, test1.getHealth());
    }

    public void testGetHealthShortConstructor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20);
        assertEquals(20, test1.getHealth());
    }

    public void testGetAttackLongConstructor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        assertEquals(10, test1.getAttack());
    }

    public void testGetAttackShortConstructor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20);
        assertEquals(CommanderUnit.DEFAULT_ATTACK, test1.getAttack());
    }

    public void testGetArmorLongConstructor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        assertEquals(5, test1.getArmor());
    }

    public void testGetArmorShortConstructor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20);
        assertEquals(CommanderUnit.DEFAULT_ARMOR, test1.getArmor());
    }

    public void testToString() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);

        assertEquals("CommanderUnit{name='Test1', health=20, attack=10, armor=5}", test1.toString());
    }

    public void testCopyOfIsNotSame() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15, 10, 5);
        CommanderUnit copy = (CommanderUnit) Unit.copyOf(test1);

        assertNotSame(test1, copy);
    }

    public void testCopyOfIsEqual() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15, 10, 5);
        CommanderUnit copy = (CommanderUnit) Unit.copyOf(test1);

        assertEquals(test1, copy);
    }

    public void testCopyOfDifferentUnitsAreNotEqual() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15, 10 ,5);
        CommanderUnit test1Copy = (CommanderUnit) Unit.copyOf(test1);

        CommanderUnit test2 = new CommanderUnit("Test2", 20, 25 ,30);
        CommanderUnit test2Copy = (CommanderUnit) Unit.copyOf(test2);

        assertFalse(test1Copy.equals(test2Copy));
    }

    public void testCopyOfRetainsStats() {
        CommanderUnit test = new CommanderUnit("Test", 100, 10 ,5);
        CommanderUnit copy = (CommanderUnit) Unit.copyOf(test);

        test.attack(copy);
        copy = (CommanderUnit) Unit.copyOf(test);

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
