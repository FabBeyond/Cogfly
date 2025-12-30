package dev.ambershadow.cogfly.elements.settings;

import dev.ambershadow.cogfly.Cogfly;

import javax.swing.*;

public class BaseGameEnabledElement extends JPanel {

    private final SettingsPanelElement parent;
    public BaseGameEnabledElement(SettingsPanelElement parent) {
        this.parent = parent;
        JLabel label = new JLabel("Allow Base Game Modding");
        JCheckBox checkBox = new JCheckBox();
        checkBox.addActionListener(_ -> {
            Cogfly.settings.baseGameEnabled = checkBox.isSelected();
            parent.onSettingChanged(true);
        });
        checkBox.setSelected(Cogfly.settings.baseGameEnabled);
        add(label);
        add(checkBox);
    }
}
