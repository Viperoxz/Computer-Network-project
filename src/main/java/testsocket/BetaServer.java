package testsocket;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import services.*;


public class BetaServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());

                //Tao luong thuc thi
                Thread thread = new Thread(()
                        -> handleClientRequest(socket)
                );
                thread.start();
            }
        }
        catch (Exception e) {
        }
    }

    public static void handleClientRequest(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            while (true) {
                String request = reader.readLine();
                System.out.println(request);

                if (request.toLowerCase().equals("shutdown")) {
                    Shutdown.controlShutdown(writer);
                }
                else if (request.toLowerCase().equals("restart")) {
                    Restart.controlRestart(writer);
                }
                else if (request.toLowerCase().equals("cancel")){
                    Shutdown.controlCancelShtudown(writer);
                }
                else if (request.toLowerCase().equals("screenshot")){
                    ScreenShot.takeScreenshot(socket, writer);
                }
            }
        }
        catch(Exception e){
            System.out.println("Request not found!");
            }
        }
}
