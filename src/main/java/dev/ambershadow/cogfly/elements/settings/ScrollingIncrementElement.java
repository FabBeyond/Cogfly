package dev.ambershadow.cogfly.elements.settings;

import dev.ambershadow.cogfly.Cogfly;
import dev.ambershadow.cogfly.elements.SettingsDialog;

import javax.swing.*;
import java.awt.*;

public class ScrollingIncrementElement extends JPanel {
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

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 10, 5, 10);
        add(label, c);
        c.gridx = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        add(box, c);
    }
}
