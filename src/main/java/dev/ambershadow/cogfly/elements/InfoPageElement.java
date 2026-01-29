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

        JLabel image = new JLabel();
        ImageIcon icon = (ImageIcon)Assets.centralIcon.getAsIcon();
        Image scaled = icon.getImage().getScaledInstance(
                549, 336, Image.SCALE_SMOOTH);
        image.setIcon(new ImageIcon(scaled));
        image.setHorizontalAlignment(SwingConstants.CENTER);
        add(image, BorderLayout.NORTH);
        add(createButtons(), BorderLayout.CENTER);
    }

    public JPanel createButtons(){
        Dimension dim = new Dimension(175, 40);
        Dimension max = new Dimension(Integer.MAX_VALUE, 40);

        JButton savesButton = new JButton("Open Saves Folder");
        savesButton.setIcon(Assets.openSaves.getAsIconWithColor(Color.RED));
        savesButton.setHorizontalAlignment(SwingConstants.LEFT);
        savesButton.setPreferredSize(dim);
        savesButton.setMaximumSize(max);
        savesButton.addActionListener(_ -> Utils.openSavePath());

        JButton logsButton = new JButton("Open Logs Folder");
        logsButton.setIcon(Assets.openSaves.getAsIconWithColor(Color.BLUE));
        logsButton.setHorizontalAlignment(SwingConstants.LEFT);
        logsButton.setPreferredSize(dim);
        logsButton.setMaximumSize(max);
        logsButton.addActionListener(_ -> Utils.openPath(Paths.get(Cogfly.localDataPath).resolve("logs")));

        JButton launchVanilla = new JButton("Launch Vanilla Game");
        launchVanilla.setHorizontalAlignment(SwingConstants.CENTER);
        launchVanilla.setPreferredSize(dim);
        launchVanilla.setMaximumSize(max);
        launchVanilla.addActionListener(_ -> Cogfly.launchGameAsync(false, "", Cogfly.settings.gamePath));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        buttons.add(savesButton);
        buttons.add(Box.createHorizontalGlue());
        buttons.add(Box.createHorizontalStrut(20));
        buttons.add(launchVanilla);
        buttons.add(Box.createHorizontalStrut(20));
        buttons.add(Box.createHorizontalGlue());
        buttons.add(logsButton);

        buttons.setBorder(
                BorderFactory.createEmptyBorder(0, 300, 200, 300)
        );

        return buttons;
    }
}
