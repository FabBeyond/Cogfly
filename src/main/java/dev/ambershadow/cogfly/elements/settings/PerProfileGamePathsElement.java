package dev.ambershadow.cogfly.elements.settings;

import dev.ambershadow.cogfly.Cogfly;
import dev.ambershadow.cogfly.elements.SettingsDialog;

import javax.swing.*;

public class PerProfileGamePathsElement extends SettingsElement {

    public PerProfileGamePathsElement(SettingsDialog parent) {
        JLabel label = new JLabel("Allow Per-Profile Game Paths");
        JCheckBox checkBox = new JCheckBox();
        checkBox.addActionListener(_ -> {
            boolean enabled = checkBox.isSelected();
            parent.updatePerProfilePaths(enabled);
        });
        checkBox.setSelected(Cogfly.settings.profileSpecificPaths);
        add(label, checkBox);
    }
}
