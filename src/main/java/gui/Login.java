package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    public static int user=0;
    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        JPanel panel= new JPanel();
        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        // Set layout
        panel.setLayout(new GridLayout(3, 2));

        // Add components to the frame
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.setSize(300,300);
        add(panel);
        pack();
        // Add login button action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Perform login validation
                if (validateLogin(username, password)) {
                    // Login successful
                    //List Nam-1 Tung-2 Dat-3
                    user=1;
                    setVisible(false);
                } else {
                    // Login failed
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password!");
                }
            }
        });
        setVisible(true);
    }

    private boolean validateLogin(String username, String password) {
        // Perform login validation logic
        // Replace this with your own login validation code
        return username.equals("admin") && password.equals("password");
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                Login loginFrame = new Login();
//            }
//        });
//    }
}