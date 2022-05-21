package org.ntnu.vsbugge.wargames.utils.config;

/**
 * An enum for the different simulation speeds.
 *
 * @author vsbugge
 */
public enum SimulationSpeedEnum {
    FAST(1), MEDIUM(2), SLOW(5);

    private int updateSpeed;

    SimulationSpeedEnum(int updateSpeed) {
        this.updateSpeed = updateSpeed;
    }

    /**
     * Gets the simulation delay.
     *
     * @return The desired simulation delay corresponding to the given enum constant.
     */
    public int getSimulationDelay() {
        return updateSpeed;
    }
}
