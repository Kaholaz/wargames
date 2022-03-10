package org.ntnu.vsbugge.wargames.files;

import org.ntnu.vsbugge.wargames.Army;
import org.ntnu.vsbugge.wargames.units.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A resource used to write and read armies from file
 */
public class ArmyFileManager {
    private final Charset CHARSET = StandardCharsets.UTF_8;
    private File defaultPath = null;

    private int lineNr;

    /**
     * Create a new instance of ArmyFileManager
     */
    public ArmyFileManager(){}

    /**
     * Private method for parsing a single line. This method does not parse the first line (the name of the amry),
     * but all subsequent lines.
     * @param line A single line in the army file
     * @return A processed version of the line that consists of a unit and the number of that unit in the army.
     * @throws FileFormatException Throws an exception if there is something wrong with the format of the line,
     * or the type of unit is not recognized.
     * @throws NumberFormatException Throws an exception if a field could not be parsed as an Integer
     */
    private AbstractMap.SimpleEntry<Unit, Integer> parseLine(String line) throws FileFormatException {
        String[] s_fields = line.split(",");
        try {
            // Extract fields from line
            String unitName = s_fields[0];
            String name = s_fields[1];
            int health = Integer.parseInt(s_fields[2]);
            Integer count = Integer.parseInt(s_fields[3]);

            // Throws FileFormatException --------------V
            return new AbstractMap.SimpleEntry<>(constructUnit(unitName, name, health), count);
        }
        catch (IndexOutOfBoundsException e) {
            throw new FileFormatException(String.format("Too few fields on line %d", lineNr));
        }
        catch (NumberFormatException e) {
            throw new FileFormatException(String.format("Could not parse integer field on line %d", lineNr));
        }
    }

    /**
     * Constructs a new instance of a unit given the type of the unit, and the unit properties
     * @param unitType The type of the unit (e.g. 'CavalryUnit' or 'InfantryUnit')
     * @param name The given name of the unit
     * @param health The health of the unit
     * @return A new Unit instance that reflects the passed parameters
     * @throws FileFormatException Trows an exception if the type of the unit is not recognized
     */
    private Unit constructUnit(String unitType, String name, int health) throws FileFormatException {
        return switch (unitType) {
            case "CavalryUnit" -> new CavalryUnit(name, health);
            case "CommanderUnit" -> new CommanderUnit(name, health);
            case "InfantryUnit" -> new InfantryUnit(name, health);
            case "RangedUnit" -> new RangedUnit(name, health);
            default -> throw new FileFormatException(String.format("Unit type not recognized on line %d", lineNr));
        };
    }

    /**
     * Returns a representation of a unit ready to write to file
     * @param unit The unit to write to file
     * @return A string representation of the unit that fits the CSV file spec
     */
    private String unitToWritableUnitString(Unit unit) {
        String unitType = unit.getClass().getSimpleName();
        String name = unit.getName();
        int health = unit.getHealth();

        return String.format("%s,%s,%d", unitType, name, health);
    }

    /**
     * Reads a file at a given filePath and returns an Army object based on the file.
     * @param filePath The filePath of the file
     * @param relativeToDefaultPath If this flag is set to true, filePath is read as a sub-filePath of defaultPath.
     *                              In other words: If default filePath is set to "some/directory" and the filePath
     *                              supplied is "some/file". Then, if relativeToDefaultPath is set to true,
     *                              the file that is read is the file at the filePath "some/directory/some/file".
     * @return An army constructed form an army file
     * @throws IOException Throws FileNotFoundException if the file is not found.<br>
     *                     Throws FileFormatException if there is anything wrong with the format of the file.<br>
     * @throws NullPointerException Throws an exception if relativeToDefaultPath is set to true, and defaultPath
     *                              is not set.
     */
    public Army loadFromPath(File filePath, boolean relativeToDefaultPath) throws IOException, NullPointerException {
        if (!relativeToDefaultPath) return loadFromPath(filePath);
        if (defaultPath == null) throw new NullPointerException("Default path needs to be set using the setDefaultPath method to use default path");
        return loadFromPath(new File(defaultPath, filePath.toString()));
    }

