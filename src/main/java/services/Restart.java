package services;

import socket.SendMail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Restart {
    public static void requestRestart( String from) throws IOException{
        Runtime.getRuntime().exec("shutdown -r -t 10");
        SendMail.serversendEmail(from, "Reply for request: Restart", "",
                HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                        "The device has just restarted."));
//        writer.println("restart"); //Goi len server
//        writer.flush();
//        System.out.println(reader.readLine());
    }


    public static void controlRestart(PrintWriter writer) throws IOException {
        Runtime.getRuntime().exec("shutdown -r -t 5");
        writer.println("May tinh dang khoi dong lai... ");
    }
}
