package testsocket;


import javax.lang.model.type.NullType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import services.*;


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

            for (CustomPair<String, String> cmd : commands) {
                String choice = cmd.getValue();
                String from = cmd.getKey();
                System.out.println(choice);

//                scanner.nextLine();
                switch (choice.toLowerCase()) {
                    case "shutdown":
                        Shutdown.handleShutdown(reader, writer, from);
                        break;

                    case "restart":
                        Restart.handelRestart(reader, writer, from);
                        break;

                    case "cancel":
                        Shutdown.handleCancelShutdown(reader, writer);
                        break;

                    case "screenshot":
                        ScreenShot.handleScreenshot(socket, writer, reader, from);
                        break;
                    default:
                        System.out.println("fault");
                        throw new AssertionError();
                }
            }
        } catch (Exception e) {
        }
    }
}
