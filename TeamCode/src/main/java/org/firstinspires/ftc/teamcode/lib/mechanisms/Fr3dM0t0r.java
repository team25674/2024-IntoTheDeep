package org.firstinspires.ftc.teamcode.lib.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Fr3dM0t0r {
    DcMotor mcguff1n;

    public Fr3dM0t0r(DcMotor mcqueen){
        mcguff1n=mcqueen;
        mcguff1n.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void mcspinny(double mcspeed){
        mcguff1n.setPower(mcspeed);
    }
}
