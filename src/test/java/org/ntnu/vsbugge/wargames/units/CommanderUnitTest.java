package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;

public class CommanderUnitTest extends TestCase {

    public void testAttack() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        test1.attack(test2);
        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 15);

        test1.attack(test2);
        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 15);

        test2.attack(test1);
        assertEquals(test1.getHealth(), -5);
        assertEquals(test2.getHealth(), 15);

    }

    public void testTestGetName() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        assertEquals(test1.getName(), "Test1");
        assertEquals(test2.getName(), "Test2");
    }

    public void testGetHealth() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 15);
    }

    public void testGetAttack() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        assertEquals(test1.getAttack(), 10);
        assertEquals(test2.getAttack(), 25);
    }

    public void testGetArmor() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);
        CommanderUnit test2 = new CommanderUnit("Test2", 15);

        assertEquals(test1.getArmor(), 5);
        assertEquals(test2.getArmor(), 15);
    }

    public void testTakeDamage() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15);

        test1.takeDamage(2);
        assertEquals(test1.getHealth(), 13);
    }

    public void testToString() {
        CommanderUnit test1 = new CommanderUnit("Test1", 20, 10, 5);

        assertEquals(test1.toString(), "Unit{name='Test1', health=20, attack=10, armor=5}");
    }

    public void testGetResistBonus() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15);

        assertEquals(test1.getResistBonus(), 1);

        test1.takeDamage(2);
        assertEquals(test1.getResistBonus(), 1);
    }

    public void testGetAttackBonus() {
        CommanderUnit test1 = new CommanderUnit("Test1", 15);
        CommanderUnit test2 = new CommanderUnit("Test2", 10);

        assertEquals(test1.getAttackBonus(), 6);

        test1.attack(test2);
        assertEquals(test1.getAttackBonus(), 2);

        test1.attack(test2);
        assertEquals(test1.getAttackBonus(), 2);
    }
}