module Wargames {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    exports org.ntnu.vsbugge.wargames.gui;
    exports org.ntnu.vsbugge.wargames.units;
    exports org.ntnu.vsbugge.wargames.army;
    exports org.ntnu.vsbugge.wargames.battle;
    opens org.ntnu.vsbugge.wargames.gui.cotrollers;
}