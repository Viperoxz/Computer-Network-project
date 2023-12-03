package services;

import socket.SendMail;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class ScreenShot {

    public static void takeScreenshot(Socket socket, PrintWriter writer) throws Exception {
        // Capture the screen as a BufferedImage
        BufferedImage screenshot = new Robot().createScreenCapture(
                new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        // Convert the image to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(screenshot, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        // Send the image size and the image data to the server
        writer.println(imageBytes.length);
        writer.flush();
        socket.getOutputStream().write(imageBytes);
        socket.getOutputStream().flush();
    }



    public static void requestScreenshot(Socket socket, PrintWriter writer, BufferedReader reader, String from) throws Exception{
        writer.println("screenshot"); //Goi len server
        writer.flush();
        int imgSize = Integer.parseInt(reader.readLine());
        byte[] imgBytes = new byte[imgSize];
        int readByte = socket.getInputStream().read(imgBytes);
        if (readByte > 0) {
            Path imgPath = Paths.get("temp_scrs_" +System.currentTimeMillis() + ".jpg");
            Files.write(imgPath, imgBytes);
            System.out.println("Done!");

            SendMail.sendEmail(from, "Reply for request: Screenshot", imgPath.toString(),
                    HTMLGenerator.generateHTML("Your request has been completed successfully",
                            """
                                    Taking screenshot successful. 
                                    This is the screenshot you want.
                                    """));
        }
    }
}
