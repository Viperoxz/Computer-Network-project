import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ProcessManager {

    public static void listProcesses() {
        try {
            // Sử dụng ProcessBuilder để chạy lệnh liệt kê tiến trình
            System.out.println("Danh sách các tiến trình:");
            ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
            Process process = processBuilder.start();

            // Đọc đầu ra của tiến trình
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Đợi tiến trình kết thúc
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startProcess(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Đợi tiến trình kết thúc
            process.waitFor();

        } catch (IOException e) {
            // Xử lý khi lệnh không tồn tại
            System.err.println("Lỗi: process không tồn tại.");
        } catch (InterruptedException e) {
            // Xử lý khi quá trình đợi bị gián đoạn
            e.printStackTrace();
        }
    }

    public static void stopProcess(String processName) {
        try {
            // Sử dụng ProcessBuilder để chạy lệnh dừng tiến trình
            ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/F", "/IM", processName);
            Process process = processBuilder.start();

            // Đợi tiến trình kết thúc
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Liệt kê các tiến trình
        listProcesses();

        // Khởi động một tiến trình (ví dụ: Notepad)
        System.out.println("\nKhởi động tiến trình Notepad:");
        startProcess("notepad.exe");

        // Dừng tiến trình Notepad
        System.out.println("\nDừng tiến trình Notepad:");
        stopProcess("notepad.exe");
    }
}
