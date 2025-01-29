package org.firstinspires.ftc.teamcode.lib.mechanisms;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LinearSlide {
    static final double COUNTS_PER_INCH = 112.8181;
    public static int POS_LOWER_BASKET_INCHES = 30;
    public static int POS_UPPER_BASKET_INCHES = 35;
    String name;
    public DcMotor motor;
    int max;


    //Could maybe add "COUNTS_PER_INCH" as a parameter for the linear slide constructor class,
    // because linear slides could have different gear ratios and such and stuff
    public LinearSlide(String name, DcMotor motor, int max) {
        this.motor = motor;
        this.max = max;
        //Brake
        motor.setZeroPowerBehavior(BRAKE);

        motor.setDirection(DcMotor.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.name = name;
    }

    public void goToPosition(int positionInches) {
        // Determine new target position, and pass to motor controller
        int newTarget = (int) (positionInches * COUNTS_PER_INCH);
        motor.setTargetPosition(newTarget);

        // Turn On RUN_TO_POSITION
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start motion
        motor.setPower(1);
    }

    public void goToZero() {
        goToPosition(0);
    }

    public void extend(double speed) {
        //encoder was going in the wrong direction(fixedMotorPos)
        double fixedMotorPos = motor.getCurrentPosition() * -1;
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      // else function thing is if the slide is above max
       if(((fixedMotorPos / COUNTS_PER_INCH) <= max && ((fixedMotorPos / COUNTS_PER_INCH) >= 0))) {
           // INSIDE LIMIT
           // If max-0.5 is higher than the current pos, and the position is more than 0
           motor.setPower(speed);

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

       }

    }
    public void telemetry(Telemetry telemetry){
        telemetry.addData("name", name);
        telemetry.addData("Target Pos", motor.getTargetPosition());
        telemetry.addData("Current Pos", motor.getCurrentPosition());
        telemetry.addData("Motor Busyness", motor.isBusy());
        telemetry.addData("Motor Power", motor.getPower());
        // TODO add isWithinRange
        telemetry.update();
    }
}
