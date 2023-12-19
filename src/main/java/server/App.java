package server;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import gui.Login;
import gui.main;

import javax.swing.*;

public class App {
    private ServerProcess serverProcess;

    public App() {
        serverProcess = new ServerProcess();
    }

    public void startClient() throws InterruptedException {
        Login login= new Login();
        while(Login.isOn==0){
            Thread.sleep(500);
        }
        new main();
    }

    public void startServer() {
        serverProcess.start();
    }

    public static void main(String[] args) throws InterruptedException {
        FlatDarkLaf.setup();
        FlatLaf.registerCustomDefaultsSource("raven.themes");

        JFrame.setDefaultLookAndFeelDecorated(true);
        UIManager.put( "Button.arc", 999 );
        App app = new App();

        try {
            while (true) {
                if (Login.isOn==0){
                    app.startClient();
                    app.startServer();
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}