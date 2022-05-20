package org.ntnu.vsbugge.wargames.utils.config;

public enum SimulationSpeedEnum {
    FAST(1),
    MEDIUM(2),
    SLOW(5);

    private int updateSpeed;
    SimulationSpeedEnum(int updateSpeed) {
        this.updateSpeed = updateSpeed;
    }

    public int getSimulationDelay() {
        return updateSpeed;
    }
}
