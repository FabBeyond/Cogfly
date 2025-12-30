package dev.ambershadow.cogfly.elements.settings;

import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import dev.ambershadow.cogfly.util.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ThemeListElement extends JPanel {

    private final SettingsPanelElement parent;
    public ThemeListElement(SettingsPanelElement parent){
        this.parent = parent;
        JComboBox<UIManager.LookAndFeelInfo> combo =
                new JComboBox<>(FlatAllIJThemes.INFOS);

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof UIManager.LookAndFeelInfo info) {
                    setText(info.getName());
                }
                return this;
            }
        });

        combo.addActionListener(e -> {
            UIManager.LookAndFeelInfo info =
                    (UIManager.LookAndFeelInfo) combo.getSelectedItem();
            if (info == null)
                return;
            if (Objects.equals(info.getClassName(), UIManager.getLookAndFeel().getClass().getName()))
                return;
            switchTheme(info);
            parent.updateTheme(info.getClassName());
            parent.onSettingChanged(true);
        });

        add(new JLabel("Theme:"));
        add(combo);

        LookAndFeel currentLaf = UIManager.getLookAndFeel();
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).getClassName().equals(currentLaf.getClass().getName())) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }
    public static void switchTheme(UIManager.LookAndFeelInfo info) {
        try {
            UIManager.setLookAndFeel(info.getClassName());
            SwingUtilities.updateComponentTreeUI(FrameManager.getOrCreate().frame);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}