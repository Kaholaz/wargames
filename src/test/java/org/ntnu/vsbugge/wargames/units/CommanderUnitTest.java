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

    public void testGetName() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        assertEquals("Test1", test1.getName());
        assertEquals("Test2", test2.getName());
    }

    public void testGetHealth() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        assertEquals(20, test1.getHealth());
        assertEquals(15, test2.getHealth());
    }

    public void testGetAttack() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        assertEquals(10, test1.getAttack());
        assertEquals(CommanderUnit.DEFAULT_ATTACK, test2.getAttack());
    }

    public void testGetArmor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        assertEquals(5, test1.getArmor());
        assertEquals(CommanderUnit.DEFAULT_ARMOR, test2.getArmor());
    }

    public void testTakeDamage() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15);

        test1.takeDamage(2);
        assertEquals(13, test1.getHealth());
    }

    public void testToString() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);

        assertEquals("CommanderUnit{name='Test1', health=20, attack=10, armor=5}", test1.toString());
    }

    public void testGetResistBonus() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15);

        assertEquals(CommanderUnit.RESIST_BONUS, test1.getResistBonus());

        test1.takeDamage(2);
        assertEquals(CommanderUnit.RESIST_BONUS, test1.getResistBonus());
    }

    public void testGetAttackBonus() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15);
        CommanderUnit test2 = new CommanderUnit("Test2", 10);

        assertEquals(CommanderUnit.FIRST_ATTACK_BONUS, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(CommanderUnit.ATTACK_BONUS, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(CommanderUnit.ATTACK_BONUS, test1.getAttackBonus());
    }

    public void testCopyOf() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15, 10 ,5);
        CommanderUnit test2 = (CommanderUnit) Unit.copyOf(test1);

        assertFalse(test1 == test2);
        assertTrue(test1.equals(test2));
    }

    public void testCopyOfRetainsStats() {
        CommanderUnit test1 = new CommanderUnit("Test1", 100, 10 ,5);
        CommanderUnit test2 = (CommanderUnit) Unit.copyOf(test1);

        test1.attack(test2);
        assertEquals(CommanderUnit.FIRST_ATTACK_BONUS, test2.getAttackBonus());

        test2 = (CommanderUnit) Unit.copyOf(test1);
        assertEquals(CommanderUnit.ATTACK_BONUS, test2.getAttackBonus());
    }

    public void testResetStats() {
        CommanderUnit test1 = new CommanderUnit("Test1", 100, 10 ,5);
        CommanderUnit test2 = (CommanderUnit) Unit.copyOf(test1);

        test1.attack(test2);
        assertEquals(CommanderUnit.ATTACK_BONUS, test1.getAttackBonus());

        test1.resetStats();
        assertEquals(CommanderUnit.FIRST_ATTACK_BONUS, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(CommanderUnit.ATTACK_BONUS, test1.getAttackBonus());
    }

    public void testEqualsCommanderUnitIsNotEqualToCavalryUnit() {
        CommanderUnit test1 = new CommanderUnit("Test", 100, 10 ,5);
        CavalryUnit test2 = (CavalryUnit) Unit.copyOf(test1);
        CavalryUnit test3 = new CavalryUnit("Test", 100, 10,5); // same stats as test1

        assertTrue(test2.equals(test1));
        assertFalse(test3.equals(test1));
    }

    public void testHashCodesDifferentForCommanderUnitAndCavalryUnit() {
        CommanderUnit test1 = new CommanderUnit("Test", 100, 10 ,5);
        CavalryUnit test2 = (CavalryUnit) Unit.copyOf(test1);
        CavalryUnit test3 = new CavalryUnit("Test", 100, 10,5); // same stats as test1

        assertTrue(test1.hashCode() == test2.hashCode());
        assertFalse(test1.hashCode() == test3.hashCode());
    }
}
