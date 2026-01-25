package dev.ambershadow.cogfly.elements.settings;

import dev.ambershadow.cogfly.Cogfly;
import dev.ambershadow.cogfly.elements.SettingsDialog;

import javax.swing.*;
import java.awt.*;

public class AutoNameSpacingElement extends SettingsElement {

    public AutoNameSpacingElement(SettingsDialog parent) {
        JLabel label = new JLabel("Add Spaces To Mod Names");
        JCheckBox checkBox = new JCheckBox();
        checkBox.addActionListener(_ -> {
            boolean enabled = checkBox.isSelected();
            parent.updateModNameSpacing(enabled);
        });
        checkBox.setSelected(Cogfly.settings.modNameSpaces);
        add(label, checkBox);
    }
}
