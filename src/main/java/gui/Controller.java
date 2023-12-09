package gui;

import javaswingdev.drawer.DrawerItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller extends DrawerItem{

    Controller() {
        super("Requests");
        setForeground(new Color(30, 44, 44, 244));
//        setBackground(new Color(8, 131, 149));

        ImageIcon icon = new ImageIcon(main.class.getResource("/icon/chat.png"));
        setFont(new Font("Consolas",Font.BOLD,20));
        icon = new ImageIcon(main.resize(icon,20,20));
        icon(icon);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout= (CardLayout) main.containerPanel.getLayout();
                cardLayout.show(main.containerPanel,"chat");
            }
        });

    }

}
