import java.io.IOException;

public class Shutdown {

    public Shutdown() {
        System.out.println("Tắt máy tính...");
        shutdown();
    }

    public void shutdown() {
        String os = System.getProperty("os.name").toLowerCase();

        String shutdownCommand;
        if (os.contains("win")) {
            shutdownCommand = "shutdown.exe -s -t 0";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            shutdownCommand = "shutdown -h now";
        } else {
            System.out.println("Không thể xác định hệ điều hành hỗ trợ tắt máy tính.");
            return;
        }

        try {
            Process process = Runtime.getRuntime().exec(shutdownCommand);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Shutdown();
    }
}