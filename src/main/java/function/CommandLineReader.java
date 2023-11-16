import java.util.Scanner;
import java.io.IOException;
import java.awt.AWTException;

public class CommandLineReader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nhập lệnh: ");
        String command = scanner.nextLine();

        switch (command.toUpperCase()) {
            case "SHUTDOWN":
                System.out.println("Tắt máy tính...");
                new Shutdown();
                break;
            case "SCREENSHOT":
                try {
                    ScreenCapture.captureScreen();
                } catch (IOException | AWTException ex) {
                    ex.printStackTrace();
                }
                break;
            case "TAKEKEY":
                new KeyCapture();
                break;
            case "PROCESS":
                System.out.println("Danh sách các tiến trình:");
                new ProcessManager().listProcesses();
                // Mở process nè
                System.out.println("Bạn muốn mở?");
                command = scanner.nextLine();
                new ProcessManager().startProcess(command);
                // Đóng process nè
                System.out.println("Bạn muốn đóng?");
                command = scanner.nextLine();
                new ProcessManager().stopProcess(command);
                break;
            case "GET FILE":
                new getFile();
                break;

            default:
                System.out.println("Lệnh không hợp lệ.");
        }
    }
}
