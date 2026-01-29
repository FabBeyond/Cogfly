package dev.ambershadow.cogfly.elements;

import dev.ambershadow.cogfly.Cogfly;
import dev.ambershadow.cogfly.asset.Assets;
import dev.ambershadow.cogfly.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class InfoPageElement extends JPanel {
    public InfoPageElement() {
        setLayout(new BorderLayout());


        JPanel buttons = new JPanel(new BorderLayout());
        JButton savesButton = new JButton();
        savesButton.setText("Open Saves Folder");
        savesButton.setIcon(Assets.openSaves.getAsIconWithColor(Color.WHITE));
        savesButton.setHorizontalAlignment(SwingConstants.LEFT);
        savesButton.addActionListener(_ -> Utils.openSavePath());

        JButton logsButton = new JButton();
        logsButton.setText("Open Logs Folder");
        logsButton.setIcon(Assets.openSaves.getAsIconWithColor(Color.WHITE));
        logsButton.setHorizontalAlignment(SwingConstants.LEFT);
        logsButton.addActionListener(_ -> Utils.openPath(Paths.get(Cogfly.localDataPath).resolve("logs")));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));
        left.add(Box.createHorizontalStrut(300));
        left.add(savesButton);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
        right.add(logsButton);
        right.add(Box.createHorizontalStrut(300));

        buttons.add(left, BorderLayout.WEST);
        buttons.add(right, BorderLayout.EAST);
        buttons.add(Box.createVerticalStrut(200), BorderLayout.SOUTH);

        add(buttons, BorderLayout.SOUTH);
    }
}
