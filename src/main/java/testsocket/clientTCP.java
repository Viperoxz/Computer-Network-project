package testsocket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

class CheckMail extends Thread{
    String host = "pop.gmail.com";
    String storeType = "pop3";
    String username = "pvhoangnamzz@gmail.com";
    String appPassword = "drzd dpmu evff ejqj";
    String sender;
    ReceiveMail mailReceived;

//    public CheckMail(String sender){
//        this.sender = sender;
//        this.mailReceived = new ReceiveMail(host, storeType, username, appPassword);
//    }
//    public boolean check(Pair<String, String> newMail, List<Pair<String, String>> oldMail){
//        return true;
//    }
}

public class clientTCP {
    public static void main(String argv[]) throws Exception {
        String sentence_to_server;
        String sentence_from_server;

        //Tạo socket cho client kết nối đến server qua ID address và port number
        Socket clientSocket = new Socket("127.0.0.1", 6543);

        //Tạo OutputStream nối với Socket
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        //Tạo inputStream nối với Socket
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // Tạo input stream từ bàn phím
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // Lấy chuỗi ký tự nhập từ bàn phím
            System.out.print("Input from client: ");
            sentence_to_server = inFromUser.readLine();

            // Gửi chuỗi ký tự tới Server thông qua outputStream đã nối với Socket (ở trên)
            outToServer.writeBytes(sentence_to_server + '\n');

            // Đọc tin từ Server thông qua InputSteam đã nối với socket
            sentence_from_server = inFromServer.readLine();

            // In kết quả ra màn hình
            System.out.println("FROM SERVER: " + sentence_from_server);

            // Kiểm tra nếu client nhập "quit" thì kết thúc vòng lặp
            if ("quit".equals(sentence_to_server)) {
                break;
            }
        }

        // Đóng liên kết socket
        clientSocket.close();
    }
}

