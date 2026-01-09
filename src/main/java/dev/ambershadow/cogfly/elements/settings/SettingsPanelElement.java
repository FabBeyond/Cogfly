package dev.ambershadow.cogfly.elements.settings;

import dev.ambershadow.cogfly.Cogfly;

import javax.swing.*;
import java.awt.*;

public class SettingsPanelElement extends JPanel {

    public JButton saveButton;
    public SettingsPanelElement() {
        super(new BorderLayout());
        JPanel holder = new JPanel();
        holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
        holder.add(new ThemeListElement(this));
        holder.add(new GamePathElement(this));
        holder.add(new ProfileSavePathPanelElement(this));
        holder.add(new ProfileSourcesPanelElement());
        holder.add(new BaseGameEnabledElement(this));
        holder.add(new AutoNameSpacingElement(this));


        saveButton = new JButton("Save");
        onSettingChanged(false);
        saveButton.addActionListener(_ -> {
            Cogfly.settings.save();
            onSettingChanged(false);
        });
        add(saveButton, BorderLayout.SOUTH);
        add(holder, BorderLayout.NORTH);
    }
    public void onSettingChanged(boolean changed) {
        saveButton.setEnabled(changed);
    }
    public void updateTheme(String name){
        Cogfly.settings.theme = name;
    }
}
