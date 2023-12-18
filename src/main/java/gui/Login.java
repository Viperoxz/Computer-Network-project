package gui;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class Login extends JFrame {
        static String cache="hehehehehe";
        public static int isOn=0;
        public Login() {
            init();
        }



        private void init() {
            setTitle("Login");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500, 450);
            setLocationRelativeTo(null);
            setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
            usernameField = new JPasswordField();
            chRememberMe = new JCheckBox("Remember me");
            cmdLogin = new JButton("Start Access");
            JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
            panel.putClientProperty(FlatClientProperties.STYLE, "" +
                    "arc:20;" +
                    "[light]background:darken(@background,3%);" +
                    "[dark]background:lighten(@background,3%)");

//        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
//                "showRevealButton:true");
            cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                    "[light]background:darken(@background,10%);" +
                    "[dark]background:lighten(@background,10%);" +
                    "borderWidth:0;" +
                    "focusWidth:0;" +
                    "innerFocusWidth:0");
            if (cache.isEmpty()){
                usernameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your email");
            }
            else{
                usernameField.setText(cache);
                chRememberMe.setSelected(true);
            }

//        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");

            JLabel lbTitle = new JLabel("Welcome back!");
            lbTitle.setFont(new Font("Consolas", Font.BOLD, 16));
            lbTitle.setHorizontalAlignment(SwingConstants.CENTER);

            panel.add(lbTitle);

            panel.add(usernameField);
            cmdLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();

                    if (!username.equals("hehehehehe")){
                        JOptionPane.showMessageDialog(Login.this, "Invalid password");
                    }else{
                        isOn=1;
                        dispose();
                    }
                    if (chRememberMe.isSelected())
                        cache=username;
                    else
                        cache="";
                }
            });
//            panel.add(chRememberMe, "grow 0");
            panel.add(cmdLogin, "gapy 10");
            add(panel);
            setVisible(true);
        }


        private JPasswordField usernameField;
        private JCheckBox chRememberMe;
        private JButton cmdLogin;
    }
