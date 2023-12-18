package gui;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.GoogleMaterialIcon;
import javaswingdev.GradientType;
import javaswingdev.drawer.Drawer;
import javaswingdev.drawer.DrawerController;
import gui.raven.chat.swing.Button;
import java.awt.*;
import java.awt.event.ActionEvent;

public class main extends JFrame {
    private JPanel heheeh;
    private Button dropdownButton;
    private DrawerController drawer;

    public static JPanel containerPanel;

    private void menuActionPerformed(ActionEvent evt){
        if (drawer.isShow())
            drawer.hide();
        else drawer.show();
    }
    public static Image resize(ImageIcon icon,int desiredWidth,int desiredHeight ){
        Image originalImage = icon.getImage();

        return originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
    }
    private  void remakeButton(){
        dropdownButton.setFocusable(false);
        dropdownButton.setBackground(new Color(10, 148, 112, 55));


        // Create a new ImageIcon with the resized Image
        GoogleMaterialIcon icon = new GoogleMaterialIcon(GoogleMaterialDesignIcon.CHEVRON_RIGHT, GradientType.VERTICAL, new Color(10, 148, 112), new Color(58, 189, 114), 35);
        dropdownButton.setIcon(icon.toIcon());
        dropdownButton.setEnabled(true);
        dropdownButton.setBounds(0,0,30,460);
//        dropdownButton.setPreferredSize(new Dimension(30,460));
//        dropdownButton.setBackground(Color.red);
    }

    public main(){
        setTitle("Activities log");
        JPanel mainPanel = new JPanel();
        ChatInterface chat= new ChatInterface();
        chat.setOpaque(false);
        InfoInterface info = new InfoInterface();
        info.setOpaque(false);
//        getRootPane().putClientProperty("JRootPane.titleBarBackground", new Color(23, 81, 194));
        getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.white);
        getRootPane().putClientProperty("JRootPane.arc", 999);
        remakeButton();
        drawer= Drawer.newDrawer(this)
                .header(new Header())
                .separator(2,new Color(28, 114, 105, 118))
                .background(new Color(10, 255, 235, 37))
//                .backgroundTransparent(0.3f)
                .drawerWidth(250)
                .enableScroll(true)
                .addChild(new Controller().build())
                .addChild(new Info().build())

                .addFooter(new Exit(this).build())
                .build();



        containerPanel= new JPanel();
        containerPanel.setLayout(new CardLayout());
        containerPanel.add(chat,"chat");
        containerPanel.add(info,"info");
        containerPanel.setOpaque(false);


        heheeh.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        mainPanel.add(heheeh);
        mainPanel.add(containerPanel);
//        mainPanel.add(info);

//        mainPanel.setOpaque(false);

        getContentPane().add(mainPanel);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,470);
        setLocationRelativeTo(null);
//        pack();


        setVisible(true);
        dropdownButton.addActionListener(this::menuActionPerformed);
    }



}

class Header extends JLabel{
    Header(){
        setText("Cute Server");
        ImageIcon icon = new ImageIcon(main.class.getResource("/icon/reindeer2.png"));
        icon=new ImageIcon(main.resize(icon,40,40));
        // Create the JLabel for the icon
        setIcon(icon);
//        JLabel iconLabel = new JLabel(icon);
        setBackground(new Color(6, 154, 142));
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setFont(new Font("MV Boli",Font.BOLD,20));
        setForeground(new Color(23, 23, 24));
        // Create the JLabel for the icon nam

        // Create an HTML formatted string to align the text beneath the icon
    }

}