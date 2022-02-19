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

    public void testGetNameLongConstructor() {
        CavalryUnit test = new CavalryUnit("Test", 20, 10, 5);
        assertEquals("Test", test.getName());
    }

    public void testGetNameShortConstructor() {
        CavalryUnit test = new CavalryUnit("Test", 20);
        assertEquals("Test", test.getName());
    }

    public void testGetHealthLongConstructor() {
        CavalryUnit test = new CavalryUnit("Test", 20, 10, 5);
        assertEquals(20, test.getHealth());
    }

    public void testGetHealthShortConstructor() {
        CavalryUnit test = new CavalryUnit("Test", 20);
        assertEquals(20, test.getHealth());
    }

    public void testGetAttackLongConstructor() {
        CavalryUnit test = new CavalryUnit("Test", 20, 10, 5);
        assertEquals(10, test.getAttack());
    }

    public void testGetAttackShortConstructor() {
        CavalryUnit test = new CavalryUnit("Test", 20);
        assertEquals(CavalryUnit.DEFAULT_ATTACK, test.getAttack());
    }

    public void testGetArmorLongConstructor() {
        CavalryUnit test = new CavalryUnit("Test", 20, 10, 5);
        assertEquals(5, test.getArmor());
    }

    public void testGetArmorShortConstructor() {
        CavalryUnit test = new CavalryUnit("Test", 20);
        assertEquals(CavalryUnit.DEFAULT_ARMOR, test.getArmor());
    }

    public void testTakeDamage() {
        CavalryUnit test = new CavalryUnit("Test", 15);

        test.takeDamage(2);
        assertEquals(13, test.getHealth());
    }

    public void testToString() {
        CavalryUnit test = new CavalryUnit("Test", 20, 10, 5);

        assertEquals("CavalryUnit{name='Test', health=20, attack=10, armor=5}", test.toString());
    }

    public void testGetResistBonusBeforeUnitHasBeenAttacked() {
        CavalryUnit test = new CavalryUnit("Test", 15);

        assertEquals(CavalryUnit.RESIST_BONUS, test.getResistBonus());
    }

    public void testGetResistBonusAfterUnitHasBeenAttacked() {
        CavalryUnit test = new CavalryUnit("Test", 15);

        test.takeDamage(2);
        assertEquals(CavalryUnit.RESIST_BONUS, test.getResistBonus());
    }

    public void testGetAttackBonusBeforeUnitHasAttacked() {
        CavalryUnit test = new CavalryUnit("Test", 15);
        assertEquals(CavalryUnit.FIRST_ATTACK_BONUS, test.getAttackBonus());
    }

    public void testGetAttackBonusAfterHasAttacked() {
        CavalryUnit test = new CavalryUnit("Test", 15);
        CavalryUnit rock = new CavalryUnit("Rock", 1000, 0, 1000);

        test.attack(rock);
        assertEquals(CavalryUnit.ATTACK_BONUS, test.getAttackBonus());
    }

    public void testCopyOfIsNotSame() {
        CavalryUnit test = new CavalryUnit("Test", 15, 10, 5);
        CavalryUnit copy = (CavalryUnit) Unit.copyOf(test);

        assertNotSame(test, copy);
    }

    public void testCopyOfIsEqual() {
        CavalryUnit test = new CavalryUnit("Test", 15, 10, 5);
        CavalryUnit copy = (CavalryUnit) Unit.copyOf(test);

        assertEquals(test, copy);
    }

    public void testCopyOfDifferentUnitsAreNotEqual() {
        CavalryUnit test1 = new CavalryUnit("Test1", 15, 10 ,5);
        CavalryUnit test1Copy = (CavalryUnit) Unit.copyOf(test1);

        CavalryUnit test2 = new CavalryUnit("Test2", 20, 25 ,30);
        CavalryUnit test2Copy = (CavalryUnit) Unit.copyOf(test2);

        assertFalse(test1Copy.equals(test2Copy));
    }

    public void testCopyOfRetainsStats() {
        CavalryUnit test = new CavalryUnit("Test", 100, 10 ,5);
        CavalryUnit copy = (CavalryUnit) Unit.copyOf(test);

        test.attack(copy);
        copy = (CavalryUnit) Unit.copyOf(test);

        assertEquals(CavalryUnit.ATTACK_BONUS, copy.getAttackBonus());
    }

    public void testResetStatsWhenUnitHasNotAttacked() {
        CavalryUnit test = new CavalryUnit("Test", 100, 10, 5);
        test.resetStats();
        assertEquals(CavalryUnit.FIRST_ATTACK_BONUS, test.getAttackBonus());
    }

    public void testResetStatsWhenUnitHasAttacked() {
        CavalryUnit test = new CavalryUnit("Test", 100, 10, 5);
        CavalryUnit rock = new CavalryUnit("Rock", 1000, 0, 1000);

        test.attack(rock);
        test.resetStats();
        assertEquals(CavalryUnit.FIRST_ATTACK_BONUS, test.getAttackBonus());
    }

    public void testEqualsReturnsFalseWhenUnitsHaveAttackedDifferentAmountOfTimes(){
        CavalryUnit test = new CavalryUnit("Test", 100, 10 ,5);
        CavalryUnit copy = (CavalryUnit) Unit.copyOf(test);
        CavalryUnit punchingBag = new CavalryUnit("PunchingBag", 1000);

        copy.attack(punchingBag);
        assertFalse(test.equals(copy));
    }

    public void testEqualsReturnsTrueWhenUnitsHaveAttackedTheSameAmountOfTimes() {
        CavalryUnit test = new CavalryUnit("Test", 100, 10 ,5);
        CavalryUnit copy = (CavalryUnit) Unit.copyOf(test);
        CavalryUnit punchingBag = new CavalryUnit("PunchingBag", 1000);

        test.attack(punchingBag);
        copy.attack(punchingBag);
        assertEquals(test, copy);
    }

    public void testHashCodeReturnsDifferentHashWhenUnitsHaveAttackedDifferentAmountOfTimes(){
        CavalryUnit test = new CavalryUnit("Test", 100, 10 ,5);
        CavalryUnit copy = (CavalryUnit) Unit.copyOf(test);
        CavalryUnit punchingBag = new CavalryUnit("PunchingBag", 1000);

        copy.attack(punchingBag);
        assertFalse(test.hashCode() == copy.hashCode());
    }

    public void testHashCodeReturnSameHashWhenUnitsHaveAttackedTheSameAmountOfTimes() {
        CavalryUnit test = new CavalryUnit("Test", 100, 10 ,5);
        CavalryUnit copy = (CavalryUnit) Unit.copyOf(test);
        CavalryUnit punchingBag = new CavalryUnit("PunchingBag", 1000);

        test.attack(punchingBag);
        copy.attack(punchingBag);
        assertEquals(test.hashCode(), copy.hashCode());
    }

    public void testCompareToReturnsNonZeroWhenUnitsHaveAttackedDifferentAmountOfTimes(){
        CavalryUnit test = new CavalryUnit("Test", 100, 10 ,5);
        CavalryUnit copy = (CavalryUnit) Unit.copyOf(test);
        CavalryUnit punchingBag = new CavalryUnit("PunchingBag", 1000);

        copy.attack(punchingBag);
        assertEquals(-1, test.compareTo(copy));
    }

    public void testCompareToReturnsZeroWhenUnitsHaveAttackedTheSameAmountOfTimes() {
        CavalryUnit test = new CavalryUnit("Test", 100, 10 ,5);
        CavalryUnit copy = (CavalryUnit) Unit.copyOf(test);
        CavalryUnit punchingBag = new CavalryUnit("PunchingBag", 1000);

        test.attack(punchingBag);
        copy.attack(punchingBag);
        assertSame(0, test.compareTo(copy));
    }
}