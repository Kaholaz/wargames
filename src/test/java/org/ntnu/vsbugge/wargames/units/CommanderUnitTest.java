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

    public void testTestGetName() {
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
        assertEquals(25, test2.getAttack());
    }

    public void testGetArmor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        assertEquals(5, test1.getArmor());
        assertEquals(15, test2.getArmor());
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

        assertEquals(1, test1.getResistBonus());

        test1.takeDamage(2);
        assertEquals(1, test1.getResistBonus());
    }

    public void testGetAttackBonus() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15);
        CommanderUnit test2 = new CommanderUnit("Test2", 10);

        assertEquals(6, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(2, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(2, test1.getAttackBonus());
    }

    public void testCopyOf() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15, 10 ,5);
        CommanderUnit test2 = (CommanderUnit) Unit.copyOf(test1);

        assertFalse(test1 == test2);
        assertTrue(test1.equals(test2));
    }
}