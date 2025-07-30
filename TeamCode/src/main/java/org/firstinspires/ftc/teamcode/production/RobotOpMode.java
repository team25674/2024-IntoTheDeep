
package org.firstinspires.ftc.teamcode.production;

//(use for later)import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.lib.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.lib.mechanisms.LinearSlide;
import org.firstinspires.ftc.teamcode.lib.mechanisms.SpyContinuous;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name = "RobotOpMode", group = "Linear OpMode")
public class RobotOpMode extends LinearOpMode {

    public static final double CAMERA_ROLL = -90;
    final double DESIRED_DISTANCE = 12.0;

    // Declare OpMode members for each of the 4 motors.
    final double SPEED_GAIN = 0.02;   //  Forward Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
    final double STRAFE_GAIN = 0.015;   //  Strafe Speed Control "Gain".  eg: Ramp up to 25% power at a 25 degree Yaw error.   (0.25 / 25.0)
    final double TURN_GAIN = 0.01;   //  Turn Control "Gain".  eg: Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)

    final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
    final double MAX_AUTO_STRAFE = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
    final double MAX_AUTO_TURN = 0.3;

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private static final int DESIRED_TAG_ID = -1;     // Choose the tag you want to approach or set to -1 for ANY tag.
    private static boolean USE_WEBCAM = true;
    private AprilTagDetection chosenTag = null;

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


    private void intiAprilTag() {

        YawPitchRollAngles cameraOrientation;

        cameraOrientation = new YawPitchRollAngles( AngleUnit.DEGREES, 0, 0, CAMERA_ROLL, 0);

        // Create proccesor
        aprilTag = new AprilTagProcessor.Builder().build();

        VisionPortal.Builder visionPortalBuilder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            visionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam jimmy"));
        } else {
            visionPortalBuilder.setCamera(BuiltinCameraDirection.BACK);
        }

        visionPortalBuilder.addProcessor(aprilTag);


        visionPortal = visionPortalBuilder.build();
    }

    //button states
    boolean lastButtonY = false;
    boolean lastButtonB = false;
    boolean lastButtonA = false;

    boolean maintanVPos = false;

    @Override
    public void runOpMode() {

        boolean targetFound = false;    // Set to true when an AprilTag target is detected
        double drive = 0;        // Desired forward power/speed (-1 to +1)
        double strafe = 0;        // Desired strafe power/speed (-1 to +1)
        double turn = 0;

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
        this.intiAprilTag(); //weeeeeeeeeeeee!


        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            targetFound = false;
            chosenTag = null;
            // Step through the list of detected tags and look for a matching tag
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            for (AprilTagDetection detection : currentDetections) {
                // Look to see if we have size info on this tag.
                if (detection.metadata != null) {
                    //  Check to see if we want to track towards this tag.
                    if ((DESIRED_TAG_ID < 0) || (detection.id == DESIRED_TAG_ID)) {
                        // Yes, we want to use this tag.
                        targetFound = true;
                        chosenTag = detection;
                        break;  // don't look any further.
                    } else {
                        // This tag is in the library, but we do not want to track it right now.
                        telemetry.addData("Skipping", "Tag ID %d is not desired", detection.id);
                    }
                } else {
                    // This tag is NOT in the library, so we don't have enough information to track to it.
                    telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
                }
            }

            if (gamepad1.left_bumper && targetFound) {

                // Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
                double rangeError = (chosenTag.ftcPose.range - DESIRED_DISTANCE);
                double headingError = chosenTag.ftcPose.bearing;
                double yawError = chosenTag.ftcPose.yaw;

                // Use the speed and turn "gains" to calculate how we want the robot to move.
                drive = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
                turn = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN);
                strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);

                telemetry.addData("Auto", "Drive %5.2f, Strafe %5.2f, Turn %5.2f ", drive, strafe, turn);
            } else {

                // drive using manual POV Joystick mode.  Slow things down to make the robot more controlable.
                drive = -gamepad1.left_stick_y / 2.0;  // Reduce drive rate to 50%.
                strafe = -gamepad1.left_stick_x / 2.0;  // Reduce strafe rate to 50%.
                turn = -gamepad1.right_stick_x / 3.0;  // Reduce turn rate to 33%.
                telemetry.addData("Manual", "Drive %5.2f, Strafe %5.2f, Turn %5.2f ", drive, strafe, turn);
            }
            telemetry.update();

            // Apply desired axes motions to the drivetrain.
            moveRobot(drive, strafe, turn);
            sleep(10);

            double max;


            // Combine the joystick requests for each axis-motion to determine each wheel's power.


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
                spy.intake(0.5 * gamepad2.right_trigger + 0.5);
            } else if (gamepad2.left_trigger > 0) {
                spy.reject(0.5 - 0.5 * gamepad2.left_trigger);
            } else {
                spy.stop();
            }
            // claw controll
            if (gamepad2.dpad_up) {
                claw.up();
            }
            if (gamepad2.dpad_down) {
                claw.down();
            }
            if (gamepad2.dpad_right) {
                claw.close();
            }
            if (gamepad2.dpad_left) {
                claw.open();
            }


//            telemetry.addData("wheel1 position", wheel1.getPosition());
//            telemetry.addData("wheel2 position", wheel2.getPosition());
//            telemetry.addData("upAndDown position", upAndDown.getPosition());
//            telemetry.update();

        }

    }

    public void moveRobot(double x, double y, double yaw) {
        // Calculate wheel powers.
        double leftFrontPower = x - y - yaw;
        double rightFrontPower = x + y + yaw;
        double leftBackPower = x + y - yaw;
        double rightBackPower = x - y + yaw;

        // Normalize wheel powers to be less than 1.0
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Send calculated power to wheels
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
    }
}
