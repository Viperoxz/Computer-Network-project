package services;

import socket.SendMail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Restart {
    public static void handelRestart(BufferedReader reader, PrintWriter writer, String from) throws IOException{
        SendMail.sendEmail(from, "Reply for request: Restart", "",
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<title>Page Title</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<h1>Your request has done successfully</h1>\n" +
                        "<p>This is a paragraph.</p>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>");
        writer.println("restart"); //Goi len server
        writer.flush();
        System.out.println(reader.readLine());
    }


    public static void controlRestart(PrintWriter writer) throws IOException {
        Runtime.getRuntime().exec("shutdown -r -t 5");
        writer.println("May tinh dang khoi dong lai... ");
    }
}
