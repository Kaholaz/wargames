package org.ntnu.vsbugge.wargames.utils.config;

public enum RenderFrequencyEnum {
    High(17),
    MEDIUM(33),
    SLOW(60);

    private int updateDelay;
    RenderFrequencyEnum(int updateDelay) {
        this.updateDelay = updateDelay;
    }

    public int getUpdateDelay() {
        return updateDelay;
    }
}
