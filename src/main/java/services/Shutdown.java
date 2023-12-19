package services;

import server.SendMail;
import java.io.*;


public class Shutdown {
    public static void requestShutdown( String from) throws IOException{
//        writer.println("May tinh dang tat... ");
        SendMail.serversendEmail(from, "Reply for request: Shutdown", "",
                HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                        "The device has just shutdown."));
        Runtime.getRuntime().exec("shutdown -s -t 10");
    }


    public static void controlShutdown(PrintWriter writer) throws IOException{
        Runtime.getRuntime().exec("shutdown -s -t 5");
        writer.println("May tinh dang tat... ");
    }

    public static void requestCancelShutdown() throws IOException{
        Runtime.getRuntime().exec("shutdown -a");
//        writer.println("cancel"); //Goi len server
//        writer.flush();
//        System.out.println(reader.readLine());
    }

    public static void controlCancelShutdown(PrintWriter writer) throws IOException{
        Runtime.getRuntime().exec("shutdown -a");
        writer.println("Cancel success");
    }
}
