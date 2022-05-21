package org.ntnu.vsbugge.wargames.utils.config;

/**
 * An enum for the different render frequencies.
 *
 * @author vsbugge
 */
public enum RenderFrequencyEnum {
    HIGH(17), MEDIUM(33), SLOW(60);

    private final int updateDelay;

    RenderFrequencyEnum(int updateDelay) {
        this.updateDelay = updateDelay;
    }

    /**
     * Gets the desired update delay corresponding to the enum constant.
     */
    public int getUpdateDelay() {
        return updateDelay;
    }
}