    /**
     * Reads a file at a given filePath and returns an Army object based on the file.
     * @param filePath The filePath of the file
     * @return An army constructed form an army file
     * @throws IOException Throws FileNotFoundException if the file is not found.<br>
     *                     Throws FileFormatException if there is anything wrong with the format of the file.
     */
    public Army loadFromPath(File filePath) throws IOException {
        Scanner sc;
        try {
            sc = new Scanner(filePath, CHARSET); // Throws FileNotFoundException
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(String.format("%s (No such file)", filePath.getAbsoluteFile()));
        }


        lineNr = 1;
        String armyName =  sc.nextLine();

        HashMap<Unit, Integer> armyTemplate = new HashMap<>();
        try {
            while (sc.hasNextLine()) {
                ++lineNr;
                Map.Entry<Unit, Integer> entry = parseLine(sc.nextLine()); // throws FileFormatException
                armyTemplate.put(entry.getKey(), entry.getValue());
            }
        }
        finally {
            sc.close();
        }
        return Army.parseArmyTemplate(armyName, armyTemplate);
    }

    /**
     * Checks if a file at the path is a readable file
     * @param filePath The path of the file
     * @param relativeToDefaultPath If this flag is set to true, the path is read as a sub-path to defaultPath,
     *                              similarly to how it is done in loadFromPath(File filePath, boolean relativeToDefaultPath)
     * @return ture if the fila at the path is valid, false if not.
     */
    public boolean isFileAtPathValid(File filePath, boolean relativeToDefaultPath) {
        try {
            loadFromPath(filePath, relativeToDefaultPath);
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Saves an army as a CSV file at a given path
     * @param army The army to save to file
     * @param filePath The path to save to
     * @param relativeToDefaultPath Whenever or not to read the supplied path as being relative to defaultPath.
     *                              If this is set to false, the path provided will be appended onto the path defaultPath
     * @param overwrite Whenever or not to overwrite any file located at the given path
     * @throws IOException Throws a FileAlreadyExistsException if the file already exists and overwrite is set to false
     *                     Throws a general IOEException if something unexpected happened while creating the file
     */
    public void saveArmyToPath(Army army, File filePath, boolean overwrite, boolean relativeToDefaultPath) throws IOException {
        if (relativeToDefaultPath) filePath = new File(defaultPath, filePath.toString());
        saveArmyToPath(army, filePath, overwrite);
    }

    /**
     * Saves an army as a CSV file at a given path
     * @param army The army to save to file
     * @param filePath The path to save to
     * @param overwrite Whenever or not to overwrite any file located at the given path
     * @throws IOException Throws a FileAlreadyExistsException if the file already exists and overwrite is set to false
     *                     Throws a general IOEException if something unexpected happened while creating the file
     */
    public void saveArmyToPath(Army army, File filePath, boolean overwrite) throws IOException {
        Map<Unit, Integer> armyTemplate = army.getArmyTemplate();

        if (!filePath.createNewFile() && !overwrite) { // file.createNewFile() throws IOException
            throw new FileAlreadyExistsException("File already exists. Writing to file would delete it's contents");
        }

        FileWriter writer = new FileWriter(filePath, CHARSET);
        writer.write(army.getName() + '\n');
        for (Map.Entry<Unit, Integer> entry : armyTemplate.entrySet()) {
            writer.append(unitToWritableUnitString(entry.getKey()));
            writer.append("," + entry.getValue().toString() + "\n");
        }
        writer.close();
    }

    /**
     * Sets a new default path
     * @param defaultPath The new default path
     */
    public void setDefaultPath(File defaultPath) {
        this.defaultPath = defaultPath;
    }

    /**
     * @return The current default path
     */
    public File getDefaultPath() {
        return defaultPath;
    }
}