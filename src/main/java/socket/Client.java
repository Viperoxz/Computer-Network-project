package socket;

import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.Timer;

import gui.Login;
import gui.main;
import gui.raven.chat.component.ChatArea;
import gui.raven.chat.component.ChatBox;
import gui.raven.chat.model.ModelMessage;
import services.*;

import javax.swing.*;

public class Client {
    private Socket socket;

    public static ArrayList<String> users=new ArrayList<>();
    public static ArrayList<String> newUsers= new ArrayList<>();
    public static Color[] color={new Color(46, 49, 146),new Color(26, 188, 156),
            new Color(33, 102, 138),new Color(96, 37, 159),new Color(133, 110, 8),
            new Color(230, 126, 34),new Color(141, 46, 35),new Color(17, 119, 98),
            new Color(27, 124, 68),new Color(5, 121, 56),new Color(25, 93, 65)};
    public void start() {
        try {
//            socket = new Socket("localhost", 5000);

            Timer timer = new Timer();
            Task task = new Task();

            // Schedule the task to run every 5 seconds
            timer.scheduleAtFixedRate(task, 0, 5 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Task extends TimerTask {

        LocalTime currentTime = LocalTime.now();
        private void logActivities(String from, String subject){
            Icon icon = new ImageIcon(main.class.getResource("/icon/user.png"));

            String name = from;
            String date = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            String mess =  subject ;
            ChatArea.addChatBox(new ModelMessage(icon, name, date, mess),Client.color[Client.users.indexOf(name)], ChatBox.BoxType.LEFT);
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
            for (CustomPair<String, String> cmd : commands) {
                String[] choice = cmd.getValue().split("&&");
                String from = cmd.getKey();
                System.out.println(Client.users.contains(from));
                if (!Client.users.contains(from)){
                    if (!Client.newUsers.contains(from))
                        Client.newUsers.add(from);
                    SendMail.serversendEmail(from, "Reply for request control PC", "",
                            HTMLGenerator.generateHTML("You are not authorized", "",
                                    "Waiting for Server to accept your request "));
                }else {
                    System.out.println(choice[0]);

                    switch (choice[0].toLowerCase()) {
                        case "shutdown":
                            logActivities(from,"Shutting down your computer");
                            Shutdown.requestShutdown(from);
                            break;
                        case "restart":
                            logActivities(from,"Restarting your computer");
                            Restart.requestRestart( from);
                            break;
                        case "cancel":
                            logActivities(from,"Shutdown got canceled");
                            Shutdown.requestCancelShutdown();
                            break;
                        case "screenshot":
                            logActivities(from,"Taking a screenshot");
                            ScreenShot.requestScreenshot( from);
//                        new ReceiveMail().getAttachments(App.user);
                            break;
                        case "listprocess":
                            logActivities(from,"Sending a list of process ");
                            HandleProcess.requestListProcess(from);
//                        new ReceiveMail().getAttachments(App.user);
                            break;
                        case "startapp":
                            logActivities(from,"Starting " + choice[1]);
                            HandleProcess.requestStartApp( choice[1], from);
                            break;
                        case "stopapp":
                            logActivities(from,"Ending " + choice[1]);
                            HandleProcess.requestStopApp( choice[1], from);
                            break;
                        case "exploredirectory":
                            logActivities(from,"Entering " + choice[1]);
                            ExploreDirectory.requestExploreDir( choice[1], from);
                            break;
                        case "getfile[1]":
                            logActivities(from,"Getting " + choice[1]);
                            GetFile.searchFileInRoots(choice[1], from);
                            break;
                        case "getfile[2]":
                            logActivities(from,"Getting " + choice[1]);
                            GetFile.getFileByPath(choice[1], from);
                            break;
                        case "startkeylogger":
                            logActivities(from,"Starting key logger");
                            KeyLogger.startKeylogger(from);
                            break;
                        case "stopkeylogger":
                            logActivities(from,"Stopped key logger");
                            KeyLogger.stopKeylogger(from);
                            break;
                        case "help":
                            logActivities(from,"Send the application usage guide.");
                            GuideTable.requestGuide(from);
                            break;
                        case "listapp":
                            logActivities(from,"Sending a list of running apps ");
                            HandleProcess.requestListApplications( from);
                            break;
                        default:
                            //Sai thi khoi list nha
                            System.out.println("Something went wrong!");
                            HandleSubCase.handleWrongRequest(from);
//                        throw new AssertionError();
                            break;
                    }
                }

            }
        }
    }
}