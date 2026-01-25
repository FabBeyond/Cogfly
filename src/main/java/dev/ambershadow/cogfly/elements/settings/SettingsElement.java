package dev.ambershadow.cogfly.elements.settings;

import javax.swing.*;
import java.awt.*;

class SettingsElement extends JPanel {

    public SettingsElement() {
        setLayout(new GridBagLayout());
    }
    protected final void add(JComponent first, JComponent second) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 10, 5, 10);
        add(first, c);
        c.gridx = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        add(second, c);
    }
}
