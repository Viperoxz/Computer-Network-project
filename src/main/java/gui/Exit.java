package gui;

import javaswingdev.drawer.DrawerItem;
import socket.App;
import socket.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Exit extends DrawerItem{


    Exit(main m) {
        super("Stop Access");
        setBackground(new Color(8, 131, 149));
        setForeground(new Color(30, 44, 44, 244));
        setFont(new Font("Consolas",Font.BOLD,17));
        ImageIcon icon = new ImageIcon(main.class.getResource("/icon/exit.png"));
        icon = new ImageIcon(main.resize(icon,20,20));
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.dispose();
                Client.users.clear();
                Login.isOn=0;
            }
        });
        icon(icon);
    }
}
