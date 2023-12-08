package socket;

import services.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private ServerSocket serverSocket;


    public void start() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(5000);
                System.out.println("Server started...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
                    handleClientRequest(socket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClientRequest(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String request = reader.readLine();
                System.out.println("Received request: " + request);

                switch (request.toLowerCase()) {
                    case "shutdown":
                        Shutdown.controlShutdown(writer);
                        break;
                    case "restart":
                        Restart.controlRestart(writer);
                        break;
                    case "cancel":
                        Shutdown.controlCancelShtudown(writer);
                        break;
                    case "screenshot":
                        ScreenShot.takeScreenshot(writer);
                        break;
                    case "listprocess":
                        HandleProcess.controlListProcess(writer);
                        break;
                    case "startapp":
                        HandleProcess.controlStartApp(reader, writer);
                        break;
                    case "stopapp":
                        HandleProcess.controlStopApp(reader, writer);
                        break;
                    case "exploredirectory":
                        ExploreDirectory.controlExploreDir(reader, writer);
                        break;
                    default:
                        writer.println("Unknown command: " + request);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Request not found!");
        }
    }
}

