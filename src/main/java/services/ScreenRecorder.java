//package services;
//
//import org.bytedeco.javacv.*;
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.javacv.Frame;
//import java.awt.*;
//import java.awt.image.*;
//import java.awt.image.BufferedImage;
//
//public class ScreenRecorder {
//
//    public static void recordScreen(int durationInSeconds) {
//        try (FrameRecorder recorder = new FFmpegFrameRecorder("./src/test/output/screenrecord.mp4", 1920, 1080)) {
//            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//            recorder.setFormat("mp4");
//            recorder.setFrameRate(20);
//            recorder.start();
//
//            long startTime = System.currentTimeMillis();
//            Robot robot = new Robot();
//            Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//            while (System.currentTimeMillis() - startTime < durationInSeconds * 1000) {
//                try {
//                    BufferedImage screenCapture = robot.createScreenCapture(captureSize);
//
//                    float[] scales = {1.5f, 1.0f, -10.0f}; // Giảm mức xanh
//                    float[] offsets = new float[3];
//                    RescaleOp op = new RescaleOp(scales, offsets, null);
//                    BufferedImage lessBlueImage = op.filter(screenCapture, null);
//
//                    Frame frame = convertImageToFrame(lessBlueImage);
//
//                    if (frame != null) {
//                        recorder.record(frame);
//                    }
//                    Thread.sleep(40);
//
//                } catch(InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            recorder.stop();
//
//        } catch (FrameRecorder.Exception | AWTException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static Frame convertImageToFrame(BufferedImage image) {
//        try {
//            Java2DFrameConverter converter = new Java2DFrameConverter();
//            return converter.convert(image);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//        int durationInSeconds = 10; // Đặt thời gian mặc định là 10 giây nếu không có tham số truyền vào từ dòng lệnh
//        if (args.length > 0) {
//            try {
//                durationInSeconds = Integer.parseInt(args[0]);
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//                System.err.println("Invalid duration format. Using default duration of 10 seconds.");
//            }
//        }
//        recordScreen(durationInSeconds);
//    }
//}