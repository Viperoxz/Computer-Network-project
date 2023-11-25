package services;

import socket.SendMail;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HandleProcess {
    private InputStream inputStream;
    private OutputStream outputStream;
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


    public static void handleListProcess(Socket socket, PrintWriter writer, String from) throws IOException{
        writer.println("listprocess"); //Goi len server
        writer.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        String fileName = "list_of_processes" + System.currentTimeMillis() +".txt";
        File file = new File(fileName);
        FileWriter myWriter = new FileWriter(file);
        while((line = reader.readLine()) != null){
            if (line.equals("END_OF_LIST")) {
                break;
            }
            System.out.println(line);
            myWriter.write(line);
            myWriter.write("\n");
        }

        String path = file.getAbsolutePath();
        SendMail.sendEmail(from, "Reply for request: List Processes", path,"");
    }


    public void controlStartApp(){
        try{
            byte[] buffer = new byte[1024];

            inputStream.read(buffer);

            String apploc = new String(buffer, StandardCharsets.UTF_8);
            apploc = apploc.trim();

            ProcessBuilder pb = new ProcessBuilder(apploc);
            pb.start();

            buffer = "1".getBytes(StandardCharsets.UTF_8);
            outputStream.write(buffer);

//            System.out.println(new String(buffer, StandardCharsets.UTF_8));
        }
        catch (IOException ioException){
            try
            {
                outputStream.write("0".getBytes(StandardCharsets.UTF_8));
            }
            catch (IOException ioE)
            {
            }
        }
    }
}