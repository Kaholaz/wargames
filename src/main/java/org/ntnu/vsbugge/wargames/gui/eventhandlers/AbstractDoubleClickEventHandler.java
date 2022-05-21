package org.ntnu.vsbugge.wargames.gui.eventhandlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Functions as an EventHandler for a MouseEvent and triggers a function if and only if the MouseEvent consisted of a
 * double click.
 *
 * @author vsbugge
 */
public abstract class AbstractDoubleClickEventHandler implements EventHandler<MouseEvent> {
    /**
     * This function is called if the EventHandler is supplied with a double click MouseEvent.
     */
    public abstract void runsIfDoubleClick();

    /**
     * {@inheritDoc}
     *
     * <br>
     * <br>
     * Overwrites the standard function called by the EventHandler and calls the runsIfDoubleClick method if the mouse
     * event was a double click.
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                runsIfDoubleClick();
            }
        }
    }
}
