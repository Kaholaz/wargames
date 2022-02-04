package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;

public class InfantryUnitTest extends TestCase {

    public void testAttack() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);
        InfantryUnit test2 = new InfantryUnit("Test2", 15);

        test1.attack(test2);
        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 14);

        test1.attack(test2);
        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 13);

        test1.attack(test2);
        assertEquals(test2.getHealth(), 12);

        assertEquals(test1.getHealth(), 20);

        test2.attack(test1);
        assertEquals(test1.getHealth(), 9);
        assertEquals(test2.getHealth(), 12);

    }

    public void testTestGetName() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);
        InfantryUnit test2 = new InfantryUnit("Test2", 15);

        assertEquals(test1.getName(), "Test1");
        assertEquals(test2.getName(), "Test2");
    }

    public void testGetHealth() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);
        InfantryUnit test2 = new InfantryUnit("Test2", 15);

        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 15);
    }

    public void testGetAttack() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);
        InfantryUnit test2 = new InfantryUnit("Test2", 15);

        assertEquals(test1.getAttack(), 10);
        assertEquals(test2.getAttack(), 15);
    }

    public void testGetArmor() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);
        InfantryUnit test2 = new InfantryUnit("Test2", 15);

        assertEquals(test1.getArmor(), 5);
        assertEquals(test2.getArmor(), 10);
    }

    public void testTakeDamage() {
        InfantryUnit test1 = new InfantryUnit("Test1", 15);

        test1.takeDamage(2);
        assertEquals(test1.getHealth(), 13);
    }

    public void testToString() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);

        assertEquals(test1.toString(), "Unit{name='Test1', health=20, attack=10, armor=5}");
    }

    public void testGetResistBonus() {
        InfantryUnit test1 = new InfantryUnit("Test1", 15);

        assertEquals(test1.getResistBonus(), 1);

        test1.takeDamage(2);
        assertEquals(test1.getResistBonus(), 1);
    }

    public void testGetAttackBonus() {
        InfantryUnit test1 = new InfantryUnit("Test1", 15);
        InfantryUnit test2 = new InfantryUnit("Test2", 10);

        assertEquals(test1.getAttackBonus(), 2);

        test1.attack(test2);
        assertEquals(test1.getAttackBonus(), 2);
    }
}