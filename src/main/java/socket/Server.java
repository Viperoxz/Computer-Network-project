package socket;

import gui.main;
import gui.raven.chat.component.ChatArea;
import gui.raven.chat.component.ChatBox;
import gui.raven.chat.model.ModelMessage;
import services.*;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


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
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
            while (true) {
                String request = reader.readLine();
//                System.out.println("Received request: " + request);
                Icon icon = new ImageIcon(main.class.getResource("/icon/reindeer.png"));

                String name = "Bot";
                String date = df.format(new Date());
                String message = "Received request: " + request ;
                ChatArea.addChatBox(new ModelMessage(icon, name, date, message), ChatBox.BoxType.LEFT);
                // Example server response handling
                switch (request.toLowerCase()) {
                    case "shutdown":
                        Shutdown.controlShutdown(writer);
                        break;
                    case "restart":
                        Restart.controlRestart(writer);
                        break;
                    case "cancel":
                        Shutdown.controlCancelShutdown(writer);
                        break;
                    case "screenshot":
                        ScreenShot.takeScreenshot(socket, writer);
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

//    public static void main(String[] args) {
//        new Server().start();
//    }
}


