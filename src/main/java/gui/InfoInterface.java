package gui;

import javax.swing.*;
import java.awt.*;

public class InfoInterface extends JPanel {
    InfoInterface(){
        setLayout(new BorderLayout());
        System.out.println(123);
        JTextArea textArea = new JTextArea();
        String s="Tình yêu đúng đắn nhất là tình yêu nước\nLý tưởng đúng đắn nhất là lý tưởng cách mạng";

        textArea.setText(s);
        textArea.setEditable(false);
//        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setFont(new Font("Times New Roman",Font.BOLD,20));
        textArea.setForeground(Color.yellow);
        textArea.setBackground(Color.red);
//        textArea.setBounds(0,100,350,400);
        setOpaque(false);
        JPanel centerPanel = new JPanel(new GridBagLayout()); // Create a new JPanel with GridBagLayout
        centerPanel.setOpaque(false); // Make the panel transparent
        centerPanel.add(textArea); // Add the textArea to the centerPanel

        add(centerPanel, BorderLayout.CENTER);

    }


}
