package services;

import socket.SendMail;
import java.io.*;


public class Shutdown {
    public static void requestShutdown(BufferedReader reader, PrintWriter writer, String from) throws IOException{
        writer.println("May tinh dang tat... ");
        SendMail.sendEmail(from, "Reply for request: Shutdown", "",
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
        writer.println("shutdown"); //Goi len server
        writer.flush();
        System.out.println(reader.readLine());
    }


    public static void controlShutdown(PrintWriter writer) throws IOException{
        Runtime.getRuntime().exec("shutdown -s -t 5");
        writer.println("May tinh dang tat... ");
    }

    public static void requestCancelShutdown(BufferedReader reader, PrintWriter writer) throws IOException{
        writer.println("cancel"); //Goi len server
        writer.flush();
        System.out.println(reader.readLine());
    }

    public static void controlCancelShtudown(PrintWriter writer) throws IOException{
        Runtime.getRuntime().exec("shutdown -a");
        writer.println("Cancel success");
    }
}
