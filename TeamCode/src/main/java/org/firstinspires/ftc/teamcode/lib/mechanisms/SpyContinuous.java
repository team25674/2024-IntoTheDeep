package org.firstinspires.ftc.teamcode.lib.mechanisms;

import com.qualcomm.robotcore.hardware.Servo;

public class SpyContinuous {
    private final double UP_POSITION = .61;
    private final double DOWN_POSTION = .02;
    private final double STOP_SPEED = 0.5;



    private Servo wheel1;
    private Servo wheel2;
    private Servo upAndDown;

    public SpyContinuous(Servo wheel1, Servo wheel2, Servo upAndDown) {
        this.wheel1 = wheel1;
        this.wheel2 = wheel2;
        this.upAndDown = upAndDown;
    }

    // TODO: Make this continuous
    // Hint: accept the float value from the right trigger of controller 2
    public void intake(double speed) {
        wheel1.setDirection(Servo.Direction.FORWARD); //rightsticky needs to be between 0-1 instead of -1 and 1, so divide by one??Spy
        wheel2.setDirection(Servo.Direction.REVERSE);
        wheel1.setPosition(speed);
        wheel2.setPosition(speed);


    }

    // TODO: Make this continuous
    // Hint: accept the float value from the left trigger of controller 2
    public void reject(double speed) {
        wheel1.setDirection(Servo.Direction.FORWARD);
        wheel2.setDirection(Servo.Direction.REVERSE);
        wheel1.setPosition(speed);
        wheel2.setPosition(speed);
    }

    public void up() {
        upAndDown.setPosition(UP_POSITION);

    }

    public void down() {
        upAndDown.setPosition(DOWN_POSTION);
    }

    public void stop() {
        wheel1.setPosition(STOP_SPEED);
        wheel2.setPosition(STOP_SPEED);
    }
}


