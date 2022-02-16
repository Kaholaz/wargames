package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;

public class InfantryUnitTest extends TestCase {

    public void testAttack() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);
        InfantryUnit test2 = new InfantryUnit("Test2", 15);

        test1.attack(test2);
        assertEquals(20, test1.getHealth());
        assertEquals(14, test2.getHealth());

        test1.attack(test2);
        assertEquals(20, test1.getHealth());
        assertEquals(13, test2.getHealth());

        test1.attack(test2);
        assertEquals(12, test2.getHealth());
        assertEquals(20, test1.getHealth());

        test2.attack(test1);
        assertEquals(9, test1.getHealth());
        assertEquals(12, test2.getHealth());

    }

    public void testGetName() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);
        InfantryUnit test2 = new InfantryUnit("Test2", 15);

        assertEquals("Test1", test1.getName());
        assertEquals("Test2", test2.getName());
    }

    public void testGetHealth() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);
        InfantryUnit test2 = new InfantryUnit("Test2", 15);

        assertEquals(20, test1.getHealth());
        assertEquals(15, test2.getHealth());
    }

    public void testGetAttack() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);
        InfantryUnit test2 = new InfantryUnit("Test2", 15);

        assertEquals(10, test1.getAttack());
        assertEquals(InfantryUnit.DEFAULT_ATTACK, test2.getAttack());
    }

    public void testGetArmor() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);
        InfantryUnit test2 = new InfantryUnit("Test2", 15);

        assertEquals(5, test1.getArmor());
        assertEquals(InfantryUnit.DEFAULT_ARMOR, test2.getArmor());
    }

    public void testTakeDamage() {
        InfantryUnit test1 = new InfantryUnit("Test1", 15);

        test1.takeDamage(2);
        assertEquals(13, test1.getHealth());
    }

    public void testToString() {
        InfantryUnit test1 = new InfantryUnit("Test1", 20, 10, 5);

        assertEquals("InfantryUnit{name='Test1', health=20, attack=10, armor=5}", test1.toString());
    }

    public void testGetResistBonus() {
        InfantryUnit test1 = new InfantryUnit("Test1", 15);

        assertEquals(InfantryUnit.RESIST_BONUS, test1.getResistBonus());

        test1.takeDamage(2);
        assertEquals(InfantryUnit.RESIST_BONUS, test1.getResistBonus());
    }

    public void testGetAttackBonus() {
        InfantryUnit test1 = new InfantryUnit("Test1", 15);
        InfantryUnit test2 = new InfantryUnit("Test2", 10);

        assertEquals(InfantryUnit.ATTACK_BONUS, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(InfantryUnit.ATTACK_BONUS, test1.getAttackBonus());
    }

    public void testCopyOf() {
        InfantryUnit test1 = new InfantryUnit("Test1", 15, 10 ,5);
        InfantryUnit test2 = (InfantryUnit) Unit.copyOf(test1);

        assertFalse(test1 == test2);
        assertTrue(test1.equals(test2));
    }
}