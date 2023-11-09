package testsocket;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;


//Create Pair class
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



//    public CheckMail(String sender){
//        this.sender = sender;
//        this.mailReceived = new ReceiveMail(host, storeType, username, appPassword);
//    }
//    public boolean check(Pair<String, String> newMail, List<Pair<String, String>> oldMail){
//        return true;
//    }
}

//public class Client {
//    public static void main(String argv[]) throws Exception {
//        String sentence_to_server;
//        String sentence_from_server;
//
//        //Tạo socket cho client kết nối đến server qua ID address và port number
//        Socket clientSocket = new Socket("127.0.0.1", 6543);
//
//        //Tạo OutputStream nối với Socket
//        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//
//        //Tạo inputStream nối với Socket
//        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//        // Tạo input stream từ bàn phím
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//
//        while (true) {
//            // Lấy chuỗi ký tự nhập từ bàn phím
//            System.out.print("Input from client: ");
//            sentence_to_server = inFromUser.readLine();
//
//            // Gửi chuỗi ký tự tới Server thông qua outputStream đã nối với Socket (ở trên)
//            outToServer.writeBytes(sentence_to_server + '\n');
//
//            // Đọc tin từ Server thông qua InputSteam đã nối với socket
//            sentence_from_server = inFromServer.readLine();
//
//            // In kết quả ra màn hình
//            System.out.println("FROM SERVER: " + sentence_from_server);
//
//            // Kiểm tra nếu client nhập "quit" thì kết thúc vòng lặp
//            if ("quit".equals(sentence_to_server)) {
//                break;
//            }
//        }
//
//        // Đóng liên kết socket
//        clientSocket.close();
//    }
//}
public class Client {
    public static void main(String[] args) throws IOException {
        try {
            Scanner scn = new Scanner(System.in);

            // get localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket socket = new Socket(ip, 5056);

            // obtaining input and out streams
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while (true) {
                System.out.println(inputStream.readUTF());
                String tosend = scn.nextLine();
                outputStream.writeUTF(tosend);

                // If client sends exit,close this connection
                // and then break from the while loop
                if (tosend.toLowerCase().equals("exit")) {
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                // printing date or time as requested by client
                String received = inputStream.readUTF();
                System.out.println(received);
            }

            // closing resources
            scn.close();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//        HandleMail test = new HandleMail();
//        System.out.println(test.commands.size());
}

