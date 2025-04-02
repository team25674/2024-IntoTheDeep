package org.firstinspires.ftc.teamcode.lib;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.Map;

public class TelemetryManager {
    private final Telemetry telemetry;
    public Map<String, String> telemetryMap;

    public TelemetryManager(Telemetry telemetry) {
        this.telemetry = telemetry;

        this.telemetryMap = new HashMap<>();
    }
}