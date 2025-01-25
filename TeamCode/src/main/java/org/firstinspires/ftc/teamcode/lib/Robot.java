package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Robot {

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    private LinearOpMode opMode = null;


    public Robot(LinearOpMode opMode, DcMotor rightBackDrive, DcMotor rightFrontDrive, DcMotor leftBackDrive, DcMotor leftFrontDrive) {

        this.opMode = opMode;

        this.rightBackDrive = rightBackDrive;
        this.rightFrontDrive = rightFrontDrive;
        this.leftBackDrive = leftBackDrive;
        this.leftFrontDrive = leftFrontDrive;

        // set motor directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    // Method with times for right front drive, left back drive, right back drive, left back drive
    public void autoDriveForward(double speed, double rfTime, double lfTime, double rbTime, double lbTime){

        ElapsedTime timer = new ElapsedTime();

        int stoppedMotorNum = 0;

        leftFrontDrive.setPower(speed);
        rightFrontDrive.setPower(speed);
        leftBackDrive.setPower(speed);
        rightBackDrive.setPower(speed);

        while(opMode.opModeIsActive() && stoppedMotorNum != 4){
            //Check for stoppage
            if(timer.seconds() >= rfTime && rightFrontDrive.getPower() != 0){
                rightFrontDrive.setPower(0);
                stoppedMotorNum ++;
            }

            if(timer.seconds() >= lfTime && leftFrontDrive.getPower() != 0){
                leftFrontDrive.setPower(0);
            }

            if(timer.seconds() >= rbTime && rightBackDrive.getPower() != 0){
                rightBackDrive.setPower(0);
            }

            if(timer.seconds() >= lbTime && leftBackDrive.getPower() != 0){
                leftBackDrive.setPower(0);

            }

        }

    }
}
