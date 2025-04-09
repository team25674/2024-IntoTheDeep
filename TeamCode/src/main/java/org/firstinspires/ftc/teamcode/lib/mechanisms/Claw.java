package org.firstinspires.ftc.teamcode.lib.mechanisms;

import static org.firstinspires.ftc.teamcode.lib.TelemetryManager.CLAW_SERVO_POS;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.lib.TelemetryManager;

import java.util.HashMap;
import java.util.Map;

public class Claw {
    private final double GRAB_POSITION = 0.6;
    private final double BASKET_POSTION = 0;
    private final double OPEN_POSITION_CLAWSERVO = .6;
    private final double CLOSE_POSITION_CLAWSERVO = .9;

    private Servo clawServo;
    private Servo rotateServo;
    private TelemetryManager telemetryManager;

    public Claw(Servo clawServo, Servo rotateServo, TelemetryManager telemetryManager){
        this.clawServo = clawServo;
        this.rotateServo = rotateServo;
        this.telemetryManager = telemetryManager;
    }


    public void close() {
        clawServo.setPosition(CLOSE_POSITION_CLAWSERVO);
    }
    public void open() {
        clawServo.setPosition(OPEN_POSITION_CLAWSERVO);
    }
    public void down() {
        rotateServo.setPosition(GRAB_POSITION);
        telemetryManager.addTelemetry(CLAW_SERVO_POS,Double.toString(clawServo.getPosition()));
    }
    public void up() {
        rotateServo.setPosition(BASKET_POSTION);
        telemetryManager.addTelemetry(CLAW_SERVO_POS, Double.toString(clawServo.getPosition()));
    }
}
