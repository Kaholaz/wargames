module Wargames {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;

    exports org.ntnu.vsbugge.wargames.gui;
    exports org.ntnu.vsbugge.wargames.models.units;
    exports org.ntnu.vsbugge.wargames.models.army;
    exports org.ntnu.vsbugge.wargames.models.battle;
    exports org.ntnu.vsbugge.wargames.utils.enums;
    opens org.ntnu.vsbugge.wargames.gui.cotrollers;
    opens org.ntnu.vsbugge.wargames.utils.config;
}