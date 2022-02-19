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

    public void testGetNameLongConstructor() {
        RangedUnit test1 = new RangedUnit("Test", 20, 10, 5);
        assertEquals("Test", test1.getName());
    }

    public void testGetNameShortConstructor() {
        RangedUnit test = new RangedUnit("Test", 15);
        assertEquals("Test", test.getName());
    }

    public void testGetHealthLongConstructor() {
        RangedUnit test = new RangedUnit("Test", 20, 10, 5);
        assertEquals(20, test.getHealth());
    }

    public void testGetHealthShortConstructor() {
        RangedUnit test = new RangedUnit("Test", 20);
        assertEquals(20, test.getHealth());
    }

    public void testGetAttackLongConstructor() {
        RangedUnit test = new RangedUnit("Test", 20, 10, 5);
        assertEquals(10, test.getAttack());
    }

    public void testGetAttackShortConstructor() {
        RangedUnit test = new RangedUnit("Test", 20);
        assertEquals(RangedUnit.DEFAULT_ATTACK, test.getAttack());
    }

    public void testGetArmorLongConstructor() {
        RangedUnit test1 = new RangedUnit("Test1", 20, 10, 5);
        assertEquals(5, test1.getArmor());
    }

    public void testGetArmorShortConstructor() {
        RangedUnit test1 = new RangedUnit("Test1", 20);
        assertEquals(RangedUnit.DEFAULT_ARMOR, test1.getArmor());
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

    public void testGetResistBonusBeforeTheUnitHasTakenDamage() {
        RangedUnit test = new RangedUnit("Test", 15);
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS, test.getResistBonus());
    }

    public void testGetResistBonusAfterTakingDamageOnce() {
        RangedUnit test = new RangedUnit("Test", 15);

        test.takeDamage(2);
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS - RangedUnit.RESIST_BONUS_PENALTY, test.getResistBonus());
    }

    public void testGetResistBonusAfterTakingDamageTwice() {
        RangedUnit test = new RangedUnit("Test", 15);

        test.takeDamage(2);test.takeDamage(2);
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS - 2 * RangedUnit.RESIST_BONUS_PENALTY, test.getResistBonus());
    }

    public void testGetResistBonusAfterTakingDamageThrice() {
        RangedUnit test = new RangedUnit("Test", 15);

        test.takeDamage(2); test.takeDamage(2); test.takeDamage(2);
        assertEquals(RangedUnit.MINIMUM_RESIST_BONUS, test.getResistBonus());
    }

    public void testGetResistBonusAfterTakingZeroDamage() {
        RangedUnit test = new RangedUnit("Test", 15);

        test.takeDamage(0);
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS- RangedUnit.RESIST_BONUS_PENALTY, test.getResistBonus());
    }

    public void testGetAttackBonusBeforeTheUnitHasAttacked() {
        RangedUnit test = new RangedUnit("Test", 15);

        assertEquals(RangedUnit.ATTACK_BONUS, test.getAttackBonus());
    }

    public void testGetAttackBonusAfterTheUnitHasAttacked() {
        RangedUnit test = new RangedUnit("Test", 15);
        RangedUnit punchingBag = new RangedUnit("Punching Bag", 1000, 0, 1000);

        test.attack(punchingBag);
        assertEquals(RangedUnit.ATTACK_BONUS, test.getAttackBonus());
    }

    public void testCopyOfIsNotSame() {
        RangedUnit test = new RangedUnit("Test", 15, 10 ,5);
        RangedUnit copy = (RangedUnit) Unit.copyOf(test);

        assertNotSame(test, copy);
    }

    public void testCopyOfIsEqual() {
        RangedUnit test = new RangedUnit("Test", 15, 10 ,5);
        RangedUnit copy = (RangedUnit) Unit.copyOf(test);

        assertEquals(test, copy);
    }

    public void testCopyOfDifferentUnitsAreNotEqual() {
        RangedUnit test1 = new RangedUnit("Test1", 15, 10 ,5);
        RangedUnit test1Copy = (RangedUnit) Unit.copyOf(test1);

        RangedUnit test2 = new RangedUnit("Test2", 20, 25 ,30);
        RangedUnit test2Copy = (RangedUnit) Unit.copyOf(test2);

        assertFalse(test1Copy.equals(test2Copy));
    }

    public void testCopyOfRetainsStatsWhenUnitHasNotBeenAttacked() {
        RangedUnit test = new RangedUnit("Test", 15, 10, 5);
        RangedUnit copy = (RangedUnit) Unit.copyOf(test);

        assertEquals(RangedUnit.INITIAL_RESIST_BONUS, copy.getResistBonus());
    }

    public void testCopyOfRetainsStatsWhenUnitHasBeenAttacked() {
        RangedUnit test = new RangedUnit("Test", 15, 10, 5);
        test.takeDamage(0); test.takeDamage(0);
        RangedUnit copy = (RangedUnit) Unit.copyOf(test);


        assertEquals(RangedUnit.MINIMUM_RESIST_BONUS, copy.getResistBonus());
    }

    public void testResetStatsWhenUnitHasNotTakenDamage() {
        RangedUnit test = new RangedUnit("Test", 15, 10, 5);
        test.resetStats();
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS, test.getResistBonus());
    }

    public void testResetStatsWhenUnitHasTakenDamage() {
        RangedUnit test = new RangedUnit("Test", 15, 10, 5);
        test.takeDamage(0);
        test.resetStats();
        assertEquals(RangedUnit.INITIAL_RESIST_BONUS, test.getResistBonus());
    }

    public void testEqualsReturnsTrueWhenTheUnitsHasBeenAttackedTheSameAmountOfTimes() {
        RangedUnit test = new RangedUnit("Test", 100);
        RangedUnit copy = (RangedUnit) Unit.copyOf(test);

        test.takeDamage(0);
        copy.takeDamage(0);

        assertEquals(test, copy);
    }

    public void testEqualsReturnsFalseWhenTheUnitsHasNotBeenAttackedTheSameAmountOfTimes() {
        RangedUnit test = new RangedUnit("Test", 100);
        RangedUnit copy = (RangedUnit) Unit.copyOf(test);

        copy.takeDamage(0);

        assertFalse(test.equals(copy));
    }

    public void testHashCodeReturnsSameHashCodeWhenTheUnitHasBeenAttackedTheSameAmountOfTimes() {
        RangedUnit test = new RangedUnit("Test", 100);
        RangedUnit copy = (RangedUnit) Unit.copyOf(test);

        copy.takeDamage(0);
        test.takeDamage(0);

        assertEquals(test.hashCode(), copy.hashCode());
    }

    public void testHashCodeReturnsDifferentHashCodeWhenTheUnitHasNotBeenAttackedTheSameAmountOfTimes() {
        RangedUnit test = new RangedUnit("Test", 100);
        RangedUnit copy = (RangedUnit) Unit.copyOf(test);

        copy.takeDamage(0);

        assertFalse(test.hashCode() == copy.hashCode());
    }

    public void testCompareToReturnsZeroForUnitsThatHasBeenAttackedTheSameAmountOfTimes() {
        RangedUnit test = new RangedUnit("Test", 100);
        RangedUnit copy = (RangedUnit) Unit.copyOf(test);

        test.takeDamage(0);
        copy.takeDamage(0);

        assertEquals(0, test.compareTo(copy));
    }

    public void testCompareToReturnsNegativeOneForUnitsThatHasNotBeenAttackedTheSameAmountOfTimes() {
        RangedUnit test = new RangedUnit("Test", 100);
        RangedUnit copy = (RangedUnit) Unit.copyOf(test);

        test.takeDamage(0);

        assertEquals(1, test.compareTo(copy));
    }
}