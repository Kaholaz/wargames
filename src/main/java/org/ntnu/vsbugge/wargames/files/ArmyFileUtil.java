package org.ntnu.vsbugge.wargames.files;

import org.ntnu.vsbugge.wargames.Army;
import org.ntnu.vsbugge.wargames.factories.UnitFactory;
import org.ntnu.vsbugge.wargames.units.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;

import java.util.*;

/**
 * A resource used to write and read armies from file
 */
public class ArmyFileUtil {
    private final Charset CHARSET = StandardCharsets.UTF_8;
    private File defaultPath = null;

    private int lineNr;

    /**
     * Create a new instance of ArmyFileManager
     */
    public ArmyFileUtil() {
    }

    /**
     * Private method for parsing a single line. This method does not parse the first line (the name of the army), but
     * all subsequent lines.
     *
     * @param line
     *            A single line in the army file
     *
     * @return A processed version of the line that consists of a unit and the number of that unit in the army.
     *
     * @throws FileFormatException
     *             Throws an exception if there is something wrong with the format of the file, or if the unit type is
     *             not recognized, or if an integer field could not be parsed.
     */
    private AbstractMap.SimpleEntry<Unit, Integer> parseLine(String line) throws FileFormatException {
        String[] s_fields = line.split(",");
        try {
            // Extract fields from line (could throw IndexOutOfBoundsException)
            String unitName = s_fields[0];
            String name = s_fields[1];
            // parseInt throws NumberFormatException
            int health = Integer.parseInt(s_fields[2]);
            Integer count = Integer.parseInt(s_fields[3]);

            // Throws IllegalArgumentException ----------V
            return new AbstractMap.SimpleEntry<>(UnitFactory.getUnit(unitName, name, health), count);
        } catch (IndexOutOfBoundsException e) {
            throw new FileFormatException(String.format("Too few fields on line %d", lineNr));
        } catch (NumberFormatException e) {
            throw new FileFormatException(String.format("Could not parse integer field on line %d", lineNr));
        } catch (IllegalArgumentException e) {
            throw new FileFormatException(
                    String.format("One or more fields on line %d are invalid: '%s'", lineNr, e.getMessage()));
        }
    }

    /**
     * Returns a representation of a unit ready to write to file.<br>
     * The unit is formatted like this '{UnitClass},{UnitName},{Health}', where UnitClass is the type of unit (e.g.
     * 'InfantryUnit')
     *
     * @param unit
     *            The unit to write to file
     *
     * @return A string representation of the unit that fits the CSV file spec
     */
    private String convertUnitToWritableString(Unit unit) {
        String unitType = unit.getClass().getSimpleName();
        String name = unit.getName();
        int health = unit.getHealth();

        return String.format("%s,%s,%d", unitType, name, health);
    }

    /**
     * Reads a file at a given filePath and returns an Army object based on the file. The file needs to be formatted in
     * the following manner:<br>
     * The first line of the file is the literal name of the army.<br>
     * Each subsequent lines have 4 fields separated by comma:
     * <ol>
     * <li>The class of the unit</li>
     * <li>The name of the unit</li>
     * <li>The health of the unit</li>
     * <li>The amount of this unit in the army</li>
     * </ol>
     * This should look something like this: 'CavalryUnit,Horsey,100,150'
     *
     * @param filePath
     *            The filePath of the file
     * @param relativeToDefaultPath
     *            If this flag is set to true, filePath is read as a sub-path of defaultPath. In other words: If default
     *            filePath is set to "some/directory" and the filePath supplied is "some/file". Then, if
     *            relativeToDefaultPath is set to true, the file that is read is the file at the filePath
     *            "some/directory/some/file".
     *
     * @return An army constructed form an army file
     *
     * @throws IOException
     *             Throws FileNotFoundException if the file is not found.<br>
     *             Throws FileFormatException if there is anything wrong with the format of the file.<br>
     * @throws NullPointerException
     *             Throws an exception if relativeToDefaultPath is set to true, and defaultPath is not set.
     */
    public Army loadFromPath(File filePath, boolean relativeToDefaultPath) throws IOException, NullPointerException {
        if (!relativeToDefaultPath)
            return loadFromPath(filePath);
        if (defaultPath == null)
            throw new NullPointerException(
                    "Default path needs to be set using the setDefaultPath method to use default path");
        return loadFromPath(new File(defaultPath, filePath.toString()));
    }

