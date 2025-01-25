package org.firstinspires.ftc.teamcode.tutorials;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TutorialAutoMove", group = "Tutorials")
public class TutorialAutoMoveTime extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private DcMotor frontLeftDrive = null;
    private DcMotor rearLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor rearRightDrive = null;

    private void stopMotors() {
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        rearLeftDrive.setPower(0);
        rearRightDrive.setPower(0);
    }

    public void autoMove2(double timeSeconds, double frontLeftPower, double frontRightPower, double rearLeftPower, double rearRightPower) {
        // reset countdown timers
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();

        // power motors
        frontLeftDrive.setPower(frontLeftPower);
        frontRightDrive.setPower(frontRightPower);
        rearLeftDrive.setPower(rearLeftPower);
        rearRightDrive.setPower(rearRightPower);

        runtime.reset();

        while (opModeIsActive() && runtime.milliseconds() < (timeSeconds*1000)) { // timeout of 15 seconds
            // wait until time is elapsed
            telemetry.addData("Elapsed", runtime.milliseconds() / 1000);
            telemetry.addData("Remaining", (timeSeconds*1000 - runtime.milliseconds())/1000);
        }

        stopMotors();
    }


    public void autoMove(double frontLeftSeconds, double frontRightSeconds, double rearLeftSeconds, double rearRightSeconds) {
        double power = 0.3;

        // reset countdown timers
        runtime.reset();


        // power motors
        if(frontLeftSeconds > 0){
            frontLeftDrive.setPower(power);
        }

        if(frontRightSeconds > 0){
            frontRightDrive.setPower(power);
        }

        if(rearLeftSeconds > 0){
            rearLeftDrive.setPower(power);
        }

        if(rearRightSeconds > 0){
            rearRightDrive.setPower(power);
        }

        runtime.reset();

        while (opModeIsActive() && runtime.seconds() < 15) { // timeout of 15 seconds
            // turn off each motor if targetTime complete
        }
    }

    @Override
    public void runOpMode() {

        // Initialize the hardware variables.
        frontLeftDrive = hardwareMap.get(DcMotor.class, "motor0");
        frontRightDrive = hardwareMap.get(DcMotor.class, "motor1");
        rearLeftDrive = hardwareMap.get(DcMotor.class, "motor2");
        rearRightDrive = hardwareMap.get(DcMotor.class, "motor3");

        // Initialize the motors
        stopMotors();

        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) { // || runtime.seconds() >= 30) {

            // Move forward 10 inches
            autoMove2(10, 0.25, 0.25, 0.25, 0.25);

            // Rotate 180
            autoMove2(5, 0.25, 0.25, -0.25, -0.25);
        }

    }
}
