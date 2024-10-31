package com.ariesninja.BlazeEngine.gui;

import com.ariesninja.BlazeEngine.structs.Camera;
import com.ariesninja.BlazeEngine.Client;

import java.awt.event.KeyEvent;

public class ClientControl {

    private static final double BASE_MOVEMENT_SPEED = 0.1;
    private static final double BASE_ROTATION_SPEED = 1.1;
    private static final double FINE_MOVEMENT_SPEED = 0.01;
    private static final double FINE_ROTATION_SPEED = 0.1;
    private static boolean moveForward, moveBackward, moveRight, moveLeft, moveUp, moveDown;
    private static boolean rotateUp, rotateDown, rotateLeft, rotateRight;
    private static boolean shiftPressed;

    public static void controlCamera(Client client) {
        Camera c = client.getCamera();
        double yaw = Math.toRadians(c.getPose().getRotation().getRY());
        double pitch = Math.toRadians(c.getPose().getRotation().getRX());

        double movementSpeed = shiftPressed ? FINE_MOVEMENT_SPEED : BASE_MOVEMENT_SPEED;
        double rotationSpeed = shiftPressed ? FINE_ROTATION_SPEED : BASE_ROTATION_SPEED;

        // Calculate direction vectors
        double forwardX = -Math.sin(yaw) * Math.cos(pitch);
        double forwardY = Math.sin(pitch);
        double forwardZ = Math.cos(yaw) * Math.cos(pitch);

        double rightX = Math.cos(yaw);
        double rightZ = Math.sin(yaw);

        double upX = Math.sin(yaw) * Math.sin(pitch);
        double upY = Math.cos(pitch);
        double upZ = -Math.cos(yaw) * Math.sin(pitch);

        // Update position based on key states
        if (moveForward) c.getPose().move(forwardX * movementSpeed, forwardY * movementSpeed, forwardZ * movementSpeed);
        if (moveBackward) c.getPose().move(-forwardX * movementSpeed, -forwardY * movementSpeed, -forwardZ * movementSpeed);
        if (moveRight) c.getPose().move(rightX * movementSpeed, 0, rightZ * movementSpeed);
        if (moveLeft) c.getPose().move(-rightX * movementSpeed, 0, -rightZ * movementSpeed);
        if (moveUp) c.getPose().move(upX * movementSpeed, upY * movementSpeed, upZ * movementSpeed);
        if (moveDown) c.getPose().move(-upX * movementSpeed, -upY * movementSpeed, -upZ * movementSpeed);

        // Update rotation based on key states
        if (rotateUp && c.getPose().getRotation().getRX() < 90) {
            c.getPose().rotate(rotationSpeed, 0, 0);
        } else if (rotateUp) {
            c.getPose().setRotation(90, c.getPose().getRotation().getRY(), c.getPose().getRotation().getRZ());
        }
        if (rotateDown && c.getPose().getRotation().getRX() > -90) {
            c.getPose().rotate(-rotationSpeed, 0, 0);
        } else if (rotateDown) {
            c.getPose().setRotation(-90, c.getPose().getRotation().getRY(), c.getPose().getRotation().getRZ());
        }
        if (rotateLeft) c.getPose().rotate(0, rotationSpeed, 0);
        if (rotateRight) c.getPose().rotate(0, -rotationSpeed, 0);
    }

    public static void handleKeyPress(Client client, KeyEvent e, boolean pressed) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> moveForward = pressed;
            case KeyEvent.VK_S -> moveBackward = pressed;
            case KeyEvent.VK_A -> moveLeft = pressed;
            case KeyEvent.VK_D -> moveRight = pressed;
            case KeyEvent.VK_E -> moveUp = pressed;
            case KeyEvent.VK_Q -> moveDown = pressed;
            case KeyEvent.VK_UP -> rotateUp = pressed;
            case KeyEvent.VK_DOWN -> rotateDown = pressed;
            case KeyEvent.VK_LEFT -> rotateLeft = pressed;
            case KeyEvent.VK_RIGHT -> rotateRight = pressed;
            case KeyEvent.VK_SHIFT -> shiftPressed = pressed;
        }
    }

    public static void handleKeyReleased(KeyEvent e) {
        handleKeyPress(null, e, false);
    }
}