package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import services.HandleProcess;
import services.Restart;
import services.ScreenShot;
import services.Shutdown;

public class IntegratedApp {

    public static void main(String[] args) {
        IntegratedApp app = new IntegratedApp();
        app.startServer();
        app.startClient();
    }

    //Server
    public void startServer() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(5000);
                System.out.println("Server started...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
                    handleClientRequest(socket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void handleClientRequest(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String request = reader.readLine();
                System.out.println("Received request: " + request);

                // Example server response handling
                switch (request.toLowerCase()) {
                    case "shutdown":
                        Shutdown.controlShutdown(writer);
                        break;
                    case "restart":
                        Restart.controlRestart(writer);
                        break;
                    case "cancel":
                        Shutdown.controlCancelShtudown(writer);
                        break;
                    case "screenshot":
                        ScreenShot.takeScreenshot(socket, writer);
                        break;
                    case "listprocess":
                        HandleProcess.controlListProcess(writer);
                        break;
                    case "startapp":
                        HandleProcess.controlStartApp(reader, writer);
                    default:
                        writer.println("Unknown command: " + request);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Request not found!");
        }
    }


    //Client
    public void startClient() {
        try {
            Socket socket = new Socket("localhost", 5000);

            Timer timer = new Timer();
            Task task = new Task(socket);

            // Schedule the task to run every 10 seconds
            timer.scheduleAtFixedRate(task, 0, 30 * 1000);
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
                processEmailCommands(socket, reader, writer);

//                writer.close();
//                reader.close();
//                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void processEmailCommands(Socket socket, BufferedReader reader, PrintWriter writer) throws Exception {
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
                    default:
                        System.out.println("Something went wrong!");
                        throw new AssertionError();
                }
            }
        }
    }
}
