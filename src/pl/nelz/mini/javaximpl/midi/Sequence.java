package pl.nelz.mini.javaximpl.midi;

import java.io.InputStream;

public class Sequence {

    private long direct;

    private static long loadSequence(InputStream stream) { return 0; }

    public Sequence(InputStream stream) {
        direct = loadSequence(stream);
    }

    public void setMicrosecondPosition(long position) { }

    public void start() { }

    public void stop() { }

    public void close() { }

    public void setTickPosition(long tickPosition) { }

    public void setLoopCount(int loopCount) { }

    public boolean isRunning() { return false; }
}
