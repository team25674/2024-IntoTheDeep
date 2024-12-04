package org.firstinspires.ftc.teamcode.lib.mechanisms;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final double UP_POSITION = 1;
    private final double DOWN_POSTION = .43;
    private final double OPEN_POSITION_CLAWSERVO = .1;
    private final double CLOSE_POSITION_CLAWSERVO = .9;

    private Servo clawServo;
    private Servo upAndDown;

    public Claw(Servo clawServo, Servo upAndDown){
        this.clawServo = clawServo;
        this.upAndDown = upAndDown;
    }
    public void close() {
        clawServo.setPosition(CLOSE_POSITION_CLAWSERVO);
    }
    public void open() {
        clawServo.setPosition(OPEN_POSITION_CLAWSERVO);
    }
    public void up() {
        upAndDown.setPosition(UP_POSITION);

    }
    public void down() {
        upAndDown.setPosition(DOWN_POSTION);

    }
}