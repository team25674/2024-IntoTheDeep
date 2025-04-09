package org.firstinspires.ftc.teamcode.lib.mechanisms;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final double GRAB_POSITION = .6;
    private final double BASKET_POSTION = -.1;
    private final double OPEN_POSITION_CLAWSERVO = .6;
    private final double CLOSE_POSITION_CLAWSERVO = .9;

    private Servo clawServo;
    private Servo rotateServo;


    public Claw(Servo clawServo, Servo rotateServo){
        this.clawServo = clawServo;
        this.rotateServo = rotateServo;
    }
    public void close() {
        clawServo.setPosition(CLOSE_POSITION_CLAWSERVO);
    }
    public void open() {
        clawServo.setPosition(OPEN_POSITION_CLAWSERVO);
    }
    public void down() {
        rotateServo.setPosition(GRAB_POSITION);

    }
    public void up() {
        rotateServo.setPosition(BASKET_POSTION);

    }
}
