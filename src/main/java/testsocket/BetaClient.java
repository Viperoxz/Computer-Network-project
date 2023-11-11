package testsocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

class HandleMail {
    private String host = "imap.gmail.com";
    //    private String storeType = "imap";
    private String username = "pvhoangnamzz@gmail.com";
    private String appPassword = "drzd dpmu evff ejqj";
    public List<CustomPair<String, String>> commands;
    String sender;
    public HandleMail() {
        ReceiveMail mailReceived = new ReceiveMail(host, username, appPassword);
        this.commands = mailReceived.getRequirements();
    }
}

public class BetaClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            ReceiveMail mailReceived = new ReceiveMail("imap.gmail.com", "pvhoangnamzz@gmail.com", "drzd dpmu evff ejqj");
            List<CustomPair<String, String>> commands = mailReceived.getRequirements();

            Scanner scanner = new Scanner(System.in);
            boolean isRunning = true;

//            while(isRunning) {
//                System.out.println("\nMENU: ");
//                System.out.println("1. Shutdown");
//                System.out.println("2. Restart");
//                System.out.println("3. Cancel Shutdown/Restart");
//                System.out.println("4. Screenshot");

                for (CustomPair<String, String>cmd : commands) {
                    String choice = cmd.getValue();
                    System.out.println(choice);
//                scanner.nextLine();
                    switch (choice.toLowerCase()) {
                        case "shutdown":
                            writer.println("shutdown"); //Goi len server
                            writer.flush();
                            System.out.println(reader.readLine());
                            break;
                        case "restart":
                            writer.println("restart"); //Goi len server
                            writer.flush();
                            System.out.println(reader.readLine());
                            break;
//                        case "cancel":
//                            writer.println("cancel"); //Goi len server
//                            writer.flush();
//                            System.out.println(reader.readLine());
//                            break;
//                        case "screenshot":
//                            writer.println("screenshot"); //Goi len server
//                            writer.flush();
//                            int imgsSize = Integer.parseInt(reader.readLine());
//                            byte[] imgBytes = new byte[imgsSize];
//                            int readByte = socket.getInputStream().read(imgBytes);
//                            if (readByte > 0) {
//                                System.out.println("Nhap ten anh: ");
//                                String filename = scanner.nextLine();
//
//                                Path imgPath = Paths.get(filename + ".png");
//                                Files.write(imgPath, imgBytes);
//                                System.out.println("Done!");
//                            }
                        default:
                            System.out.println("fault");
                            throw new AssertionError();
                    }
                }
//            }
        }
        catch (Exception e){
        }
    }
}
