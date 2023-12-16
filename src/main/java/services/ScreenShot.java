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
import java.awt.Dimension;
import java.util.Base64;

public class ScreenShot {

    public static void takeScreenshot(PrintWriter writer) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);

        BufferedImage screenshot = new Robot().createScreenCapture(screenRectangle);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(screenshot, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        baos.close();


        writer.println(imageBytes.length);
        writer.flush();

        String imageString = Base64.getEncoder().encodeToString(imageBytes);
        writer.println(imageString);
        writer.flush();
    }




    public static void requestScreenshot( String from) throws Exception {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);

        BufferedImage screenshot = new Robot().createScreenCapture(screenRectangle);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(screenshot, "png", baos);
        byte[] imgBytes = baos.toByteArray();
        baos.close();
        String imageString = Base64.getEncoder().encodeToString(imgBytes);

        imgBytes = Base64.getDecoder().decode(imageString);

        Path imgPath = Paths.get("./src/test/output/screenshot.png");
        Files.write(imgPath, imgBytes);

        System.out.println("Done!");

        SendMail.serversendEmail(from, "Reply for request: Screenshot", imgPath.toString(),
                HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                        """
                        Taking screenshot successful. 
                        This is the screenshot you want.
                        """));
    }

}
