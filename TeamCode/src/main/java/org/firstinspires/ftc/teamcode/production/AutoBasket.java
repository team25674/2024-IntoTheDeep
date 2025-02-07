package org.firstinspires.ftc.teamcode.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.Robot;
import org.firstinspires.ftc.teamcode.lib.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.lib.mechanisms.LinearSlide;


@Autonomous(name = "AutoBasket", group = "Auto")
public class AutoBasket extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private Servo clawServo;
    private Servo rotateServo;
    private Claw claw;
    private LinearSlide verticalLinearSlide = null;


    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftFrontDrive = hardwareMap.get(DcMotor.class, "motor0");
        leftBackDrive = hardwareMap.get(DcMotor.class, "motor2");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "motor1");
        rightBackDrive = hardwareMap.get(DcMotor.class, "motor3");

        DcMotor verticalLinearSlideMotor = hardwareMap.get(DcMotor.class, "vlsMotor");
        rotateServo = hardwareMap.get(Servo.class, "rotateServo");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        claw = new Claw(clawServo, rotateServo);

        Robot frobot = new Robot(this, rightBackDrive, rightFrontDrive, leftBackDrive, leftFrontDrive);

        verticalLinearSlide = new LinearSlide(verticalLinearSlideMotor, LinearSlide.POS_UPPER_BASKET_INCHES, null);

/*
        frobot.autoClawGrab(true);
        Robot.sleep(3000);
        frobot.autoClawGrab(false);*/


        waitForStart();

        // Movement Test
        //frobot.autoDriveY(0.35, 5, 5, 5, 5);

        //AutoBasket(Not tested yet)
        claw.down();
        frobot.autoClawGrab(false);
        frobot.autoDriveX(-0.2, 0.3, 0.3, 0.3, 0.3);
        frobot.autoDriveY(-.6, 1, 1, 1, 1);
        frobot.autoDriveRot(0.35, 1, 1, 1, 1);
        frobot.autoHighBasket();
        claw.up();
        frobot.autoDriveY(-.2, 0.1, .1, .1, .1);
        frobot.autoClawGrab(true);

        runtime.reset();







        }

    }

