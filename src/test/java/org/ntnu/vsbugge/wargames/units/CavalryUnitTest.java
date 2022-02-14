package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;

public class CavalryUnitTest extends TestCase {
    
    public void testAttack() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        test1.attack(test2);
        assertEquals(20, test1.getHealth());
        assertEquals(12, test2.getHealth());

        test1.attack(test2);
        assertEquals(20, test1.getHealth());
        assertEquals(12, test2.getHealth());

        test1.attack(test2);
        assertEquals(12, test2.getHealth());

        assertEquals(20, test1.getHealth());

        test2.attack(test1);
        assertEquals(0, test1.getHealth());
        assertEquals(12, test2.getHealth());

    }

    public void testGetName() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        assertEquals("Test1", test1.getName());
        assertEquals("Test2", test2.getName());
    }

    public void testGetHealth() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        assertEquals(20, test1.getHealth());
        assertEquals(15, test2.getHealth());
    }

    public void testGetAttack() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        assertEquals(10, test1.getAttack());
        assertEquals(20, test2.getAttack());
    }

    public void testGetArmor() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        assertEquals(5, test1.getArmor());
        assertEquals(12, test2.getArmor());
    }

    public void testTakeDamage() {
        CavalryUnit test1 = new CavalryUnit("Test1", 15);

        test1.takeDamage(2);
        assertEquals(13, test1.getHealth());
    }

    public void testToString() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);

        assertEquals("CavalryUnit{name='Test1', health=20, attack=10, armor=5}", test1.toString());
    }

    public void testGetResistBonus() {
        CavalryUnit test1 = new CavalryUnit("Test1", 15);

        assertEquals(1, test1.getResistBonus());

        test1.takeDamage(2);
        assertEquals(1, test1.getResistBonus());
    }

    public void testGetAttackBonus() {
        CavalryUnit test1 = new CavalryUnit("Test1", 15);
        CavalryUnit test2 = new CavalryUnit("Test2", 10);

        assertEquals(6, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(2, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(2, test1.getAttackBonus());
    }

    public void testCopyOf() {
        CavalryUnit test1 = new CavalryUnit("Test1", 15, 10 ,5);
        CavalryUnit test2 = (CavalryUnit) Unit.copyOf(test1);

        assertFalse(test1 == test2);
        assertTrue(test1.equals(test2));
    }
}