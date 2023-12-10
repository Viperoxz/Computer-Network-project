package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import gui.Login;
import gui.main;
import gui.raven.chat.component.ChatArea;
import gui.raven.chat.component.ChatBox;
import gui.raven.chat.model.ModelMessage;
import services.*;

import javax.swing.*;

public class Client {
    private Socket socket;

    public void start() {
        try {
            socket = new Socket("localhost", 5000);

            Timer timer = new Timer();
            Task task = new Task(socket);

            // Schedule the task to run every 5 seconds
            timer.scheduleAtFixedRate(task, 0, 5 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Task extends TimerTask {
        private final Socket socket;
        private final BufferedReader reader;
        private final PrintWriter writer;
        private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
        private void logActivities(String subject){
            Icon icon = new ImageIcon(main.class.getResource("/icon/reindeer.png"));

            String name = "Bot";
            String date = df.format(new Date());
            String mess = "Received request: " + subject ;
            ChatArea.addChatBox(new ModelMessage(icon, name, date, mess), ChatBox.BoxType.LEFT);
        }

        public Task(Socket socket) {
            this.socket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (Exception e) {
                throw new RuntimeException("Error setting up reader/writer for Task", e);
            }
        }

        @Override
        public void run() {
            try {
                processEmailCommands();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void processEmailCommands() throws Exception {
            ReceiveMail mailReceived = new ReceiveMail("imap.gmail.com", "pvhoangnamzz@gmail.com", "drzd dpmu evff ejqj");
            List<CustomPair<String, String>> commands = mailReceived.getRequirements();
            String currentUser = Login.user;
            for (CustomPair<String, String> cmd : commands) {
                String[] choice = cmd.getValue().split("&&");
                String from = cmd.getKey();

                if (from.trim().equals(currentUser.trim())) {
                    System.out.println(choice[0]);

                    switch (choice[0].toLowerCase()) {
                        case "shutdown":
                            logActivities("Shutting down your computer");
                            Shutdown.requestShutdown(reader, writer, from);
                            break;
                        case "restart":
                            logActivities("Restarting your computer");
                            Restart.requestRestart(reader, writer, from);
                            break;
                        case "cancel":
                            logActivities("Shutdown got canceled");
                            Shutdown.requestCancelShutdown(reader, writer);
                            break;
                        case "screenshot":
                            logActivities("Taking a screenshot");
                            ScreenShot.requestScreenshot(writer, reader, from);
//                        new ReceiveMail().getAttachments(App.user);
                            break;
                        case "listprocess":
                            logActivities("Sent a list of process on your computer");
                            HandleProcess.requestListProcess(socket, writer, from);
//                        new ReceiveMail().getAttachments(App.user);
                            break;
                        case "startapp":
                            logActivities("Starting " + choice[1]);
                            HandleProcess.requestStartApp(socket, writer, choice[1], from);
                            break;
                        case "stopapp":
                            logActivities("Ending " + choice[1]);
                            HandleProcess.requestStopApp(socket, writer, choice[1], from);
                            break;
                        case "exploredirectory":
                            logActivities("Entering " + choice[1]);
                            ExploreDirectory.requestExploreDir(socket, writer, choice[1], from);
                            break;
                        case "getfile[1]":
                            logActivities("Getting " + choice[1]);
                            GetFile.searchFileInRoots(choice[1], from);
                            break;
                        case "getfile[2]":
                            logActivities("Getting " + choice[1]);
                            GetFile.getFileByPath(choice[1], from);
                            break;
                        case "startkeylogger":
                            logActivities("Starting key logger");
                            KeyLogger.startKeylogger(from);
                            break;
                        case "stopkeylogger":
                            logActivities("Stopped key logger");
                            KeyLogger.stopKeylogger(from);
                            break;
                        case "help":
                            GuideTable.requestGuide(from);
                            break;
                        case "listapp":
                            logActivities("Sent a list of running apps on your computer");
                            HandleProcess.requestListApplications(socket, writer, from);
                            break;
                        default:
                            System.out.println("Something went wrong!");
                            logActivities(String.format("%s request is wrong.", from));
                            HandleSubCase.handleWrongRequest(from);
//                        throw new AssertionError();
                            break;
                    }
                }
                else{
                    SendMail.serversendEmail(from, "Reply for request control PC", "",
                            HTMLGenerator.generateHTML("Server is busy", "",
                                    String.format("This device is under control by %s." +
                                            " PLease comeback later.", currentUser)));
                }
            }
        }
    }
}