package org.ntnu.vsbugge.wargames.utils.files;

import junit.framework.TestCase;
import org.ntnu.vsbugge.wargames.models.army.Army;
import org.ntnu.vsbugge.wargames.models.units.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmyFileUtilTest extends TestCase {
    static private final ArmyFileUtil ARMY_FILE_UTIL = new ArmyFileUtil();
    static {
        ARMY_FILE_UTIL.setDefaultPath(new File("src/main/resources/testFiles"));
    }

    // Human Army
    static private final Map<Unit, Integer> humanArmyTemplate = new HashMap<>();
    static {
        humanArmyTemplate.put(new InfantryUnit("Footman", 100), 500);
        humanArmyTemplate.put(new CavalryUnit("Knight", 100), 100);
        humanArmyTemplate.put(new RangedUnit("Archer", 100), 200);
        humanArmyTemplate.put(new CommanderUnit("Mountain King", 180), 1);
    }

    // Orcish Horde
    static private final Map<Unit, Integer> orchishHordeTemplate = new HashMap<>();
    static {
        orchishHordeTemplate.put(new InfantryUnit("Grunt", 100), 500);
        orchishHordeTemplate.put(new CavalryUnit("Raider", 100), 100);
        orchishHordeTemplate.put(new RangedUnit("Spearman", 100), 200);
        orchishHordeTemplate.put(new CommanderUnit("Gul ́dan", 180), 1);
    }

    public void testLoadFromPath() throws IOException {
        Army fileHumanArmy = ARMY_FILE_UTIL.loadFromPath(new File("src/main/resources/testFiles/HumanArmy.army"));
        Army templateHumanArmy = Army.parseArmyTemplate("Human Army", humanArmyTemplate);

        assertEquals(templateHumanArmy, fileHumanArmy);
    }

    public void testGetDefaultPath() {
        File file = new File("src/main/resources/testFiles");

        assertEquals(file, ARMY_FILE_UTIL.getDefaultPath());
    }

    public void testLoadFromPathUsingDefaultPath() throws IOException {
        Army fileHumanArmy = ARMY_FILE_UTIL.loadFromPath(new File("HumanArmy.army"), true);
        Army templateHumanArmy = Army.parseArmyTemplate("Human Army", humanArmyTemplate);

        assertEquals(templateHumanArmy, fileHumanArmy);
    }

    public void testLoadFromPathThrowsExceptionIfDefaultPathIsNotSet() {
        ArmyFileUtil armyFileUtil = new ArmyFileUtil();
        armyFileUtil.setDefaultPath(null);

        try {
            armyFileUtil.loadFromPath(new File("HumanArmy.army"), true);
            fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            assertEquals("Default path needs to be set using the setDefaultPath method to use default path",
                    e.getMessage());
        } catch (Exception e) {
            fail("NullPointerException should have been thrown");
        }
    }

    public void testLoadFromPathNonAsciiChars() throws IOException {
        Army fileOnlyGuldan = ARMY_FILE_UTIL.loadFromPath(new File("NonAscii.army"), true);
        Army templateOrchishHorde = Army.parseArmyTemplate("Orcish Horde", orchishHordeTemplate);

        assertEquals(1, fileOnlyGuldan.getUnitsOfType(CommanderUnit.class).size());
        assertEquals(templateOrchishHorde.getUnitsOfType(CommanderUnit.class),
                fileOnlyGuldan.getUnitsOfType(CommanderUnit.class));
    }

    public void testLoadFromPathOrcishArmy() throws IOException {
        Army fileOrcishHorde = ARMY_FILE_UTIL.loadFromPath(new File("OrcishHorde.army"), true);
        Army templateOrchishHorde = Army.parseArmyTemplate("Orcish Horde", orchishHordeTemplate);

        assertEquals(templateOrchishHorde, fileOrcishHorde);
    }

    public void testLoadFromPathThrowsFileNotFoundException() {
        try {
            ARMY_FILE_UTIL.loadFromPath(new File("FileDoesNotExist.army"), true);
            fail("Should throw FileNotFoundException");
        } catch (FileNotFoundException e) {
            assertTrue(true); // pass
        } catch (Exception e) {
            fail("Should throw FileNotFoundException");
        }
    }

    public void testLoadFromPathThrowsFileFormatExceptionIfUnrecognizedUnitType() {
        try {
            ARMY_FILE_UTIL.loadFromPath(new File("UnrecognizedUnitType.army"), true);
            fail("Should throw FileFormatException");
        } catch (FileFormatException e) {
            assertEquals("One or more fields on line 2 are invalid: 'Unit type 'NotAUnitType' not found.'",
                    e.getMessage());
        } catch (IOException e) {
            fail("Should throw FileFormatException");
        }
    }

    public void testLoadFromPathThrowsFileFormatExceptionIfTooFewFields() {
        try {
            ARMY_FILE_UTIL.loadFromPath(new File("TooFewFields.army"), true);
            fail("Should throw FileFormatException");
        } catch (FileFormatException e) {
            assertEquals("Too few fields on line 2", e.getMessage());
        } catch (IOException e) {
            fail("Should throw FileFormatException");
        }
    }

    public void testLoadFromPathThrowsNumberFormatExceptionIfFieldIsNotInteger() {
        try {
            ARMY_FILE_UTIL.loadFromPath(new File("NonNumberFields.army"), true);
            fail("Should throw FileFormatException");
        } catch (FileFormatException e) {
            assertEquals("Could not parse integer field on line 3", e.getMessage());
        } catch (IOException e) {
            fail("Should throw FileFormatException");
        }
    }

    public void testLoadFromPathThrowsFileFormatExceptionIfFileIsEmpty() {
        try {
            ARMY_FILE_UTIL.loadFromPath(new File("EmptyFile.army"), true);
            fail("The method should throw an exception when trying to read empty file");
        } catch (FileFormatException e) {
            assertEquals("The supplied file is empty", e.getMessage());
        } catch (IOException e) {
            fail("The method should complain about the file being empty");
        }
    }

    public void testLoadFromPathLoadsEmptyArmy() throws IOException {
        Army fileArmy = ARMY_FILE_UTIL.loadFromPath(new File("EmptyArmy.army"), true);
        Army army = new Army("Empty Army");

        assertEquals(army, fileArmy);
    }

    public void testLoadFromPathAddsUnitCounts() throws IOException {
        Army army = new Army("Sum Army");
        army.add(new RangedUnit("Range", 20), 20);

        Army fileArmy = ARMY_FILE_UTIL.loadFromPath(new File("SumUnitCount.army"), true);

        assertEquals(army, fileArmy);
    }

    public void testIsValidFilePath() {
        assertTrue(ARMY_FILE_UTIL.isFileAtPathValid(new File("HumanArmy.army"), true));
        assertTrue(ARMY_FILE_UTIL.isFileAtPathValid(new File("OrcishHorde.army"), true));
        assertTrue(ARMY_FILE_UTIL.isFileAtPathValid(new File("EmptyArmy.army"), true));
        assertFalse(ARMY_FILE_UTIL.isFileAtPathValid(new File("FileDoesNotExist.army"), true));
        assertFalse(ARMY_FILE_UTIL.isFileAtPathValid(new File("UnrecognizedUnitType.army"), true));
        assertFalse(ARMY_FILE_UTIL.isFileAtPathValid(new File("TooFewFields.army"), true));
        assertFalse(ARMY_FILE_UTIL.isFileAtPathValid(new File("NonNumberFields.army"), true));
        assertFalse(ARMY_FILE_UTIL.isFileAtPathValid(new File("EmptyFile.army"), true));
    }

    public void testIsValidFilePathBlankStringFields() {
        assertTrue(ARMY_FILE_UTIL.isFileAtPathValid(new File("BlankFields.army"), true));
    }

    public void testIsValidFilePathSpacesInFileName() {
        assertTrue(ARMY_FILE_UTIL.isFileAtPathValid(new File("Space In File.army"), true));
    }

    public void testSaveArmyToPath() throws IOException {
        File filePath = new File("src/main/resources/testFiles/SaveFile.army");
        Army army = Army.parseArmyTemplate("Human Army", humanArmyTemplate);
        ARMY_FILE_UTIL.saveArmyToPath(army, filePath, true);

        assertEquals(army, ARMY_FILE_UTIL.loadFromPath(filePath));
    }

    public void testSaveArmyToPathRelativeToDefaultPath() throws IOException {
        File filePath = new File("SaveFile.army");
        Army army = Army.parseArmyTemplate("Human Army", humanArmyTemplate);
        ARMY_FILE_UTIL.saveArmyToPath(army, filePath, true, true);

        assertEquals(army, ARMY_FILE_UTIL.loadFromPath(filePath, true));
    }

    public void testSaveArmyToPathOrcishHorde() throws IOException {
        File filePath = new File("SaveFile.army");
        Army army = Army.parseArmyTemplate("Orcish Horde", orchishHordeTemplate);
        ARMY_FILE_UTIL.saveArmyToPath(army, filePath, true, true);

        assertEquals(army, ARMY_FILE_UTIL.loadFromPath(filePath, true));
    }

    public void testSaveArmyToPathThrowsFileAlreadyExistsIfOverwriteIsFalse() {
        File filePath = new File("SaveFile.army");
        Army army = Army.parseArmyTemplate("Human Army", humanArmyTemplate);

        try {
            ARMY_FILE_UTIL.saveArmyToPath(army, filePath, false, true);
            fail("Saving to a file that already exists should throw FileAlreadyExistsException");
        } catch (FileAlreadyExistsException e) {
            assertEquals("File already exists. Writing to file would delete it's contents", e.getMessage());
        } catch (IOException e) {
            fail("Saving to a file that already exists should throw FileAlreadyExistsException");
        }
    }

    public void testSaveArmyToPathSaveToPathThatDoesNotExist() throws IOException {
        File filePath = new File("src/main/resources/testFiles/SaveFile.army");
        Army army = Army.parseArmyTemplate("Human Army", humanArmyTemplate);

        filePath.delete();
        ARMY_FILE_UTIL.saveArmyToPath(army, filePath, false);

        assertEquals(army, ARMY_FILE_UTIL.loadFromPath(filePath));
    }

    public void testGetArmiesFromDefaultPath() {
        List<Army> armyList = ARMY_FILE_UTIL.getArmiesFromDefaultPath();

        // There are nine readable army files
        assertEquals(9, armyList.size());
    }

    public void testGetArmiesFromDefaultPathThrowsExceptionIfNoDefaultPathIsSet() {
        ArmyFileUtil armyFileUtil = new ArmyFileUtil();
        armyFileUtil.setDefaultPath(null);

        try {
            armyFileUtil.getArmiesFromDefaultPath();
            fail("getArmiesFromDefaultPath should throw exception.");
        } catch (RuntimeException ignored) {
        } catch (Exception e) {
            fail("getArmiesFromDefaultPath should throw exception.");
        }

    }
}