    /**
     * Reads a file at a given filePath and returns an Army object based on the file. The file needs to be formatted in
     * the following manner:<br>
     * The first line of the file is the literal name of the army.<br>
     * Each subsequent lines have 4 fields separated by comma:
     * <ol>
     * <li>The class of the unit</li>
     * <li>The name of the unit</li>
     * <li>The health of the unit</li>
     * <li>The amount of this unit in the army</li>
     * </ol>
     * This should look something like this: 'CavalryUnit,Horsey,100,150'
     *
     * @param filePath
     *            The filePath of the file
     *
     * @return An army constructed form an army file
     *
     * @throws IOException
     *             Throws FileNotFoundException if the file is not found.<br>
     *             Throws FileFormatException if there is anything wrong with the format of the file.
     */
    public Army loadFromPath(File filePath) throws IOException {
        // Open scanner
        Scanner sc;
        try {
            sc = new Scanner(filePath, CHARSET); // Throws FileNotFoundException
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(String.format("File does not exist: '%s'", filePath.getAbsoluteFile()));
        }

        // Get the name of the army
        lineNr = 1;
        String armyName;
        try {
            armyName = sc.nextLine();
        } catch (NoSuchElementException e) {
            sc.close();
            throw new FileFormatException("The supplied file is empty");
        }

        // Parse the file line by line. ArmyTemplate uses the same format as stipulated by Army::parseArmyTemplate
        // i.e. the key is a unit in an army, and the value is the number of that unit in that army.
        HashMap<Unit, Integer> armyTemplate = new HashMap<>();
        try {
            while (sc.hasNextLine()) {
                ++lineNr; // lineNr is incremented before parseLine to avoid having to increment lineNr outside the loop
                Map.Entry<Unit, Integer> entry = parseLine(sc.nextLine()); // throws FileFormatException

                // Sums the current count (the one currently in the template) with the count from the parsed line
                armyTemplate.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        } finally {
            sc.close();
        }
        return Army.parseArmyTemplate(armyName, armyTemplate);
    }

    /**
     * Checks if a file at the path is a readable file. This is currently not an efficient method, as this method just
     * calls loadFromPath and checks if the method completed without exceptions. If this check is done before a call to
     * loadFromPath, one should probably just call that method and surround it with a try/catch (which to my knowledge
     * should be best practice in the regardless).
     *
     * @param filePath
     *            The path of the file
     * @param relativeToDefaultPath
     *            If this flag is set to true, the path is read as a sub-path to defaultPath, similarly to how it is
     *            done in loadFromPath(File filePath, boolean relativeToDefaultPath)
     *
     * @return ture if the fila at the path is valid, false if not.
     */
    public boolean isFileAtPathValid(File filePath, boolean relativeToDefaultPath) {
        // TODO: Find an efficient way to verify a file
        try {
            loadFromPath(filePath, relativeToDefaultPath);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Saves an army as a CSV file at a given path. The file is written this way:<br>
     * The first line of the file is the literal name of the army.<br>
     * Each subsequent lines have 4 fields separated by comma:
     * <ol>
     * <li>The class of the unit</li>
     * <li>The name of the unit</li>
     * <li>The health of the unit</li>
     * <li>The amount of this unit in the army</li>
     * </ol>
     * This should look something like this: 'CavalryUnit,Horsey,100,150'
     *
     * @param army
     *            The army to save to file
     * @param filePath
     *            The path to save to
     * @param relativeToDefaultPath
     *            Whenever or not to read the supplied path as being relative to defaultPath. If this is set to false,
     *            the path provided will be appended onto the path defaultPath
     * @param overwrite
     *            Whenever or not to overwrite any file located at the given path
     *
     * @throws IOException
     *             Throws a FileAlreadyExistsException if the file already exists and overwrite is set to false Throws a
     *             general IOEException if something unexpected happened while creating the file
     */
    public void saveArmyToPath(Army army, File filePath, boolean overwrite, boolean relativeToDefaultPath)
            throws IOException {
        if (relativeToDefaultPath)
            filePath = new File(defaultPath, filePath.toString());
        saveArmyToPath(army, filePath, overwrite);
    }

    /**
     * Saves an army as a CSV file at a given path. The file is written this way:<br>
     * The first line of the file is the literal name of the army.<br>
     * Each subsequent lines have 4 fields separated by comma:
     * <ol>
     * <li>The class of the unit</li>
     * <li>The name of the unit</li>
     * <li>The health of the unit</li>
     * <li>The amount of this unit in the army</li>
     * </ol>
     * This should look something like this: 'CavalryUnit,Horsey,100,150'
     *
     * @param army
     *            The army to save to file
     * @param filePath
     *            The path to save to
     * @param overwrite
     *            Whenever or not to overwrite any file located at the given path
     *
     * @throws IOException
     *             Throws a FileAlreadyExistsException if the file already exists and overwrite is set to false Throws a
     *             general IOEException if something unexpected happened while creating the file
     */
    public void saveArmyToPath(Army army, File filePath, boolean overwrite) throws IOException {
        Map<Unit, Integer> armyTemplate = army.getArmyTemplate();

        // File already exist and overwrite is set to false
        if (!filePath.createNewFile() && !overwrite) { // file.createNewFile() throws IOException
            throw new FileAlreadyExistsException("File already exists. Writing to file would delete it's contents");
        }

        FileWriter writer = new FileWriter(filePath, CHARSET);

        // .write erases all contents of the file and writes the given string to the file
        // making the supplied string its only content.
        writer.write(army.getName() + '\n');
        for (Map.Entry<Unit, Integer> entry : armyTemplate.entrySet()) {

            // Each line is formatted like this: "{UnitClass},{UnitName},{UnitHealth},{Count}",
            // where UnitClass is the Unit subclass of the unit,
            // and Count is the number of this unit in the army.
            writer.append(convertUnitToWritableString(entry.getKey())).append(",").append(entry.getValue().toString())
                    .append("\n");
        }
        writer.close();
    }

    /**
     * Sets a new default path. The default path is the path from witch all paths are interpreted as relative to if the
     * flag {@code relativeToDefaultPath} is set to {@code true}
     *
     * @param defaultPath
     *            The new default path
     */
    public void setDefaultPath(File defaultPath) {
        this.defaultPath = defaultPath;
    }

    /**
     * Gets the current default path. The default path is the path from witch all paths are interpreted as relative to
     * if the flag {@code relativeToDefaultPath} is set to {@code true}
     *
     * @return The current default path
     */
    public File getDefaultPath() {
        return defaultPath;
    }
}
