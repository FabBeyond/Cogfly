package dev.ambershadow.cogfly.elements.settings;

import dev.ambershadow.cogfly.util.FrameManager;

import javax.swing.*;

public class ProfileSourcesPanelElement extends JPanel {
    public ProfileSourcesPanelElement() {
        JButton button = new JButton("Manage Profile Sources");
        button.addActionListener(_ -> {
            ManageProfileSourcesDialog dialog = new ManageProfileSourcesDialog(FrameManager.getOrCreate().frame);
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.setVisible(true);
        });
        add(button);
    }
}
