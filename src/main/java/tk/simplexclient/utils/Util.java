package tk.simplexclient.utils;

public class Util {
    public static boolean isDevEnv(){
        return Boolean.getBoolean("fabric.development");
    }
}
