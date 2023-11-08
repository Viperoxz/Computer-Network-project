import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;

public class KeyCapture extends JFrame {

    private final ArrayList<String> pressedKeys = new ArrayList<>();

    public KeyCapture() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Không sử dụng trong trường hợp này
            }

            @Override
            public void keyPressed(KeyEvent e) {
                String keyText = KeyEvent.getKeyText(e.getKeyCode());
                System.out.println("Phím " + keyText + " được nhấn.");
                pressedKeys.add(keyText);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Không sử dụng trong trường hợp này
            }
        });

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Tạo một luồng mới để theo dõi sự kiện phím trong 5 giây
        Thread keyCaptureThread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // In ra các phím đã được nhấn sau khi kết thúc vòng lặp
            System.out.println("Các phím đã được nhấn trong 5 giây:");
            for (String key : pressedKeys) {
                System.out.println(key);
            }

            // Đóng cửa sổ JFrame sau khi đã chạy trong 5 giây
            dispose();
        });

        // Bắt đầu luồng để theo dõi sự kiện phím
        keyCaptureThread.start();

        // Vòng lặp để theo dõi sự kiện phím trong khi cửa sổ JFrame còn mở
        while (isVisible()) {
            // Thực hiện bất kỳ công việc cụ thể nếu cần
        }
    }

    public static void main(String[] args) {
        new KeyCapture();
    }
}
