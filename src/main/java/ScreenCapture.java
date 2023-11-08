import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ScreenCapture {
    public static void captureScreen() throws AWTException, IOException {
        // Tạo đối tượng Robot để chụp màn hình
        Robot robot = new Robot();

        // Lấy kích thước màn hình
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

        // Chụp màn hình
        BufferedImage screenCapture = robot.createScreenCapture(screenRect);

        // Lưu hình ảnh vào một tệp tin
        File outputFile = new File("screenshot.png");
        ImageIO.write(screenCapture, "png", outputFile);

        System.out.println("Chụp màn hình thành công. Đã lưu vào: " + outputFile.getAbsolutePath());
    }
}
