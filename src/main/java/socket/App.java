package socket;

public class App {
    private Server server;
    private Client client;


    public App() {
        server = new Server();
        client = new Client();
    }

    public void startServer() {
        server.start();
    }

    public void startClient() {
        client.start();
    }

    public static void main(String[] args) {
        try {
            App app = new App();
            app.startServer();
            app.startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
