package org.firstinspires.ftc.teamcode.tutorials;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TutorialAutoMove", group = "Tutorials")
public class TutorialAutoMoveEncoders extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private DcMotor frontLeftDrive = null;
    private DcMotor rearLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor rearRightDrive = null;

    private int motorCountsPerRev = 100;
    private double motorRadiusInches = 10;
    private double motorCountsPerInch = motorCountsPerRev / ( 2* Math.PI * motorRadiusInches );

    private ElapsedTime runtime = new ElapsedTime();

    private int distanceToTicks(double inches) {
        return (int) (motorCountsPerInch * inches);
    }

    private void resetMotors() {
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setTargetPositions(0,0, 0, 0);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void setTargetPositions(double frontLeftInches, double frontRightInches, double rearLeftInches, double rearRightInches) {
        frontLeftDrive.setTargetPosition(distanceToTicks(frontLeftInches));
        frontRightDrive.setTargetPosition(distanceToTicks(frontRightInches));
        rearLeftDrive.setTargetPosition(distanceToTicks(rearLeftInches));
        rearRightDrive.setTargetPosition(distanceToTicks(rearRightInches));
    }

    private void runMotors(double speed){
        frontLeftDrive.setPower(speed);
        frontRightDrive.setPower(speed);
        rearLeftDrive.setPower(speed);
        rearRightDrive.setPower(speed);

        while (opModeIsActive() &&
                (frontLeftDrive.isBusy() || frontRightDrive.isBusy() ||
                        rearLeftDrive.isBusy() || rearRightDrive.isBusy())) {
            // do nothing, or add telemetry
        }
    }

    public void autoMove(int frontLeftInches, int frontRightInches, int leftRearInches, int rightRearInches) {
        setTargetPositions(
                distanceToTicks(frontLeftInches),
                distanceToTicks(frontRightInches),
                distanceToTicks(leftRearInches),
                distanceToTicks(rightRearInches)
        );

        runMotors(0.3);
    }

    @Override
    public void runOpMode() {

        // Initialize the hardware variables.
        frontLeftDrive = hardwareMap.get(DcMotor.class, "motor0");
        frontRightDrive = hardwareMap.get(DcMotor.class, "motor1");
        rearLeftDrive = hardwareMap.get(DcMotor.class, "motor2");
        rearRightDrive = hardwareMap.get(DcMotor.class, "motor3");

        // Initialize the motors
        resetMotors();


        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) { // || runtime.seconds() >= 30) {

            // Move forward 10 inches
            autoMove(10, 10, 10, 10);

            // Rotate 180
            autoMove(10, 10, 10, 10);
        }

    }
}
