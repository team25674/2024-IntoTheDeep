package org.firstinspires.ftc.teamcode.lib.mechanisms;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class LinearSlide {
    static final double COUNTS_PER_INCH = 112.8181;
    public static int POS_LOWER_BASKET_INCHES = 20;
    public static int POS_UPPER_BASKET_INCHES = 30;
    Telemetry telemetry1;
    public DcMotorEx motor;

    int max;

    int lastTarget = 0;
    boolean extend = false;

    boolean maintain = false;
    int maintainTargetPos = 0;

    int goToCount = 0;


    //Could maybe add "COUNTS_PER_INCH" as a parameter for the linear slide constructor class,
    // because linear slides could have different gear ratios and such and stuff
    public LinearSlide(DcMotorEx motor, int max, Telemetry telemetry) {
        this.motor = motor;
        this.max = max;
        //Brake
        motor.setZeroPowerBehavior(BRAKE);

        motor.setDirection(DcMotor.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motor.setZeroPowerBehavior(BRAKE);
//        this.telemetry = telemetry;
    }

    public void goToPosition(int positionInches) {
        // Determine new target position, and pass to motor controller
        int newTarget = (int) (positionInches * COUNTS_PER_INCH) * -1;
        motor.setTargetPosition(newTarget);

        // Turn On RUN_TO_POSITION
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start motion
        motor.setPower(0.5);

        maintainTargetPos = newTarget;
        maintain = true;
        lastTarget = newTarget;
        extend = false;
        goToCount++;
    }

    public void maintain() {
        if(maintain) {
            motor.setTargetPosition(maintainTargetPos);

            // Turn On RUN_TO_POSITION
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Start motion
            motor.setPower(0.5);
        }
    }

    public void goToZero() {
        goToPosition(0);
    }

    public void extend(double speed) {
        maintain = false;
        extend = true;
        //encoder was going in the wrong direction(fixedMotorPos)
        double fixedMotorPos = motor.getCurrentPosition() * -1;
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(BRAKE);
      // else function thing is if the slide is above max
       if(((fixedMotorPos / COUNTS_PER_INCH) <= max && ((fixedMotorPos / COUNTS_PER_INCH) >= 0))) {
           // INSIDE LIMIT
           // If max-0.5 is higher than the current pos, and the position is more than 0
           motor.setPower(speed);
//           if(telemetry != null) {
//               telemetry.addData( "Status: ", "Within limit. Have a nice day!");
//           }
       } else {
           // OUT OF LIMIT
           if(speed < 0 && fixedMotorPos < 0){
               motor.setPower(speed);
           }
           else if(speed > 0 && fixedMotorPos > max){
               motor.setPower(speed);
           }
           else{
               motor.setPower(0);
           }
//           if(telemetry != null) {
//               telemetry.addData("Status: ", "OUT OF LIMIT");
//               telemetry.addData("speed:", speed);
//               telemetry.addData("fixed_motor_potition:", fixedMotorPos);
//           }
       }

//        if(telemetry != null) {
//            telemetry.addData("Approx. position fixed encoder: ", fixedMotorPos);
//            telemetry.addData("Approx. position inches: ", (fixedMotorPos / COUNTS_PER_INCH));
//            telemetry.update();
//        }
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("Pos", motor.getCurrentPosition());
        telemetry.addData("Target", lastTarget);
        telemetry.addData("MTarget", motor.getTargetPosition());
        telemetry.addData("Extend?", extend);
        telemetry.addData("GoToCount", goToCount);
        telemetry.addData("Busy", motor.isBusy());
        telemetry.addData("I", motor.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.update();
    }
}
