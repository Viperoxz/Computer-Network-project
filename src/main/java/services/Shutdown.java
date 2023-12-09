package services;

import socket.SendMail;
import java.io.*;


public class Shutdown {
    public static void requestShutdown(BufferedReader reader, PrintWriter writer, String from) throws IOException{
//        writer.println("May tinh dang tat... ");
        SendMail.serversendEmail(from, "Reply for request: Shutdown", "",
                HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                        "The device has just shutdown."));
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

    public static void controlCancelShutdown(PrintWriter writer) throws IOException{
        Runtime.getRuntime().exec("shutdown -a");
        writer.println("Cancel success");
    }
}
