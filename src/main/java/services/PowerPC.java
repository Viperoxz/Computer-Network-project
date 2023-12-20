package services;


import java.io.IOException;

public class PowerPC {
    public static void requestRestart() throws IOException {
        Runtime.getRuntime().exec("shutdown -r -t 5");
    }


    public static void requestShutdown() throws IOException{
        Runtime.getRuntime().exec("shutdown -s -t 5");
    }


    public static void requestLogout(){
        try {
            Runtime.getRuntime().exec("shutdown -l");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void requestSleep(){
        try{
            Runtime.getRuntime().exec("shutdown -h");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void requestCancelShutdown() throws IOException{
        Runtime.getRuntime().exec("shutdown -a");
    }
}
