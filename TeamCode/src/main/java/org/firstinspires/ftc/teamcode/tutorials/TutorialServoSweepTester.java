
package org.firstinspires.ftc.teamcode.tutorials;

//(use for later)import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TutorialServoSweepTester", group = "Tutorials")
public class TutorialServoSweepTester extends LinearOpMode {

    private static final int POLLING_INTERVAL_MS = 250;
    private static final double SERVO_INCREMENT = 0.05;

    @Override
    public void runOpMode() {
        // Test servo
        Servo servo = hardwareMap.get(Servo.class, "clawServo");

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Working variables
        long lastExecutionTime = System.currentTimeMillis();
        double servoPosition = 0;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Has polling interval elapsed?
            long currentTime = System.currentTimeMillis();
            if(currentTime - lastExecutionTime > POLLING_INTERVAL_MS){
                lastExecutionTime = currentTime; // reset timer

                // Handle buttons
                if(gamepad1.dpad_up){
                    servoPosition += SERVO_INCREMENT;
                    if(servoPosition > 1.0){
                        servoPosition = 1.0;
                    }
                    servo.setPosition(servoPosition);
                }
                if(gamepad1.dpad_down){
                    servoPosition -= SERVO_INCREMENT;
                    if(servoPosition < 0){
                        servoPosition = 0;
                    }
                    servo.setPosition(servoPosition);
                }
            }

            telemetry.addData("servo position", servo.getPosition());
            telemetry.update();
        }
    }
}
