package testsocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
        } catch (Exception e) {
        }
    }

    public static void handleClientRequest(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            while (true) {
                String request = reader.readLine();
                if (request.toLowerCase().equals("shutdown")) {
                    Runtime.getRuntime().exec("shutdown -s -t 0");
                    writer.println("May tinh dang tat... ");
                } else if (request.toLowerCase().equals("restart")) {
                    Runtime.getRuntime().exec("");
                    writer.println("May tinh dang khoi dong lai... ");
                }
            }
        }
        catch(Exception e){
            }
        }
}
