package testsocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BetaClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            boolean isRunning = true;

            while(isRunning){
                System.out.println("\nMENU: ");
                System.out.println("1. Shutdown");
                System.out.println("2. Restart");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        writer.println("shutdown"); //Goi len server
                        System.out.println(reader.readLine());
                        break;
                    case 2:
                        writer.println("restart"); //Goi len server
                        System.out.println(reader.readLine());
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }
        catch (Exception e){
        }
    }
}
