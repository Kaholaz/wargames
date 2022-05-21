package org.ntnu.vsbugge.wargames.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * A class that keeps track of the different settings.
 *
 * @author vsbugge
 */
public class Settings {
    private static final String CONFIG_PATH = "config.json";
    private RenderFrequencyEnum renderFrequency = RenderFrequencyEnum.MEDIUM;
    private SimulationSpeedEnum simulationSpeed = SimulationSpeedEnum.FAST;
    private boolean editArmiesTutorial = true;

    /**
     * Reads the config from disk.
     *
     * @throws java.io.IOException
     *             Throws an exception if the config-file could not be read.
     */
    public static Settings readConfig() throws IOException {
        File config = new File(CONFIG_PATH);

        if (!config.exists()) {
            Settings settings = new Settings(); // Default settings
            settings.saveConfig();
            return settings;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(config))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Settings.class);
        } catch (IOException e) {
            throw new IOException("Could not read config-file (" + e.getMessage() + ")");
        }
    }

    /**
     * Saves the config to file.
     *
     * @throws java.io.IOException
     *             Throws an exception if the config could not be saved.
     */
    public boolean saveConfig() throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);

        File config = new File(CONFIG_PATH);
        if (!config.exists()) {
            if (!config.createNewFile()) {
                throw new IOException("Could not save config to file. (You do not have write permissions)");
            }
        }

        try (Writer writer = new FileWriter(config)) {
            writer.write(jsonString);
            return true;
        } catch (IOException e) {
            throw new IOException("Could not save config to file. (" + e.getMessage() + ")");
        }
    }

    /**
     * Getter for the renderFrequency field.
     *
     * @return The value of the renderFrequency field..
     */
    public RenderFrequencyEnum getRenderFrequency() {
        return renderFrequency;
    }

    /**
     * Sets teh render frequency.
     *
     * @param renderFrequency
     *            The new render frequency.
     */
    public void setRenderFrequency(RenderFrequencyEnum renderFrequency) {
        this.renderFrequency = renderFrequency;
    }

    /**
     * Getter for the simulationSpeed field.
     *
     * @return The value of the renderFrequency field.
     */
    public SimulationSpeedEnum getSimulationSpeed() {
        return simulationSpeed;
    }

    /**
     * Setter for the simulationSpeed field.
     *
     * @param simulationSpeed
     *            The value of the simulationSpeed field.
     */
    public void setSimulationSpeed(SimulationSpeedEnum simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }

    /**
     * Getter for the editArmiesTutorial field.
     *
     * @return The value of the editArmiesTutorial field.
     */
    public boolean isEditArmiesTutorial() {
        return editArmiesTutorial;
    }

    /**
     * Setter for the editArmiesTutorial field.
     *
     * @param editArmiesTutorial
     *            The new value for the editArmiesTutorial field.
     */
    public void setEditArmiesTutorial(boolean editArmiesTutorial) {
        this.editArmiesTutorial = editArmiesTutorial;
    }
}