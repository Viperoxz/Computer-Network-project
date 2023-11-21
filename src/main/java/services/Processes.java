package services;

import java.io.*;
import java.net.Socket;

public class Processes {
    public static void controlListProcess(PrintWriter writer){
        try {

            // String variable to store process details
            String processes;

            // Execute tasklis.exe from win32
            Process process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");

            // Buffered reader to read from the process object
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // Prints all processes one by one
            while ((processes = br.readLine()) != null) {
                writer.println(processes);
                writer.flush();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void handleListProcess(Socket socket, PrintWriter writer) throws IOException{
        writer.println("listprocess"); //Goi len server
        writer.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while((line = reader.readLine()) != null){
            System.out.println(line);
        }

        System.out.println(reader.readLine());
        socket.close();
    }
}
