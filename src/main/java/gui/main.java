package gui;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;
import javaswingdev.drawer.Drawer;
import javaswingdev.drawer.DrawerController;

import java.awt.*;
import java.awt.event.ActionEvent;

public class main extends JFrame {
    private JPanel heheeh;
    private JButton dropdownButton;
    private DrawerController drawer;

    public static JPanel containerPanel;

    private void menuActionPerformed(ActionEvent evt){
        if (drawer.isShow())
            drawer.hide();
        else drawer.show();
    }
    public static Image resize(ImageIcon icon,int desiredWidth,int desiredHeight ){
        Image originalImage = icon.getImage();

        return originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_DEFAULT);
    }
    private  void remakeButton(){
        dropdownButton.setBackground(new Color(10, 148, 112, 163));
        ImageIcon icon = new ImageIcon(main.class.getResource("/icon/hamburger.png"));
        Image resizedImage= resize(icon,20,20);

        // Create a new ImageIcon with the resized Image
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        dropdownButton.setIcon(resizedIcon);
        dropdownButton.setEnabled(true);
        dropdownButton.setPreferredSize(new Dimension(40,40));
//        dropdownButton.setBackground(Color.red);
    }

    public main(){
        JPanel mainPanel = new JPanel();
        ChatInterface chat= new ChatInterface();
        chat.setOpaque(false);
        InfoInterface info = new InfoInterface();
        info.setOpaque(false);
        getRootPane().putClientProperty("JRootPane.titleBarBackground", new Color(23, 81, 194));
        getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.white);
        getRootPane().putClientProperty("JRootPane.arc", 999);
        remakeButton();
        System.out.println(123);
        drawer= Drawer.newDrawer(this)
                .header(new Header())
                .separator(2,new Color(28, 114, 105, 118))
                .background(new Color(10, 255, 235, 37))
//                .backgroundTransparent(0.3f)
                .drawerWidth(250)
                .enableScroll(true)
                .addChild(new Controller().build())
                .addChild(new Info().build())

                .addFooter(new Exit().build())
                .build();



        containerPanel= new JPanel();
        containerPanel.setLayout(new CardLayout());
        containerPanel.add(chat,"chat");
        containerPanel.add(info,"info");
        containerPanel.setOpaque(false);
        containerPanel.setBackground(new Color(0,0,0,0));

        heheeh.setOpaque(false);
        mainPanel.setLayout(new OverlayLayout(mainPanel));
        mainPanel.add(heheeh);
        mainPanel.add(containerPanel);

//        mainPanel.setOpaque(false);

        getContentPane().add(mainPanel);
        setTitle("Hehe");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(350,470);
        setLocationRelativeTo(null);
        pack();


        setVisible(true);
        dropdownButton.addActionListener(this::menuActionPerformed);
    }
//    public static void main (String [] args){

//        UIManager.put( "Component.arc", 999 );
//
//        UIManager.put( "Button.arc", 999 );
//        UIManager.put( "TextComponent.arc", 5 );
//        UIManager.put("ScrollPane.background",new Color(0,0,0,0));
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new main().setVisible(true);
//            }
//        });
//    }



}

class Header extends JLabel{
    Header(){
        setText("Cute Server");
        ImageIcon icon = new ImageIcon(main.class.getResource("/icon/reindeer.png"));
        icon=new ImageIcon(main.resize(icon,40,40));
        // Create the JLabel for the icon
        setIcon(icon);
//        JLabel iconLabel = new JLabel(icon);
        setBackground(new Color(10, 148, 112, 163));
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setFont(new Font("MV Boli",Font.BOLD,20));
        setForeground(new Color(30, 44, 44, 244));
        // Create the JLabel for the icon nam

        // Create an HTML formatted string to align the text beneath the icon
    }

}