package com.ariesninja.BlazeEngine.visual;

import com.ariesninja.BlazeEngine.Camera;
import com.ariesninja.BlazeEngine.Client;

import java.awt.event.KeyEvent;

public class ClientControl {

    public static void controlCamera(Client client) {
        // No changes needed here
    }

    public static void handleKeyPress(Client client, KeyEvent e) {
        Camera c = client.getCamera();
        double yaw = Math.toRadians(c.getPose().getRotation().getRY());
        double pitch = Math.toRadians(c.getPose().getRotation().getRX());

        // Calculate forward, right, and up vectors
        double forwardX = Math.cos(yaw) * Math.cos(pitch);
        double forwardY = Math.sin(pitch);
        double forwardZ = Math.sin(yaw) * Math.cos(pitch);

        double rightX = Math.sin(yaw);
        double rightY = 0;
        double rightZ = -Math.cos(yaw);

        double upX = -Math.cos(yaw) * Math.sin(pitch);
        double upY = Math.cos(pitch);
        double upZ = -Math.sin(yaw) * Math.sin(pitch);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_E:
                c.getPose().move(upX, upY, upZ);
                break;
            case KeyEvent.VK_Q:
                c.getPose().move(-upX, -upY, -upZ);
                break;
            case KeyEvent.VK_W:
                c.getPose().move(forwardX, forwardY, forwardZ);
                break;
            case KeyEvent.VK_S:
                c.getPose().move(-forwardX, -forwardY, -forwardZ);
                break;
            case KeyEvent.VK_A:
                c.getPose().move(-rightX, -rightY, -rightZ);
                break;
            case KeyEvent.VK_D:
                c.getPose().move(rightX, rightY, rightZ);
                break;
            case KeyEvent.VK_UP:
                c.getPose().rotate(2, 0, 0);
                break;
            case KeyEvent.VK_DOWN:
                c.getPose().rotate(-2, 0, 0);
                break;
            case KeyEvent.VK_LEFT:
                c.getPose().rotate(0, 2, 0);
                break;
            case KeyEvent.VK_RIGHT:
                c.getPose().rotate(0, -2, 0);
                break;
        }
    }
}