package socket;

import com.formdev.flatlaf.FlatDarkLaf;
import gui.Login;
import gui.main;

import javax.swing.*;

public class App {
    private Server server;
    private Client client;

    private int user=0;
    public App() {
        server = new Server();
        client = new Client();
    }

    public void startServer() throws InterruptedException {
        server.start();
        Login login= new Login();
        while(user==0){
            user= Login.user;
//            System.out.println(123);
            Thread.sleep(500);
        }
        System.out.println(user);
    }

    public void startClient() {
        main m= new main();
        client.start();
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            App app = new App();
            app.startServer();
            app.startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
