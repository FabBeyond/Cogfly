package dev.ambershadow.cogfly.elements;

import javax.swing.*;
import java.awt.*;

public class InfoPageElement extends JPanel {
    public InfoPageElement() {
        setLayout(new BorderLayout());
        add(new SidebarElement(), BorderLayout.WEST);
    }
}
