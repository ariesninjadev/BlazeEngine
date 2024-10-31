package com.ariesninja.BlazeEngine.gui;

public class Framerate {
    private static final int UPDATE_INTERVAL = 100; // Update every 100 milliseconds
    private static final int AVERAGE_INTERVAL = 1000; // Average over 1 second
    private long[] frameTimes = new long[AVERAGE_INTERVAL / UPDATE_INTERVAL];
    private int frameTimeIndex = 0;
    private long lastTime = System.currentTimeMillis();
    private int frames = 0;
    private int framerate = 0;

    public void update() {
        frames++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= UPDATE_INTERVAL) {
            frameTimes[frameTimeIndex] = frames * (1000 / UPDATE_INTERVAL);
            frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
            frames = 0;
            lastTime = currentTime;

            // Calculate average FPS
            int totalFrames = 0;
            for (long frameTime : frameTimes) {
                totalFrames += frameTime;
            }
            framerate = totalFrames / frameTimes.length;
        }
    }

    public int getFramerate() {
        return framerate;
    }
}