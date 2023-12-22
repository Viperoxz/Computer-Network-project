package services;

import server.SendMail;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class HandleProcess {
    static ArrayList<File> apps= new ArrayList<>();

    public static void requestListProcess( String from) throws IOException {

        Process process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

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
                                <b>Listing processes</b> running successful. <br>
                                This file contains all processes currently running on the device.
                                """));
    }

    static void list(File files){
        for (File file: files.listFiles()){
            if (file.isDirectory()){
                list(file);
            }else{
                apps.add(file);
            }

        }
    }

    public static void requestStartApp( String appLocation, String from) throws IOException {
        list(new File("C:/ProgramData/Microsoft/Windows/Start Menu/Programs"));
        String[] appLoc = appLocation.split("\\\\");
        String appName = appLoc[appLoc.length-1];
        int ok=0;
        try{
            for (File file: apps){
                if (file.getName().toLowerCase().contains(appName)){
                    if (Desktop.isDesktopSupported()) {
                        appName=file.getName();
                        Desktop.getDesktop().open(file);
                        ok=1;
                    }else{
                        throw new Exception();
                    }
                    break;
                }
            }
            if (ok==0){
                ProcessBuilder pb = new ProcessBuilder(appLocation);
                Process process = pb.start();
                if (process.isAlive())
                    ok=1;
                appName = appLoc[appLoc.length-1];

            }

            if (ok==1) {
                SendMail.serversendEmail(from, "Reply for request: Start App succeed", "",
                        HTMLGenerator.generateHTML("Your request has been completed successfully", appName,
                                String.format("""
                                    <b>%s</b> has started.
                                    """, appName)));
            }

        } catch(Exception e) {
            SendMail.serversendEmail(from, "Reply for request: Start App failed", "",
                    HTMLGenerator.generateHTML("Your request has failed", "",
                            String.format("""
                                    There was a failure when starting <b>%s</b>.<br>
                                    Something went wrong.
                                    """, appName)));
            System.out.println("ngu");
        }
//        System.out.println(213);
    }



    public static void requestStopApp( String appName, String from) throws IOException {

        try {
            String appLocation= appName;
            ProcessBuilder pb = new ProcessBuilder("taskkill", "/F", "/IM", appLocation);
            Process process = pb.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                SendMail.serversendEmail(from, "Reply for request: Stop App succeed", "",
                        HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                                String.format("""
                                   <b>%s</b> has stop.
                                    """, appName)));
            }else {
                throw new Exception("Can't stop app");
            }
        } catch (Exception e) {
            SendMail.serversendEmail(from, "Reply for request: Stop App failed", "",
                    HTMLGenerator.generateHTML("Your request has failed", "",
                            String.format("""
                                    There was a failure when stopping <b>%s</b>.<br>
                                    Something went wrong.
                                    """, appName)));
        }


    }



    public static void requestListApplications( String from) throws IOException {

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
                        "<b>Listing applications</b> running successful. <br>This file contains all applications currently running on the device."));
    }

//    public static void main(String[] args) throws IOException {
//
//        for (File file : apps) {
//            System.out.println(file.getAbsolutePath());
//        }
//        requestStartApp("edge","hihi");
//    }
}