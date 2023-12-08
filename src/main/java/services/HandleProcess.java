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


    public static void requestListProcess(Socket socket, PrintWriter writer, String from) throws IOException {
        writer.println("listprocess");
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        String fileName = "./src/test/output/processes.txt";
        File file = new File(fileName);
        FileWriter myWriter = new FileWriter(file, false);

        while ((line = reader.readLine()) != null) {
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
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    writer.println("APP_STARTED");
                } else {
                    writer.println("APP_START_FAILED");
                }
                writer.flush();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                writer.println("APP_START_FAILED");
                writer.flush();
            }
        }

    public static void requestStartApp(Socket socket, PrintWriter writer, String appName, String from) throws IOException {
        writer.println("startapp");
        writer.flush();
        writer.println(appName);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();

        if (response != null && response.equals("APP_STARTED")) {
            SendMail.serversendEmail(from, "Reply for request: Start App sucessed", "",
                    HTMLGenerator.generateHTML("Your request has been completed successfully", appName,
                            String.format("""
                                    The app %s has started.
                                    """, appName)));
        } else {
            SendMail.serversendEmail(from, "Reply for request: Start App failed", "",
                    String.format("""
                                    There was a failure when starting %s.
                                    Something went wrong.
                                    """, appName));
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

    public static void requestStopApp(Socket socket, PrintWriter writer, String appName, String from) throws IOException {
        writer.println("stopapp");
        writer.flush();
        writer.println(appName);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();

        if (response != null && response.equals("APP_STOPPED")) {
            SendMail.serversendEmail(from, "Reply for request: Stop App succeeded", "",
                    HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                            String.format("""
                                    The app %s has stop.
                                    """, appName)));
        } else {
            SendMail.serversendEmail(from, "Reply for request: Stop App failed", "",
                    HTMLGenerator.generateHTML("Your request has failed", "",
                            String.format("""
                                    There was a failure when stopping %s.
                                    Something went wrong.
                                    """, appName)));
        }
    }

}
