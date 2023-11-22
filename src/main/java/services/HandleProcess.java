package services;

import socket.SendMail;

import java.io.*;
import java.net.Socket;

public class HandleProcess {
    public static void controlListProcess(PrintWriter writer) {
        try {
            String processes;
            Process process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
            // Buffered reader to read from the process obchect
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while ((processes = br.readLine()) != null) {
                    writer.println(processes);
                    writer.flush();
                }
            }
            writer.println("END_OF_LIST");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleListProcess(Socket socket, PrintWriter writer, String from) {
        try {
            writer.println("listprocess"); //Goi len server
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            String fileName = "list_of_processes" + System.currentTimeMillis() + ".txt";

            try (FileWriter myWriter = new FileWriter(fileName)) {
                while ((line = reader.readLine()) != null) {
                    if (line.equals("END_OF_LIST")) {
                        break;
                    }
                    System.out.println(line);
                    myWriter.write(line);
                    myWriter.write("\n");
                }
            }

            String path = new File(fileName).getAbsolutePath();
            SendMail.sendEmail(from, "Reply for request: List Processes", path, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
