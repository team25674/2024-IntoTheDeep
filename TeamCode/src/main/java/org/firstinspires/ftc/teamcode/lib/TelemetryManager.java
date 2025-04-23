package org.firstinspires.ftc.teamcode.lib;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelemetryManager {
    public static String CLAW_SERVO_POS = "Claw Pos";
    public static String CLAW_SERVO_MOUTH = "Claw Mouth"

    private final Telemetry telemetry;
    public Map<String, String> telemetryMap;

    public TelemetryManager(Telemetry telemetry) {
        this.telemetry = telemetry;
        this.telemetryMap = new HashMap<>();
    }

    public void addTelemetry(String name, String value){
        telemetryMap.put(name, value);

    }
    public void passToTelemerty(List<String>list) {
       // clear out old telemetry.
        for (String key : list) {
            // loop through each key in the provided list
             // get the value for the key
           String value = telemetryMap.get(key);
           telemetry.addData(key, value);
        }

    }

}