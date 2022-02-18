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
        assertEquals(CavalryUnit.DEFAULT_ATTACK, test2.getAttack());
    }

    public void testGetArmor() {
        CavalryUnit test1 = new CavalryUnit("Test1", 20, 10, 5);
        CavalryUnit test2 = new CavalryUnit("Test2", 15);

        assertEquals(5, test1.getArmor());
        assertEquals(CavalryUnit.DEFAULT_ARMOR, test2.getArmor());
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

        assertEquals(CavalryUnit.RESIST_BONUS, test1.getResistBonus());

        test1.takeDamage(2);
        assertEquals(CavalryUnit.RESIST_BONUS, test1.getResistBonus());
    }

    public void testGetAttackBonus() {
        CavalryUnit test1 = new CavalryUnit("Test1", 15);
        CavalryUnit test2 = new CavalryUnit("Test2", 10);

        assertEquals(CavalryUnit.FIRST_ATTACK_BONUS, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(CavalryUnit.ATTACK_BONUS, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(CavalryUnit.ATTACK_BONUS, test1.getAttackBonus());
    }

    public void testCopyOf() {
        CavalryUnit test1 = new CavalryUnit("Test1", 15, 10 ,5);
        CavalryUnit test2 = (CavalryUnit) Unit.copyOf(test1);

        assertFalse(test1 == test2);
        assertTrue(test1.equals(test2));
    }

    public void testCopyOfRetainsStats() {
        CavalryUnit test1 = new CavalryUnit("Test1", 100, 10 ,5);
        CavalryUnit test2 = (CavalryUnit) Unit.copyOf(test1);

        test1.attack(test2);
        assertEquals(CavalryUnit.FIRST_ATTACK_BONUS, test2.getAttackBonus());

        test2 = (CavalryUnit) Unit.copyOf(test1);
        assertEquals(CavalryUnit.ATTACK_BONUS, test2.getAttackBonus());
    }

    public void testResetStats() {
        CavalryUnit test1 = new CavalryUnit("Test1", 100, 10 ,5);
        CavalryUnit test2 = (CavalryUnit) Unit.copyOf(test1);

        test1.attack(test2);
        assertEquals(CavalryUnit.ATTACK_BONUS, test1.getAttackBonus());

        test1.resetStats();
        assertEquals(CavalryUnit.FIRST_ATTACK_BONUS, test1.getAttackBonus());

        test1.attack(test2);
        assertEquals(CavalryUnit.ATTACK_BONUS, test1.getAttackBonus());
    }

    public void testEqualsComparesUnitSpecificTraits(){
        CavalryUnit test1 = new CavalryUnit("Test1", 100, 10 ,5);
        CavalryUnit test2 = (CavalryUnit) Unit.copyOf(test1);
        CavalryUnit punchingBag = new CavalryUnit("PunchingBag", 1000);

        assertTrue(test1.equals(test2));

        test1.attack(punchingBag);
        assertFalse(test1.equals(test2));

        test2.attack(punchingBag);
        assertTrue(test1.equals(test2));
    }

    public void testHashCodeHashesUnitSpecificStats(){
        CavalryUnit test1 = new CavalryUnit("Test1", 100, 10 ,5);
        CavalryUnit test2 = (CavalryUnit) Unit.copyOf(test1);
        CavalryUnit punchingBag = new CavalryUnit("PunchingBag", 1000);

        assertTrue(test1.hashCode() == test2.hashCode());

        test1.attack(punchingBag);
        assertFalse(test1.hashCode() == test2.hashCode());

        test2.attack(punchingBag);
        assertTrue(test1.hashCode() == test2.hashCode());
    }

    public void testCompareToComparesUnitSpecificStats() {
        CavalryUnit test1 = new CavalryUnit("Test1", 100, 10 ,5);
        CavalryUnit test2 = (CavalryUnit) Unit.copyOf(test1);
        CavalryUnit punchingBag = new CavalryUnit("PunchingBag", 1000);

        assertEquals(0, test1.compareTo(test2));

        test1.attack(punchingBag);
        assertEquals(1, test1.compareTo(test2));

        test2.attack(punchingBag);
        assertEquals(0, test1.compareTo(test2));
    }
}