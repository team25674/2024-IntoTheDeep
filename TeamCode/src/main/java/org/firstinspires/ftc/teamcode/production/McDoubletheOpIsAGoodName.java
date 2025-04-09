
package org.firstinspires.ftc.teamcode.production;

//(use for later)import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.lib.mechanisms.Fr3dM0t0r;
import org.firstinspires.ftc.teamcode.lib.mechanisms.LinearSlide;
import org.firstinspires.ftc.teamcode.lib.mechanisms.SpyContinuous;

@TeleOp(name = "McRobot", group = "Linear OpMode")
public class McDoubletheOpIsAGoodName extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private LinearSlide verticalLinearSlide = null;
    private LinearSlide horizontalLinearSlide = null;
    private Servo wheel1;
    private Servo wheel2;
    private Servo upAndDown;
    private SpyContinuous spy;
    private Servo clawServo;
    private Servo rotateServo;
    private Claw claw;

    //buton states
    boolean lastButtonY = false;
    boolean lastButtonB = false;
    boolean lastButtonA = false;

    boolean maintanVPos = false;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftFrontDrive = hardwareMap.get(DcMotor.class, "motor0");
        leftBackDrive = hardwareMap.get(DcMotor.class, "motor2");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "motor1");
        rightBackDrive = hardwareMap.get(DcMotor.class, "motor3");
        //

        waitForStart();
        runtime.reset();
        Fr3dM0t0r bigmcmac=new Fr3dM0t0r(leftFrontDrive);
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double mchermes= -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            bigmcmac.mcspinny(mchermes);


            lastButtonY = gamepad2.y;


        }

    }
}
