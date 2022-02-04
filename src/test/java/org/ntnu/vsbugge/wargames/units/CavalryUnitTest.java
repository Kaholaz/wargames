package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;

public class CavalryUnitTest extends TestCase {
    
    public void testAttack() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        test1.attack(test2);
        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 12);

        test1.attack(test2);
        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 12);

        test1.attack(test2);
        assertEquals(test2.getHealth(), 12);

        assertEquals(test1.getHealth(), 20);

        test2.attack(test1);
        assertEquals(test1.getHealth(), 0);
        assertEquals(test2.getHealth(), 12);

    }

    public void testTestGetName() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        assertEquals(test1.getName(), "Test1");
        assertEquals(test2.getName(), "Test2");
    }

    public void testGetHealth() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        assertEquals(test1.getHealth(), 20);
        assertEquals(test2.getHealth(), 15);
    }

    public void testGetAttack() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        assertEquals(test1.getAttack(), 10);
        assertEquals(test2.getAttack(), 20);
    }

    public void testGetArmor() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        assertEquals(test1.getArmor(), 5);
        assertEquals(test2.getArmor(), 12);
    }

    public void testTakeDamage() {
        CavalryUnit test1 = new CavalryUnit("Test1", 15);

        test1.takeDamage(2);
        assertEquals(test1.getHealth(), 13);
    }

    public void testToString() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);

        assertEquals(test1.toString(), "Unit{name='Test1', health=20, attack=10, armor=5}");
    }

    public void testGetResistBonus() {
        CavalryUnit test1 = new CavalryUnit("Test1", 15);

        assertEquals(test1.getResistBonus(), 1);

        test1.takeDamage(2);
        assertEquals(test1.getResistBonus(), 1);
    }

    public void testGetAttackBonus() {
        CavalryUnit test1 = new CavalryUnit("Test1", 15);
        CavalryUnit test2 = new CavalryUnit("Test2", 10);

        assertEquals(test1.getAttackBonus(), 6);

        test1.attack(test2);
        assertEquals(test1.getAttackBonus(), 2);

        test1.attack(test2);
        assertEquals(test1.getAttackBonus(), 2);
    }
}