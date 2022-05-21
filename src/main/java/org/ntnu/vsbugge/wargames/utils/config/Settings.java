package org.ntnu.vsbugge.wargames.utils.config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Settings {
    private static final String CONFIG_PATH = "config.json";
    private RenderFrequencyEnum renderFrequency = RenderFrequencyEnum.MEDIUM;
    private SimulationSpeedEnum simulationSpeed = SimulationSpeedEnum.FAST;
    private boolean editArmiesTutorial = true;

    public static Settings readConfig() throws IOException{
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

    public RenderFrequencyEnum getRenderFrequency() {
        return renderFrequency;
    }

    public void setRenderFrequency(RenderFrequencyEnum renderFrequency) {
        this.renderFrequency = renderFrequency;
    }

    public SimulationSpeedEnum getSimulationSpeed() {
        return simulationSpeed;
    }

    public void setSimulationSpeed(SimulationSpeedEnum simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }

    public boolean isEditArmiesTutorial() {
        return editArmiesTutorial;
    }

    public void setEditArmiesTutorial(boolean editArmiesTutorial) {
        this.editArmiesTutorial = editArmiesTutorial;
    }
}
