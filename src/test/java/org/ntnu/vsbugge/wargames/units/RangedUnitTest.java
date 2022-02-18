package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;

public class RangedUnitTest extends TestCase {

    public void testAttack() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        RangedUnit test2 = new RangedUnit("Test2", 15);

        test1.attack(test2);
        assertEquals(20, test1.getHealth());
        assertEquals(15, test2.getHealth());

        test1.attack(test2);
        assertEquals(20, test1.getHealth());
        assertEquals(14, test2.getHealth());

        test1.attack(test2);
        assertEquals(11, test2.getHealth());

        test1.attack(test2);
        assertEquals(8, test2.getHealth());

        assertEquals(20, test1.getHealth());

        test2.attack(test1);
        assertEquals(13, test1.getHealth());
        assertEquals(8, test2.getHealth());

    }

    public void testGetName() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        RangedUnit test2 = new RangedUnit("Test2", 15);

        assertEquals("Test1", test1.getName());
        assertEquals("Test2", test2.getName());
    }

    public void testGetHealth() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        RangedUnit test2 = new RangedUnit("Test2", 15);

        assertEquals(20, test1.getHealth());
        assertEquals(15, test2.getHealth());
    }

    public void testGetAttack() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        RangedUnit test2 = new RangedUnit("Test2", 15);

        assertEquals(10, test1.getAttack());
        assertEquals(RangedUnit.DEFAULT_ATTACK, test2.getAttack());
    }

    public void testGetArmor() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        RangedUnit test2 = new RangedUnit("Test2", 15);

        assertEquals(5, test1.getArmor());
        assertEquals(RangedUnit.DEFAULT_ARMOR, test2.getArmor());
    }

    public void testTakeDamage() {
        RangedUnit test1 = new RangedUnit("Test1", 15);

        test1.takeDamage(2);
        assertEquals(13, test1.getHealth());
    }

    public void testToString() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);

        assertEquals("RangedUnit{name='Test1', health=20, attack=10, armor=5}", test1.toString());
    }

    public void testGetResistBonus() {
        RangedUnit test1 = new RangedUnit("Test1", 15);

        assertEquals(RangedUnit.INITIAL_RESIST_BONUS, test1.getResistBonus());

        test1.takeDamage(2);
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS - RangedUnit.RESIST_BONUS_PENALTY, test1.getResistBonus());

        test1.takeDamage(2);
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS - 2 * RangedUnit.RESIST_BONUS_PENALTY, test1.getResistBonus());

        test1.takeDamage(2);
        assertEquals(RangedUnit.MINIMUM_RESIST_BONUS, test1.getResistBonus());
    }

    public void testGetAttackBonus() {
        RangedUnit test1 = new RangedUnit("Test1", 15);
        RangedUnit test2 = new RangedUnit("Test2", 10);

        assertEquals(RangedUnit.ATTACK_BONUS, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(RangedUnit.ATTACK_BONUS, test1.getAttackBonus());
    }

    public void testCopyOf() {
        RangedUnit test1 = new RangedUnit("Test1", 15, 10 ,5);
        RangedUnit test2 = (RangedUnit) Unit.copyOf(test1);

        assertFalse(test1 == test2);
        assertTrue(test1.equals(test2));
    }

    public void testCopyOfRetainsStats() {
        RangedUnit test1 = new RangedUnit("Test1", 15, 10 ,5);

        test1.takeDamage(2);
        RangedUnit test2 = (RangedUnit) Unit.copyOf(test1);

        assertEquals(RangedUnit.INITIAL_RESIST_BONUS - RangedUnit.RESIST_BONUS_PENALTY, test2.getResistBonus());

        test1.takeDamage(2);
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS - RangedUnit.RESIST_BONUS_PENALTY, test2.getResistBonus());

        test2.takeDamage(2);
        assertEquals(RangedUnit.MINIMUM_RESIST_BONUS, test2.getResistBonus());
    }

    public void testResetStats() {
        RangedUnit test1 = new RangedUnit("Test1", 15, 10 ,5);

        test1.takeDamage(1);
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS - RangedUnit.RESIST_BONUS_PENALTY, test1.getResistBonus());

        test1.resetStats();
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS, test1.getResistBonus());

        test1.takeDamage(1);
        test1.takeDamage(1);
        assertEquals(RangedUnit.MINIMUM_RESIST_BONUS, test1.getResistBonus());

        test1.resetStats();
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS, test1.getResistBonus());
    }

    public void testEqualsComparesUnitSpecificStats() {
        RangedUnit test1 = new RangedUnit("Test1", 100);
        RangedUnit test2 = (RangedUnit) Unit.copyOf(test1);
        RangedUnit pokingStick = new RangedUnit("Poking Stick", 1, 0,0);

        assertTrue(test1.equals(test2));

        pokingStick.attack(test1);
        assertFalse(test1.equals(test2));

        pokingStick.attack(test2);
        assertTrue(test1.equals(test2));
    }

    public void testHashCodeHashesUnitSpecificStats() {
        RangedUnit test1 = new RangedUnit("Test1", 100);
        RangedUnit test2 = (RangedUnit) Unit.copyOf(test1);
        RangedUnit pokingStick = new RangedUnit("Poking Stick", 1, 0,0);

        assertTrue(test1.hashCode() == test2.hashCode());

        pokingStick.attack(test1);
        assertFalse(test1.hashCode() == test2.hashCode());

        pokingStick.attack(test2);
        assertTrue(test1.hashCode() == test2.hashCode());
    }

    public void testCompareToForUnitSpecificStats() {
        RangedUnit test1 = new RangedUnit("Test", 100);
        RangedUnit test2 = (RangedUnit) Unit.copyOf(test1);

        assertEquals(0, test1.compareTo(test2));

        test2.takeDamage(0);
        assertEquals(-1, test1.compareTo(test2));

        test1.takeDamage(0);
        assertEquals(0, test1.compareTo(test2));
    }
}