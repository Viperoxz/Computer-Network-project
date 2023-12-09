package socket;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import gui.Login;
import gui.main;

import javax.swing.*;

public class App {
    private Server server;
    private Client client;

    public static String user="";
    public App() {
        server = new Server();
        client = new Client();
    }

    public void startServer() throws InterruptedException {
        Login login= new Login();
        while(App.user==""){
            App.user= Login.user;
//            System.out.println(123);
            Thread.sleep(500);
        }
        new main();
        System.out.println(user);


    }

    public void startClient() {
            client.start();

    }

    public static void main(String[] args) throws InterruptedException {
        FlatDarkLaf.setup();
        FlatLaf.registerCustomDefaultsSource("raven.themes");

        JFrame.setDefaultLookAndFeelDecorated(true);
        UIManager.put( "Button.arc", 999 );
        App app = new App();
        app.server.start();
        try {
            while (true) {
//                System.out.println(1234);
                if (App.user == ""){
                    System.out.println("h"+App.user);
                    app.startServer();
                    app.startClient();
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}