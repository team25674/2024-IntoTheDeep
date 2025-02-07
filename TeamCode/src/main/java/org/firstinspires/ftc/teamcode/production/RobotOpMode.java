
package org.firstinspires.ftc.teamcode.production;

//(use for later)import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.lib.mechanisms.LinearSlide;
import org.firstinspires.ftc.teamcode.lib.mechanisms.SpyContinuous;

@TeleOp(name = "RobotOpMode", group = "Linear OpMode")
public class RobotOpMode extends LinearOpMode {

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
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
       // telemetry.addData("Status", "Initialized");
      //  telemetry.update();

        DcMotor verticalLinearSlideMotor = hardwareMap.get(DcMotor.class, "vlsMotor");

        DcMotor horizontalLinearSlideMotor = hardwareMap.get(DcMotor.class, "hlsMotor");
        //Vertical linear slide
        verticalLinearSlide = new LinearSlide(verticalLinearSlideMotor, LinearSlide.POS_UPPER_BASKET_INCHES, null);
        //Horizontal linear slide TODO Measure real value of horizontalLinearSlide max position
        horizontalLinearSlide = new LinearSlide(horizontalLinearSlideMotor, 16, telemetry);
        // spy servos
        wheel1 = hardwareMap.get(Servo.class, "wheel1Servo");
        wheel2 = hardwareMap.get(Servo.class, "wheel2Servo");
        upAndDown = hardwareMap.get(Servo.class, "upAndDownServo");
        spy = new SpyContinuous(wheel1, wheel2, upAndDown);
        //claw init
        rotateServo = hardwareMap.get(Servo.class, "rotateServo");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        claw = new Claw(clawServo, rotateServo);



        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double max;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }

            // This is test code:
            //
            // Uncomment the following code to test your motor directions.
            // Each button should make the corresponding motor run FORWARD.
            //   1) First get all the motors to take to correct positions on the robot
            //      by adjusting your Robot Configuration if necessary.
            //   2) Then make sure they run in the correct direction by modifying the
            //      the setDirection() calls above.
            // Once the correct motors move in the correct direction re-comment this code.

            /*
            leftFrontPower  = gamepad1.x ? 1.0 : 0.0;  // X gamepad
            leftBackPower   = gamepad1.a ? 1.0 : 0.0;  // A gamepad
            rightFrontPower = gamepad1.y ? 1.0 : 0.0;  // Y gamepad
            rightBackPower  = gamepad1.b ? 1.0 : 0.0;  // B gamepad
            */

            // Send calculated power to wheels
            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            leftBackDrive.setPower(leftBackPower);
            rightBackDrive.setPower(rightBackPower);

            // Show the elapsed game time and wheel power.
          //  telemetry.addData("Status", "Run Time: " + runtime.toString());
          //  telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
          //  telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
          //  telemetry.update();


            //------------------------------------------------------
            // LinearSlide Code
            //------------------------------------------------------

            //Button detection

            if (gamepad2.y) {
                verticalLinearSlide.goToPosition(LinearSlide.POS_UPPER_BASKET_INCHES);
                maintanVPos = true;
            }

            if (gamepad2.b) {
                verticalLinearSlide.goToPosition(LinearSlide.POS_LOWER_BASKET_INCHES);
                maintanVPos = true;
            }

            if (gamepad2.a) {
                verticalLinearSlide.goToPosition(0);
                maintanVPos = false;
            }

            // VERTICAL LINEAR SLIDE
            if (gamepad2.right_stick_y != 0) {
                verticalLinearSlide.extend(gamepad2.right_stick_y);// y -1.0 <> 1.0   servo takes 0-1.0, direction
                maintanVPos = false;
            } else if (!verticalLinearSlide.motor.isBusy() && !maintanVPos) {
                verticalLinearSlide.motor.setPower(0);

            }

            // HORIZONTAL LINEAR SLIDE
            if (gamepad2.left_stick_y != 0) {
                horizontalLinearSlide.extend(gamepad2.left_stick_y);
            } else if (!horizontalLinearSlide.motor.isBusy()) {
                horizontalLinearSlide.motor.setPower(0);
            }

            lastButtonY = gamepad2.y;
            lastButtonB = gamepad2.b;
            lastButtonA = gamepad2.a;

            // Spy controls
            if (gamepad2.left_bumper) {
                spy.down();
                //telemetry.addLine("down detected");
            }
            if (gamepad2.right_bumper) {
                spy.up();
                telemetry.addLine("up detected");

            }
            if (gamepad2.right_trigger > 0) {
                spy.intake(0.5*gamepad2.right_trigger+0.5);
            }
            else if (gamepad2.left_trigger > 0) {
                spy.reject(0.5-0.5*gamepad2.left_trigger);
            } else
             {
                spy.stop();
            }
            // claw controll
            if (gamepad2.dpad_up) {
                claw.up();
            }
            if (gamepad2.dpad_down){
                claw.down();
            }
            if (gamepad2.dpad_right){
                claw.close();
            }
            if (gamepad2.dpad_left){
                claw.open();
            }


//            telemetry.addData("wheel1 position", wheel1.getPosition());
//            telemetry.addData("wheel2 position", wheel2.getPosition());
//            telemetry.addData("upAndDown position", upAndDown.getPosition());
//            telemetry.update();

        }

    }
}
