package testsocket;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket serverSocket = new ServerSocket(5056);

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket socket = null;

            try
            {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(socket, dis, dos);

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }
    }
}

// ClientHandler class
class ClientHandler extends Thread
{

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket clientSocket;


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
    {
        this.clientSocket = s;
        this.inputStream = dis;
        this.outputStream = dos;
    }

    @Override
    public void run()
    {
        String received;
        String toreturn;
        while (true)
        {
            try {
                // receive the answer from client
                received = inputStream.readUTF();

                if(received.equals("Exit"))
                {
                    System.out.println("Client " + this.clientSocket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.clientSocket.close();
                    System.out.println("Connection closed");
                    break;
                }

                // write on output stream based on the
                // answer from the client
                switch (received) {

                    case "Keylog" :
                        break;

                    case "Time" :
                        break;

                    case "List Process":
                        break;

                    case "Screenshot":
                        break;

                    default:
                        outputStream.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.inputStream.close();
            this.outputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
