package gui;

import socket.App;

import javax.swing.*;
import java.awt.*;

public class InfoInterface extends JPanel {
    InfoInterface(){
        setLayout(new BorderLayout());
        System.out.println(123);
        JLabel textArea = new JLabel();
        String s="Server is being\ncontrolled by\n";
        String nwColor= "rgb(50,141,88)";
        textArea.setText("<html><font color='" + "gray" + "'>Server is being<br>controlled by </font><br>" +
                "<font color='"+nwColor+"'>"+App.user+"</font></html>");
//        textArea.setEditable(false);
//        textArea.setLineWrap(true);
//        textArea.setWrapStyleWord(true);

        textArea.setFont(new Font("Consolas",Font.BOLD,20));

//        textArea.setBounds(0,100,350,400);
        setOpaque(false);
        JPanel centerPanel = new JPanel(new GridBagLayout()); // Create a new JPanel with GridBagLayout
        centerPanel.setOpaque(false); // Make the panel transparent
        centerPanel.add(textArea); // Add the textArea to the centerPanel

        add(centerPanel, BorderLayout.CENTER);

    }


}
