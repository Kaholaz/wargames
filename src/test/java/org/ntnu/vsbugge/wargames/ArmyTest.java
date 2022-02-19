package org.ntnu.vsbugge.wargames;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.units.*;

import java.util.*;

public class ArmyTest extends TestCase {

    public void testAddToEmptyArmy() {
        List<Unit> testList = new ArrayList<>();
        Army testObj = new Army("TestObj");

        RangedUnit test = new RangedUnit("Test1", 10);

        testList.add(test);
        testObj.add(test);
        assertEquals(testList, testObj.getAllUnits());
    }

    public void testAddToNonEmptyArmy() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);

        Army testObj = new Army("TestObj", List.of(test1));

        testObj.add(test2);
        assertEquals(List.of(test1, test2), testObj.getAllUnits());
    }

    public void testAddMultiple() {
        Army testObj = new Army("TestObj");
        RangedUnit test = new RangedUnit("Test", 10);
        testObj.add(test, 5);

        // Check count and equals
        assertEquals(5, testObj.getAllUnits().size());
        for(Unit unit : testObj.getAllUnits()) {
            assertEquals(test, unit);
        }
    }

    public void testAddAll() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = new LinkedList<>(Arrays.asList(test1, test2));

        Army testObj = new Army("TestObj", testList);

        testObj.addAll(testList);
        testList.addAll(testList);
        assertEquals(testObj.getAllUnits(), testList);
    }

    public void testRemove() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);

        Army testObj = new Army("TestObj", List.of(test1, test2));

        testObj.remove(test1);
        assertEquals(List.of(test2), testObj.getAllUnits());
    }

    public void testRemoveOnEmptyArmy() {
        Army testObj = new Army("TestObj");

        try {
            testObj.remove(new CavalryUnit("prank", 5));
        } catch (Exception e) {
            fail("Remove from empty should not throw exception");
        }
    }

    public void testRemoveRemoveUnitNotInArmy() {
        CommanderUnit test = new CommanderUnit("Test", 100);
        Army armyObj = new Army("TestObj", List.of(new InfantryUnit("prank", 10)));

        armyObj.remove(test);
        assertEquals(List.of(new InfantryUnit("prank", 10)), armyObj.getAllUnits());
    }

    public void testRemoveUniqueCavalryUnit() {
        CavalryUnit test = new CavalryUnit("Test", 100);
        CavalryUnit punchingBag = new CavalryUnit("PUNCH ME!", 1000, 0, 1000);
        Army testArmy = new Army("TestArmy");

        testArmy.add(test, 2);

        // Should only remove one copy
        testArmy.remove(test);
        assertEquals(1, testArmy.getAllUnits().size());

        // Should not be removed because of different unit-specific traits (hasAttacked)
        test.attack(punchingBag); // ouch!
        testArmy.remove(test);
        assertTrue(testArmy.hasUnits());

        testArmy.getRandomUnit().attack(punchingBag);
        testArmy.remove(test);
        assertFalse(testArmy.hasUnits());
    }

    public void testRemoveUniqueRangedUnit(){
        RangedUnit test = new RangedUnit("Test", 100);
        Army testArmy = new Army("TestArmy");

        testArmy.add(test, 2);

        // Should only remove one copy
        testArmy.remove(test);
        assertEquals(1, testArmy.getAllUnits().size());

        // Should not be removed because of different unit-specific traits (timesTakenDamage)
        test.takeDamage(0);
        testArmy.remove(test);
        assertTrue(testArmy.hasUnits());

        testArmy.getRandomUnit().takeDamage(0); // ouch!
        testArmy.remove(test);
        assertFalse(testArmy.hasUnits());
    }

    public void testGetAllUnitsReturnsEqualList() {
        List<Unit> testList = List.of(new RangedUnit("Range", 10), new InfantryUnit("Close", 10));
        Army army = new Army("Test", testList);

        assertEquals(testList, army.getAllUnits());
    }

    public void testGetAllUnitsDoesNotReturnReference() {
        List<Unit> testList = List.of(new RangedUnit("Range", 10), new InfantryUnit("Close", 10));
        Army army = new Army("Test", testList);

        army.getAllUnits().remove(1);
        assertEquals(testList, army.getAllUnits());
    }

    public void testGetAllUnitsReturnsListOfReferences() {
        List<Unit> testList = List.of(new RangedUnit("Range", 10), new InfantryUnit("Close", 10));
        Army army = new Army("Test", testList);

        army.getAllUnits().get(0).takeDamage(0);
        assertFalse(testList.equals(army.getAllUnits()));

        testList.get(0).takeDamage(0);
        assertEquals(testList, army.getAllUnits());
    }

    public void testHasUnitsEmptyArmyReturnsFalse() {
        Army testObj = new Army("TestObj");
        assertFalse(testObj.hasUnits());
    }

    public void testHasUnitsNonEmptyArmyReturnsTrue() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);

        Army testObj = new Army("TestObj", List.of(test1, test2));
        assertTrue(testObj.hasUnits());
    }

    public void testHasUnitsArmyWhereAllUnitsAreRemovedReturnFalse() {
        RangedUnit test = new RangedUnit("Test", 10);

        Army testObj = new Army("TestObj", List.of(test));
        testObj.remove(test);

        assertFalse(testObj.hasUnits());
    }

    public void testGetRandomUnitThrowsExceptionWhenCalledOnAnEmptyArmy() {
        Army testObj = new Army("TestObj");

        try {
            testObj.getRandomUnit();
            fail("IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            assertEquals("The army has no units", e.getMessage());
        } catch (Exception e) {
            fail("IllegalStateException not thrown");
        }
    }

    public void testGetRandomUnitReturnsUnit() {
        RangedUnit test = new RangedUnit("Test", 10);
        Army testObj = new Army("TestObj");
        testObj.add(test);

        assertEquals(test, testObj.getRandomUnit());
    }

    public void testGetName() {
        Army testObj = new Army("TestObj");
        assertEquals("TestObj", testObj.getName());
    }

    public void testGetAllUnits() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        Army testObj = new Army("TestObj", testList);
        assertEquals(testList, testObj.getAllUnits());
    }

    public void testToString() {
        Army testObj = new Army("TestObj");
        assertEquals("Army{name='TestObj'}", testObj.toString());
    }

    public void testEqualsOnEmptyArmy() {
        Army testObj1 = new Army("TestObj");
        Army testObj2 = new Army("TestObj");

        assertEquals(testObj1, testObj2);
    }

    public void testEqualsOnNonEmptyArmy() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        Army testObj1 = new Army("TestObj", testList);
        Army testObj2 = new Army("TestObj", testList);
        assertEquals(testObj1, testObj2);
    }

    public void testEqualsDoesNotTakeOrderIntoAccount() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);

        Army testObj1 = new Army("TestObj");
        Army testObj2 = new Army("TestObj");

        testObj1.add(test1);
        testObj1.add(test2);

        // different insertion order
        testObj2.add(test2);
        testObj2.add(test1);

        assertEquals(testObj1, testObj2);
    }

    public void testEqualsReturnFalse() {
        Army testObj1 = new Army("TestObj");
        Army testObj2 = new Army("TestObj");

        testObj1.add(new RangedUnit("Test", 10));

        assertFalse(testObj1.equals(testObj2));
    }

    public void testHashCodeOfEmptyArmy() {
        Army testObj1 = new Army("TestObj");
        Army testObj2 = new Army("TestObj");

        assertEquals(testObj1.hashCode(), testObj2.hashCode());
    }

    public void testHashCodeOfNonEmptyArmy() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        Army testObj1 = new Army("TestObj", testList);
        Army testObj2 = new Army("TestObj", testList);
        assertEquals(testObj1.hashCode(), testObj2.hashCode());
    }

    public void testHashCodeDoesNotTakeOrderIntoAccount() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);

        Army testObj1 = new Army("TestObj");
        Army testObj2 = new Army("TestObj");

        testObj1.add(test1);
        testObj1.add(test2);

        testObj2.add(test2);
        testObj2.add(test1);

        assertEquals(testObj1.hashCode(), testObj2.hashCode());
    }

    public void testHashCodeIsUnique() {
        Army testObj1 = new Army("TestObj");
        Army testObj2 = new Army("TestObj");

        testObj1.add(new RangedUnit("Test1", 10));

        assertFalse(testObj1.hashCode() == testObj2.hashCode());
    }

    public void testArmyTemplate() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        Army testObj1 = new Army("Test", testList);
        Map<Unit, Integer> template = testObj1.getArmyTemplate();
        Army testObj2 = Army.parseArmyTemplate("Test", template);

        assertEquals(testObj1, testObj2);
    }

    public void testArmyTemplateDoesNotRetainUnitSpecificStats() {
        RangedUnit test1 = new RangedUnit("Test1", 100);
        CavalryUnit test2 = new CavalryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        // Changing unit specific stats
        test2.attack(test1);
        Army army = new Army("Test", testList);

        Army armyCopy = Army.parseArmyTemplate("Test", army.getArmyTemplate());
        assertFalse(army.equals(armyCopy));
    }
}