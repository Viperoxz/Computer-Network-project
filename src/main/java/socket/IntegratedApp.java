package socket;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class IntegratedApp {
    private Server server;
    private Client client;


    public IntegratedApp() {
        server = new Server();
        client = new Client();
    }

    public void startServer() {
        server.start();
    }

    public void startClient() {
        client.start();
    }

    public static void main(String[] args) {
        try {
            IntegratedApp app = new IntegratedApp();
            app.startServer();
            app.startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
