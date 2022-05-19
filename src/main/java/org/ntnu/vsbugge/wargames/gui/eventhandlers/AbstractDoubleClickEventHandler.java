package org.ntnu.vsbugge.wargames.gui.eventhandlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public abstract class AbstractDoubleClickEventHandler implements EventHandler<MouseEvent> {
    public abstract void runsIfDoubleClick();

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                runsIfDoubleClick();
            }
        }
    }
}
