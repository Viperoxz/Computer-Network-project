package services;

import server.SendMail;

import java.io.*;

public class HandleProcess {


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



    public static void requestStartApp( String appLocation, String from) throws IOException {
        String[] appLoc = appLocation.split("\\\\");
        String appName = appLoc[appLoc.length-1];

        try{
            ProcessBuilder pb = new ProcessBuilder(appLocation);
            Process process = pb.start();

            if (process.isAlive()) {
                SendMail.serversendEmail(from, "Reply for request: Start App sucessed", "",
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
            e.printStackTrace();
        }
    }



    public static void requestStopApp( String appName, String from) throws IOException {
    try {
        String appLocation= appName;
        ProcessBuilder pb = new ProcessBuilder("taskkill", "/F", "/IM", appLocation);
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            SendMail.serversendEmail(from, "Reply for request: Stop App succeeded", "",
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
}
