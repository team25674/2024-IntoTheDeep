package org.firstinspires.ftc.teamcode.lib;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.lib.mechanisms.LinearSlide;

public class Robot {

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private Servo clawServo;
    private Servo rotateServo;
    private Claw claw;
    private LinearSlide verticalLinearSlide = null;



    private LinearOpMode opMode = null;




    public Robot(LinearOpMode opMode, DcMotor rightBackDrive, DcMotor rightFrontDrive, DcMotor leftBackDrive, DcMotor leftFrontDrive) {

        this.opMode = opMode;

        this.rightBackDrive = rightBackDrive;
        this.rightFrontDrive = rightFrontDrive;
        this.leftBackDrive = leftBackDrive;
        this.leftFrontDrive = leftFrontDrive;

        DcMotor verticalLinearSlideMotor = opMode.hardwareMap.get(DcMotor.class, "vlsMotor");
        verticalLinearSlide = new LinearSlide(verticalLinearSlideMotor, LinearSlide.POS_UPPER_BASKET_INCHES, null);
        rotateServo = opMode.hardwareMap.get(Servo.class, "rotateServo");
        clawServo = opMode.hardwareMap.get(Servo.class, "clawServo");
        claw = new Claw(clawServo, rotateServo);

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
    public void autoDriveY(double speed, double rfTime, double lfTime, double rbTime, double lbTime){

        leftFrontDrive.setPower(speed);
        rightFrontDrive.setPower(speed);
        leftBackDrive.setPower(speed);
        rightBackDrive.setPower(speed);

        checkForStoppage(rfTime, lfTime, rbTime, lbTime);
        }
    public void autoDriveX(double speed, double rfTime, double lfTime, double rbTime, double lbTime){


        //Right = positive speed
        leftFrontDrive.setPower(speed);
        rightFrontDrive.setPower(-speed);
        leftBackDrive.setPower(-speed);
        rightBackDrive.setPower(speed);
        checkForStoppage(rfTime, lfTime, rbTime, lbTime);
    }
    public void autoDriveRot(double speed, double rfTime, double lfTime, double rbTime, double lbTime){
        //Clockwise = positive speed
        leftFrontDrive.setPower(speed);
        rightFrontDrive.setPower(-speed);
        leftBackDrive.setPower(speed);
        rightBackDrive.setPower(-speed);
        checkForStoppage(rfTime, lfTime, rbTime, lbTime);
    }
    public void checkForStoppage(double rfTime, double lfTime, double rbTime, double lbTime){
        ElapsedTime timer = new ElapsedTime();

        int stoppedMotorNum = 0;

        while(opMode.opModeIsActive() && stoppedMotorNum != 4){
            //Check for stoppage
            if(timer.seconds() >= rfTime && rightFrontDrive.getPower() != 0){
                rightFrontDrive.setPower(0);
                stoppedMotorNum ++;
            }

            if(timer.seconds() >= lfTime && leftFrontDrive.getPower() != 0){
                leftFrontDrive.setPower(0);
                stoppedMotorNum ++;
            }

            if(timer.seconds() >= rbTime && rightBackDrive.getPower() != 0){
                rightBackDrive.setPower(0);
                stoppedMotorNum ++;
            }
            if(timer.seconds() >= lbTime && leftBackDrive.getPower() != 0) {
                 leftBackDrive.setPower(0);
                stoppedMotorNum ++;
            }
        }
    }
    //Maybe change speed to be configurable
    // IMPORTANT: If you are planning to open the claw, do it right after calling the autoUpBasket method.
    public void autoHighBasket(){
        ElapsedTime timer = new ElapsedTime();
        verticalLinearSlide.goToPosition(LinearSlide.POS_UPPER_BASKET_INCHES);
        while(opMode.opModeIsActive() && !verticalLinearSlide.motor.isBusy()){

        }
    }
    public void autoZeroLinearSlide(){
        ElapsedTime timer = new ElapsedTime();
        verticalLinearSlide.goToPosition(0);
        while(opMode.opModeIsActive() && !verticalLinearSlide.motor.isBusy()){

        }
    }
    //True = Open, False = close
    public void autoClawGrab(boolean grabState){
        if(grabState){
            claw.open();
        }else{
            claw.close();
        }
        sleep(250);
    }

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch(InterruptedException e) {
            // oh no!
        }
    }
}