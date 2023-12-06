package gui;

import javaswingdev.drawer.DrawerItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Exit extends DrawerItem{

    Exit() {
        super("Exit");
        setBackground(new Color(8, 131, 149));

        ImageIcon icon = new ImageIcon(main.class.getResource("/icon/exit.png"));
        icon = new ImageIcon(main.resize(icon,20,20));
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        icon(icon);
    }
}
