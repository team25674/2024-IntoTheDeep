package org.firstinspires.ftc.teamcode.lib;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.Map;

public class TelemetryManager {
    public static String CLAW_SERVO_POS = "Claw Pos";

    private final Telemetry telemetry;
    public Map<String, String> telemetryMap;

    public TelemetryManager(Telemetry telemetry) {
        this.telemetry = telemetry;

        this.telemetryMap = new HashMap<>();
    }

    public void addTelemetry(String name, String value){
        telemetryMap.put(name, value);
    }
}