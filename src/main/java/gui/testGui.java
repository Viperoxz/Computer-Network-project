package gui;

import javax.swing.*;
import java.awt.*;

public class testGui {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Colorful Label Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        JLabel label = new JLabel("<html><font color='red'>Hello</font> <font color='green'>world!</font></html>");
        String customColor = "rgb(" + 1 + ", " + 2 + ", " + 3+ ")";

        JLabel label = new JLabel("<html><font color='" + customColor + "'>Custom Color</font></html>");
        frame.getContentPane().add(label);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}