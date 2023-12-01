package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import services.*;

public class Client {
    private Socket socket;

    public void start() {
        try {
            socket = new Socket("localhost", 5000);

            Timer timer = new Timer();
            Task task = new Task(socket);

            // Schedule the task to run every 10 seconds
            timer.scheduleAtFixedRate(task, 0, 20 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Task extends TimerTask {
        private final Socket socket;
        private final BufferedReader reader;
        private final PrintWriter writer;

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

            for (CustomPair<String, String> cmd : commands) {
                String[] choice = cmd.getValue().split("&&");
                String from = cmd.getKey();
                System.out.println(choice);

                switch (choice[0].toLowerCase()) {
                    case "shutdown":
                        Shutdown.requestShutdown(reader, writer, from);
                        break;
                    case "restart":
                        Restart.requestRestart(reader, writer, from);
                        break;
                    case "cancel":
                        Shutdown.requestCancelShutdown(reader, writer);
                        break;
                    case "screenshot":
                        ScreenShot.requestScreenshot(socket, writer, reader, from);
                        break;
                    case "listprocess":
                        HandleProcess.requestListProcess(socket, writer, from);
                        break;
                    case "startapp":
                        HandleProcess.requestStartApp(socket, writer, choice[1], from);
                        break;
                    case "stopapp":
                        HandleProcess.requestStopApp(socket, writer, choice[1], from);
                        break;
                    case "exploredirectory":
                        ExploreDirectory.requestExploreDir(socket, writer, choice[1], from);
                        break;
                    case "getfile[1]":
                        GetFile.searchFileInRoots(choice[1], from);
                    default:
                        System.out.println("Something went wrong!");
                        throw new AssertionError();
                }
            }
        }
    }
}

