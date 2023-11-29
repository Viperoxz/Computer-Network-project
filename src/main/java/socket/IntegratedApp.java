package socket;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import services.HandleProcess;
import services.Restart;
import services.ScreenShot;
import services.Shutdown;

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
