package org.ntnu.vsbugge.wargames.units;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.enums.TerrainEnum;

import static org.junit.Assert.assertNotEquals;

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

    public void testGetNameLongConstructor() {
        InfantryUnit test = new InfantryUnit("Test", 20, 10, 5);
        assertEquals("Test", test.getName());
    }

    public void testGetNameShortConstructor() {
        InfantryUnit test = new InfantryUnit("Test", 20);
        assertEquals("Test", test.getName());
    }

    public void testGetHealthLongConstructor() {
        InfantryUnit test = new InfantryUnit("Test", 20, 10, 5);
        assertEquals(20, test.getHealth());
    }

    public void testGetHealthShortConstructor() {
        InfantryUnit test = new InfantryUnit("Test", 20);
        assertEquals(20, test.getHealth());
    }

    public void testGetAttackLongConstructor() {
        InfantryUnit test = new InfantryUnit("Test", 20, 10, 5);
        assertEquals(10, test.getAttack());
    }

    public void testGetAttackShortConstructor() {
        InfantryUnit test = new InfantryUnit("Test", 20);
        assertEquals(InfantryUnit.DEFAULT_ATTACK, test.getAttack());
    }

    public void testGetArmorLongConstructor() {
        InfantryUnit test = new InfantryUnit("Test", 20, 10, 5);
        assertEquals(5, test.getArmor());
    }

    public void testGetArmorShortConstructor() {
        InfantryUnit test = new InfantryUnit("Test", 20);
        assertEquals(InfantryUnit.DEFAULT_ARMOR, test.getArmor());
    }

    public void testTakeDamage() {
        InfantryUnit test = new InfantryUnit("Test", 15);

        test.takeDamage(2);
        assertEquals(13, test.getHealth());
    }

    public void testToString() {
        InfantryUnit test = new InfantryUnit("Test", 20, 10, 5);

        assertEquals("InfantryUnit{name='Test', health=20, attack=10, armor=5}", test.toString());
    }

    public void testGetResistBonus() {
        InfantryUnit test = new InfantryUnit("Test", 15);
        assertEquals(InfantryUnit.RESIST_BONUS, test.getResistBonus());
    }

    public void testGetResistBonusTakesForrestTerrainIntoAccount() {
        InfantryUnit test = new InfantryUnit("Test", 15);

        assertEquals(InfantryUnit.RESIST_BONUS + 2, test.getResistBonus(TerrainEnum.FORREST));
    }

    public void testGetAttackBonus() {
        InfantryUnit test = new InfantryUnit("Test", 15);
        assertEquals(InfantryUnit.ATTACK_BONUS, test.getAttackBonus());
    }

    public void testGetAttackBonusTakesForrestTerrainIntoAccount() {
        InfantryUnit test = new InfantryUnit("Test", 15);

        assertEquals(InfantryUnit.ATTACK_BONUS + 2, test.getAttackBonus(TerrainEnum.FORREST));
    }

    public void testCopyIsNotSame() {
        InfantryUnit test = new InfantryUnit("Test", 15, 10, 5);
        InfantryUnit copy = test.copy();

        assertNotSame(test, copy);
    }

    public void testCopyIsEqual() {
        InfantryUnit test = new InfantryUnit("Test", 15, 10, 5);
        InfantryUnit copy = test.copy();

        assertEquals(test, copy);
    }

    public void testCopyDifferentUnitsAreNotEqual() {
        InfantryUnit test1 = new InfantryUnit("Test1", 15, 10, 5);
        InfantryUnit test1Copy = test1.copy();

        InfantryUnit test2 = new InfantryUnit("Test2", 20, 25, 30);
        InfantryUnit test2Copy = test2.copy();

        assertNotEquals(test1Copy, test2Copy);
    }
}