package org.ntnu.vsbugge.wargames;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.units.InfantryUnit;
import org.ntnu.vsbugge.wargames.units.RangedUnit;
import org.ntnu.vsbugge.wargames.units.Unit;

import java.util.*;

public class ArmyTest extends TestCase {

    public void testAdd() {
        List<Unit> testList = new ArrayList<>();
        Army testObj = new Army("TestObj");

        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);

        assertEquals(testList, testObj.getAllUnits());

        testList.add(test1);
        testObj.add(test1);
        assertEquals(testList, testObj.getAllUnits());

        testList.add(test2);
        testObj.add(test2);
        assertEquals(testList, testObj.getAllUnits());
    }

    public void testAddMultiple() {
        Army testObj = new Army("TestObj");

        RangedUnit test = new RangedUnit("Test", 10);
        testObj.add(test, 5);
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
        assertEquals(testObj.getAllUnits(), testList);

        testObj.addAll(testList);
        testList.addAll(testList);
        assertEquals(testObj.getAllUnits(), testList);
    }

    public void testRemove() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = new LinkedList<>(Arrays.asList(test1, test2));

        Army testObj = new Army("TestObj", testList);

        testObj.remove(test1);
        testList.remove(test1);
        assertEquals(testObj.getAllUnits(), testList);

        testObj.remove(test2);
        assertFalse(testObj.hasUnits());
    }

    public void testHasUnits() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        Army testObj = new Army("TestObj");
        assertFalse(testObj.hasUnits());

        testObj.addAll(testList);
        assertTrue(testObj.hasUnits());

        testObj.remove(test1);
        assertTrue(testObj.hasUnits());

        testObj.remove(test2);
        assertFalse(testObj.hasUnits());
    }

    public void testGetRandomUnit() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        Army testObj = new Army("TestObj");

        try {
            testObj.getRandomUnit();
            fail("IllegalStateException not thrown");
        }
        catch (IllegalStateException e) {
            assertEquals("The army has no units", e.getMessage());
        }
        catch (Exception e) {
            fail("IllegalStateException not thrown");
        }

        testObj.add(test1);
        assertEquals(test1, testObj.getRandomUnit());
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

    public void testEquals() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        Army testObj1 = new Army("TestObj");
        Army testObj2 = new Army("TestObj");

        assertTrue(testObj1.equals(testObj2));

        testObj1.addAll(testList);
        testObj2.addAll(testList);
        assertTrue(testObj1.equals(testObj2));

        testObj1.remove(test1);
        assertFalse(testObj1.equals(testObj2));
    }

    public void testEqualsDoesNotAlterUnitOrder() {
        RangedUnit ranged1 = new RangedUnit("Test1", 20,10,10);
        InfantryUnit infantryUnit1 = new InfantryUnit("Test2", 20, 10, 10);
        InfantryUnit infantryUnit2 = new InfantryUnit("Test1", 20, 11, 10);
        InfantryUnit infantryUnit3 = new InfantryUnit("Test1", 20, 10, 12);
        InfantryUnit infantryUnit4 = new InfantryUnit("Test1", 20, 10, 10);
        InfantryUnit infantryUnit5 = new InfantryUnit("Test1", 15, 10, 10);
        InfantryUnit infantryUnit6 = new InfantryUnit("Test1", 100, 10, 10);

        List<Unit> testList = new LinkedList<>(Arrays.asList(
                ranged1,
                infantryUnit1,
                infantryUnit2,
                infantryUnit3,
                infantryUnit4,
                infantryUnit5,
                infantryUnit5,
                infantryUnit6
        ));

        Army testArmy1 = new Army("Test", testList);
        Army testArmy2 = new Army("Test", testList);
        assertTrue(testArmy1.getAllUnits().equals(testArmy2.getAllUnits())); // same order, same units (List.equals) [true]

        assertTrue(testArmy1.equals(testArmy2)); // same order, same units (Army.equals) [true]
        assertEquals(testList, testArmy1.getAllUnits()); // same order, same units (List.equals) [true]
        assertEquals(testList, testArmy2.getAllUnits()); // same order, same units (List.equals) [true]

        testArmy2 = new Army("Test", testList.stream().sorted().toList());
        assertFalse(testList.equals(testArmy2.getAllUnits())); // different order, same units (List.equals) [false]
        assertTrue(testArmy1.equals(testArmy2)); // different order, same units (Army.equals) [true]
        assertTrue(testArmy1.getAllUnits().equals(testList)); // same order, same units (List.equals) [true]
        assertFalse(testArmy2.getAllUnits().equals(testList)); // different order, same units (List.equals) [false]
    }

    public void testHashCode() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        Army testObj1 = new Army("TestObj");
        Army testObj2 = new Army("TestObj");

        assertTrue(testObj1.hashCode() == testObj2.hashCode());

        testObj1.addAll(testList);
        testObj2.addAll(testList);
        assertTrue(testObj1.hashCode() == testObj2.hashCode());

        testObj1.remove(test1);
        assertTrue(testObj1.hashCode() != testObj2.hashCode());
    }

    public void testArmyTemplate() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        Army testObj1 = new Army("Test", testList);
        Map<Unit, Integer> template = testObj1.getArmyTemplate();
        Army testObj2 = Army.parseArmyTemplate("Test", template);

        assertTrue(testObj1.equals(testObj2));

        testObj1.remove(test1);
        assertFalse(testObj1.equals(testObj2));
    }
}