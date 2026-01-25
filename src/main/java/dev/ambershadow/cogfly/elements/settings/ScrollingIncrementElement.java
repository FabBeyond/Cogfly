package dev.ambershadow.cogfly.elements.settings;

import dev.ambershadow.cogfly.Cogfly;
import dev.ambershadow.cogfly.elements.SettingsDialog;

import javax.swing.*;
import java.awt.*;

public class ScrollingIncrementElement extends SettingsElement {
    public ScrollingIncrementElement(SettingsDialog parent) {
        JLabel label = new JLabel("Scrolling Increment");
        JComboBox<Integer> box = new JComboBox<>();
        box.addItem(8);
        box.addItem(16);
        box.addItem(24);
        box.addItem(32);
        box.addItem(64);

        box.setSelectedItem(Cogfly.settings.scrollingIncrement);
        box.addActionListener(_ -> {
            int n = (int)box.getSelectedItem();
            parent.updateScrollIncrement(n);
        });

        add(label, box);
    }
}
