package pl.nelz.choklad;

import org.recompile.mobile.Mobile;
import org.recompile.mobile.MobilePlatform;

import java.util.Set;

public class Choklad {

    private static volatile boolean willLoadJar = false;

    private static Thread runJarThread = null;

    public static void main(String[] args) {
        System.exit(uiMain(args));
    }

    public static void resetPlatform(int lcdWidth, int lcdHeight) {
        System.out.println("Reset to width: " + lcdWidth + ", height: " + lcdHeight);
        Mobile.setPlatform(new MobilePlatform(lcdWidth, lcdHeight));
        Mobile.getPlatform().setPainter(() -> onPaint(Mobile.getPlatform().getLCD()));
    }

    public static void loadJar(String jarurl) {
        System.out.println("Load jar: " + jarurl);
        if (willLoadJar)
            return;
        willLoadJar = true;

        new Thread(() -> {
            try {
                if (Mobile.getPlatform().loadJar(jarurl)) {
                    System.out.println("Jar load success: " + jarurl);
                    onJarLoaded();
                } else {
                    System.out.println("Jar load failed: " + jarurl);
                    onJarLoadFailed("Unable to load the specified JAR file");
                }
            } catch (Exception e) {
                e.printStackTrace();
                onJarLoadFailed(e.getMessage());
            } finally {
                willLoadJar = false;
            }
        }).start();
    }

    public static boolean isRunning() {
        return runJarThread != null && runJarThread.isAlive();
    }

    public static void runJar() {
        if (runJarThread != null && runJarThread.isAlive())
            runJarThread.stop();

        runJarThread = new Thread(() -> {
            try {
                Mobile.getPlatform().runJar();
            } catch (Exception e) {
                e.printStackTrace();
                onJarError(e.getMessage());
            }
        });
        runJarThread.start();
    }

    public static native void onPaint(long source);

    public static native void onJarLoaded();

    public static native void onJarError(String e);

    public static native void onJarExited();

    public static native void onJarLoadFailed(String e);

    public static native int uiMain(String[] args);
}
