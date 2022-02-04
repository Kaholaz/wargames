package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;

public class RangedUnitTest extends TestCase {

    public void testAttack() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        RangedUnit test2 = new RangedUnit("Test2", 15);

        test1.attack(test2);
        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 15);

        test1.attack(test2);
        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 14);

        test1.attack(test2);
        assertEquals(test2.getHealth(), 11);

        test1.attack(test2);
        assertEquals(test2.getHealth(), 8);

        assertEquals(test1.getHealth(), 20);

        test2.attack(test1);
        assertEquals(test1.getHealth(), 13);
        assertEquals(test2.getHealth(), 8);

    }

    public void testTestGetName() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        RangedUnit test2 = new RangedUnit("Test2", 15);

        assertEquals(test1.getName(), "Test1");
        assertEquals(test2.getName(), "Test2");
    }

    public void testGetHealth() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        RangedUnit test2 = new RangedUnit("Test2", 15);

        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 15);
    }

    public void testGetAttack() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        RangedUnit test2 = new RangedUnit("Test2", 15);

        assertEquals(test1.getAttack(), 10);
        assertEquals(test2.getAttack(), 15);
    }

    public void testGetArmor() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        RangedUnit test2 = new RangedUnit("Test2", 15);

        assertEquals(test1.getArmor(), 5);
        assertEquals(test2.getArmor(), 8);
    }

    public void testTakeDamage() {
        RangedUnit test1 = new RangedUnit("Test1", 15);

        test1.takeDamage(2);
        assertEquals(test1.getHealth(), 13);
    }

    public void testToString() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);

        assertEquals(test1.toString(), "Unit{name='Test1', health=20, attack=10, armor=5}");
    }

    public void testGetResistBonus() {
        RangedUnit test1 = new RangedUnit("Test1", 15);

        assertEquals(test1.getResistBonus(), 6);

        test1.takeDamage(2);
        assertEquals(test1.getResistBonus(), 4);

        test1.takeDamage(2);
        assertEquals(test1.getResistBonus(), 2);

        test1.takeDamage(2);
        assertEquals(test1.getResistBonus(), 2);
    }

    public void testGetAttackBonus() {
        RangedUnit test1 = new RangedUnit("Test1", 15);
        RangedUnit test2 = new RangedUnit("Test2", 10);

        assertEquals(test1.getAttackBonus(), 3);

        test1.attack(test2);
        assertEquals(test1.getAttackBonus(), 3);
    }
}