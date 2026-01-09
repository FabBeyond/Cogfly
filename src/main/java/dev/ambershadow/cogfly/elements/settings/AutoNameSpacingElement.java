package dev.ambershadow.cogfly.elements.settings;

import dev.ambershadow.cogfly.Cogfly;

import javax.swing.*;

public class AutoNameSpacingElement extends JPanel {

    public AutoNameSpacingElement(SettingsPanelElement parent) {
        JLabel label = new JLabel("Add Spaces To Mod Names");
        JCheckBox checkBox = new JCheckBox();
        checkBox.addActionListener(_ -> {
            Cogfly.settings.modNameSpaces = checkBox.isSelected();
            parent.onSettingChanged(true);
        });
        checkBox.setSelected(Cogfly.settings.modNameSpaces);
        add(label);
        add(checkBox);
    }
}
