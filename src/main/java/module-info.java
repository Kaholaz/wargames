module Wargames {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    exports org.ntnu.vsbugge.wargames.gui;
    opens org.ntnu.vsbugge.wargames.gui.cotrollers;
}