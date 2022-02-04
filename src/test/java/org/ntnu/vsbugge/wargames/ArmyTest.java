package org.ntnu.vsbugge.wargames;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.units.InfantryUnit;
import org.ntnu.vsbugge.wargames.units.RangedUnit;
import org.ntnu.vsbugge.wargames.units.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArmyTest extends TestCase {

    public void testAdd() {
        List<Unit> testList = new ArrayList<Unit>();
        Army testObj = new Army("TestObj");

        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);

        assertEquals(testObj.getAllUnits(), testList);

        testList.add(test1);
        testObj.add(test1);
        assertEquals(testObj.getAllUnits(), testList);

        testList.add(test2);
        testObj.add(test2);
        assertEquals(testObj.getAllUnits(), testList);

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
        assertEquals(testObj.hasUnits(), false);

        testObj.addAll(testList);
        assertEquals(testObj.hasUnits(), true);

        testObj.remove(test1);
        assertEquals(testObj.hasUnits(), true);

        testObj.remove(test2);
        assertEquals(testObj.hasUnits(), false);
    }

    public void testGetRandomUnit() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        Army testObj = new Army("TestObj");

        try {
            Unit unit = testObj.getRandomUnit();
            fail("IllegalStateException not thrown");
        }
        catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "The army has no units");
        }
        catch (Exception e) {
            fail("IllegalStateException not thrown");
        }

        testObj.add(test1);
        assertSame(testObj.getRandomUnit(), test1);
    }

    public void testGetName() {
        Army testObj = new Army("TestObj");
        assertEquals(testObj.getName(), "TestObj");
    }

    public void testGetAllUnits() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        Army testObj = new Army("TestObj", testList);
        assertEquals(testObj.getAllUnits(), testList);
    }

    public void testToString() {
        Army testObj = new Army("TestObj");
        assertEquals(testObj.toString(), "Army{name='TestObj'}");
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

    public void testHashCode() {
        RangedUnit test1 = new RangedUnit("Test1", 10);
        InfantryUnit test2 = new InfantryUnit("Test2", 20);
        List<Unit> testList = List.of(test1, test2);

        Army testObj1 = new Army("TestObj");
        Army testObj2 = new Army("TestObj");

        assertEquals(testObj1.hashCode(), testObj2.hashCode());

        testObj1.addAll(testList);
        testObj2.addAll(testList);
        assertEquals(testObj1.hashCode(), testObj2.hashCode());

        testObj1.remove(test1);
        assertTrue(testObj1.hashCode() != testObj2.hashCode());
    }
}