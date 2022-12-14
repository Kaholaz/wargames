package org.ntnu.vsbugge.wargames.models.units;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.models.units.InfantryUnit;
import org.ntnu.vsbugge.wargames.models.units.RangedUnit;
import org.ntnu.vsbugge.wargames.models.units.Unit;

import java.util.*;

public class UnitTest extends TestCase {

    public void testCompareToNUnitSpecificStats() {
        RangedUnit ranged1 = new RangedUnit("Test1", 20, 10, 10);
        RangedUnit ranged2 = ranged1.copy();
        ranged1.takeDamage(0); // different unit specific stat
        InfantryUnit infantryUnit1 = new InfantryUnit("Test2", 20, 10, 10);
        InfantryUnit infantryUnit2 = new InfantryUnit("Test1", 20, 11, 10);
        InfantryUnit infantryUnit3 = new InfantryUnit("Test1", 20, 10, 12);
        InfantryUnit infantryUnit4 = new InfantryUnit("Test1", 20, 10, 10);
        InfantryUnit infantryUnit5 = new InfantryUnit("Test1", 15, 10, 10);
        InfantryUnit infantryUnit6 = new InfantryUnit("Test1", 100, 10, 10);

        List<Unit> list = new LinkedList<>(Arrays.asList(ranged2, ranged1, infantryUnit1, infantryUnit2, infantryUnit3,
                infantryUnit4, infantryUnit5, infantryUnit5, infantryUnit6));

        // list is shuffled to make initial order of equal items (where compareTo returns 0) non-deterministic
        Collections.shuffle(list);

        Collections.sort(list);
        assertSame(infantryUnit5, list.get(0)); // First based on Name
        assertSame(infantryUnit5, list.get(1)); // then attack
        assertSame(infantryUnit4, list.get(2)); // then armor
        assertSame(infantryUnit6, list.get(3)); // lastly health
        assertSame(infantryUnit3, list.get(4));
        assertSame(infantryUnit2, list.get(5));
        assertSame(infantryUnit1, list.get(6));
        assertSame(ranged1, list.get(7));
        assertSame(ranged2, list.get(8));
    }

    public void testEqualsReturnsTrueWhenSame() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        InfantryUnit test = base.copy();

        assertEquals(base, test);
    }

    public void testEqualsReturnsFalseWhenDifferentName() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        InfantryUnit test = new InfantryUnit("Jim", 1, 2, 3);

        assertFalse(base.equals(test));
    }

    public void testEqualsReturnsFalseWhenDifferentHealth() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        InfantryUnit test = new InfantryUnit("Test", 4, 2, 3);

        assertFalse(base.equals(test));
    }

    public void testEqualsReturnsFalseWhenDifferentAttack() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        InfantryUnit test = new InfantryUnit("Test", 1, 4, 3);

        assertFalse(base.equals(test));
    }

    public void testEqualsReturnsFalseWhenDifferentArmor() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        InfantryUnit test = new InfantryUnit("Test", 1, 2, 4);

        assertFalse(base.equals(test));
    }

    public void testEqualsReturnsFalseWhenDifferentSubClass() {
        Unit base = new InfantryUnit("Test", 1, 2, 3);
        Unit test = new RangedUnit("Test", 1, 2, 3);

        assertFalse(base.equals(test));
    }

    public void testHashCodeReturnsSameHashWhenSame() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        InfantryUnit test = base.copy();

        assertEquals(base.hashCode(), test.hashCode());
    }

    public void testHashCodeReturnsDifferentHashWhenDifferentName() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        InfantryUnit test = new InfantryUnit("Jim", 1, 2, 3);

        assertFalse(base.hashCode() == test.hashCode());
    }

    public void testHashCodeReturnsDifferentHashWhenDifferentHealth() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        InfantryUnit test = new InfantryUnit("Test", 4, 2, 3);

        assertFalse(base.hashCode() == test.hashCode());
    }

    public void testHashCodeReturnsDifferentHashWhenDifferentAttack() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        InfantryUnit test = new InfantryUnit("Test", 1, 4, 3);

        assertFalse(base.hashCode() == test.hashCode());
    }

    public void testHashCodeReturnsDifferentHashWhenDifferentArmor() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        InfantryUnit test = new InfantryUnit("Test", 1, 2, 4);

        assertFalse(base.hashCode() == test.hashCode());
    }

    public void testHashCodeReturnsDifferentHashWhenDifferentSubClass() {
        InfantryUnit base = new InfantryUnit("Test", 1, 2, 3);
        RangedUnit test = new RangedUnit("Test", 1, 2, 3);

        assertFalse(base.hashCode() == test.hashCode());
    }

    public void testInitializeWithNegativeHealth() {
        try {
            Unit unit = new Unit("Test", -5, 1, 2) {
                public Unit copy() {
                    return null;
                }

                public void resetStats() {
                }

                public int getAttackBonus() {
                    return 0;
                }

                public int getResistBonus() {
                    return 0;
                }
            };
            fail("Negative health not allowed");
        } catch (IllegalArgumentException e) {
            assertEquals("Health cannot be negative!", e.getMessage());
        } catch (Exception e) {
            fail("Should throw an IllegalArgumentException");
        }
    }

    public void testInitializeWithNegativeAttack() {
        try {
            Unit unit = new Unit("Test", 5, -13, 2) {
                public Unit copy() {
                    return null;
                }

                public void resetStats() {
                }

                public int getAttackBonus() {
                    return 0;
                }

                public int getResistBonus() {
                    return 0;
                }
            };
            fail("Negative health not allowed");
        } catch (IllegalArgumentException e) {
            assertEquals("Attack cannot be negative!", e.getMessage());
        } catch (Exception e) {
            fail("Should throw an IllegalArgumentException");
        }
    }

    public void testInitializeWithNegativeArmor() {
        try {
            Unit unit = new Unit("Test", 23, 1, -69) {
                public Unit copy() {
                    return null;
                }

                public void resetStats() {
                }

                public int getAttackBonus() {
                    return 0;
                }

                public int getResistBonus() {
                    return 0;
                }
            };
            fail("Negative health not allowed");
        } catch (IllegalArgumentException e) {
            assertEquals("Armor cannot be negative!", e.getMessage());
        } catch (Exception e) {
            fail("Should throw an IllegalArgumentException");
        }
    }

    public void testTakeDamageWhenDamageIsGreaterThanHealth() {
        Unit unit = new Unit("Test", 10, 15, 20) {
            public Unit copy() {
                return null;
            }

            public void resetStats() {
            }

            public int getAttackBonus() {
                return 0;
            }

            public int getResistBonus() {
                return 0;
            }
        };
        unit.takeDamage(100);

        assertEquals(0, unit.getHealth());
    }
}