
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
        boolean isQueued = false;
        double queuedPosition = 0;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Handle buttons
            if(gamepad1.dpad_up && !isQueued){
                servoPosition += SERVO_INCREMENT;
                if(servoPosition > 1.0){
                    servoPosition = 1.0;
                }
                isQueued = true;
            }
            if(gamepad1.dpad_down && !isQueued){
                servoPosition -= SERVO_INCREMENT;
                if(servoPosition < 0){
                    servoPosition = 0;
                }
                isQueued = true;
            }

            // Has polling interval elapsed?
            long currentTime = System.currentTimeMillis();
            if(currentTime - lastExecutionTime > POLLING_INTERVAL_MS){
                lastExecutionTime = currentTime; // reset timer
                servo.setPosition(servoPosition);
                isQueued = false;
            }

            telemetry.addData("servo position", servo.getPosition());
            telemetry.update();
        }
    }
}
