package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.nio.charset.StandardCharsets;
import services.HandleProcess;
import services.Restart;
import services.ScreenShot;
import services.Shutdown;

public class BetaClient {
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;

    public void main(String[] args) {
        try {
            // Establish socket connection
            socket = new Socket("localhost", 5000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

            // Schedule a task to run every 30 seconds
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new Task(), 0, 30 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Task extends TimerTask {
        @Override
        public void run() {
            try {
                ReceiveMail mailReceived = new ReceiveMail("imap.gmail.com", "pvhoangnamzz@gmail.com", "drzd dpmu evff ejqj");
                List<CustomPair<String, String>> commands = mailReceived.getRequirements();

                for (CustomPair<String, String> cmd : commands) {
                    String[] choice = cmd.getValue().split(":");
                    String from = cmd.getKey();
                    System.out.println(choice);

                    switch (choice[0].toLowerCase()) {
                        case "shutdown":
                            Shutdown.handleShutdown(reader, writer, from);
                            break;
                        case "restart":
                            Restart.handleRestart(reader, writer, from);
                            break;
                        case "cancel":
                            Shutdown.handleCancelShutdown(reader, writer);
                            break;
                        case "screenshot":
                            ScreenShot.handleScreenshot(socket, writer, reader, from);
                            break;
                        case "listprocess":
                            HandleProcess.handleListProcess(socket, writer, from);
                            break;
                        case "startapp":


                        default:
                            System.out.println("Something went wrong!");
                            throw new AssertionError();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
