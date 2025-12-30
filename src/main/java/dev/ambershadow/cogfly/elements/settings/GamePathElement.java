package dev.ambershadow.cogfly.elements.settings;

import dev.ambershadow.cogfly.Cogfly;
import dev.ambershadow.cogfly.util.Utils;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;

public class GamePathElement extends JPanel {
    private final SettingsPanelElement parent;
    public GamePathElement(SettingsPanelElement parent){
        this.parent = parent;

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Game Path: ");
        JButton button = new JButton(Cogfly.settings.gamePath);

        button.addActionListener(e -> Utils.pickFileAsync(path -> {
            Cogfly.settings.gamePath = path.toFile()
                    .getParentFile().getAbsolutePath();
            button.setText(Cogfly.settings.gamePath);
            parent.onSettingChanged(true);
        }, new FileChooser.ExtensionFilter(
                "Silksong Executable",
                "*.exe", "*.app", "*.   x86_64"
        )));


        panel.add(label, BorderLayout.WEST);
        panel.add(button, BorderLayout.EAST);
        add(panel);
    }
}
