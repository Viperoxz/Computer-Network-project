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
import services.ScreenShot;

class CustomPair<K, V> {
    private K key;
    private V value;

    private CustomPair(){}

    public CustomPair(K k, V v) {
        this.key = k;
        this.value = v;
    }
    public static <K, V> CustomPair of (K key, V value) {
        return new CustomPair<>(key, value);
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return value;
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

            for (CustomPair<String, String> cmd : commands) {
                String choice = cmd.getValue();
                String from = cmd.getKey();
                System.out.println(choice);
//                scanner.nextLine();
                switch (choice.toLowerCase()) {
                    case "shutdown":
                        SendMail.sendEmail(from, "Reply for request: Shutdown", "<!DOCTYPE html>\n" +
                                "<html>\n" +
                                "<head>\n" +
                                "<title>Page Title</title>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                "\n" +
                                "<h1>Your request has done successfully</h1>\n" +
                                "<p>This is a paragraph.</p>\n" +
                                "\n" +
                                "<img src=\"https://img.cdn-pictorem.com/uploads/collection/I/IB5PAB9RBI/900_Anime_7_1608090041.5705.jpg\" alt=\"Naruto\">+" +
                                "</body>\n" +
                                "</html>");
                        writer.println("shutdown"); //Goi len server
                        writer.flush();
                        System.out.println(reader.readLine());
                        break;
                    case "restart":
                        SendMail.sendEmail(from, "Reply for request: Restart", "<!DOCTYPE html>\n" +
                                "<html>\n" +
                                "<head>\n" +
                                "<title>Page Title</title>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                "\n" +
                                "<h1>Your request has done successfully</h1>\n" +
                                "<p>This is a paragraph.</p>\n" +
                                "\n" +
                                "<img src=\"https://img.cdn-pictorem.com/uploads/collection/I/IB5PAB9RBI/900_Anime_7_1608090041.5705.jpg\" alt=\"Naruto\">+" +
                                "</body>\n" +
                                "</html>");
                        writer.println("restart"); //Goi len server
                        writer.flush();
                        System.out.println(reader.readLine());
                        break;
//                        case "cancel":
//                            writer.println("cancel"); //Goi len server
//                            writer.flush();
//                            System.out.println(reader.readLine());
//                            break;
                    case "screenshot":
                        writer.println("screenshot"); //Goi len server
                        writer.flush();
                        int imgsSize = Integer.parseInt(reader.readLine());
                        byte[] imgBytes = new byte[imgsSize];
                        int readByte = socket.getInputStream().read(imgBytes);
                        if (readByte > 0) {
                            System.out.println("Nhap ten anh: ");
                            String filename = scanner.nextLine();

                            Path imgPath = Paths.get("D:\\"+".png");
                            Files.write(imgPath, imgBytes);
                            System.out.println("Done!");
                        }
                    default:
                        System.out.println("fault");
                        throw new AssertionError();
                }
            }
        } catch (Exception e) {
        }
    }
}
