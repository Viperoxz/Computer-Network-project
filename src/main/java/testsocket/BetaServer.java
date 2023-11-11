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
import services.ScreenShot;

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
                System.out.println(request);
                if (request.toLowerCase().equals("shutdown")) {
                    Runtime.getRuntime().exec("shutdown -s -t 5");
                    writer.println("May tinh dang tat... ");
                }
                else if (request.toLowerCase().equals("restart")) {
                    Runtime.getRuntime().exec("shutdown -r -t 5");
                    writer.println("May tinh dang khoi dong lai... ");
                }
                else if (request.toLowerCase().equals("cancel")){
                    Runtime.getRuntime().exec("shutdown -a");
                    writer.println("");
                }
                else if (request.toLowerCase().equals("screenshot")){
                    BufferedImage screenshot = new Robot().createScreenCapture(
                            new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(screenshot, "png", baos);

                    byte[] imgaeBytes = baos.toByteArray();
                    baos.close();

                    writer.println(imgaeBytes.length);
                    writer.flush();
                    socket.getOutputStream().write(imgaeBytes);
                }
            }
        }
        catch(Exception e){
            }
        }
}
