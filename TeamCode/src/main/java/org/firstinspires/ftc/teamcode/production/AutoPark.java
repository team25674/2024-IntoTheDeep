package org.firstinspires.ftc.teamcode.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.Robot;


@Autonomous(name = "Autopark", group = "Auto")
public class AutoPark extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;


    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftFrontDrive = hardwareMap.get(DcMotor.class, "motor0");
        leftBackDrive = hardwareMap.get(DcMotor.class, "motor2");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "motor1");
        rightBackDrive = hardwareMap.get(DcMotor.class, "motor3");

        DcMotor verticalLinearSlideMotor = hardwareMap.get(DcMotor.class, "vlsMotor");
        DcMotor horizontalLinearSlideMotor = hardwareMap.get(DcMotor.class, "hlsMotor");

        Robot frobot = new Robot(this, rightBackDrive, rightFrontDrive, leftBackDrive, leftFrontDrive);





        waitForStart();
        frobot.autoDriveY(0.35, 5, 5, 5, 5);
        frobot.autoDriveY(-0.35, 5, 5, 5, 5);
        frobot.autoDriveX(0.35, 5, 5, 5, 5);
        frobot.autoDriveX(-0.35, 5, 5, 5, 5);
        frobot.autoDriveRot(-0.35, 10, 10, 10, 10);

        // Actall AutoPark code
//        frobot.autoDriveY(-0.2, 0.1, 0.1, 0.1, 0.1);
//        frobot.autoDriveY(0.2, 3, 3, 3, 3);
        runtime.reset();







        }

    }

