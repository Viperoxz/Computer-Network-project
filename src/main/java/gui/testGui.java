package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class testGui {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Panel Toggle Button Visibility Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton button = new JButton("Toggle Me");
        button.setVisible(true); // Initially hide the button

        JPanel panel = new JPanel();
        panel.setBackground(Color.orange);
        panel.setOpaque(true);
        panel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                boolean isVisible = button.isVisible();
                System.out.println(isVisible);
                button.setVisible(!isVisible); // Toggle the button's visibility
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        panel.add(button);
        frame.getContentPane().add(panel);
//        frame.getContentPane().add(button);
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}