package testsocket;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.io.*;
import java.net.*;

public class serverTCP {
    public static void main(String argv[]) throws Exception {
        String sentence_from_client;
        String sentence_to_client;

        //while(true){}
        // Tạo socket server, chờ tại cổng '6543'
        ServerSocket welcomeSocket = new ServerSocket(6543);


        // Chờ yêu cầu từ client
        Socket connectionSocket = welcomeSocket.accept();

        // Tạo input stream, nối tới Socket
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

        // Tạo outputStream, nối tới socket
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

        while (true) {
            // Đọc thông tin từ socket
            sentence_from_client = inFromClient.readLine();

            if (sentence_from_client == null || "quit".equalsIgnoreCase(sentence_from_client)) {
                // Đóng kết nối với client hiện tại
                connectionSocket.close();
                System.out.println(1);
                return; // Thoát khỏi vòng lặp và chờ kết nối mới
            }

            sentence_to_client = sentence_from_client + " (Server accepted!)" + '\n';
            // Ghi dữ liệu ra socket
            outToClient.writeBytes(sentence_to_client);

        }
    }
}

