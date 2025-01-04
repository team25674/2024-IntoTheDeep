package org.firstinspires.ftc.teamcode.lib;

import org.firstinspires.ftc.teamcode.lib.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.lib.mechanisms.LinearSlide;
import org.firstinspires.ftc.teamcode.lib.mechanisms.SpyContinuous;

public class TelemtryController {
    LogMode logMode;

    Claw claw;
    SpyContinuous spycontinuous;
    LinearSlide horizontallinearslide;
    LinearSlide veticallinearslide;

    public TelemtryController(Claw claw, LinearSlide horizontallinearslide, SpyContinuous spycontinuous, LinearSlide veticallinearslide) {
        this.claw = claw;
        this.horizontallinearslide = horizontallinearslide;
        this.spycontinuous = spycontinuous;
        this.veticallinearslide = veticallinearslide;

        this.logMode = LogMode.CLAW;
    }
}
enum LogMode{
    CLAW,
    SPY,
    HLS,
    VLS,

}