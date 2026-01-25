package dev.ambershadow.cogfly.elements.settings;

import dev.ambershadow.cogfly.Cogfly;
import dev.ambershadow.cogfly.elements.SettingsDialog;
import dev.ambershadow.cogfly.util.Utils;

import javax.swing.*;
import java.awt.*;

public class ProfileSavePathPanelElement extends SettingsElement {

    public ProfileSavePathPanelElement(SettingsDialog parent) {
        JLabel label = new JLabel("Profile Save Path ");
        JButton button = new JButton(Cogfly.settings.profileSavePath);

        button.addActionListener(_ -> Utils.pickFolder(path -> {
            String p = path.toFile().getAbsolutePath();
            button.setText(p);
            parent.updateProfileSavePath(p);
        }));

        add(label, button);
    }
}
