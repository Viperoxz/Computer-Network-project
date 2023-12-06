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
        writer.println("listprocess"); //Gửi lệnh lên server
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        String fileName = "./src/test/java/output/processes.txt"; // Đường dẫn tới tệp cần ghi đè
        File file = new File(fileName);
        FileWriter myWriter = new FileWriter(file, false); // Sử dụng cờ ghi đè

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
                HTMLGenerator.generateHTML("Your request has been completed successfully",
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

    public static void requestStartApp(Socket socket, PrintWriter writer, String appLocation, String from) throws IOException {
        writer.println("startapp");
        writer.flush();
        writer.println(appLocation);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();

        if (response != null && response.equals("APP_STARTED")) {
            SendMail.serversendEmail(from, "Reply for request: Start App sucessed", "",
                    HTMLGenerator.generateHTML("Your request has been completed successfully",
                            """
                                    The app has started.
                                    """));
        } else {
            SendMail.serversendEmail(from, "Reply for request: Start App failed", "", "");
        }
    }



    public static void controlStopApp(BufferedReader reader, PrintWriter writer) {
        try {
            String appLocation = reader.readLine();
            System.out.println(appLocation); // Đọc đường dẫn ứng dụng từ BufferedReader
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

    public static void requestStopApp(Socket socket, PrintWriter writer, String appLocation, String from) throws IOException {
        writer.println("stopapp"); // Gửi yêu cầu dừng ứng dụng lên server
        writer.flush();
        writer.println(appLocation); // Gửi đường dẫn của ứng dụng cần dừng lên server
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = reader.readLine();

        if (response != null && response.equals("APP_STOPPED")) {
            SendMail.serversendEmail(from, "Reply for request: Stop App succeeded", "",
                    HTMLGenerator.generateHTML("Your request has been completed successfully",
                            """
                                    The app has stopped.
                                    """));
        } else {
            SendMail.serversendEmail(from, "Reply for request: Stop App failed", "",
                    HTMLGenerator.generateHTML("Your request has failed",
                            """
                                    There was a failure when stopping this application.
                                    Something went wrong.
                                    """));
        }
    }

}
