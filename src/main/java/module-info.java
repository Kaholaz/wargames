module Wargames {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;

    exports org.ntnu.vsbugge.wargames.gui;
    exports org.ntnu.vsbugge.wargames.units;
    exports org.ntnu.vsbugge.wargames.army;
    exports org.ntnu.vsbugge.wargames.battle;
    exports org.ntnu.vsbugge.wargames.utils.enums;
    opens org.ntnu.vsbugge.wargames.gui.cotrollers;
    opens org.ntnu.vsbugge.wargames.utils.config;
}