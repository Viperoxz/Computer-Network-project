package services;

import socket.SendMail;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class HandleProcess {
    public static void controlListProcess(PrintWriter writer){
        try {
            String processes;
            Process process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
            // Buffered reader to read from the process object
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((processes = br.readLine()) != null) {
                writer.println(processes);
                writer.flush();
            }
            writer.println("END_OF_LIST");
            writer.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void requestListProcess( String from) throws IOException {
//        writer.println("listprocess");
//        writer.flush();

        Process process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        // Buffered reader to read from the process object
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

//        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        String fileName = "./src/test/output/processes.txt";
        File file = new File(fileName);
        FileWriter myWriter = new FileWriter(file, false);

        while ((line = br.readLine()) != null) {
            if (line.equals("END_OF_LIST")) {
                break;
            }
            System.out.println(line);
            myWriter.write(line);
            myWriter.write("\n");
        }

        myWriter.close();

        String path = file.getAbsolutePath();
        SendMail.serversendEmail(from, "Reply for request: List Processes", path,
                HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                        """
                                Listing processes running successful. 
                                This file contains all processes currently running on the device.
                                """));
    }

            public static void controlStartApp(BufferedReader reader, PrintWriter writer) {
                try {
                    String appLocation = reader.readLine();
                    System.out.println(appLocation);
                    ProcessBuilder pb = new ProcessBuilder(appLocation);
                    Process process = pb.start();
//                    int exitCode = process.waitFor();
//                    System.out.println("Exit code: " + exitCode);
                    if (process.isAlive()) {
                        System.out.println("xong");
                        writer.println("APP_STARTED");
                    } else {
                        writer.println("APP_START_FAILED");
                    }
                    writer.flush();
                } catch (IOException e/*| InterruptedException e*/) {
                    e.printStackTrace();
                    writer.println("APP_START_FAILED");
                    writer.flush();
                }
            }

    public static void requestStartApp( String appName, String from) throws IOException {
//        writer.println("startapp");
//        writer.flush();
//        writer.println(appName);
//        writer.flush();

        try{

            String appLocation = appName;
            ProcessBuilder pb = new ProcessBuilder(appLocation);
            Process process = pb.start();
            if (process.isAlive()) {
                SendMail.serversendEmail(from, "Reply for request: Start App sucessed", "",
                        HTMLGenerator.generateHTML("Your request has been completed successfully", appName,
                                String.format("""
                                    %s has started.
                                    """, appName)));

            }

        } catch(Exception e) {
            SendMail.serversendEmail(from, "Reply for request: Start App failed", "",
                    String.format("""
                                    There was a failure when starting %s.
                                    Something went wrong.
                                    """, appName));
            e.printStackTrace();
        }
    }



    public static void controlStopApp(BufferedReader reader, PrintWriter writer) {
        try {
            String appLocation = reader.readLine();
            System.out.println(appLocation);
            ProcessBuilder pb = new ProcessBuilder("taskkill", "/F", "/IM", appLocation);
            Process process = pb.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                writer.println("APP_STOPPED");
            } else {
                writer.println("APP_STOP_FAILED");
            }
            writer.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            writer.println("APP_STOP_FAILED");
            writer.flush();
        }
    }

    public static void requestStopApp( String appName, String from) throws IOException {
//        writer.println("stopapp");
//        writer.flush();
//        writer.println(appName);
//        writer.flush();

//        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        String response = reader.readLine();
    try {
        String appLocation= appName;
        ProcessBuilder pb = new ProcessBuilder("taskkill", "/F", "/IM", appLocation);
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            SendMail.serversendEmail(from, "Reply for request: Stop App succeeded", "",
                    HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                            String.format("""
                                    %s has stop.
                                    """, appName)));
        }else {
            throw new Exception("Hoi non");
        }
    } catch (Exception e) {
        SendMail.serversendEmail(from, "Reply for request: Stop App failed", "",
                HTMLGenerator.generateHTML("Your request has failed", "",
                        String.format("""
                                    There was a failure when stopping %s.
                                    Something went wrong.
                                    """, appName)));
    }


    }


    public static void controlListApplications(PrintWriter writer) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe",
                    "Get-Process | Where-Object { $_.MainWindowTitle } | Format-Table ID,Name,Mainwindowtitle -AutoSize");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Buffered reader to read from the process object
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                writer.println(line);
                writer.flush();
            }
            writer.println("END_OF_LIST");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void requestListApplications( String from) throws IOException {
//        writer.println("listapp");
//        writer.flush();

//        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe",
                "Get-Process | Where-Object { $_.MainWindowTitle } | Format-Table ID,Name,Mainwindowtitle -AutoSize");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Buffered reader to read from the process object
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String fileName = "./src/test/output/applications.txt";
        File file = new File(fileName);
        FileWriter myWriter = new FileWriter(file, false);

        while ((line = br.readLine()) != null) {
            if (line.equals("END_OF_LIST")) {
                break;
            }
            System.out.println(line);
            myWriter.write(line);
            myWriter.write("\n");
        }

        myWriter.close();

        String path = file.getAbsolutePath();
        SendMail.serversendEmail(from, "Reply for request: List Applications", path,
                HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                        "Listing applications running successful. This file contains all applications currently running on the device."));
    }
}
