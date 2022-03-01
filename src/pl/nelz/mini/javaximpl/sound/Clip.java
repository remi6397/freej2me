package pl.nelz.mini.javaximpl.sound;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Clip {

    private static final int BUFFER_SIZE = 1024;

    private long direct;

    private static long loadClip(InputStream stream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int read = 0;
        try {
            while ((read = stream.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadClip(baos.toByteArray());
    }

    private static native long loadClip(byte[] data);

    public Clip(InputStream stream) {
        direct = loadClip(stream);
    }

    public Clip(byte[] data) {
        direct = loadClip(data);
    }

    public void setMicrosecondPosition(long position) { }

    public long getMicrosecondPosition() { return 0; }

    public void start() { }

    public void stop() { }

    public void close() { }

    public void setFramePosition(long tickPosition) { }

    public void loop(int loopCount) { }

    public boolean isRunning() { return false; }
}
