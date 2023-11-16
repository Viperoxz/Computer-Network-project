import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class KeyCapture extends JFrame {

    private final List<String> pressedKeys = new CopyOnWriteArrayList<>();

    public KeyCapture() {
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                // Unused in this case
            }

            public void keyPressed(KeyEvent e) {
                String keyText = KeyEvent.getKeyText(e.getKeyCode());
                System.out.println("Phím " + keyText + " được nhấn.");
                pressedKeys.add(keyText);
            }

            public void keyReleased(KeyEvent e) {
                // Unused in this case
            }
        });

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Use Timer for better performance
        Timer timer = new Timer(5000, actionEvent -> {
            // In ra các phím đã được nhấn sau khi kết thúc vòng lặp
            System.out.println("Các phím đã được nhấn trong 5 giây:");
            for (String key : pressedKeys) {
                System.out.print(key + "   ");
            }

            // Đóng cửa sổ JFrame sau khi đã chạy trong 5 giây
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KeyCapture());
    }
}
