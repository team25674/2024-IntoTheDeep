package org.firstinspires.ftc.teamcode.lib.mechanisms;

import com.qualcomm.robotcore.hardware.Servo;

public class SpyContinuous {
    private final double UP_POSITION = 1;
    private final double DOWN_POSTION = .43;
    private final double REJECT_POSITION_WHEEL1 = .1;
    private final double REJECT_POSITION_WHEEL2 = .9;
    private final double INTAKE_POSITION_WHEEL1 = .9;
    private final double INTAKE_POSITION_WHEEL2 = .1;

    private Servo wheel1;
    private Servo wheel2;
    private Servo upAndDown;

    public SpyContinuous(Servo wheel1, Servo wheel2, Servo upAndDown){
        this.wheel1 = wheel1;
        this.wheel2 = wheel2;
        this.upAndDown = upAndDown;
    }
    public void intake() {
        wheel1.setPosition(INTAKE_POSITION_WHEEL1); //rightsticky needs to be between 0-1 instead of -1 and 1, so divide by one??Spy
        wheel2.setPosition(INTAKE_POSITION_WHEEL2);

    }
    public void reject() {
        wheel1.setPosition(REJECT_POSITION_WHEEL1);
        wheel2.setPosition(REJECT_POSITION_WHEEL2);

    }
    public void up() {
        upAndDown.setPosition(UP_POSITION);

    }
    public void down() {
        upAndDown.setPosition(DOWN_POSTION);

    }
}